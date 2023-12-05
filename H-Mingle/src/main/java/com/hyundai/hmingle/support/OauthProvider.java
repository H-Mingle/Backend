package com.hyundai.hmingle.support;

public interface OauthProvider {

	String generateLoginUrl(String redirectUrl);
}
