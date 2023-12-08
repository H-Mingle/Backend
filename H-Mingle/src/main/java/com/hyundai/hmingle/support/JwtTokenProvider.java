package com.hyundai.hmingle.support;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;

@Getter
@Component
public class JwtTokenProvider {

	private final SecretKey key;
	private final long accessTokenExpiration;
	private final long refreshTokenExpiration;

	public JwtTokenProvider(
		@Value("${jwt.secret-key}") final String secretKey,
		@Value("${jwt.access-token-expiration}") final long accessTokenExpiration,
		@Value("${jwt.refresh-token-expiration}") final long refreshTokenExpiration
	) {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		this.accessTokenExpiration = accessTokenExpiration;
		this.refreshTokenExpiration = refreshTokenExpiration;
	}

	public String createAccessToken(String payload) {
		return createToken(payload, accessTokenExpiration);
	}

	public String createRefreshToken(String payload) {
		return createToken(payload, refreshTokenExpiration);
	}

	public boolean validateTokenNotUsable(String token) {
		try {
			Jws<Claims> claims = getClaims(token);

			return claims.getBody().getExpiration().before(new Date());
		} catch (ExpiredJwtException e) {
			throw new RuntimeException("");
		} catch (JwtException | IllegalArgumentException e) {
			return true;
		}
	}

	private String createToken(String payload, long expiration) {
		Claims claims = Jwts.claims().setSubject(payload);
		Date now = new Date();
		Date validity = new Date(now.getTime() + expiration);

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(validity)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	private Jws<Claims> getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
	}
}
