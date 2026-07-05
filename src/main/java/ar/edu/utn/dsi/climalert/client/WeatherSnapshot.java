package ar.edu.utn.dsi.climalert.client;

import java.time.Instant;

public record WeatherSnapshot(
        String requestedLocation,
        String resolvedLocation,
        Instant observedAt,
        double temperatureC,
        int humidity,
        String conditionText,
        double feelsLikeC,
        double windKph,
        String rawPayload
) {
}
