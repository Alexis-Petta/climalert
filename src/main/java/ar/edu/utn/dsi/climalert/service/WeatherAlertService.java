package ar.edu.utn.dsi.climalert.service;

import ar.edu.utn.dsi.climalert.config.ClimalertProperties;
import ar.edu.utn.dsi.climalert.domain.WeatherAlert;
import ar.edu.utn.dsi.climalert.domain.WeatherRecord;
import ar.edu.utn.dsi.climalert.repository.WeatherAlertRepository;
import ar.edu.utn.dsi.climalert.repository.WeatherRecordRepository;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeatherAlertService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherAlertService.class);

    private final WeatherRecordRepository weatherRecordRepository;
    private final WeatherAlertRepository weatherAlertRepository;
    private final AlertEvaluator alertEvaluator;
    private final AlertNotifier alertNotifier;
    private final ClimalertProperties properties;

    public WeatherAlertService(WeatherRecordRepository weatherRecordRepository,
                               WeatherAlertRepository weatherAlertRepository,
                               AlertEvaluator alertEvaluator,
                               AlertNotifier alertNotifier,
                               ClimalertProperties properties) {
        this.weatherRecordRepository = weatherRecordRepository;
        this.weatherAlertRepository = weatherAlertRepository;
        this.alertEvaluator = alertEvaluator;
        this.alertNotifier = alertNotifier;
        this.properties = properties;
    }

    @Transactional
    public void analyzeLatestWeather() {
        Optional<WeatherRecord> latestRecord = weatherRecordRepository.findTopByOrderByObservedAtDescCreatedAtDesc();

        if (latestRecord.isEmpty()) {
            LOGGER.info("No hay mediciones climaticas disponibles para analizar");
            return;
        }

        WeatherRecord record = latestRecord.get();

        if (!alertEvaluator.isCritical(record)) {
            LOGGER.info("Clima sin alerta. Temperatura={} Humedad={}", record.getTemperatureC(), record.getHumidity());
            return;
        }

        if (weatherAlertRepository.existsByWeatherRecord_Id(record.getId())) {
            LOGGER.info("La medicion {} ya tiene una alerta generada", record.getId());
            return;
        }

        WeatherAlert alert = new WeatherAlert();
        alert.setWeatherRecord(record);
        alert.setTemperatureC(record.getTemperatureC());
        alert.setHumidity(record.getHumidity());
        alert.setRecipients(String.join(",", properties.getNotification().getRecipients()));
        alert = weatherAlertRepository.save(alert);

        NotificationResult result = alertNotifier.notify(alert, record);
        alert.setNotificationStatus(result.status());
        alert.setNotificationDetail(result.detail());
        weatherAlertRepository.save(alert);

        LOGGER.warn("Alerta generada para medicion {}. Estado notificacion={}", record.getId(), result.status());
    }
}
