package com.swp.user.dto;

import com.swp.user.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
	private Role role;
	private String email;
	private String nickname;
	private String profileImage;
}
