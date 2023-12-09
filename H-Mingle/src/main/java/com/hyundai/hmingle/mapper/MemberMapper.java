package com.hyundai.hmingle.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import com.hyundai.hmingle.controller.dto.response.MemberGetResponse;
import com.hyundai.hmingle.domain.member.Member;

public interface MemberMapper {

	@Insert("insert into member(email, nickname, introduction, image_url)\n"
		+ "        values (#{email}, #{nickname}, #{introduction}, #{imageUrl})")
	void save(Member member);

	@Select("select id, email, nickname, introduction, image_url\n"
		+ "        from member\n"
		+ "        where email = #{email}")
	Optional<Member> findByEmail(String email);
	
	MemberGetResponse getMember(Long memberId);
}
