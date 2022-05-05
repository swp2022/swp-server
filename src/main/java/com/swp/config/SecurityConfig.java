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
}
