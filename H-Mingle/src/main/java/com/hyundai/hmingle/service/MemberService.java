package com.hyundai.hmingle.service;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.request.MemberUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.controller.dto.response.MemberUpdateResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.mapper.dto.request.ImageUpdateMapperRequest;
import com.hyundai.hmingle.repository.MemberRepository;
import com.hyundai.hmingle.repository.PostRepository;
import com.hyundai.hmingle.repository.TokenRepository;
import com.hyundai.hmingle.support.ImageUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberService {

	private final MemberRepository memberRepository;
	private final TokenRepository tokenRepository;
	private final PostRepository postRepository;
	private final ImageUtils imageUtils;

	@Transactional(readOnly = true)
	public MemberGetResponse findById(Long id, Long memberId) {
		Member savedMember = memberRepository.findById(memberId);
		int postCount = postRepository.findCountByMemberId(memberId);
		byte[] image = imageUtils.convertUrlToBytes(savedMember.getImageUrl());
		boolean owner = Objects.equals(id, savedMember.getId());

		return new MemberGetResponse(
			savedMember.getId(), savedMember.getEmail(), savedMember.getNickname(), savedMember.getIntroduction(),
			postCount, image, owner
		);
	}

	public MemberUpdateResponse update(MemberUpdateRequest memberUpdateDto) {
		Member savedMember = memberRepository.findById(memberUpdateDto.getMemberId());
		memberRepository.update(savedMember.getId(), memberUpdateDto.getNickname(), memberUpdateDto.getIntroduction());
		Member updatedMember = memberRepository.findById(savedMember.getId());

		return new MemberUpdateResponse(
			updatedMember.getId(),
			updatedMember.getEmail(),
			updatedMember.getNickname(),
			updatedMember.getIntroduction(),
			updatedMember.getModifiedDate()
		);
	}

	public void leave(Long memberId) {
		Member savedMember = memberRepository.findById(memberId);
		tokenRepository.delete(savedMember.getId());
		memberRepository.delete(savedMember.getId());
	}

	public void updateFile(Long memberId, MultipartFile image) {
		ImageCreateRequest uploadImg = imageUtils.updateFile(image);
		ImageUpdateMapperRequest dto = new ImageUpdateMapperRequest(memberId, uploadImg.getSaveName());
		memberRepository.updateImg(dto);
	}
}
