package com.hyundai.hmingle.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberGetResponse {

	private final Long memberId;
	private final String email;
	private final String nickname;
	private final String introduction;
	private final int postCount;
}
