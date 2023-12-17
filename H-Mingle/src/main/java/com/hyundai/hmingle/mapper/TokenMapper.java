package com.hyundai.hmingle.mapper;

import java.util.Optional;

import com.hyundai.hmingle.domain.member.Token;

public interface TokenMapper {

	Optional<Token> findByMemberId(Long memberId);

	void save(Token token);

	void update(Token token);

	void delete(Long memberId);
}
