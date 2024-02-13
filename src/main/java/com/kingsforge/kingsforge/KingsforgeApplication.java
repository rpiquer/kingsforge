package com.kingsforge.kingsforge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@SpringBootApplication
public class KingsforgeApplication {

	@Bean
	HiddenHttpMethodFilter hiddenHttpMethodFilter() {
    return new HiddenHttpMethodFilter();
	}

	public static void main(String[] args) {
		SpringApplication.run(KingsforgeApplication.class, args);
	}

}
