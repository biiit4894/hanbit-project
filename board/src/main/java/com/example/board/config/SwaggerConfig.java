package com.example.board.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@EnableWebMvc
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(apiInfo());
    }

    private io.swagger.v3.oas.models.info.Info apiInfo() {
        return new Info()
                .title("게시판 서비스 API") // API 제목
                .description("게시판 서비스 API 목록입니다.") // API 설명
                .version("v1.0.0"); // API 버전
    }
}
