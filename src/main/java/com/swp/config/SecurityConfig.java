package com.swp.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.swp.oauth.OAuth2SuccessHandler;
import com.swp.oauth.ThirdPartyOAuth2UserService;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final ThirdPartyOAuth2UserService thirdPartyOAuth2UserService;
	private final OAuth2SuccessHandler successHandler;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
			.mvcMatchers("/", "/login/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and()
			.oauth2Login()
			.userInfoEndpoint()
			.userService(thirdPartyOAuth2UserService)
			.and()
			.successHandler(successHandler);
	}

	/*@Bean
	public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties) {

		List<ClientRegistration> registrations = oAuth2ClientProperties.getRegistration().keySet().stream()
			.map(client -> getRegistration(oAuth2ClientProperties, client))
			.filter(Objects::nonNull).collect(Collectors.toList());

		return new InMemoryClientRegistrationRepository(registrations);
	}

	public ClientRegistration getRegistration(OAuth2ClientProperties oAuth2ClientProperties, String client) {

		OAuth2ClientProperties.Registration registration = oAuth2ClientProperties.getRegistration().get(client);
		String clientId = registration.getClientId();
		String clientSecret = registration.getClientSecret();

		if (clientId == null) {
			return null;
		}

		if ("kakao".equals(client)) {
			return ThirdPartyOAuth2Provider.KAKAO.getBuilder(client)
				.clientId(clientId)
				.clientSecret(clientSecret).build();
		}
		return null;
	}*/

}
