package com.swp.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.swp.auth.JwtAuthenticationEntryPoint;
import com.swp.auth.JwtAuthenticationFilter;
import com.swp.auth.JwtProvider;
import com.swp.oauth.OAuth2SuccessHandler;
import com.swp.oauth.ThirdPartyOAuth2UserService;
import com.swp.user.domain.Role;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final ThirdPartyOAuth2UserService thirdPartyOAuth2UserService;
	private final OAuth2SuccessHandler successHandler;
	private final JwtProvider jwtProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().mvcMatchers(HttpMethod.POST, "/v1/auth/renew");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
			.and()
			.csrf()
			.disable()

			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()

			.exceptionHandling()
			.authenticationEntryPoint(jwtAuthenticationEntryPoint)

			.and()
			.authorizeRequests()
			.antMatchers("/v1/**").hasRole(Role.USER.toString())
			.anyRequest().authenticated()
			.and()

			.oauth2Login()
			.userInfoEndpoint()
			.userService(thirdPartyOAuth2UserService)
			.and()
			.successHandler(successHandler);

		http.addFilterBefore(new JwtAuthenticationFilter(jwtProvider, jwtAuthenticationEntryPoint),
			UsernamePasswordAuthenticationFilter.class);
	}
}
