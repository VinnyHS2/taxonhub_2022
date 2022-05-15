package com.eng.taxonhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("com.eng")
public class TaxonhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxonhubApplication.class, args);
	}

}
