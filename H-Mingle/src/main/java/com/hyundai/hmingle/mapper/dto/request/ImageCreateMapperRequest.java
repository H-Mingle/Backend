package com.hyundai.hmingle.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ImageCreateMapperRequest {

	private final String originalName;
	private final String saveName;
	private final long size;
}
