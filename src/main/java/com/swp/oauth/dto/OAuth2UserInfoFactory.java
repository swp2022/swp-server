package com.swp.oauth.dto;

import java.util.Map;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class OAuth2UserInfoFactory {

	public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equals("kakao"))
			return new KakaoOAuth2UserInfo(attributes);
		else
			throw new OAuth2AuthenticationException("Invalid authorization provider: " + registrationId);
	}
}
