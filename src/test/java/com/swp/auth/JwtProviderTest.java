package com.swp.auth;

import static org.assertj.core.api.Assertions.*;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;

import com.swp.auth.dto.TokenRequestDto;
import com.swp.auth.dto.TokenResponseDto;
import com.swp.auth.exception.InvalidTokenException;
import com.swp.auth.exception.TokenException;
import com.swp.user.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@SpringBootTest
class JwtProviderTest {

	@Autowired
	private JwtProvider jwtProvider;
	private final Key accessTokenSecret;
	private final Key refreshTokenSecret;
	private TokenResponseDto responseDto;
	private static final String providerExpected = "kakao";
	private static final String providerIdExpected = "12333333";
	private static final String roleExpected = Role.USER.toString();

	public JwtProviderTest(@Value("${app.auth.jwtSecret}") String accessTokenSecret,
		@Value("${app.auth.jwtRefreshSecret}") String refreshTokenSecret) {
		byte[] secretBytes = Base64.getEncoder().encode(accessTokenSecret.getBytes(StandardCharsets.UTF_8));
		this.accessTokenSecret = Keys.hmacShaKeyFor(secretBytes);
		secretBytes = Base64.getEncoder().encode(refreshTokenSecret.getBytes(StandardCharsets.UTF_8));
		this.refreshTokenSecret = Keys.hmacShaKeyFor(secretBytes);
	}

	private Jws<Claims> parseJws(String token, Key key) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
	}

	private String getExpiredToken() {
		Claims claims = Jwts.claims();
		claims.setSubject(providerIdExpected);
		claims.put("provider", providerExpected);
		claims.put("role", roleExpected);
		Date now = new Date();

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() - 1))
			.signWith(accessTokenSecret, SignatureAlgorithm.HS512)
			.compact();
	}

	private String getExpiredRefreshToken() {
		Claims claims = Jwts.claims();
		claims.setSubject(providerIdExpected);
		claims.put("provider", providerExpected);
		claims.put("role", roleExpected);
		Date now = new Date();

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() - 1))
			.signWith(refreshTokenSecret, SignatureAlgorithm.HS512)
			.compact();
	}

	private String getNearExpiryRefreshToken() {
		Claims claims = Jwts.claims();
		claims.setSubject(providerIdExpected);
		claims.put("provider", providerExpected);
		claims.put("role", roleExpected);
		Date now = new Date();

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(now)
			.setExpiration(new Date(now.getTime() + 3600 * 1000))
			.signWith(refreshTokenSecret, SignatureAlgorithm.HS512)
			.compact();
	}

	@BeforeEach
	void setUp() {
		this.responseDto = jwtProvider.createToken(providerExpected, providerIdExpected, roleExpected);
	}

	@Test
	void createToken() {
		// given, when
		Claims accessTokenClaims = parseJws(responseDto.getAccessToken(), accessTokenSecret).getBody();
		Claims refreshTokenClaims = parseJws(responseDto.getRefreshToken(), refreshTokenSecret).getBody();
		// then
		assertThat(accessTokenClaims.getSubject()).isEqualTo(providerIdExpected);
		assertThat(accessTokenClaims.get("provider")).isEqualTo(providerExpected);
		assertThat(accessTokenClaims.get("role")).isEqualTo(roleExpected);
		assertThat(refreshTokenClaims.getSubject()).isEqualTo(providerIdExpected);
		assertThat(refreshTokenClaims.get("provider")).isEqualTo(providerExpected);
		assertThat(refreshTokenClaims.get("role")).isEqualTo(roleExpected);
	}

	@Test
	void getAuthentication() {
		// given, when
		Authentication authentication = jwtProvider.getAuthentication(responseDto.getAccessToken());
		// then
		assertThat(authentication.getAuthorities().size()).isEqualTo(1);
		assertThat(authentication.getName()).isEqualTo(providerIdExpected);
	}

	@Test
	void validateEmptyToken() {
		assertThatThrownBy(() -> jwtProvider.validate(null))
			.isInstanceOf(TokenException.class)
			.hasMessageContaining("비어있습니다");
	}

	@Test
	void validateInvalidToken() {
		assertThatThrownBy(() -> jwtProvider.validate("IAMINVALIDTOKEN"))
			.isInstanceOf(TokenException.class)
			.hasMessageContaining("비정상");
	}

	@Test
	void validateExpiredToken() {
		assertThatThrownBy(() -> jwtProvider.validate(getExpiredToken()))
			.isInstanceOf(TokenException.class)
			.hasMessageContaining("만료");
	}

	@Test
	void renewTokenAccessTokenExpired() {
		// given, when
		TokenRequestDto requestDto = TokenRequestDto.builder()
			.accessToken(getExpiredToken())
			.refreshToken(responseDto.getRefreshToken())
			.build();
		TokenResponseDto renewedToken = jwtProvider.renewToken(requestDto);
		// then
		assertThat(renewedToken.getAccessToken()).isNotEqualTo(requestDto.getAccessToken());
		assertThat(renewedToken.getRefreshToken()).isEqualTo(requestDto.getRefreshToken());
	}

	@Test
	void renewTokenRefreshTokenNearExpiry() {
		// given, when
		TokenRequestDto requestDto = TokenRequestDto.builder()
			.accessToken(getExpiredToken())
			.refreshToken(getNearExpiryRefreshToken())
			.build();
		TokenResponseDto renewedToken = jwtProvider.renewToken(requestDto);
		// then
		assertThat(renewedToken.getAccessToken()).isNotEqualTo(requestDto.getAccessToken());
		assertThat(renewedToken.getRefreshToken()).isNotEqualTo(requestDto.getRefreshToken());
	}

	@Test
	void renewTokenBothExpired() {
		// given, when
		TokenRequestDto requestDto = TokenRequestDto.builder()
			.accessToken(getExpiredToken())
			.refreshToken(getExpiredRefreshToken())
			.build();
		// then
		assertThatThrownBy(()->jwtProvider.renewToken(requestDto))
			.isInstanceOf(InvalidTokenException.class)
			.hasMessageContaining("만료");
	}
}