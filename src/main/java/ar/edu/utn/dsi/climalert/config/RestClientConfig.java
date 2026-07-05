package ar.edu.utn.dsi.climalert.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Bean
    RestClient weatherApiRestClient(ClimalertProperties properties) {
        return RestClient.builder()
                .baseUrl(properties.getWeatherApi().getBaseUrl())
                .build();
    }
}
