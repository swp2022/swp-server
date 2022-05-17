package com.swp.user.domain;

import java.util.NoSuchElementException;

import com.swp.auth.dto.JwtUserDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.swp.user.dto.UserResponseDto;
import com.swp.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserResponseDto getUser(String provider, String providerId) {
		User user = userRepository.findByProviderAndProviderId(provider, providerId)
			.orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
		return UserResponseDto.builder()
			.nickname(user.getNickname())
			.email(user.getEmail())
			.profileImage(user.getProfileImage())
			.role(user.getRole())
			.build();
	}
}
