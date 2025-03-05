package org.example.erudio.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI  customOpenAPI(){
        return new OpenAPI()
                .info(
                        new Info()
                            .title("RESTFUL APIT with java")
                            .version("v1")
                            .description("Erudio API com docker")
//                                .termsOfService("https://github.com/")
//                                .license(new License().name("Apache 2.0").url("https://github.com/"))
                );
    }
}
