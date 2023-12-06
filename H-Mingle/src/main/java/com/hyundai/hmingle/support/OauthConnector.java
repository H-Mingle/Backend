package com.hyundai.hmingle.support;

import org.springframework.http.ResponseEntity;

import com.hyundai.hmingle.support.dto.response.GoogleUserResponse;

public interface OauthConnector {

	ResponseEntity<GoogleUserResponse> requestUserInfo(String redirectUrl, String authorizationCode);
}
