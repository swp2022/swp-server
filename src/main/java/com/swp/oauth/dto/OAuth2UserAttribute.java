package com.swp.oauth.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public abstract class OAuth2UserAttribute {

	private static final String PROVIDER_KEY = "provider";
	protected Map<String, Object> attributes;

	public OAuth2UserAttribute(Map<String, Object> attributes, String provider) {
		this.attributes = new HashMap<>(attributes);
		this.attributes.put(PROVIDER_KEY, provider);
	}

	public abstract String getProviderId();

	public abstract String getEmail();

	public abstract String getNickname();

	public abstract String getProfileImage();
}
