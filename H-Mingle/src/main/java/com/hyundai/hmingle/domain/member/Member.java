package com.hyundai.hmingle.domain.member;

import org.springframework.stereotype.Component;

import com.hyundai.hmingle.domain.common.Base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Component
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Member extends Base {

	private String email;
	private String nickname;
	private String introduction;
	private String imageUrl;

	public Member(Long id, String email, String nickname, String introduction, String imageUrl) {
		this.id = id;
		this.email = email;
		this.nickname = nickname;
		this.introduction = introduction;
		this.imageUrl = imageUrl;
	}

	public static Member toDomain(String email, String nickname, String introduction, String imageUrl) {
		return new Member(null, email, nickname, introduction, imageUrl);
	}

	public boolean isSame(Member member) {
		return id.equals(member.id);
	}
}
