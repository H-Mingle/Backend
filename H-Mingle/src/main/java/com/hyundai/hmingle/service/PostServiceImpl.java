package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.request.PostRequest;
import com.hyundai.hmingle.mapper.dto.response.PostDetailResponse;
import org.springframework.stereotype.Service;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;

import com.hyundai.hmingle.mapper.PostMapper;

import lombok.AllArgsConstructor;
import lombok.extern.java.Log;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Log
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

	private PostMapper mapper;
	
	public Long savePost(PostCreateRequest params) {
		return mapper.save(params);
	}

	public PostGetResponse getPost(Long postId) {
		PostDetailResponse details = mapper.getPostDetail(postId);
		BigDecimal id = BigDecimal.valueOf(postId);

		Map<String, BigDecimal> parameterMap = new HashMap<>();
		parameterMap.put("postId", id);
		parameterMap.put("previousId", null);
		parameterMap.put("subsequentId", null);
		mapper.getPostId(parameterMap);

		Long previousId = convertToLong(parameterMap.get("previousId"));
		Long subsequentId = convertToLong(parameterMap.get("subsequentId"));

		PostGetResponse response = new PostGetResponse(postId,
				                                       details.getTitle(),
													   details.getContent(),
													   details.getReadCount(),
				                                       details.getNickname(),
		                                               details.getHeartCount(),
		                                               previousId,
				                                       subsequentId);
		return response;
	}

	private Long convertToLong(BigDecimal value) {
		if (value == null) {
			return null;
		}
		return value.longValue();
	}

	public Long removePost(PostRequest params){
		return mapper.removePost(params);
	}
}
