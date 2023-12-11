package com.hyundai.hmingle.repository;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.mapper.MemberMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRepository {

	private final MemberMapper memberMapper;

	public Member findById(Long memberId) {
		return memberMapper.findById(memberId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 계정 입니다."));
	}
}
