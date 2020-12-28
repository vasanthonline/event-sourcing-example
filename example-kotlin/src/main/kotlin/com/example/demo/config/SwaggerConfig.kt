package com.example.demo.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.service.ApiInfo

import springfox.documentation.builders.PathSelectors

import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact

import springfox.documentation.spi.DocumentationType

import springfox.documentation.spring.web.plugins.Docket

import springfox.documentation.swagger2.annotations.EnableSwagger2
import java.util.*


@Configuration
@EnableSwagger2
class SwaggerConfig() {
    @Bean
    fun apiDocket(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.example.demo"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(apiInfo)
    }

    private val apiInfo: ApiInfo
        private get() = ApiInfo(
            "Event Sourcing using Axon and Spring Boot",
            "App to demonstrate Event Sourcing using Axon and Spring Boot",
            "1.0.0",
            "Terms of Service",
            Contact("Saurabh Dashora", "progressivecoder.com", "coder.progressive@gmail.com"),
            "",
            "",
            Collections.emptyList()
        )
}