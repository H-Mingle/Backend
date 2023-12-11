package com.hyundai.hmingle.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hyundai.hmingle.controller.dto.response.OauthLoginResponse;
import com.hyundai.hmingle.controller.dto.response.OauthLoginUrlResponse;
import com.hyundai.hmingle.controller.dto.response.RefreshResponse;
import com.hyundai.hmingle.domain.member.Member;
import com.hyundai.hmingle.domain.member.Token;
import com.hyundai.hmingle.mapper.MemberMapper;
import com.hyundai.hmingle.mapper.TokenMapper;
import com.hyundai.hmingle.support.JwtTokenProvider;
import com.hyundai.hmingle.support.oauth.OauthConnector;
import com.hyundai.hmingle.support.oauth.OauthProvider;
import com.hyundai.hmingle.support.dto.response.GoogleUserResponse;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class OauthService {

	private final OauthProvider googleOauthProvider;
	private final OauthConnector googleOauthConnector;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberMapper memberMapper;
	private final TokenMapper tokenMapper;

	@Transactional(readOnly = true)
	public OauthLoginUrlResponse generateLoginUrl(String redirectUrl) {
		String loginUrl = googleOauthProvider.generateLoginUrl(redirectUrl);
		return new OauthLoginUrlResponse(loginUrl);
	}

	public OauthLoginResponse login(String redirectUrl, String authorizationCode) {
		GoogleUserResponse response = googleOauthConnector.requestUserInfo(redirectUrl, authorizationCode);
		Member member = saveMember(response);

		String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()));
		String refreshToken = jwtTokenProvider.createRefreshToken(String.valueOf(member.getId()));

		saveToken(member, accessToken, refreshToken, member.getId());
		return new OauthLoginResponse(accessToken, refreshToken);
	}

	public RefreshResponse refresh(Long memberId) {
		Optional<Token> savedToken = tokenMapper.findByMemberId(memberId);
		if (savedToken.isEmpty()) {
			throw new RuntimeException("로그아웃된 계정입니다.");
		}
		Token token = savedToken.get();

		String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(memberId));
		token.renew(accessToken);
		tokenMapper.update(token);
		return new RefreshResponse(accessToken);
	}

	public void logout(Long memberId) {
		Optional<Token> savedToken = tokenMapper.findByMemberId(memberId);
		if (savedToken.isPresent()) {
			tokenMapper.delete(memberId);
		}
	}

	private Member saveMember(GoogleUserResponse response) {
		return memberMapper.findByEmail(response.getEmail())
			.orElseGet(() -> {
				memberMapper.save(Member.toDomain(response.getEmail(), response.getName(), null, response.getPicture()));
				return memberMapper.findByEmail(response.getEmail()).get();
			});
	}

	private void saveToken(Member member, String accessToken, String refreshToken, Long memberId) {
		Token token = new Token(member, accessToken, refreshToken);
		tokenMapper.findByMemberId(memberId)
			.ifPresentOrElse(
				value -> tokenMapper.update(token),
				() -> tokenMapper.save(token)
			);
	}
}
