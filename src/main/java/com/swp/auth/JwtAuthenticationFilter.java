package com.swp.auth;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private static final String HEADER_PREFIX = "Bearer ";
	private final JwtProvider jwtProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		String accessToken = parseToken(request);
		if (jwtProvider.validate(accessToken)) {
			Authentication authentication = jwtProvider.getAuthentication(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}

	private String parseToken(HttpServletRequest request) {
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (StringUtils.hasText(authHeader) && authHeader.contains(HEADER_PREFIX)) {
			return authHeader.substring(HEADER_PREFIX.length());
		}
		return null;
	}
}
