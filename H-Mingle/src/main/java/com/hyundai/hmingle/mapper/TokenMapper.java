package com.hyundai.hmingle.mapper;

import java.util.Optional;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.hyundai.hmingle.domain.member.Token;

public interface TokenMapper {

	@Insert("insert into token (id, member_id, access_token, refresh_token)\n"
		+ "        values (SEQ_TOKEN_ID.nextval, #{member.id}, #{accessToken}, #{refreshToken})")
	void save(Token token);

	@Update("update token\n"
		+ "        set access_token = #{accessToken},\n"
		+ "            refresh_token = #{refreshToken}\n"
		+ "        where member_id = #{member.id}")
	void update(Token token);

	@Select("select id, member_id, access_token, refresh_token\n"
		+ "        from token\n"
		+ "        where member_id = #{memberId}")
	Optional<Token> findByMemberId(Long memberId);
}
