package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;

public interface MemberService {

	MemberGetResponse findById(Long memberId);
}
