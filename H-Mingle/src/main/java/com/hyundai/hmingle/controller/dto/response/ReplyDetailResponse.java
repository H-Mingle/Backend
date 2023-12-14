package com.hyundai.hmingle.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ReplyDetailResponse {

	private final Long id;
	private final Long memberId;
	private final String nickname;
	private final String content;
	private final int like;
	private final String recent;
	private final Long parentId;
	private final boolean writer;
	private final byte[] image;
}
