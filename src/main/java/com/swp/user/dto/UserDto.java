package com.swp.user.dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;

import org.springframework.data.annotation.ReadOnlyProperty;

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
	@Email
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false, unique = true)
	private String nickname;
	private String profileImage;
}
