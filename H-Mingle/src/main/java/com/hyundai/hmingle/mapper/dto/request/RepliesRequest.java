package com.hyundai.hmingle.mapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RepliesRequest {

	private final Long postId;
	private final Long parentId;
	private final int startRow;
	private final int size;
}
