package com.hyundai.hmingle.mapper.dto.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberUpdateMapperRequest {

	private final Long memberId;
	private final String nickname;
	private final String introduction;
}
