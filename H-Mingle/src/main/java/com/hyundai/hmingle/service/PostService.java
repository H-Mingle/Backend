package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.request.PostUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;
import com.hyundai.hmingle.controller.dto.response.PostListGetResponse;

import java.util.List;

public interface PostService {
	public Long savePost(PostCreateRequest params, Long memberId);

	public PostGetResponse getPost(Long postId);

	public Long removePost(Long postId, Long memberId);

	public void updatePost(PostUpdateRequest params);

	public List<PostListGetResponse> getPostsByChannel(Long channelId, int page, int size);
}
