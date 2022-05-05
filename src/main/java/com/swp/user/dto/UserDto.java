package com.swp.user.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;

import org.springframework.data.annotation.ReadOnlyProperty;

import com.swp.user.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

	@ReadOnlyProperty
	private Integer id;
	@ReadOnlyProperty
	private Role role;
	@Email
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false, unique = true)
	private String nickname;
	private String profileImage;
}
