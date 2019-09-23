package com.kakaopay.homework.internetbanking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class InternetBankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternetBankingApplication.class, args);
	}

}
