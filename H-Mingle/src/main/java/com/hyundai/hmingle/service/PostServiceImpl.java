package com.hyundai.hmingle.service;

import com.hyundai.hmingle.mapper.dto.response.PostDetailResponse;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;

import com.hyundai.hmingle.repository.PostRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PostServiceImpl implements PostService {

	private final PostRepository postRepository;

	public Long savePost(PostCreateRequest params) {
		return postRepository.save(params);
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
			details.getTitle(),
			details.getContent(),
			details.getReadCount(),
			details.getNickname(),
			details.getHeartCount(),
			previousId,
			subsequentId
		);
	}

	private Long convertToLong(BigDecimal value) {
		if (value == null) {
			return null;
		}
		return value.longValue();
	}
}
