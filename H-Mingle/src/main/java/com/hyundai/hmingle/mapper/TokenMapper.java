package com.hyundai.hmingle.mapper;

import java.util.Optional;

import com.hyundai.hmingle.domain.member.Token;

public interface TokenMapper {

	void save(Token token);

	void update(Token token);

	Optional<Token> findByMemberId(Long memberId);
}
