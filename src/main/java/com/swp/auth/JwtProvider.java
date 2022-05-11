package com.swp.auth;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.auth.dto.TokenResponseDto;
import com.swp.auth.exception.TokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtProvider {

	private static final String ROLE_KEY = "role";
	private static final String PROVIDER_KEY = "provider";
	private final Key secret;
	@Value("${app.auth.tokenExpiry}")
	private Long accessTokenExpiry;
	@Value("${app.auth.refreshTokenExpiry}")
	private Long refreshTokenExpiry;

	public JwtProvider(@Value("${app.auth.jwtSecret}") String secret) {
		byte[] secretBytes = Base64.getEncoder().encode(secret.getBytes(StandardCharsets.UTF_8));
		this.secret = Keys.hmacShaKeyFor(secretBytes);
	}

	public TokenResponseDto createToken(String provider, String providerId, String role) {
		Claims claims = Jwts.claims();
		claims.setSubject(providerId);
		claims.put(PROVIDER_KEY, provider);
		claims.put(ROLE_KEY, role);
		Date now = new Date();

		String accessToken = Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + accessTokenExpiry))
			.signWith(secret, SignatureAlgorithm.HS512)
			.compact();

		String refreshToken = Jwts.builder()
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + refreshTokenExpiry))
			.signWith(secret, SignatureAlgorithm.HS512)
			.compact();

		return TokenResponseDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseJws(accessToken).getBody();
		String providerId = claims.getSubject();
		String provider = claims.get(PROVIDER_KEY).toString();
		String role = claims.get(ROLE_KEY).toString();

		UserDetails userDetails = JwtUserDetails.builder()
			.provider(provider)
			.providerId(providerId)
			.role(role)
			.build();
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public void validate(String token) {
		try {
			parseJws(token);
		} catch (ExpiredJwtException e) {
			throw new TokenException("만료된 토큰입니다");
		} catch (UnsupportedJwtException | MalformedJwtException | SignatureException e) {
			throw new TokenException("비정상적인 토큰입니다");
		} catch (IllegalArgumentException e) {
			throw new TokenException("토큰이 비어있습니다");
		}
	}

	private Jws<Claims> parseJws(String accessToken) {
		return Jwts.parserBuilder()
			.setSigningKey(secret)
			.build().parseClaimsJws(accessToken);
	}

}
