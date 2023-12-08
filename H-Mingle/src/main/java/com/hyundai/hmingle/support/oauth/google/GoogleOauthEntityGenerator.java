package com.hyundai.hmingle.support.oauth.google;

import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.hyundai.hmingle.support.oauth.OauthEntityGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class GoogleOauthEntityGenerator implements OauthEntityGenerator {

	private static final String BEARER_TYPE = "Bearer";

	private final GoogleOauthProvider provider;

	@Override
	public HttpEntity<MultiValueMap<String, String>> createForAccessToken(String redirectUrl, String authorizationCode) {
		return new HttpEntity<>(createBodyForAccessToken(authorizationCode, redirectUrl), createHeaders());
	}

	@Override
	public HttpEntity<Void> createForUserInfo(String accessToken) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", String.join(" ", BEARER_TYPE, accessToken));
		return new HttpEntity<>(headers);
	}

	private HttpHeaders createHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return headers;
	}

	private MultiValueMap<String, String> createBodyForAccessToken(String code, String redirectUrl) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("code", code);
		body.add("client_id", provider.getClientId());
		body.add("client_secret", provider.getClientSecret());
		body.add("redirect_uri", redirectUrl);
		body.add("grant_type", provider.getGrantType());
		return body;
	}
}
