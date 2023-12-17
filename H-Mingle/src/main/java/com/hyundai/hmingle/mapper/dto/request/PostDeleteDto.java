package com.hyundai.hmingle.mapper.dto.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostDeleteDto {

	private final Long postId;
	private final Long memberId;
	private LocalDateTime modifiedDate;
}
