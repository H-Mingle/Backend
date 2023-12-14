package com.hyundai.hmingle.service;

import com.hyundai.hmingle.controller.dto.request.ImageCreateRequest;
import com.hyundai.hmingle.controller.dto.request.MemberUpdateRequest;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.mapper.dto.request.ImageUpdateDto;
import com.hyundai.hmingle.mapper.dto.response.MemberUpdateResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.repository.MemberRepository;
import com.hyundai.hmingle.repository.TokenRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.print.DocFlavor;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.beans.Encoder;
import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberServiceImpl implements MemberService {

	private final MemberRepository memberRepository;
	private final TokenRepository tokenRepository;

	@Transactional(readOnly = true)
	public MemberGetResponse findById(Long id, Long memberId) throws IOException {
		return memberRepository.findWithPostCountByMemberId(id, memberId);
	}

	public MemberUpdateResponse update(MemberUpdateRequest memberUpdateDto){
		return memberRepository.update(memberUpdateDto);
	}

	public void leave(Long memberId) {
		Member savedMember = memberRepository.findById(memberId);
		tokenRepository.delete(savedMember.getId());
		memberRepository.delete(savedMember.getId());
	}

	public void updateFile(Long memberId, ImageCreateRequest img){
		ImageUpdateDto dto = new ImageUpdateDto(memberId, img.getSaveName());

		memberRepository.updateImg(dto);
	}



}
