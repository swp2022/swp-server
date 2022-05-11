package com.swp.auth;

import static java.time.LocalDateTime.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.swp.common.dto.ErrorResponseDto;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding(JsonEncoding.UTF8.getJavaName());

		ErrorResponseDto responseDto = ErrorResponseDto.builder()
			.timestamp(now())
			.status(HttpStatus.UNAUTHORIZED.value())
			.error(HttpStatus.UNAUTHORIZED.getReasonPhrase())
			.message(authException.getMessage())
			.remote(request.getRemoteAddr())
			.path(request.getRequestURI())
			.build();

		response.getWriter().write(mapDto(responseDto));
	}

	private String mapDto(ErrorResponseDto responseDto) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		return mapper.writeValueAsString(responseDto);
	}
}
