package com.hyundai.hmingle.domain.member;

import org.springframework.stereotype.Component;

import com.hyundai.hmingle.domain.common.Base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class Token extends Base {

	private Member member;
	private String accessToken;
	private String refreshToken;

	public Token(Member member, String accessToken, String refreshToken) {
		this.member = member;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
}
