package com.blog.itstory.api.health.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiHealthCheckController {

    @GetMapping("/api/health/item1")
    public String healthCheck(){
        return "zzzz";
    }
}
