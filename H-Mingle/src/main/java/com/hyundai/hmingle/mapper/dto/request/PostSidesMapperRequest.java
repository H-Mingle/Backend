package com.hyundai.hmingle.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostSidesMapperRequest {

	private final Long postId;
	private Long previousId;
	private Long subsequentId;
}
