package com.hyundai.hmingle.controller.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyPostsResponse {

	private final boolean hasNext;
	private final List<PostsResponse> post;
}
