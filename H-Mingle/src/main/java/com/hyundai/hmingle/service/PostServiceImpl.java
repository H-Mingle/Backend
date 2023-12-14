package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.request.PostUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.PostListGetResponse;
import com.hyundai.hmingle.controller.dto.response.PostsGetResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.mapper.dto.request.ImagesRequest;
import com.hyundai.hmingle.mapper.dto.request.PostCreateDto;
import com.hyundai.hmingle.mapper.dto.request.PostDeleteDto;
import com.hyundai.hmingle.mapper.dto.response.PostDetailResponse;

import com.hyundai.hmingle.mapper.dto.response.PostResponse;
import com.hyundai.hmingle.repository.HeartRepository;
import com.hyundai.hmingle.repository.ImageRepository;
import com.hyundai.hmingle.repository.MemberRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;

import com.hyundai.hmingle.repository.PostRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;
	private final ImageRepository imageRepository;
	private final HeartRepository heartRepository;
	private final MemberRepository memberRepository;

	public Long savePost(PostCreateRequest params, Long memberId) {
		PostCreateDto dto = new PostCreateDto(null, params.getContent(), params.getChannelId(), memberId);
		postRepository.save(dto);
		return dto.getPostId();
	}

	@Transactional(readOnly = true)
	public PostGetResponse getPost(Long postId, Long memberId) {
		postRepository.upReadCount(postId);

		PostDetailResponse details = postRepository.getPostDetail(postId, memberId);
		BigDecimal id = BigDecimal.valueOf(postId);

		int heartCount = 0;
		if(details.getHeartCount() != null)
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
		if(heartRepository.findHeart(postId, memberId)!=null)
			liked = true;

		Member member = memberRepository.findById(memberId);
		byte[] imageByteArray = null;
		try (InputStream imageStream = new FileInputStream(member.getImageUrl())) {
			imageByteArray = IOUtils.toByteArray(imageStream);
		}  catch (IOException e) {
			e.printStackTrace();
		}
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
				                                       imageByteArray);
		return response;
	}

	public Long removePost(Long postId, Long memberId){
		PostDeleteDto params = new PostDeleteDto(postId, memberId);
		return postRepository.removePost(params);
	}

	public void updatePost(PostUpdateRequest params){
		postRepository.updatePost(params);
	}


	private Long convertToLong(BigDecimal value) {
		if (value == null) {
			return null;
		}
		return value.longValue();
	}


	@Transactional(readOnly = true)
	public PostsGetResponse getPostsByChannel(Long channelId, Integer requestPage, Integer requestsize){
		int page = validatePageIsNotNegative(requestPage);
		int size = validateSizeIsNotNegative(requestsize);
		int startRow = calculateStartRow(page, size);

		ImagesRequest nextImage = new ImagesRequest(channelId, startRow+size, size);
		boolean isNext = false;
		if(!imageRepository.findByPostId(nextImage).isEmpty()){
			isNext = true;
		}

		String channelName = getChannelName(channelId);

		List<PostListGetResponse> list = new ArrayList<>();
		ImagesRequest request = new ImagesRequest(channelId, startRow, size);

		if(!imageRepository.findByPostId(request).isEmpty()) {
			List<PostResponse> images = imageRepository.findByPostId(request);
			for(PostResponse image:images) {
				PostListGetResponse response = new PostListGetResponse();
				try (InputStream imageStream = new FileInputStream(image.getImage())) {
					byte[] imageByteArray = IOUtils.toByteArray(imageStream);
					response.setPostId(image.getPostId());
					response.setImage(imageByteArray);
                }  catch (IOException e) {
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

	public String getChannelName(Long channelId){
		String channelName = "";
		if(channelId == 1) {
			channelName = "더현대 서울";
		} else if(channelId == 2){
			channelName = "더현대 대구";
		} else if(channelId == 3){
			channelName = "현대백화점 압구정본점";
		} else if(channelId == 4){
			channelName = "현대백화점 천호점";
		} else{
			channelName = "현대백화점 신촌점";
		}
		return channelName;
	}
}
