package com.hyundai.hmingle.controller.dto.response;

import lombok.Getter;

@Getter
public class OauthLoginUrlResponse {

	private String oauthLink;

	public OauthLoginUrlResponse(String oauthLink) {
		this.oauthLink = oauthLink;
	}
}
