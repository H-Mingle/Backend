package com.hyundai.hmingle.repository;

import com.hyundai.hmingle.controller.dto.request.MemberUpdateRequest;
import com.hyundai.hmingle.mapper.dto.request.ImageUpdateDto;
import com.hyundai.hmingle.mapper.dto.response.MemberUpdateResponse;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.mapper.MemberMapper;
import com.hyundai.hmingle.mapper.PostMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.print.DocFlavor;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

	public MemberGetResponse findWithPostCountByMemberId(Long memberId) throws IOException {
		Member savedMember = findById(memberId);
		int postCount = postMapper.findPostCountByMemberId(savedMember.getId());
		boolean owner = false;
		byte[] image = null;

		Pattern p = Pattern.compile("^(?:https?:\\/\\/)?(?:www\\.)?[a-zA-Z0-9./]+$");
		Matcher m = p.matcher(savedMember.getImageUrl());
		if  (m.matches()){
			DocFlavor.URL url = new DocFlavor.URL(savedMember.getImageUrl());
			BufferedImage img = ImageIO.read((ImageInputStream) url);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(img, "jpg", bos);
			Base64.Encoder encoder = Base64.getEncoder();

			image = encoder.encode(bos.toByteArray());
		}
		else{

		}

		return new MemberGetResponse(
			savedMember.getId(), savedMember.getEmail(), savedMember.getNickname(), savedMember.getIntroduction(),
			postCount, image, owner
		);
	}

	public MemberUpdateResponse update(MemberUpdateRequest memberUpdateDto) {
		Member savedMember = findById(memberUpdateDto.getMemberId());
		memberMapper.update(memberUpdateDto);

		Member updatedMember = findById(savedMember.getId());
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

	public int updateImg(ImageUpdateDto imageUpdateDto){
		return memberMapper.updateImg(imageUpdateDto);
	}

}
