package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.request.PostCreateRequest;
import com.hyundai.hmingle.controller.dto.request.PostUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.PostGetResponse;
import com.hyundai.hmingle.controller.dto.response.PostListGetResponse;
import com.hyundai.hmingle.controller.dto.response.PostsGetResponse;

import java.util.List;

public interface PostService {
	public Long savePost(PostCreateRequest params, Long memberId);

	public PostGetResponse getPost(Long postId, Long memberId);

	public Long removePost(Long postId, Long memberId);

	public void updatePost(PostUpdateRequest params);

	public PostsGetResponse getPostsByChannel(Long channelId, Integer page, Integer size);
}
