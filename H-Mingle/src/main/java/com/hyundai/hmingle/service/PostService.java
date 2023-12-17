package com.hyundai.hmingle.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.hyundai.hmingle.mapper.dto.request.ImagesMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.PostDeleteMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.PostDetailMapperResponse;
import com.hyundai.hmingle.mapper.dto.response.PostMapperResponse;
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
			throw new RuntimeException("이미지가 존재하지 않습니다");
		}
		return images.stream()
			.map(imageConvertor::convertPath)
			.collect(Collectors.toUnmodifiableList());
	}

	@Transactional(readOnly = true)
	public PostGetResponse getPost(Long postId, Long memberId) {
		postRepository.upReadCount(postId);

		Long writerId = postRepository.findMemberId(postId);

		PostDetailMapperResponse details = postRepository.getPostDetail(postId, writerId);
		BigDecimal id = BigDecimal.valueOf(postId);

		int heartCount = 0;
		if (details.getHeartCount() != null)
			heartCount = details.getHeartCount();

		Map<String, BigDecimal> parameterMap = new HashMap<>();
		parameterMap.put("postId", id);
		parameterMap.put("previousId", null);
		parameterMap.put("subsequentId", null);
		postRepository.getPostId(parameterMap);

		Long previousId = convertToLong(parameterMap.get("previousId"));
		Long subsequentId = convertToLong(parameterMap.get("subsequentId"));

		Long channelId = details.getChannelId();
		String channelName = getChannelName(channelId);

		boolean liked = false;
		if (heartRepository.findHeart(postId, memberId) != null)
			liked = true;

		byte[] writerImg = convertByteArr(writerId);
		byte[] memberImg = convertByteArr(memberId);
		PostGetResponse response = new PostGetResponse(postId,
			details.getContent(),
			details.getReadCount(),
			details.getNickname(),
			heartCount,
			channelName,
			details.getCreatedDate(),
			previousId,
			subsequentId,
			liked,
			memberImg,
			writerImg);
		return response;
	}

	public byte[] convertByteArr(long id) {
		Member member = memberRepository.findById(id);
		byte[] memberImg = null;
		try (InputStream imageStream = new FileInputStream(member.getImageUrl())) {
			memberImg = IOUtils.toByteArray(imageStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return memberImg;
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

	private Long convertToLong(BigDecimal value) {
		if (value == null) {
			return null;
		}
		return value.longValue();
	}

	@Transactional(readOnly = true)
	public PostsGetResponse getPostsByChannel(Long channelId, Integer requestPage, Integer requestsize) {
		int page = validatePageIsNotNegative(requestPage);
		int size = validateSizeIsNotNegative(requestsize);
		int startRow = calculateStartRow(page, size);

		ImagesMapperRequest nextImage = new ImagesMapperRequest(channelId, startRow + size, size);
		boolean isNext = false;
		if (!imageRepository.findByPostId(nextImage).isEmpty()) {
			isNext = true;
		}

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
			throw new RuntimeException("page를 입력해주세요.");
		}
		if (page <= 0) {
			throw new RuntimeException("page는 1보다 커야합니다.");
		}
		return page;
	}

	private int validateSizeIsNotNegative(Integer size) {
		if (size == null) {
			throw new RuntimeException("size를 입력해주세요.");
		}
		if (size <= 0) {
			throw new RuntimeException("size는 1보다 커야합니다.");
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
