package com.xchangecurrency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CurrencyExchangeApiApplication {

	public static void main(final String[] args) {
		SpringApplication.run(CurrencyExchangeApiApplication.class, args);
	}

}
