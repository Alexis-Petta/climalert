package ar.edu.utn.dsi.climalert.client;

import ar.edu.utn.dsi.climalert.config.ClimalertProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

@Service
public class WeatherApiClient {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final ClimalertProperties properties;

    public WeatherApiClient(RestClient weatherApiRestClient,
                            ObjectMapper objectMapper,
                            ClimalertProperties properties) {
        this.restClient = weatherApiRestClient;
        this.objectMapper = objectMapper;
        this.properties = properties;
    }

    public WeatherSnapshot fetchCurrentWeather() {
        String apiKey = properties.getWeatherApi().getApiKey();
        if (!StringUtils.hasText(apiKey)) {
            throw new IllegalStateException("WEATHER_API_KEY no esta configurada");
        }

        WeatherApiResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam("key", apiKey)
                        .queryParam("q", properties.getWeatherApi().getLocation())
                        .queryParam("aqi", "no")
                        .build())
                .retrieve()
                .body(WeatherApiResponse.class);

        if (response == null || response.current() == null || response.location() == null) {
            throw new IllegalStateException("WeatherAPI no devolvio datos climaticos validos");
        }

        return toSnapshot(response);
    }

    private WeatherSnapshot toSnapshot(WeatherApiResponse response) {
        WeatherApiResponse.Location location = response.location();
        WeatherApiResponse.Current current = response.current();

        Instant observedAt = current.lastUpdatedEpoch() != null
                ? Instant.ofEpochSecond(current.lastUpdatedEpoch())
                : Instant.now();

        String resolvedLocation = String.join(", ",
                safe(location.name()),
                safe(location.region()),
                safe(location.country())
        ).replaceAll("(, )+", ", ").replaceAll("^, |, $", "");

        return new WeatherSnapshot(
                properties.getWeatherApi().getLocation(),
                resolvedLocation,
                observedAt,
                requiredDouble(current.temperatureC(), "temperatura"),
                requiredInteger(current.humidity(), "humedad"),
                current.condition() != null ? current.condition().text() : "Sin descripcion",
                current.feelsLikeC() != null ? current.feelsLikeC() : 0,
                current.windKph() != null ? current.windKph() : 0,
                serialize(response)
        );
    }

    private String serialize(WeatherApiResponse response) {
        try {
            return objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }

    private double requiredDouble(Double value, String fieldName) {
        if (value == null) {
            throw new IllegalStateException("WeatherAPI no devolvio " + fieldName);
        }
        return value;
    }

    private int requiredInteger(Integer value, String fieldName) {
        if (value == null) {
            throw new IllegalStateException("WeatherAPI no devolvio " + fieldName);
        }
        return value;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
