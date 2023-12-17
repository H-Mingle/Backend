package com.hyundai.hmingle.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.controller.dto.request.MemberUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.mapper.MemberMapper;
import com.hyundai.hmingle.mapper.PostMapper;
import com.hyundai.hmingle.mapper.dto.request.ImageUpdateMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.MemberUpdateMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.MemberUpdateMapperResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRepository {

	private final MemberMapper memberMapper;
	private final PostMapper postMapper;

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

	public MemberGetResponse findWithPostCountByMemberId(Long id, Long memberId) {
		Member savedMember = findById(memberId);
		int postCount = postMapper.findPostCountByMemberId(savedMember.getId());

		byte[] imageByteArray = null;
		try (InputStream imageStream = new FileInputStream(savedMember.getImageUrl())) {
			imageByteArray = IOUtils.toByteArray(imageStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

		boolean owner = false;
		if (id == memberId)
			owner = true;

		return new MemberGetResponse(
			savedMember.getId(), savedMember.getEmail(), savedMember.getNickname(), savedMember.getIntroduction(),
			postCount, imageByteArray, owner
		);
	}

	public MemberUpdateMapperResponse update(MemberUpdateRequest memberUpdateDto) {
		Member savedMember = findById(memberUpdateDto.getMemberId());
		MemberUpdateMapperRequest memberUpdateMapperRequest = new MemberUpdateMapperRequest(
			memberUpdateDto.getMemberId(), memberUpdateDto.getNickname(), memberUpdateDto.getIntroduction()
		);
		memberMapper.update(memberUpdateMapperRequest);

		Member updatedMember = findById(savedMember.getId());
		Timestamp date = Timestamp.valueOf(updatedMember.getModifiedDate());
		LocalDateTime modifiedDate = date.toLocalDateTime();

		return new MemberUpdateMapperResponse(updatedMember.getId(),
			updatedMember.getEmail(),
			updatedMember.getNickname(),
			updatedMember.getIntroduction(),
			modifiedDate.toString()
		);
	}

	public void delete(Long memberId) {
		memberMapper.delete(memberId);
	}

	public int updateImg(ImageUpdateMapperRequest imageUpdateMapperRequest) {
		return memberMapper.updateImg(imageUpdateMapperRequest);
	}

}
