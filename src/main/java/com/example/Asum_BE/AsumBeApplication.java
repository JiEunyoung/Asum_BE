package com.example.Asum_BE;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.example.Asum_BE.board.mapper", "com.example.Asum_BE.comment.mapper", "com.example.Asum_BE.quote.mapper"})
public class AsumBeApplication {
	public static void main(String[] args) {
		SpringApplication.run(AsumBeApplication.class, args);
	}
}
