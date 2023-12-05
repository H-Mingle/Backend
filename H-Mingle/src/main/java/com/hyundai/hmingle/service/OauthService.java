package com.hyundai.hmingle.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.response.OauthLoginUrlResponse;
import com.hyundai.hmingle.support.OauthProvider;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OauthService {

	private final OauthProvider googleOauthProvider;

	@Transactional(readOnly = true)
	public OauthLoginUrlResponse generateLoginUrl(String redirectUrl) {
		String loginUrl = googleOauthProvider.generateLoginUrl(redirectUrl);
		return new OauthLoginUrlResponse(loginUrl);
	}
}
