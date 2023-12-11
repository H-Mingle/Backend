package com.hyundai.hmingle.mapper;

import java.util.Optional;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.domain.member.Member;

public interface MemberMapper {

	void save(Member member);

	Optional<Member> findByEmail(String email);
	
	MemberGetResponse getMember(Long memberId);
}
