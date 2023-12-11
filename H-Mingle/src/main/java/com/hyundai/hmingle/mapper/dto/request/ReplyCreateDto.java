package com.hyundai.hmingle.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReplyCreateDto {

	private Long id;
	private final String content;
	private final Long postId;
	private final Long memberId;
	private final Long parentId;
	private final int depth;
}
