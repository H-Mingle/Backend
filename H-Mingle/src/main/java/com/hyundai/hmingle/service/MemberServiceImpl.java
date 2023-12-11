package com.hyundai.hmingle.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.mapper.MemberMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;

	@Transactional(readOnly = true)
	public MemberGetResponse getMember(Long memberId) {
		return mapper.getMember(memberId);
	}
}
