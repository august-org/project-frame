package com.august.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AUGUST
 * @description TODO
 * @date 2020/10/15
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Value("${swagger.enable}")
    private boolean enable;

    @Bean
    public Docket createDocket(){
        List<Parameter> parameters = new ArrayList<>();
        ParameterBuilder accessToken = new ParameterBuilder();
        ParameterBuilder refreshToken = new ParameterBuilder();
        accessToken.name("authorization").description("程序员自测的时候动态传输AccessToken入口")
                .modelRef(new ModelRef("String")).parameterType("header").required(false);
        refreshToken.name("refreshToken").description("程序员自测的时候动态传输refreshToken入口")
                .modelRef(new ModelRef("String")).parameterType("header").required(false);
        parameters.add(accessToken.build());
        parameters.add(refreshToken.build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.august.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(parameters)
                .enable(enable);
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("后端接口文档")
                .description("")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }
}
