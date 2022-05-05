package com.swp.oauth.dto;

import java.util.Map;

import lombok.Getter;

@Getter
public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

	private final Long id;

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
		return attributes.get("email").toString();
	}

	@Override
	public String getNickname() {
		return ((Map<String, Object>)attributes.get("profile")).get("nickname").toString();
	}

	@Override
	public String getProfileImage() {
		return ((Map<String, Object>)attributes.get("profile")).get("thumbnail_image_url").toString();
	}
}
