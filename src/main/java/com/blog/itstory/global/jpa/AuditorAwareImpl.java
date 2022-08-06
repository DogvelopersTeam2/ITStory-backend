package com.blog.itstory.global.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final HttpServletRequest httpServletRequest;


    @Override
    public Optional<String> getCurrentAuditor() {

        return Optional.of("someone");
    }
}
