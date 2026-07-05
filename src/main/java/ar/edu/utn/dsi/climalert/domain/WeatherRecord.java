package ar.edu.utn.dsi.climalert.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.Instant;

@Entity
@Table(name = "weather_records")
public class WeatherRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String requestedLocation;

    @Column(nullable = false)
    private String resolvedLocation;

    @Column(nullable = false)
    private Instant observedAt;

    @Column(nullable = false)
    private double temperatureC;

    @Column(nullable = false)
    private int humidity;

    @Column(nullable = false)
    private String conditionText;

    @Column(nullable = false)
    private double feelsLikeC;

    @Column(nullable = false)
    private double windKph;

    @Lob
    @Column(nullable = false)
    private String rawPayload;

    @Column(nullable = false)
    private Instant createdAt;

    @PrePersist
    void prePersist() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }

    public Long getId() {
        return id;
    }

    public String getRequestedLocation() {
        return requestedLocation;
    }

    public void setRequestedLocation(String requestedLocation) {
        this.requestedLocation = requestedLocation;
    }

    public String getResolvedLocation() {
        return resolvedLocation;
    }

    public void setResolvedLocation(String resolvedLocation) {
        this.resolvedLocation = resolvedLocation;
    }

    public Instant getObservedAt() {
        return observedAt;
    }

    public void setObservedAt(Instant observedAt) {
        this.observedAt = observedAt;
    }

    public double getTemperatureC() {
        return temperatureC;
    }

    public void setTemperatureC(double temperatureC) {
        this.temperatureC = temperatureC;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public String getConditionText() {
        return conditionText;
    }

    public void setConditionText(String conditionText) {
        this.conditionText = conditionText;
    }

    public double getFeelsLikeC() {
        return feelsLikeC;
    }

    public void setFeelsLikeC(double feelsLikeC) {
        this.feelsLikeC = feelsLikeC;
    }

    public double getWindKph() {
        return windKph;
    }

    public void setWindKph(double windKph) {
        this.windKph = windKph;
    }

    public String getRawPayload() {
        return rawPayload;
    }

    public void setRawPayload(String rawPayload) {
        this.rawPayload = rawPayload;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
