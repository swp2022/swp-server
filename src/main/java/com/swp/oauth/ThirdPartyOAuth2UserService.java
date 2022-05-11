package com.swp.oauth;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swp.oauth.dto.OAuth2UserAttribute;
import com.swp.oauth.dto.OAuth2UserAttributeFactory;
import com.swp.user.domain.Role;
import com.swp.user.domain.User;
import com.swp.user.domain.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThirdPartyOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final UserRepository userRepository;

	@Transactional
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = oAuth2UserService.loadUser(userRequest);
		return handleResponse(oAuth2User, userRequest);
	}

	private OAuth2User handleResponse(OAuth2User oAuth2User, OAuth2UserRequest userRequest) {
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails()
			.getUserInfoEndpoint()
			.getUserNameAttributeName();

		OAuth2UserAttribute userAttribute = OAuth2UserAttributeFactory.getOAuth2UserAttribute(registrationId,
			oAuth2User.getAttributes());

		User user = userRepository.findByProviderAndProviderId(registrationId, userAttribute.getProviderId())
			.map(found -> update(found, userAttribute))
			.orElseGet(() -> register(userAttribute, userRequest));

		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRole().getValue())),
			userAttribute.getAttributes(), userNameAttributeName);
	}

	private User register(OAuth2UserAttribute userAttribute, OAuth2UserRequest userRequest) {
		return userRepository.save(User.builder()
			.role(Role.USER)
			.email(userAttribute.getEmail())
			.nickname(userAttribute.getNickname())
			.profileImage(userAttribute.getProfileImage())
			.provider(userRequest.getClientRegistration().getRegistrationId())
			.providerId(userAttribute.getProviderId())
			.build());
	}

	private User update(User user, OAuth2UserAttribute userAttribute) {
		return user.updateProfile(userAttribute.getNickname(), user.getProfileImage());
	}
}
