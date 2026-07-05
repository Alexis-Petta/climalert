package ar.edu.utn.dsi.climalert;

import ar.edu.utn.dsi.climalert.config.ClimalertProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(ClimalertProperties.class)
public class ClimalertApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClimalertApplication.class, args);
    }
}
