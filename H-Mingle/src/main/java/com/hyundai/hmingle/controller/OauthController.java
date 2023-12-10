package com.hyundai.hmingle.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hyundai.hmingle.controller.dto.response.MingleResponse;
import com.hyundai.hmingle.controller.dto.response.OauthLoginResponse;
import com.hyundai.hmingle.controller.dto.response.OauthLoginUrlResponse;
import com.hyundai.hmingle.controller.dto.response.RefreshResponse;
import com.hyundai.hmingle.service.OauthService;
import com.hyundai.hmingle.support.JwtTokenExtractor;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OauthController {

	private final OauthService oauthService;
	private final JwtTokenExtractor jwtTokenExtractor;

	@GetMapping(value = "/login", params = {"redirectUrl"})
	public ResponseEntity<MingleResponse<OauthLoginUrlResponse>> generateLoginUrl(@RequestParam String redirectUrl) {
		OauthLoginUrlResponse response = oauthService.generateLoginUrl(redirectUrl);
		return ResponseEntity.ok(MingleResponse.success(
			"OAuth2.0 로그인 URL 요청에 성공하였습니다.",
			response
		));
	}

	@GetMapping(value = "/login", params = {"redirectUrl", "authorizationCode"})
	public ResponseEntity<MingleResponse<OauthLoginResponse>> login(@RequestParam String redirectUrl, @RequestParam String authorizationCode) {
		OauthLoginResponse response = oauthService.login(redirectUrl, authorizationCode);
		return ResponseEntity.ok(MingleResponse.success(
			"OAuth2.0 로그인에 성공하였습니다.",
			response
		));
	}

	@GetMapping("/refresh")
	public ResponseEntity<MingleResponse<RefreshResponse>> refresh(@RequestHeader HttpHeaders headers) {
		Long memberId = jwtTokenExtractor.extract(headers);
		RefreshResponse response = oauthService.refresh(memberId);
		return ResponseEntity.ok(MingleResponse.success(
			"Access Token 재발급에 성공하였습니다.",
			response
		));
	}

	@PostMapping("/logout")
	public ResponseEntity<MingleResponse<Void>> logout(@RequestHeader HttpHeaders headers) {
		Long memberId = jwtTokenExtractor.extract(headers);
		oauthService.logout(memberId);
		return ResponseEntity.ok(MingleResponse.success(
			"OAuth2.0 로그아웃에 성공하였습니다.",
			null
		));
	}
}