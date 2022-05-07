package com.swp.user.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.user.domain.UserService;
import com.swp.user.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserController {

	private final UserService userService;

	@GetMapping(value = "/v1/user")
	public UserDto getUser() {
		JwtUserDetails userDetails = (JwtUserDetails)SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();
		return userService.getUser(userDetails.getProvider(), userDetails.getUsername());
	}
}
