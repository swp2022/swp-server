package com.swp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

	private static final String API_TITLE = "OStudy API";
	private static final String API_DESCRIPTION = "OStudy API Document";
	private static final String API_VERSION = "Version 1";

	private ApiInfo createApiInfo() {
		return new ApiInfoBuilder()
			.title(API_TITLE)
			.description(API_DESCRIPTION)
			.version(API_VERSION)
			.build();
	}

	@Bean
	public UiConfiguration uiConfiguration() {
		return UiConfigurationBuilder.builder()
			.docExpansion(DocExpansion.LIST)
			.build();
	}

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.OAS_30)
			.apiInfo(createApiInfo())
			.select()
			.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
			.paths(PathSelectors.any())
			.build();
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/docs", "/swagger-ui/index.html");
		registry.addRedirectViewController("/swagger-ui", "/swagger-ui/index.html");
	}
}
