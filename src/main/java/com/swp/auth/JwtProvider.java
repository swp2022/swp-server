package com.swp.auth;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.swp.auth.dto.TokenDto;

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
	private final Key secret;
	@Value("${app.auth.tokenExpiry}")
	private Long accessTokenExpiry;
	@Value("${app.auth.refreshTokenExpiry}")
	private Long refreshTokenExpiry;

	public JwtProvider(@Value("${app.auth.jwtSecret}") String secret) {
		byte[] secretBytes = Base64.getEncoder().encode(secret.getBytes(StandardCharsets.UTF_8));
		this.secret = Keys.hmacShaKeyFor(secretBytes);
	}

	public TokenDto createToken(String userId, String role) {
		Claims claims = Jwts.claims();
		claims.setSubject(userId);
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

		return TokenDto.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseJws(accessToken).getBody();
		String userId = claims.getSubject();
		String role = claims.get(ROLE_KEY).toString();

		Collection<? extends GrantedAuthority> roles = List.of(new SimpleGrantedAuthority(role));

		UserDetails userDetails = new User(userId, "", roles);
		return new UsernamePasswordAuthenticationToken(userDetails, "", roles);
	}

	public boolean validate(String token) {
		try {
			parseJws(token);
			return true;
		} catch (ExpiredJwtException | SignatureException e) {
			System.err.println("Expired JWS");
		} catch (UnsupportedJwtException e) {
			System.err.println("Unsupported JWS");
		} catch (MalformedJwtException e) {
			System.err.println("Malformed JWS");
		} catch (IllegalArgumentException e) {
			System.err.println("Null JWS");
		}
		return false;
	}

	private Jws<Claims> parseJws(String accessToken) {
		return Jwts.parserBuilder()
			.setSigningKey(secret)
			.build().parseClaimsJws(accessToken);
	}

}
