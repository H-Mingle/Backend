package com.hyundai.hmingle.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.response.OauthLoginResponse;
import com.hyundai.hmingle.controller.dto.response.OauthLoginUrlResponse;
import com.hyundai.hmingle.controller.dto.response.RefreshResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.repository.MemberRepository;
import com.hyundai.hmingle.repository.TokenRepository;
import com.hyundai.hmingle.support.JwtTokenProvider;
import com.hyundai.hmingle.support.dto.response.GoogleUserResponse;
import com.hyundai.hmingle.support.oauth.OauthConnector;
import com.hyundai.hmingle.support.oauth.OauthProvider;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OauthService {

	private final OauthProvider googleOauthProvider;
	private final OauthConnector googleOauthConnector;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;
	private final TokenRepository tokenRepository;

	@Transactional(readOnly = true)
	public OauthLoginUrlResponse generateLoginUrl(String redirectUrl) {
		String loginUrl = googleOauthProvider.generateLoginUrl(redirectUrl);
		return new OauthLoginUrlResponse(loginUrl);
	}

	public OauthLoginResponse login(String redirectUrl, String authorizationCode) {
		GoogleUserResponse response = googleOauthConnector.requestUserInfo(redirectUrl, authorizationCode);
		Member member = memberRepository.save(response.getEmail(), response.getName(), response.getPicture());

		String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
		String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(member.getId()));

		tokenRepository.save(member, accessToken, refreshToken, member.getId());
		return new OauthLoginResponse(accessToken, refreshToken);
	}

	public RefreshResponse refresh(Long memberId) {
		String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(memberId));
		tokenRepository.update(memberId, accessToken);
		return new RefreshResponse(accessToken);
	}

	public void logout(Long memberId) {
		tokenRepository.delete(memberId);
	}
}
