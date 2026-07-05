package ar.edu.utn.dsi.climalert.service;

import ar.edu.utn.dsi.climalert.client.WeatherSnapshot;
import ar.edu.utn.dsi.climalert.domain.WeatherRecord;
import ar.edu.utn.dsi.climalert.repository.WeatherRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WeatherHistoryService {

    private final WeatherRecordRepository repository;

    public WeatherHistoryService(WeatherRecordRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public WeatherRecord save(WeatherSnapshot snapshot) {
        WeatherRecord record = new WeatherRecord();
        record.setRequestedLocation(snapshot.requestedLocation());
        record.setResolvedLocation(snapshot.resolvedLocation());
        record.setObservedAt(snapshot.observedAt());
        record.setTemperatureC(snapshot.temperatureC());
        record.setHumidity(snapshot.humidity());
        record.setConditionText(snapshot.conditionText());
        record.setFeelsLikeC(snapshot.feelsLikeC());
        record.setWindKph(snapshot.windKph());
        record.setRawPayload(snapshot.rawPayload());
        return repository.save(record);
    }
}
