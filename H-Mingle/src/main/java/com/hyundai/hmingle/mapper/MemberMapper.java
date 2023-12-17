package com.hyundai.hmingle.mapper;

import java.util.Optional;

import com.hyundai.hmingle.controller.dto.request.MemberUpdateRequest;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.mapper.dto.request.ImageUpdateMapperRequest;

public interface MemberMapper {

	void save(Member member);

	Optional<Member> findById(Long memberId);

	Optional<Member> findByEmail(String email);

	void update(MemberUpdateRequest memberUpdateDto);

	void delete(Long memberId);

	int updateImg(ImageUpdateMapperRequest imageUpdateMapperRequest);
}
