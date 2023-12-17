package com.hyundai.hmingle.controller.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberUpdateRequest {

	private Long memberId;
	private String nickname;
	private String introduction;
}
