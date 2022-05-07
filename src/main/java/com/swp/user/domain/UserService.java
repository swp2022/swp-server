package com.swp.user.domain;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.swp.user.dto.UserDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserDto getUser(String provider, String providerId) {
		User user = userRepository.findByProviderAndProviderId(provider, providerId)
			.orElseThrow(() -> new NoSuchElementException("없는 유저입니다"));
		System.out.println("user = " + user);
		return UserDto.builder()
			.nickname(user.getNickname())
			.email(user.getEmail())
			.profileImage(user.getProfileImage())
			.role(user.getRole())
			.build();
	}
}
