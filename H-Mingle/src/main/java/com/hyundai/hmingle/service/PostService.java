package com.hyundai.hmingle.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.request.PostUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.PostCreateResponse;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;
import com.hyundai.hmingle.controller.dto.response.PostListGetResponse;
import com.hyundai.hmingle.controller.dto.response.PostsGetResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.exception.MingleException;
import com.hyundai.hmingle.mapper.dto.request.ImagesMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostDeleteMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.PostDetailMapperResponse;
import com.hyundai.hmingle.mapper.dto.response.PostMapperResponse;
import com.hyundai.hmingle.mapper.dto.response.PostSidesMapperResponse;
import com.hyundai.hmingle.repository.HeartRepository;
import com.hyundai.hmingle.repository.ImageRepository;
import com.hyundai.hmingle.repository.MemberRepository;
import com.hyundai.hmingle.repository.PostRepository;
import com.hyundai.hmingle.support.ImageConvertor;
import com.hyundai.hmingle.support.ImageUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostService {

	private final PostRepository postRepository;
	private final ImageRepository imageRepository;
	private final HeartRepository heartRepository;
	private final MemberRepository memberRepository;
	private final ImageUtils imageUtils;
	private final ImageService imageService;
	private final ImageConvertor imageConvertor;

	public PostCreateResponse savePost(PostCreateRequest params, Long memberId, List<MultipartFile> uploadImgs) {
		Long postId = postRepository.save(params.getContent(), params.getChannelId(), memberId);
		List<ImageCreateRequest> images = imageUtils.uploadFiles(uploadImgs);
		return imageService.saveFiles(postId, params.getContent(), images);
	}

	public List<byte[]> getFourImages(Long postId) {
		List<String> images = imageService.getFourImages(postId);
		if (images.isEmpty()) {
			throw new MingleException("이미지가 존재하지 않습니다");
		}
		return images.stream()
			.map(imageConvertor::convertPath)
			.collect(Collectors.toUnmodifiableList());
	}

	@Transactional(readOnly = true)
	public PostGetResponse getPost(Long postId, Long memberId) {
		Member savedMember = memberRepository.findById(memberId);
		Long writerId = postRepository.findMemberId(postId);
		Member savedWriter = memberRepository.findById(writerId);
		postRepository.upReadCount(postId);

		PostDetailMapperResponse details = postRepository.getPostDetail(postId, writerId);
		PostSidesMapperResponse postSidesMapperResponse = postRepository.getPostId(details.getPostId());

		boolean liked = heartRepository.findHeart(postId, memberId) != null;

		return new PostGetResponse(
			postId,
			details.getContent(),
			details.getReadCount(),
			details.getNickname(),
			details.getHeartCount(),
			getChannelName(details.getChannelId()),
			details.getCreatedDate(),
			postSidesMapperResponse.getPreviousId(),
			postSidesMapperResponse.getSubsequentId(),
			liked,
			imageUtils.convertUrlToBytes(savedMember.getImageUrl()),
			imageUtils.convertUrlToBytes(savedWriter.getImageUrl())
		);
	}

	public Long removePost(Long postId, Long memberId) {
		PostDeleteMapperRequest params = new PostDeleteMapperRequest(postId, memberId);
		return postRepository.removePost(params);
	}

	public PostCreateResponse updatePost(PostUpdateRequest params, Long postId, List<MultipartFile> uploadImgs) {
		postRepository.updatePost(params);
		List<ImageCreateRequest> images = imageUtils.uploadFiles(uploadImgs);
		imageService.removeImages(postId);
		return imageService.saveFiles(postId, params.getContent(), images);
	}

	@Transactional(readOnly = true)
	public PostsGetResponse getPostsByChannel(Long channelId, Integer requestPage, Integer requestsize) {
		int page = validatePageIsNotNegative(requestPage);
		int size = validateSizeIsNotNegative(requestsize);
		int startRow = calculateStartRow(page, size);

		ImagesMapperRequest nextImage = new ImagesMapperRequest(channelId, startRow + size, size);
		boolean isNext = !imageRepository.findByPostId(nextImage).isEmpty();

		String channelName = getChannelName(channelId);

		List<PostListGetResponse> list = new ArrayList<>();
		ImagesMapperRequest request = new ImagesMapperRequest(channelId, startRow, size);

		if (!imageRepository.findByPostId(request).isEmpty()) {
			List<PostMapperResponse> images = imageRepository.findByPostId(request);
			for (PostMapperResponse image : images) {
				PostListGetResponse response = new PostListGetResponse();
				try (InputStream imageStream = new FileInputStream(image.getImage())) {
					byte[] imageByteArray = IOUtils.toByteArray(imageStream);
					response.setPostId(image.getPostId());
					response.setImage(imageByteArray);
				} catch (IOException e) {
					e.printStackTrace();
				}

				list.add(response);
			}
		}

		return new PostsGetResponse(channelName, isNext, list);
	}

	private int calculateStartRow(int page, int size) {
		return (page - 1) * size;
	}

	private int validatePageIsNotNegative(Integer page) {
		if (page == null) {
			throw new MingleException("page를 입력해주세요.");
		}
		if (page <= 0) {
			throw new MingleException("page는 1보다 커야합니다.");
		}
		return page;
	}

	private int validateSizeIsNotNegative(Integer size) {
		if (size == null) {
			throw new MingleException("size를 입력해주세요.");
		}
		if (size <= 0) {
			throw new MingleException("size는 1보다 커야합니다.");
		}
		return size;
	}

	public String getChannelName(Long channelId) {
		String channelName = "";
		if (channelId == 1) {
			channelName = "더현대 서울";
		} else if (channelId == 2) {
			channelName = "더현대 대구";
		} else if (channelId == 3) {
			channelName = "현대백화점 압구정본점";
		} else if (channelId == 4) {
			channelName = "현대백화점 천호점";
		} else {
			channelName = "현대백화점 신촌점";
		}
		return channelName;
	}
}
