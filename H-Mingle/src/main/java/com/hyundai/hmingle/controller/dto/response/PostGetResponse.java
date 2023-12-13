package com.hyundai.hmingle.controller.dto.response;

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
	private Integer heartCount;
	private String channelName;
	private String createdDate;
	private Long previousId;
	private Long subsequentId;
}