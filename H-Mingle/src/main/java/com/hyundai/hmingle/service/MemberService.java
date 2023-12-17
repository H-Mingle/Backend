package com.hyundai.hmingle.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.request.MemberUpdateRequest;
import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.support.ImageUtils;
import com.hyundai.hmingle.mapper.dto.request.ImageUpdateMapperRequest;
import com.hyundai.hmingle.mapper.dto.response.MemberUpdateMapperResponse;
import com.hyundai.hmingle.repository.MemberRepository;
import com.hyundai.hmingle.repository.TokenRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberService {

	private final MemberRepository memberRepository;
	private final TokenRepository tokenRepository;
	private final ImageUtils imageUtils;

	@Transactional(readOnly = true)
	public MemberGetResponse findById(Long id, Long memberId) {
		return memberRepository.findWithPostCountByMemberId(id, memberId);
	}

	public MemberUpdateMapperResponse update(MemberUpdateRequest memberUpdateDto) {
		return memberRepository.update(memberUpdateDto);
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
