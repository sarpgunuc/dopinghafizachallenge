package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.demo.config.JwtProperties;

@SpringBootApplication(scanBasePackages = "com.example.demo")
public class DopingimApplication {

	public static void main(String[] args) {
		SpringApplication.run(DopingimApplication.class, args);
		System.out.println("Projeye başlıyorum !");
		
		
	}

}
