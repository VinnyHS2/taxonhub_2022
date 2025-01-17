package com.eng.taxonhub.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
	
	@Bean(name = "webClientGbif")
	public WebClient webClientGbif(WebClient.Builder builder) {
		return builder.baseUrl("http://api.gbif.org/v1/")
		    .exchangeStrategies(ExchangeStrategies.builder()
		            .codecs(configurer -> configurer
		                    .defaultCodecs()
		                    .maxInMemorySize(16 * 1024 * 1024))
		            .build())
		    .build();
	}
	
}
