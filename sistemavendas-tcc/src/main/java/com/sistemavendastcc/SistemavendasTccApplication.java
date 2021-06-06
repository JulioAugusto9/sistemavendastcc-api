package com.sistemavendastcc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SistemavendasTccApplication {

	public static void main(String[] args) {
		SpringApplication.run(SistemavendasTccApplication.class, args);
		System.out.println("login: admin, senha: admin");
	}

}
