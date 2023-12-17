package com.hyundai.hmingle.mapper.dto.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUpdateMapperRequest {

	private final Long postId;
	private final String content;
	private final LocalDateTime modifiedDate;
}
