package com.hyundai.hmingle.mapper;

import java.util.Optional;

import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.mapper.dto.request.ImageUpdateMapperRequest;
import com.hyundai.hmingle.mapper.dto.request.MemberUpdateMapperRequest;

public interface MemberMapper {

	Optional<Member> findById(Long memberId);

	Optional<Member> findByEmail(String email);

	void save(Member member);

	void update(MemberUpdateMapperRequest memberUpdateDto);

	int updateImg(ImageUpdateMapperRequest imageUpdateMapperRequest);

	void delete(Long memberId);
}
