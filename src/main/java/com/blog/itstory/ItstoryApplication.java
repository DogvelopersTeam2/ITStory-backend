package com.blog.itstory;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItstoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItstoryApplication.class, args);
	}

}
