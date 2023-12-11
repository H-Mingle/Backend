package com.hyundai.hmingle.mapper;

import java.util.Optional;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.request.PostRequest;
import com.hyundai.hmingle.mapper.dto.response.PostDetailResponse;

import java.math.BigDecimal;
import java.util.Map;

import com.hyundai.hmingle.controller.dto.response.PostGetResponse;
import com.hyundai.hmingle.domain.post.Post;

public interface PostMapper {

	Long save(PostCreateRequest params);
	
	PostDetailResponse getPostDetail(Long postId);

	void getPostId(Map<String, BigDecimal> map);

	Long removePost(PostRequest params);

	Optional<Post> findById(Long id);

	PostGetResponse getPost(Long postId);
}
