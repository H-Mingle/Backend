package com.hyundai.hmingle.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RepliesRequest {

	private final Long postId;
	private final Long parentId;
}
