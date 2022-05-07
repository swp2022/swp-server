package com.swp.oauth.dto;

import java.util.Map;

import lombok.Getter;

@Getter
public class KakaoOAuth2UserAttribute extends OAuth2UserAttribute {

	private final Long id;
	private Map<String, Object> kakaoAccount;

	public KakaoOAuth2UserAttribute(Map<String, Object> attributes, String provider) {
		super(attributes, provider);
		this.kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
		this.id = (Long)attributes.get("id");
	}

	@Override
	public String getProviderId() {
		return id.toString();
	}

	@Override
	public String getEmail() {
		return kakaoAccount.get("email").toString();
	}

	@Override
	public String getNickname() {
		return ((Map<String, Object>)kakaoAccount.get("profile")).get("nickname").toString();
	}

	@Override
	public String getProfileImage() {
		return ((Map<String, Object>)kakaoAccount.get("profile")).get("thumbnail_image_url").toString();
	}
}
