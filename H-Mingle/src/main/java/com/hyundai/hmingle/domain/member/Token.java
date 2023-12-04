package com.hyundai.hmingle.domain.member;

import org.springframework.stereotype.Component;

import com.hyundai.hmingle.domain.common.Base;

@Component
public class Token extends Base {

	private String accessToken;
	private String refreshToken;
}
