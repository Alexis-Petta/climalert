package ar.edu.utn.dsi.climalert.service;

import ar.edu.utn.dsi.climalert.config.ClimalertProperties;
import ar.edu.utn.dsi.climalert.domain.WeatherRecord;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlertEvaluatorTest {

    @Test
    void returnsTrueWhenTemperatureAndHumidityAreAboveThresholds() {
        AlertEvaluator evaluator = new AlertEvaluator(defaultProperties());
        WeatherRecord record = record(36, 61);

        assertTrue(evaluator.isCritical(record));
    }

    @Test
    void returnsFalseWhenTemperatureIsEqualToThreshold() {
        AlertEvaluator evaluator = new AlertEvaluator(defaultProperties());
        WeatherRecord record = record(35, 70);

        assertFalse(evaluator.isCritical(record));
    }

    @Test
    void returnsFalseWhenHumidityIsEqualToThreshold() {
        AlertEvaluator evaluator = new AlertEvaluator(defaultProperties());
        WeatherRecord record = record(40, 60);

        assertFalse(evaluator.isCritical(record));
    }

    @Test
    void returnsFalseWhenOnlyOneConditionIsCritical() {
        AlertEvaluator evaluator = new AlertEvaluator(defaultProperties());
        WeatherRecord record = record(36, 50);

        assertFalse(evaluator.isCritical(record));
    }

    private ClimalertProperties defaultProperties() {
        ClimalertProperties properties = new ClimalertProperties();
        properties.getAlert().setMaxTemperatureC(35);
        properties.getAlert().setMinHumidity(60);
        return properties;
    }

    private WeatherRecord record(double temperatureC, int humidity) {
        WeatherRecord record = new WeatherRecord();
        record.setTemperatureC(temperatureC);
        record.setHumidity(humidity);
        return record;
    }
}
