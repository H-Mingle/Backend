package com.hyundai.hmingle.controller.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostGetResponse {
	private Long postId;
	private String title;
	private String content;
	private int readCount;
	private String nickname;
	private int heartCount;

}