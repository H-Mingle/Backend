package com.hyundai.hmingle.support.oauth;

public interface OauthProvider {

	String generateLoginUrl(String redirectUrl);
}
