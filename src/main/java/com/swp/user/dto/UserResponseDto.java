package com.swp.user.dto;

import com.swp.user.domain.Role;
import com.swp.user.domain.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
	private Integer userId;
	private Role role;
	private String email;
	private String nickname;
	private String profileImage;
	private Boolean isFollowing;
	public static UserResponseDto from(User user) {
		return UserResponseDto.builder()
			.userId(user.getId())
			.role(user.getRole())
			.nickname(user.getNickname())
			.email(user.getEmail())
			.profileImage(user.getProfileImage())
			.build();
	}
	public static UserResponseDto from(User user,Boolean isFollowing){
		return UserResponseDto.builder()
				.userId(user.getId())
				.role(user.getRole())
				.nickname(user.getNickname())
				.email(user.getEmail())
				.profileImage(user.getProfileImage())
				.isFollowing(isFollowing)
				.build();
	}
}
