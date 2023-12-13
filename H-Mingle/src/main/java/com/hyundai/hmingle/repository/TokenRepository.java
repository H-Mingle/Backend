package com.hyundai.hmingle.repository;

import org.springframework.stereotype.Repository;

import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.domain.member.Token;
import com.hyundai.hmingle.mapper.TokenMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenRepository {

	private final TokenMapper tokenMapper;

	public void save(Member member, String accessToken, String refreshToken, Long memberId) {
		Token token = new Token(member, accessToken, refreshToken);
		tokenMapper.findByMemberId(memberId)
			.ifPresentOrElse(
				value -> tokenMapper.update(token),
				() -> tokenMapper.save(token)
			);
	}

	public void update(Long memberId, String accessToken) {
		Token token = findByMemberId(memberId);

		token.renew(accessToken);
		tokenMapper.update(token);
	}

	public void delete(Long memberId) {
		tokenMapper.findByMemberId(memberId)
			.ifPresent(value -> tokenMapper.delete(memberId));
	}

	public Token findByMemberId(Long memberId) {
		Token savedToken = tokenMapper.findByMemberId(memberId)
			.orElseThrow(() -> new RuntimeException("로그아웃된 계정입니다."));
		if (savedToken.isRemoved()) {
			throw new RuntimeException("삭제된 토큰입니다.");
		}
		return savedToken;
	}
}
