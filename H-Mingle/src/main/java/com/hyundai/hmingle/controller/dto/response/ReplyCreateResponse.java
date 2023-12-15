package com.hyundai.hmingle.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReplyCreateResponse {

	private final Long postId;
	private final Long replyId;
	private final Long memberId;
	private final String nickname;
	private final String content;
	private final String recent;
	private final Long parentId;
	private final byte[] image;
}
