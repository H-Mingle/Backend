package com.hyundai.hmingle.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCreateResponse {
	private Long postId;
	private String title;
	private String content;
}
