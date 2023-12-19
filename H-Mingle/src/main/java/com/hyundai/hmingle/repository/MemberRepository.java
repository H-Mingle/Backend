package com.hyundai.hmingle.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.mapper.MemberMapper;
import com.hyundai.hmingle.mapper.dto.request.ImageUpdateMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.MemberUpdateMapperRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRepository {

	private final MemberMapper memberMapper;

	public Member save(String email, String name, String imageUrl) {
		Optional<Member> member = memberMapper.findByEmail(email);
		if (member.isEmpty()) {
			memberMapper.save(Member.toDomain(email, name, null, imageUrl));
		}
		return member.get();
	}

	public Member findById(Long memberId) {
		Member savedMember = memberMapper.findById(memberId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 계정 입니다."));
		if (savedMember.isRemoved()) {
			throw new RuntimeException("탈퇴한 사용자 입니다.");
		}
		return savedMember;
	}

	public void update(Long memberId, String nickname, String introduction) {
		MemberUpdateMapperRequest memberUpdateMapperRequest = new MemberUpdateMapperRequest(
			memberId, nickname, introduction
		);
		memberMapper.update(memberUpdateMapperRequest);
	}

	public void delete(Long memberId) {
		memberMapper.delete(memberId);
	}

	public int updateImg(ImageUpdateMapperRequest imageUpdateMapperRequest) {
		return memberMapper.updateImg(imageUpdateMapperRequest);
	}
}
