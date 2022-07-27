package com.risingtest.wanted.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebMvc
public class SwaggerConfig {

    private ApiInfo apiInfo() {

        return new ApiInfoBuilder()
                .title("원티드")
                .description("원티드 프로젝트 API")
                .version("0.0.1")
                .build();
    }

    @Bean
    public Docket commonApi() {
        RequestParameterBuilder requestParameterBuilder = new RequestParameterBuilder();
        // header
        RequestParameter requestParameter =
                requestParameterBuilder
                .in(ParameterType.HEADER)
                .name("X-ACCESS-TOKEN")
                .description("인증 토큰")
                .build();
        List<RequestParameter> list = new ArrayList<>();
        list.add(requestParameter);

        return new Docket(DocumentationType.OAS_30)
                .globalRequestParameters(list)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors
                        .basePackage("com.risingtest.wanted"))
                .paths(PathSelectors.ant("/**"))
                .build();
    }

}