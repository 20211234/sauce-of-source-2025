package com.example.sauceofsource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import com.example.sauceofsource.persistence.ProductRepository;
import com.example.sauceofsource.model.ProductEntity;

@SpringBootApplication
public class SauceofsourceApplication {
	public static void main(String[] args) {
		SpringApplication.run(SauceofsourceApplication.class, args);
	}
}