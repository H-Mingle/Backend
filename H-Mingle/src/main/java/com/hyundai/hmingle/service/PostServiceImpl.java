package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.request.PostUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.PostListGetResponse;
import com.hyundai.hmingle.mapper.dto.request.PostCreateDto;
import com.hyundai.hmingle.mapper.dto.request.PostDeleteDto;
import com.hyundai.hmingle.mapper.dto.response.PostDetailResponse;

import com.hyundai.hmingle.repository.ImageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;

import com.hyundai.hmingle.repository.PostRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

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

	public Long savePost(PostCreateRequest params, Long memberId) {
		PostCreateDto dto = new PostCreateDto(null, params.getContent(), params.getChannelId(), memberId);
		postRepository.save(dto);
		return dto.getPostId();
	}

	public PostGetResponse getPost(Long postId) {
		PostDetailResponse details = postRepository.getPostDetail(postId);
		BigDecimal id = BigDecimal.valueOf(postId);

		Map<String, BigDecimal> parameterMap = new HashMap<>();
		parameterMap.put("postId", id);
		parameterMap.put("previousId", null);
		parameterMap.put("subsequentId", null);
		postRepository.getPostId(parameterMap);

		Long previousId = convertToLong(parameterMap.get("previousId"));
		Long subsequentId = convertToLong(parameterMap.get("subsequentId"));

		return new PostGetResponse(
			postId,
			details.getContent(),
			details.getReadCount(),
			details.getNickname(),
			details.getHeartCount(),
			previousId,
			subsequentId
		);
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


	public List<PostListGetResponse> getPostsByChannel(Long channelId, int page, int size){
		List<PostListGetResponse> list = new ArrayList<>();
		List<Long> postIds = postRepository.findPostByChannelId(channelId);

		for(Long postId : postIds){
			if(!imageRepository.findByPostId(postId).isEmpty()) {
				List<PostListGetResponse> image = imageRepository.findByPostId(postId);
				list.addAll(image);
			}
		}

		return list;
	}
}
