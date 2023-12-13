package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.controller.dto.request.MemberUpdateRequest;
import com.hyundai.hmingle.mapper.dto.response.MemberUpdateResponse;

public interface MemberService {

	MemberGetResponse findById(Long memberId);

	MemberUpdateResponse update(MemberUpdateRequest memberUpdateDto);
}
