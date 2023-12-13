package com.hyundai.hmingle.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MyPostRequest {

	private final Long memberId;
	private final int startRow;
	private final int size;
}
