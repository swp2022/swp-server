package com.swp.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.swp.auth.JwtProvider;
import com.swp.auth.dto.TokenDto;
import com.swp.user.domain.Role;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Value("${frontend.redirect-uri}")
	private String REDIRECTION_URI;
	private final JwtProvider jwtProvider;

	// TODO: JWT Token 발행 및 전송
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		TokenDto token = jwtProvider.createToken(oAuth2User.getName(), Role.USER.getValue());

		String targetUrl = UriComponentsBuilder.fromUriString(REDIRECTION_URI)
			.queryParam("accessToken", token.getAccessToken())
			.queryParam("refreshToken", token.getRefreshToken())
			.build()
			.toUriString();
		System.out.println("targetUrl = " + targetUrl);
		response.sendRedirect(targetUrl);
	}
}