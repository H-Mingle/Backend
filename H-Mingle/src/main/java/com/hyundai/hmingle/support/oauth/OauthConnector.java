package com.hyundai.hmingle.support.oauth;

import com.hyundai.hmingle.support.dto.response.GoogleUserResponse;

public interface OauthConnector {

	GoogleUserResponse requestUserInfo(String redirectUrl, String authorizationCode);
}
