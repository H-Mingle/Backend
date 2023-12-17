package com.hyundai.hmingle.mapper.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageUpdateDto {
	private Long memberId;
	private String image;
}
