package com.hyundai.hmingle.controller.dto.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostUpdateRequest {

	private final Long postId;
	private final String content;
	private final LocalDateTime modifiedDate;
}
