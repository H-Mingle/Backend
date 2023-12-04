package com.hyundai.hmingle.domain.member;

import org.springframework.stereotype.Component;

import com.hyundai.hmingle.domain.common.Base;

@Component
public class Member extends Base {

	private String email;
	private String nickname;
	private String introduction;
	private Token token;
}
