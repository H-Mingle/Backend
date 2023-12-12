package com.hyundai.hmingle.mapper;

import java.util.Optional;

import com.hyundai.hmingle.domain.member.Member;

public interface MemberMapper {

	void save(Member member);

	Optional<Member> findById(Long memberId);

	Optional<Member> findByEmail(String email);
}
