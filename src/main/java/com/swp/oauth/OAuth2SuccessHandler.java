package com.swp.oauth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

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
	@Value("${frontend.redirectUri}")
	private String REDIRECTION_URI;
	@Value("${frontend.authorizedRedirectUris}")
	private String[] AUTHORIZED_URIS;
	private final JwtProvider jwtProvider;
	private final CookieOAuth2AuthorizationRequestRepository requestRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
		OAuth2User oAuth2User = (OAuth2User)authentication.getPrincipal();
		String targetUrl = getTargetUrl(request, oAuth2User);
		requestRepository.removeAuthorizationRequestCookies(request, response);
		response.sendRedirect(targetUrl);
	}

	private String getTargetUrl(HttpServletRequest request, OAuth2User oAuth2User) {
		String provider = oAuth2User.getAttribute(PROVIDER_KEY);
		String providerId = oAuth2User.getName();
		String role = Role.USER.getValue();
		TokenResponseDto token = jwtProvider.createToken(provider, providerId, role);

		Optional<String> cookieRedirectUri = CookieUtils.getCookie(request,
			CookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);
		String redirectUri = REDIRECTION_URI;

		if (cookieRedirectUri.isPresent() && isAuthorizedPattern(cookieRedirectUri)) {
			redirectUri = cookieRedirectUri.get();
		}
		return UriComponentsBuilder.fromUriString(redirectUri)
			.queryParam("accessToken", token.getAccessToken())
			.queryParam("refreshToken", token.getRefreshToken())
			.build()
			.toUriString();
	}

	private boolean isAuthorizedPattern(Optional<String> cookieRedirectUri) {
		return cookieRedirectUri.stream()
			.anyMatch(uri -> Arrays.stream(AUTHORIZED_URIS)
				.anyMatch(authorizedUri -> Pattern.matches(authorizedUri, uri)));
	}
}