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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	@Value("${frontend.redirect-uri}")
	private String REDIRECTION_URI;

	// TODO: JWT Token 발행 및 전송
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		String targetUrl = UriComponentsBuilder.fromUriString(REDIRECTION_URI)
			.queryParam("accessToken", "acc.ess.token")
			.queryParam("refreshToken", "refre.sh.to.ken")
			.build()
			.toUriString();
		response.sendRedirect(targetUrl);
	}
}