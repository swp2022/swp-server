package com.swp.user.domain;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.swp.user.dto.UserResponseDto;
import com.swp.user.exception.UserNotFoundException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserResponseDto getUser(JwtUserDetails userDetails) {
		User user = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
			.orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
		return UserResponseDto.from(user);
	}
}
