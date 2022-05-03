package com.swp.oauth.dto;

import java.util.Map;

import lombok.Getter;

@Getter
public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

	private Long id;

	public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
		super((Map<String, Object>)attributes.get("kakao_account"));
		this.id = (Long)attributes.get("id");
	}

	@Override
	public String getId() {
		return id.toString();
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}

	@Override
	public String getNickname() {
		return (String)((Map<String, Object>)attributes.get("profile")).get("nickname");
	}

	@Override
	public String getProfileImage() {
		return (String)((Map<String, Object>)attributes.get("profile")).get("thumbnail_image_url");
	}
}
