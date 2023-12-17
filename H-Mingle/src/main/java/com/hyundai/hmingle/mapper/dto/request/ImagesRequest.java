package com.hyundai.hmingle.mapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ImagesRequest {
	private final Long channelId;
	private final int startRow;
	private final int size;
}
