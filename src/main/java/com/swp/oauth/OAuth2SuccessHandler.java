package com.swp.oauth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.swp.auth.JwtProvider;
import com.swp.auth.dto.TokenResponseDto;
import com.swp.user.domain.Role;
import com.swp.util.CookieUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {

	private static final String PROVIDER_KEY = "provider";
	@Value("${frontend.redirect-uri}")
	private String REDIRECTION_URI;
	@Value("${frontend.authorized-redirect-uris}")
	private String[] AUTHORIZED_URIS;
	private final JwtProvider jwtProvider;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();

		response.sendRedirect(getTargetUrl(request, oAuth2User));
	}

	private String getTargetUrl(HttpServletRequest request, OAuth2User oAuth2User) {
		String provider = oAuth2User.getAttribute(PROVIDER_KEY);
		String providerId = oAuth2User.getName();
		String role = Role.USER.getValue();
		TokenResponseDto token = jwtProvider.createToken(provider, providerId, role);

		Optional<String> redirectUri = CookieUtils.getCookie(request,
			CookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);

		if (redirectUri.isPresent() && redirectUri.stream()
			.anyMatch(uri -> Arrays.asList(AUTHORIZED_URIS).contains(uri))) {
			return UriComponentsBuilder.fromUriString(redirectUri.get())
				.queryParam("accessToken", token.getAccessToken())
				.queryParam("refreshToken", token.getRefreshToken())
				.build()
				.toUriString();
		}
		return REDIRECTION_URI;
	}
}