package com.swp.config.handler;

import static java.time.LocalDateTime.*;
import static org.springframework.http.HttpStatus.*;

import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.swp.common.dto.ErrorResponseDto;
import com.swp.common.exception.ApiException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	protected ResponseEntity<Object> handleApiException(Exception exception, WebRequest request) {
		if (!Objects.nonNull(exception))
			return null;
		if (exception instanceof ApiException) {
			return handleExceptionInternal(exception, null, new HttpHeaders(), ((ApiException)exception).getStatus(),
				request);
		} else if (exception instanceof AuthenticationException) {
			return handleExceptionInternal(exception, null, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
		} else
			return handleExceptionInternal(exception, null, new HttpHeaders(), INTERNAL_SERVER_ERROR, request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest)request;
		ErrorResponseDto dto = ErrorResponseDto.builder()
			.timestamp(now())
			.status(status.value())
			.error(status.getReasonPhrase())
			.message(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage())
			.remote(servletWebRequest.getRequest().getRemoteAddr())
			.path(servletWebRequest.getRequest().getRequestURI())
			.build();
		return new ResponseEntity<>(dto, headers, status);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
		HttpStatus status, WebRequest request) {
		ServletWebRequest servletWebRequest = (ServletWebRequest)request;
		ErrorResponseDto dto;
		if (status.equals(INTERNAL_SERVER_ERROR)) {
			dto = ErrorResponseDto.builder()
				.timestamp(now())
				.status(status.value())
				.error(status.getReasonPhrase())
				.message("Internal Server Error")
				.path(servletWebRequest.getRequest().getRequestURI())
				.remote(servletWebRequest.getRequest().getRemoteAddr())
				.build();
		} else {
			dto = ErrorResponseDto.builder()
				.timestamp(now())
				.status(status.value())
				.error(status.getReasonPhrase())
				.message(ex.getMessage())
				.remote(servletWebRequest.getRequest().getRemoteAddr())
				.path(servletWebRequest.getRequest().getRequestURI())
				.build();
		}
		return new ResponseEntity<>(dto, headers, status);
	}
}
