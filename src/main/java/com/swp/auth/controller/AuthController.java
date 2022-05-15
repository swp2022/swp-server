package com.swp.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.swp.auth.JwtProvider;
import com.swp.auth.dto.TokenRequestDto;
import com.swp.auth.dto.TokenResponseDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class AuthController {

	private final JwtProvider jwtProvider;

	@PostMapping(path = "/v1/auth/renew")
	public TokenResponseDto renewToken(@RequestBody TokenRequestDto requestDto) {
		return jwtProvider.renewToken(requestDto);
	}
}
