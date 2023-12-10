package com.hyundai.hmingle.controller.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RefreshResponse {

	private final String accessToken;
}
