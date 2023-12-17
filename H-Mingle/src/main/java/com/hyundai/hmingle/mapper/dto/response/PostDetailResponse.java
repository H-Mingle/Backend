package com.hyundai.hmingle.mapper.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostDetailResponse {
	private Long postId;
	private String content;
	private int readCount;
	private String nickname;
	private Long channelId;
	private String createdDate;
	private Integer heartCount;
	private Long liked;
}