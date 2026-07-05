package ar.edu.utn.dsi.climalert.scheduler;

import ar.edu.utn.dsi.climalert.service.WeatherAlertService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AlertAnalysisScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlertAnalysisScheduler.class);

    private final WeatherAlertService weatherAlertService;

    public AlertAnalysisScheduler(WeatherAlertService weatherAlertService) {
        this.weatherAlertService = weatherAlertService;
    }

    @Scheduled(
            fixedDelayString = "${climalert.scheduler.alert-check-ms:60000}",
            initialDelayString = "${climalert.scheduler.initial-delay-ms:5000}"
    )
    public void analyzeWeatherAlerts() {
        try {
            weatherAlertService.analyzeLatestWeather();
        } catch (RuntimeException exception) {
            LOGGER.error("No se pudo analizar la ultima medicion climatica: {}", exception.getMessage(), exception);
        }
    }
}
