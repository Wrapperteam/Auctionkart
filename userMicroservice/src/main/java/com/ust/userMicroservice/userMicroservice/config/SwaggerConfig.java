package com.ust.userMicroservice.userMicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ust.userMicroservice.userMicroservice"))
                .paths(regex("/api.*"))
                .build()
                .apiInfo(metaInfo());

    }

    private ApiInfo metaInfo() {
        ApiInfo info = new ApiInfo("AuctionKart", "Online Auction API", "1.0"
                , "Terms of Service",
                new Contact("UserService", "https://www.auction.com/",
                        "sg@gmail.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licesen.html"
        );
        return info;
    }
}
