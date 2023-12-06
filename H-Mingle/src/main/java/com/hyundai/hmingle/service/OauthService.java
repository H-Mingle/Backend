package com.hyundai.hmingle.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.response.OauthLoginResponse;
import com.hyundai.hmingle.controller.dto.response.OauthLoginUrlResponse;
import com.hyundai.hmingle.support.JwtTokenProvider;
import com.hyundai.hmingle.support.OauthConnector;
import com.hyundai.hmingle.support.OauthProvider;
import com.hyundai.hmingle.support.dto.response.GoogleUserResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OauthService {

	private final OauthProvider googleOauthProvider;
	private final OauthConnector googleOauthConnector;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional(readOnly = true)
	public OauthLoginUrlResponse generateLoginUrl(String redirectUrl) {
		String loginUrl = googleOauthProvider.generateLoginUrl(redirectUrl);
		return new OauthLoginUrlResponse(loginUrl);
	}

	public OauthLoginResponse login(String redirectUrl, String authorizationCode) {
		ResponseEntity<GoogleUserResponse> response = googleOauthConnector.requestUserInfo(redirectUrl, authorizationCode);

		String accessToken = jwtTokenProvider.createAccessToken("1L");
		String refreshToken = jwtTokenProvider.createRefreshToken("1L");

		return new OauthLoginResponse(accessToken, refreshToken);
	}
}
