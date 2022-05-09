package com.swp.oauth.dto;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class OAuth2UserAttributeFactory {

	public static OAuth2UserAttribute getOAuth2UserAttribute(String registrationId, Map<String, Object> attributes) {
		if (registrationId.equals("kakao"))
			return new KakaoOAuth2UserAttribute(attributes, registrationId);
		else
			throw new OAuth2AuthenticationException("Invalid authorization provider: " + registrationId);
	}
}
