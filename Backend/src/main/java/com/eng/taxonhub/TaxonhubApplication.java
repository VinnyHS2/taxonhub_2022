package com.eng.taxonhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.eng.taxonhub.config.StorageConfig;


@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("com.eng")
@EnableConfigurationProperties(StorageConfig.class)
public class TaxonhubApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaxonhubApplication.class, args);
	}

}
