package com.swp.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDto {

	private String accessToken;
	private String refreshToken;
}
