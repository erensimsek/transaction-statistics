package com.eren.n26.hiring.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 
 * @author artesdev team
 *
 */
@Configuration
@EnableSwagger2

public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.eren.n26"))
				.paths(PathSelectors.ant("/api/v1/**")).build().apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo("Transaction Statistics REST API",
				"API is to \n" +
						"calculate realtime statistic from the last 60 seconds. There will be two APIs, one of them is \n" +
						"called every time a transaction is made. It is also the sole input of this rest API. The other one \n" +
						"returns the statistic based of the transactions of the last 60 seconds.", "By Eren Simsek v 1.0",
				"There is no terms of service", "erensimsek@gmail.com | www.erensimsek.com", "MIT License", "http://www.erensimsek.com");

	}
}
