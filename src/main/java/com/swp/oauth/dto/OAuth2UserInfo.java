package com.swp.oauth.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class OAuth2UserInfo {

	protected Map<String, Object> attributes;

	public abstract String getId();

	public abstract String getEmail();

	public abstract String getNickname();

	public abstract String getProfileImage();

}
