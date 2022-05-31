package com.swp.user.domain;

import com.swp.auth.dto.JwtUserDetails;
import com.swp.user.dto.UserResponseDto;
import com.swp.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final RelationshipRepository relationshipRepository;

	public UserResponseDto getUser(JwtUserDetails userDetails) {
		User user = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
			.orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
		return UserResponseDto.from(user);
	}

	@Transactional(readOnly = true)
	public List<UserResponseDto> getFollowers(JwtUserDetails userDetails, Integer size, Integer page) {
		Pageable pageable = PageRequest.of(page, size);
		User user = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
			.orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
		return relationshipRepository.findByToUser(user, pageable).stream()
			.map(Relationship::getFromUser)
			.map(UserResponseDto::from)
			.collect(toList());
	}

	@Transactional(readOnly = true)
	public List<UserResponseDto> getFollowers(Integer userId, Integer size, Integer page) {
		Pageable pageable = PageRequest.of(page, size);
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
		return relationshipRepository.findByToUser(user, pageable).stream()
			.map(Relationship::getFromUser)
			.map(UserResponseDto::from)
			.collect(toList());
	}

	@Transactional(readOnly = true)
	public List<UserResponseDto> getFollowings(JwtUserDetails userDetails, Integer size, Integer page) {
		Pageable pageable = PageRequest.of(page, size);
		User user = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
			.orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
		return relationshipRepository.findByFromUser(user, pageable).stream()
			.map(Relationship::getToUser)
			.map(UserResponseDto::from)
			.collect(toList());
	}

	@Transactional(readOnly = true)
	public List<UserResponseDto> getFollowings(Integer userId, Integer size, Integer page) {
		Pageable pageable = PageRequest.of(page, size);
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
		return relationshipRepository.findByFromUser(user, pageable).stream()
			.map(Relationship::getToUser)
			.map(UserResponseDto::from)
			.collect(toList());
	}

	@Transactional(readOnly = true)
	public List<UserResponseDto> searchUserByNickname(JwtUserDetails userDetails, String nickname, Integer size, Integer page) {
		Pageable pageable = PageRequest.of(page, size);
		List<User> userList = userRepository.findByNicknameStartsWith(nickname, pageable);
		List<User> followingList = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
			.orElseThrow(() -> new UserNotFoundException("없는 유저입니다"))
			.getFollowingList().stream()
			.map(Relationship::getToUser)
			.collect(toList());
		return userList.stream()
			.map(user -> {
				return UserResponseDto.from(user, followingList.contains(user));
			})
			.collect(toList());
	}
}
