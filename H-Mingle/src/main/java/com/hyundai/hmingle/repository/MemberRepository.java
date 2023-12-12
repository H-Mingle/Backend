package com.hyundai.hmingle.repository;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.mapper.MemberMapper;
import com.hyundai.hmingle.mapper.PostMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRepository {

	private final MemberMapper memberMapper;
	private final PostMapper postMapper;

	public Member save(String email, String name, String imageUrl) {
		return memberMapper.findByEmail(email)
			.orElseGet(() -> {
				memberMapper.save(Member.toDomain(email, name, null, imageUrl));
				return memberMapper.findByEmail(email).get();
			});
	}

	public Member findById(Long memberId) {
		return memberMapper.findById(memberId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 계정 입니다."));
	}

	public MemberGetResponse findWithPostCountByMemberId(Long memberId) {
		Member savedMember = findById(memberId);
		int postCount = postMapper.findPostCountByMemberId(savedMember.getId());
		return new MemberGetResponse(
			savedMember.getId(), savedMember.getEmail(), savedMember.getNickname(), savedMember.getIntroduction(),
			postCount
		);
	}
}
