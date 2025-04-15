package edu.ifmg.br.produto.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @OpenAPIDefinition
    @Configuration
    public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
            return new OpenAPI().info(new Info().tittle("Produto API"))
                    .version("v1")
                    .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                    .description("API for managing products and categories"));
        }
    }

