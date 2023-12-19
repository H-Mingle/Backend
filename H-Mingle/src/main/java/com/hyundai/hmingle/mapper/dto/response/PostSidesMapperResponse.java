package com.hyundai.hmingle.mapper.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostSidesMapperResponse {

	private final Long previousId;
	private final Long subsequentId;
}
