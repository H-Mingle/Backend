package com.hyundai.hmingle.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OauthLoginResponse {

	private final String accessToken;
	private final String refreshToken;
}
