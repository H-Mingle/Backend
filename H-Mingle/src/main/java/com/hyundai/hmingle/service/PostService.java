package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;

public interface PostService {
	public Long savePost(PostCreateRequest params, Long memberId);

	public PostGetResponse getPost(Long postId);

	public Long removePost(Long postId, Long memberId);
}
