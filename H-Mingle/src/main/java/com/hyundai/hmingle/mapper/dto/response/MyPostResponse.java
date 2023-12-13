package com.hyundai.hmingle.mapper.dto.response;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MyPostResponse {

	private final long id;
	private final String imageUrl;
}
