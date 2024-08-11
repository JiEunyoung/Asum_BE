package com.example.myproject;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.myproject.mapper")
@ComponentScan(basePackages = {"com.example.myproject"})
@OpenAPIDefinition(
		info = @Info(
				title = "Asum API",
				version = "1.0",
				description = "Documentation for Asum API"
		)
)
public class MyprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyprojectApplication.class, args);
	}

}
