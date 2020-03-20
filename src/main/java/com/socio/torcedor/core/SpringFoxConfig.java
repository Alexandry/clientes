package com.socio.torcedor.core;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.socio.torcedor.domain.model.Clube;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig implements WebMvcConfigurer {

	
	private static final String CLASSPATH_META_INF_RESOURCES_WEBJARS = "classpath:/META-INF/resources/webjars/";
	private static final String CLASSPATH_META_INF_RESOURCES = "classpath:/META-INF/resources/";

	@Bean
	public Docket apiDocket() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.socio.torcedor.controller"))
				.build()
				.apiInfo(apiInfo())
				.tags(new Tag("Clientes", "Gereciamento de clientes"));
	}
	
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Clientes API")
				.description("API de clientes")
				.version("1")
				.contact(new Contact("Alexandre", "", "ale_and5@hotmail.com"))
				.build();
		
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("swagger-ui.html")
				.addResourceLocations(CLASSPATH_META_INF_RESOURCES);
		
		registry.addResourceHandler("/webjars/**")
		.addResourceLocations(CLASSPATH_META_INF_RESOURCES_WEBJARS);
	}
}
