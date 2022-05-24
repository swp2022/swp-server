package com.swp.user.domain;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swp.auth.dto.JwtUserDetails;
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

        @Transactional(readOnly = true)
        public List<UserResponseDto> getFollowers(JwtUserDetails userDetails) {
            User user = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
                    .orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
            return user.getFollowerList().stream()
                    .map(Relationship::getFromUser)
                    .map(UserResponseDto::from)
                    .collect(toList());
        }

        @Transactional(readOnly = true)
        public List<UserResponseDto> getFollowers(Integer userId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
            return user.getFollowerList().stream()
                    .map(Relationship::getFromUser)
                    .map(UserResponseDto::from)
                    .collect(toList());
        }

        @Transactional(readOnly = true)
        public List<UserResponseDto> getFollowings(JwtUserDetails userDetails) {
            User user = userRepository.findByProviderAndProviderId(userDetails.getProvider(), userDetails.getUsername())
                    .orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
            return user.getFollowingList().stream()
                    .map(Relationship::getToUser)
                    .map(UserResponseDto::from)
                    .collect(toList());
        }

        @Transactional(readOnly = true)
        public List<UserResponseDto> getFollowings(Integer userId) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
            return user.getFollowingList().stream()
                    .map(Relationship::getToUser)
                    .map(UserResponseDto::from)
                    .collect(toList());
        }

        @Transactional(readOnly = true)
        public UserResponseDto getUserByNickname(String nickname) {
            User user = userRepository.findByNickname(nickname)
                    .orElseThrow(() -> new UserNotFoundException("없는 유저입니다"));
            return UserResponseDto.from(user);
        }
}
