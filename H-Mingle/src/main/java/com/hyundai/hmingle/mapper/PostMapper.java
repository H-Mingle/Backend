package com.hyundai.hmingle.mapper;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.mapper.dto.response.PostDetailResponse;
import com.hyundai.hmingle.mapper.dto.response.PostIdResponse;

import java.math.BigDecimal;
import java.util.Map;


public interface PostMapper {

	public Long save(PostCreateRequest params);
	
	public PostDetailResponse getPostDetail(Long postId);

	public void getPostId(Map<String, BigDecimal> map);

	public Long findById(Long postId);

}
