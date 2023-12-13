package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.request.MemberUpdateRequest;
import com.hyundai.hmingle.mapper.dto.response.MemberUpdateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.repository.MemberRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public MemberGetResponse findById(Long memberId) {
		return memberRepository.findWithPostCountByMemberId(memberId);
	}

	public MemberUpdateResponse update(MemberUpdateRequest memberUpdateDto){
		return memberRepository.update(memberUpdateDto);
	}

}
