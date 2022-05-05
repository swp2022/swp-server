package com.swp.oauth;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.swp.oauth.dto.OAuth2UserInfo;
import com.swp.oauth.dto.OAuth2UserInfoFactory;
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
		Map<String, Object> attributes = oAuth2User.getAttributes();
		OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);

		System.out.println("attributes = " + attributes);

		Optional<User> optionalUser = userRepository.findByProviderAndProviderId(registrationId, userInfo.getId());
		optionalUser
			.map(found -> update(found, userInfo))
			.orElseGet(() -> register(userInfo, userRequest));

		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
			attributes, "id");
	}

	private User register(OAuth2UserInfo userInfo, OAuth2UserRequest userRequest) {
		return userRepository.save(User.builder()
			.role(Role.USER)
			.email(userInfo.getEmail())
			.nickname(userInfo.getNickname())
			.profileImage(userInfo.getProfileImage())
			.provider(userRequest.getClientRegistration().getRegistrationId())
			.providerId(userInfo.getId())
			.build());
	}

	private User update(User user, OAuth2UserInfo userInfo) {
		return user.updateProfile(userInfo.getNickname(), user.getProfileImage());
	}
}
