package ar.edu.utn.dsi.climalert.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WeatherApiResponse(
        Location location,
        Current current
) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Location(
            String name,
            String region,
            String country,
            @JsonProperty("tz_id") String timezoneId,
            String localtime
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Current(
            @JsonProperty("last_updated_epoch") Long lastUpdatedEpoch,
            @JsonProperty("last_updated") String lastUpdated,
            @JsonProperty("temp_c") Double temperatureC,
            Integer humidity,
            Condition condition,
            @JsonProperty("wind_kph") Double windKph,
            @JsonProperty("feelslike_c") Double feelsLikeC
    ) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Condition(
            String text,
            String icon,
            Integer code
    ) {
    }
}
