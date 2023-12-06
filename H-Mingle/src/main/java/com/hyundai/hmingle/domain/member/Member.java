package com.hyundai.hmingle.domain.member;

import org.springframework.stereotype.Component;

import com.hyundai.hmingle.domain.common.Base;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Component
public class Member extends Base {

	private String email;
	private String nickname;
	private String introduction;

	public Member(Long id, String email, String nickname, String introduction) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
		this.introduction = introduction;
	}

	public static Member toDomain(String email, String nickname, String introduction) {
		return new Member(null, email, nickname, introduction);
	}
}
