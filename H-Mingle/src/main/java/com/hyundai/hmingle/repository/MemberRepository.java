package com.hyundai.hmingle.repository;

import com.hyundai.hmingle.controller.dto.request.MemberUpdateRequest;
import com.hyundai.hmingle.mapper.dto.response.MemberUpdateResponse;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.mapper.MemberMapper;
import com.hyundai.hmingle.mapper.PostMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

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
		Member savedMember = memberMapper.findById(memberId)
			.orElseThrow(() -> new RuntimeException("존재하지 않는 계정 입니다."));
		if (savedMember.isRemoved()) {
			throw new RuntimeException("탈퇴한 사용자 입니다.");
		}
		return savedMember;
	}

	public MemberGetResponse findWithPostCountByMemberId(Long memberId) {
		Member savedMember = findById(memberId);
		int postCount = postMapper.findPostCountByMemberId(savedMember.getId());
		return new MemberGetResponse(
			savedMember.getId(), savedMember.getEmail(), savedMember.getNickname(), savedMember.getIntroduction(),
			postCount
		);
	}

	public MemberUpdateResponse update(MemberUpdateRequest memberUpdateDto) {
		findById(memberUpdateDto.getMemberId());
		memberMapper.update(memberUpdateDto);

		Member updatedMember = findById(memberUpdateDto.getMemberId());
		Timestamp date = Timestamp.valueOf(updatedMember.getModifiedDate());
		LocalDateTime modifiedDate = date.toLocalDateTime();

		return new MemberUpdateResponse(updatedMember.getId(),
			updatedMember.getEmail(),
			updatedMember.getNickname(),
			updatedMember.getIntroduction(),
			modifiedDate.toString()
		);
	}

	public void delete(Long memberId) {
		memberMapper.delete(memberId);
	}
}
