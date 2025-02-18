package com.likelion.officialsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Likelion13thOfficialSiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(Likelion13thOfficialSiteApplication.class, args);
	}

}



