package com.hyundai.hmingle.controller.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberUpdateResponse {

	private final Long memberId;
	private final String email;
	private final String nickname;
	private final String introduction;
	private final LocalDateTime modifiedDate;
}
