package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;

public interface MemberService {
	public MemberGetResponse getMember(Long memberId);
}
