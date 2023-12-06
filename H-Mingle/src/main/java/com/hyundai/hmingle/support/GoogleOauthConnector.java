package com.hyundai.hmingle.support;

import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.hyundai.hmingle.support.dto.response.GoogleTokenResponse;
import com.hyundai.hmingle.support.dto.response.GoogleUserResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GoogleOauthConnector implements OauthConnector {

	private static final String BEARER_TYPE = "Bearer";

	private final GoogleOauthProvider provider;

	@Override
	public GoogleUserResponse requestUserInfo(String redirectUrl, String authorizationCode) {
		String accessToken = requestAccessToken(redirectUrl, authorizationCode);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", String.join(" ", BEARER_TYPE, accessToken));
		HttpEntity<Void> entity = new HttpEntity<>(headers);

		ResponseEntity<GoogleUserResponse> response = ApiConnector.get(provider.getUserInfoUrl(), entity, GoogleUserResponse.class);
		validateResponseStatusIsOk(response.getStatusCode());

		return Optional.ofNullable(response.getBody())
			.orElseThrow(() -> new RuntimeException("oauth 사용자 정보를 조회하는데 실패하였습니다."));
	}

	private String requestAccessToken(String redirectUrl, String authorizationCode) {
		HttpEntity<MultiValueMap<String, String>> entity = createEntityForToken(redirectUrl, authorizationCode);
		ResponseEntity<GoogleTokenResponse> response = ApiConnector.post(
			provider.getAccessTokenUrl(), entity, GoogleTokenResponse.class
		);
		return extractAccessToken(response);
	}

	private HttpEntity<MultiValueMap<String, String>> createEntityForToken(String redirectUrl, String authorizationCode) {
		return new HttpEntity<>(createBodyForToken(authorizationCode, redirectUrl), createHeaders());
	}

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return headers;
	}

	private MultiValueMap<String, String> createBodyForToken(String code, String redirectUrl) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("code", code);
		body.add("client_id", provider.getClientId());
		body.add("client_secret", provider.getClientSecret());
		body.add("redirect_uri", redirectUrl);
		body.add("grant_type", provider.getGrantType());
		return body;
	}

	private String extractAccessToken(ResponseEntity<GoogleTokenResponse> responseEntity) {
		validateResponseStatusIsOk(responseEntity.getStatusCode());

		GoogleTokenResponse response = Optional.ofNullable(responseEntity.getBody())
			.orElseThrow(() -> new RuntimeException("Oauth 로그인에 실패하였습니다."));

		return response.getAccessToken();
	}

	private void validateResponseStatusIsOk(HttpStatus status) {
		if (!status.is2xxSuccessful()) {
			throw new RuntimeException("Oauth 로그인에 실패하였습니다.");
		}
	}
}
