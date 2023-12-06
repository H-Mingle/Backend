package com.hyundai.hmingle.support.oauth;

import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;

public interface OauthEntityGenerator {

	HttpEntity<MultiValueMap<String, String>> createForAccessToken(String redirectUrl, String authorizationCode);

	HttpEntity<Void> createForUserInfo(String accessToken);
}
