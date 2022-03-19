package com.teamy.mini.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket api() { // Docket : 스웨거 설정 Bean
        return new Docket(DocumentationType.OAS_30)
                .useDefaultResponseMessages(false) // Swagger 에서 제공해주는 기본 응답 코드. false 로 하면 기본 응답 코드 노출하지 않음.
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.teamy.mini.controller")) // api 스펙이 작성 되어있는 패키지 설정
                .paths(PathSelectors.any()) // apis 에 있는 api 중 특정 path 를 선택
                .build()
                .apiInfo(apiInfo()); // swagger ui 로 노출할 정보
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Mini Project Swagger")
                .description("Mini Project API config")
                .version("1.0")
                .build();
    }
}
