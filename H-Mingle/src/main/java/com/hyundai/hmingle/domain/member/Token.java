package com.hyundai.hmingle.domain.member;

import org.springframework.stereotype.Component;

import com.hyundai.hmingle.domain.common.Base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

	public void renew(String accessToken) {
		this.accessToken = accessToken;
	}
}
