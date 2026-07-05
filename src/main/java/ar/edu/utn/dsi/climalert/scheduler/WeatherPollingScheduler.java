package ar.edu.utn.dsi.climalert.scheduler;

import ar.edu.utn.dsi.climalert.client.WeatherApiClient;
import ar.edu.utn.dsi.climalert.client.WeatherSnapshot;
import ar.edu.utn.dsi.climalert.domain.WeatherRecord;
import ar.edu.utn.dsi.climalert.service.WeatherHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WeatherPollingScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherPollingScheduler.class);

    private final WeatherApiClient weatherApiClient;
    private final WeatherHistoryService weatherHistoryService;

    public WeatherPollingScheduler(WeatherApiClient weatherApiClient,
                                   WeatherHistoryService weatherHistoryService) {
        this.weatherApiClient = weatherApiClient;
        this.weatherHistoryService = weatherHistoryService;
    }

    @Scheduled(
            fixedDelayString = "${climalert.scheduler.weather-poll-ms:300000}",
            initialDelayString = "${climalert.scheduler.initial-delay-ms:5000}"
    )
    public void pollWeather() {
        try {
            WeatherSnapshot snapshot = weatherApiClient.fetchCurrentWeather();
            WeatherRecord saved = weatherHistoryService.save(snapshot);
            LOGGER.info("Medicion climatica guardada. id={} ubicacion={} temp={} humedad={}",
                    saved.getId(), saved.getResolvedLocation(), saved.getTemperatureC(), saved.getHumidity());
        } catch (RuntimeException exception) {
            LOGGER.error("No se pudo obtener o guardar el clima actual: {}", exception.getMessage(), exception);
        }
    }
}
