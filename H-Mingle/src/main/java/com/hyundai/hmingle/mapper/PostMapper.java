package com.hyundai.hmingle.mapper;

import java.util.Optional;

import com.hyundai.hmingle.controller.dto.request.PostUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.PostCreateResponse;
import com.hyundai.hmingle.mapper.dto.request.PostCreateDto;
import com.hyundai.hmingle.mapper.dto.request.PostDeleteDto;
import com.hyundai.hmingle.mapper.dto.response.PostDetailResponse;

import java.math.BigDecimal;
import java.util.Map;

import com.hyundai.hmingle.controller.dto.response.PostGetResponse;
import com.hyundai.hmingle.domain.post.Post;

public interface PostMapper {

	Long save(PostCreateDto params);
	
	PostDetailResponse getPostDetail(Long postId);

	void getPostId(Map<String, BigDecimal> map);

	Long removePost(PostDeleteDto params);

	Optional<Post> findById(Long id);

	int findPostCountByMemberId(Long memberId);

	PostGetResponse getPost(Long postId);

	void updatePost(PostUpdateRequest params);

}
