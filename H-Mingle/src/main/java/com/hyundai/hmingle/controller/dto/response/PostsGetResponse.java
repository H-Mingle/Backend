package com.hyundai.hmingle.controller.dto.response;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostsGetResponse {

	private final String channelName;
	private final boolean isNext;
	private final List<PostListGetResponse> posts;
}
