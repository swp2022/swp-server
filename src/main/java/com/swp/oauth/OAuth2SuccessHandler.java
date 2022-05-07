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

	private static final String PROVIDER_KEY = "provider";
	@Value("${frontend.redirect-uri}")
	private String REDIRECTION_URI;
	private final JwtProvider jwtProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		String provider = oAuth2User.getAttribute(PROVIDER_KEY);

		String providerId = oAuth2User.getName();
		String role = Role.USER.getValue();
		TokenDto token = jwtProvider.createToken(provider, providerId, role);

		String targetUrl = UriComponentsBuilder.fromUriString(REDIRECTION_URI)
			.queryParam("accessToken", token.getAccessToken())
			.queryParam("refreshToken", token.getRefreshToken())
			.build()
			.toUriString();
		response.sendRedirect(targetUrl);
	}
}