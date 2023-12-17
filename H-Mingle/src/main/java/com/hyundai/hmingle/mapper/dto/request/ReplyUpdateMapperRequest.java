package com.hyundai.hmingle.mapper.dto.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReplyUpdateMapperRequest {

	private final Long id;
	private final String content;
	private final LocalDateTime modifiedDate;
}
