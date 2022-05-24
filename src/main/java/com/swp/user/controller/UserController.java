package com.swp.user.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.swp.auth.dto.JwtUserDetails;
import com.swp.user.domain.UserService;
import com.swp.user.dto.UserResponseDto;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/v1/user")
@RestController
public class UserController {

	private final UserService userService;

	@ApiOperation("자신의 정보 가져오기")
	@GetMapping
	public UserResponseDto getUser() {
		JwtUserDetails userDetails = (JwtUserDetails)SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();
		return userService.getUser(userDetails);
	}

	@ApiOperation(value = "자신의 팔로워 가져오기", notes = "자신**을** 팔로잉하는 사람들")
	@GetMapping(value = "/followers")
	public List<UserResponseDto> getMyFollowers() {
		JwtUserDetails userDetails = (JwtUserDetails)SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();
		return userService.getFollowers(userDetails);
	}

	@ApiOperation(value = "자신의 팔로잉 가져오기", notes = "자신**이** 팔로잉하는 사람들")
	@GetMapping(value = "/followings")
	public List<UserResponseDto> getMyFollowings() {
		JwtUserDetails userDetails = (JwtUserDetails) SecurityContextHolder.getContext()
			.getAuthentication()
			.getPrincipal();
		return userService.getFollowings(userDetails);
	}

	@ApiOperation(value = "유저의 팔로워 가져오기", notes = "유저**를** 팔로잉하는 사람들")
	@GetMapping(value = "{userId}/followers")
	public List<UserResponseDto> getFollowers(@PathVariable Integer userId) {
		return userService.getFollowers(userId);
	}

	@ApiOperation(value = "유저의 팔로잉 가져오기", notes = "유저**가** 팔로잉하는 사람들")
	@GetMapping(value = "{userId}/followings")
	public List<UserResponseDto> getFollowings(@PathVariable Integer userId) {
		return userService.getFollowings(userId);
	}

	@ApiOperation(value = "nickname으로 유저 검색하기")
	@GetMapping(value = "/search/{nickname}")
	public UserResponseDto getUserByNickname(@PathVariable String nickname) {
		return userService.getUserByNickname(nickname);
	}
}
