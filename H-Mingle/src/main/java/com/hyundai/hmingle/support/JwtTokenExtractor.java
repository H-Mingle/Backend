package com.hyundai.hmingle.support;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class JwtTokenExtractor {

	private final JwtTokenProvider jwtTokenProvider;

	public Long extract(HttpHeaders headers) {
		List<String> authorizations = headers.get(HttpHeaders.AUTHORIZATION);
		if (authorizations == null) {
			return null;
		}
		String token;
		for (String authorization : authorizations) {
			if (authorization.toLowerCase().startsWith("Bearer".toLowerCase())) {
				token = authorization.substring("Bearer".length()).trim();
				if (isExpired(token)) {
					throw new RuntimeException("로그아웃된 계정입니다.");
				}
				return getPayload(token);
			}
		}
		throw new RuntimeException("잘못된 토큰 형식입니다.");
	}

	private boolean isExpired(String token) {
		try {
			Jws<Claims> claims = getClaims(token);
			return claims.getBody().getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			return true;
		}
	}

	private Long getPayload(String token) {
		return Long.valueOf(getClaims(token).getBody().getSubject());
	}

	private Jws<Claims> getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(jwtTokenProvider.getKey()).build().parseClaimsJws(token);
	}
}