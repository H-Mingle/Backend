package com.hyundai.hmingle.support.oauth.google;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import com.hyundai.hmingle.support.ApiConnector;
import com.hyundai.hmingle.support.dto.response.GoogleTokenResponse;
import com.hyundai.hmingle.support.dto.response.GoogleUserResponse;
import com.hyundai.hmingle.support.oauth.OauthConnector;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GoogleOauthConnector implements OauthConnector {

	private final GoogleOauthProvider provider;
	private final GoogleOauthEntityGenerator entityGenerator;

	@Override
	public GoogleUserResponse requestUserInfo(String redirectUrl, String authorizationCode) {
		String accessToken = requestAccessToken(redirectUrl, authorizationCode);
		return requestUserInfo(accessToken);
	}

	private String requestAccessToken(String redirectUrl, String authorizationCode) {
		HttpEntity<MultiValueMap<String, String>> entity = entityGenerator.createForAccessToken(redirectUrl, authorizationCode);
		ResponseEntity<GoogleTokenResponse> response = ApiConnector.post(provider.getAccessTokenUrl(), entity, GoogleTokenResponse.class);

		validateResponseStatusIsOk(response.getStatusCode());

		return Optional.ofNullable(response.getBody())
			.orElseThrow(() -> new RuntimeException("Oauth 로그인에 실패하였습니다."))
			.getAccessToken();
	}

	private GoogleUserResponse requestUserInfo(String accessToken) {
		HttpEntity<Void> entity = entityGenerator.createForUserInfo(accessToken);
		ResponseEntity<GoogleUserResponse> response = ApiConnector.get(provider.getUserInfoUrl(), entity, GoogleUserResponse.class);

		validateResponseStatusIsOk(response.getStatusCode());

		return Optional.ofNullable(response.getBody())
			.orElseThrow(() -> new RuntimeException("oauth 사용자 정보를 조회하는데 실패하였습니다."));
	}

	private void validateResponseStatusIsOk(HttpStatus status) {
		if (!status.is2xxSuccessful()) {
			throw new RuntimeException("Oauth 로그인에 실패하였습니다.");
		}
	}
}
