package com.swp.oauth;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.swp.util.CookieUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuth2FailureHandler implements AuthenticationFailureHandler {

	@Value("${frontend.redirectUri}")
	private String REDIRECTION_URI;
	@Value("${frontend.authorizedRedirectUris}")
	private String[] AUTHORIZED_URIS;

	private final CookieOAuth2AuthorizationRequestRepository requestRepository;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {
		requestRepository.removeAuthorizationRequestCookies(request, response);
		response.sendRedirect(getTargetUrl(request));
	}

	private String getTargetUrl(HttpServletRequest request) {
		Optional<String> redirectUri = CookieUtils.getCookie(request,
			CookieOAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME).map(Cookie::getValue);

		if (redirectUri.isPresent() && redirectUri.stream()
			.anyMatch(uri -> Arrays.asList(AUTHORIZED_URIS).contains(uri))) {
			return UriComponentsBuilder.fromUriString(redirectUri.get()).build().toUriString();
		}
		return REDIRECTION_URI;
	}
}
