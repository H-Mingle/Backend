package com.hyundai.hmingle.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReplyUpdateResponse {

	private final Long postId;
	private final Long replyId;
	private final String content;
}
