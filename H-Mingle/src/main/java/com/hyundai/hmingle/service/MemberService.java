package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.controller.dto.request.MemberUpdateRequest;
import com.hyundai.hmingle.mapper.dto.response.MemberUpdateResponse;

import java.io.IOException;

public interface MemberService {

	MemberGetResponse findById(Long id, Long memberId) throws IOException;

	MemberUpdateResponse update(MemberUpdateRequest memberUpdateDto);

	void leave(Long memberId);

	void updateFile(Long memberId, ImageCreateRequest img);
}
