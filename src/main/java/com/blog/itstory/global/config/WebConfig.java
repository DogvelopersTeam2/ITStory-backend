package com.blog.itstory.global.config;

import com.blog.itstory.global.converter.CategoryConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // 이 경로에 대해서
                .allowedOrigins("*") // CORS 모두 미적용
                .allowedMethods(
                        HttpMethod.GET.name()
                        , HttpMethod.POST.name()
                        , HttpMethod.PUT.name()
                        , HttpMethod.PATCH.name()
                        , HttpMethod.DELETE.name()
                        , HttpMethod.OPTIONS.name()
                );
    }

    // RequestParam 에서 Enum Category 를 바로 받기 위해,
    // String -> ENUM 변환하는 컨버터를 따로 구현해 등록한다.
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new CategoryConverter());
    }
}














