package com.hyundai.hmingle.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.controller.dto.response.OauthLoginUrlResponse;
import com.hyundai.hmingle.service.OauthService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OauthController {

	private final OauthService oauthService;

	@GetMapping("/login")
	public ResponseEntity<MingleResponse<OauthLoginUrlResponse>> generateLoginUrl(@RequestParam("redirectUrl") String redirectUrl) {
		OauthLoginUrlResponse response = oauthService.generateLoginUrl(redirectUrl);
		return ResponseEntity.ok(MingleResponse.success(
			"OAuth2.0 로그인 URL 요청에 성공하였습니다.",
			response
		));
	}
}
