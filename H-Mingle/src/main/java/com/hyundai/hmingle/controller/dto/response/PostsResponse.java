package com.hyundai.hmingle.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostsResponse {

	private final long id;
	private final byte[] image;
}
