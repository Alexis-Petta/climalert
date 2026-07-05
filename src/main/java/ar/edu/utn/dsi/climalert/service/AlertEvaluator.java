package ar.edu.utn.dsi.climalert.service;

import ar.edu.utn.dsi.climalert.config.ClimalertProperties;
import ar.edu.utn.dsi.climalert.domain.WeatherRecord;
import org.springframework.stereotype.Service;

@Service
public class AlertEvaluator {

    private final ClimalertProperties properties;

    public AlertEvaluator(ClimalertProperties properties) {
        this.properties = properties;
    }

    public boolean isCritical(WeatherRecord record) {
        return record.getTemperatureC() > properties.getAlert().getMaxTemperatureC()
                && record.getHumidity() > properties.getAlert().getMinHumidity();
    }
}
