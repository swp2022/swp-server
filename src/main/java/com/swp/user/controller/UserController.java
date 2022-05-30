package com.swp.user.controller;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.user.domain.UserService;
import com.swp.user.dto.UserResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/v1/user")
@RestController
public class UserController {

	private final UserService userService;

	@ApiOperation("자신의 정보 가져오기")
	@GetMapping
	public UserResponseDto getUser(@AuthenticationPrincipal JwtUserDetails userDetails) {
		return userService.getUser(userDetails);
	}

	@ApiOperation(value = "자신의 팔로워 가져오기", notes = "자신**을** 팔로잉하는 사람들")
	@GetMapping(value = "/followers")
	public List<UserResponseDto> getMyFollowers(@AuthenticationPrincipal JwtUserDetails userDetails,
	                                            @RequestParam(name = "size", defaultValue = "20") Integer size,
	                                            @RequestParam(name = "page", defaultValue = "0") Integer page) {
		return userService.getFollowers(userDetails, size, page);
	}

	@ApiOperation(value = "자신의 팔로잉 가져오기", notes = "자신**이** 팔로잉하는 사람들")
	@GetMapping(value = "/followings")
	public List<UserResponseDto> getMyFollowings(@AuthenticationPrincipal JwtUserDetails userDetails,
	                                             @RequestParam(name = "size", defaultValue = "20") Integer size,
	                                             @RequestParam(name = "page", defaultValue = "0") Integer page) {
		return userService.getFollowings(userDetails, size, page);
	}

	@ApiOperation(value = "유저의 팔로워 가져오기", notes = "유저**를** 팔로잉하는 사람들")
	@GetMapping(value = "{userId}/followers")
	public List<UserResponseDto> getFollowers(@PathVariable Integer userId,
	                                          @RequestParam(name = "size", defaultValue = "20") Integer size,
	                                          @RequestParam(name = "page", defaultValue = "0") Integer page) {
		return userService.getFollowers(userId, size, page);
	}

	@ApiOperation(value = "유저의 팔로잉 가져오기", notes = "유저**가** 팔로잉하는 사람들")
	@GetMapping(value = "{userId}/followings")
	public List<UserResponseDto> getFollowings(@PathVariable Integer userId,
	                                           @RequestParam(name = "size", defaultValue = "20") Integer size,
	                                           @RequestParam(name = "page", defaultValue = "0") Integer page) {
		return userService.getFollowings(userId, size, page);
	}

	@ApiOperation(value = "유저 검색하기", notes = "nickname에 검색 대상 문자열을 포함하는 유저 검색하기, page 기능 o (default size=20)")
	@GetMapping("/search/{nickname}")
	public List<UserResponseDto> searchUserByNickname(@AuthenticationPrincipal JwtUserDetails userDetails, @PathVariable String nickname,
	                                                  @RequestParam(name = "size", defaultValue = "20") Integer size,
	                                                  @RequestParam(name = "page", defaultValue = "0") Integer page) {
		return userService.searchUserByNickname(userDetails, nickname, size, page);
	}
}