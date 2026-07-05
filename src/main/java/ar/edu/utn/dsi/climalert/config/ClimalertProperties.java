package ar.edu.utn.dsi.climalert.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "climalert")
public class ClimalertProperties {

    private WeatherApi weatherApi = new WeatherApi();
    private Scheduler scheduler = new Scheduler();
    private Alert alert = new Alert();
    private Notification notification = new Notification();

    public WeatherApi getWeatherApi() {
        return weatherApi;
    }

    public void setWeatherApi(WeatherApi weatherApi) {
        this.weatherApi = weatherApi;
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public Alert getAlert() {
        return alert;
    }

    public void setAlert(Alert alert) {
        this.alert = alert;
    }

    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }

    public static class WeatherApi {
        private String baseUrl = "https://api.weatherapi.com/v1";
        private String apiKey = "";
        private String location = "-34.6037,-58.3816";

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getApiKey() {
            return apiKey;
        }

        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }

    public static class Scheduler {
        private long weatherPollMs = 300000;
        private long alertCheckMs = 60000;
        private long initialDelayMs = 5000;

        public long getWeatherPollMs() {
            return weatherPollMs;
        }

        public void setWeatherPollMs(long weatherPollMs) {
            this.weatherPollMs = weatherPollMs;
        }

        public long getAlertCheckMs() {
            return alertCheckMs;
        }

        public void setAlertCheckMs(long alertCheckMs) {
            this.alertCheckMs = alertCheckMs;
        }

        public long getInitialDelayMs() {
            return initialDelayMs;
        }

        public void setInitialDelayMs(long initialDelayMs) {
            this.initialDelayMs = initialDelayMs;
        }
    }

    public static class Alert {
        private double maxTemperatureC = 35;
        private int minHumidity = 60;

        public double getMaxTemperatureC() {
            return maxTemperatureC;
        }

        public void setMaxTemperatureC(double maxTemperatureC) {
            this.maxTemperatureC = maxTemperatureC;
        }

        public int getMinHumidity() {
            return minHumidity;
        }

        public void setMinHumidity(int minHumidity) {
            this.minHumidity = minHumidity;
        }
    }

    public static class Notification {
        private boolean enabled = false;
        private List<String> recipients = new ArrayList<>(List.of(
                "admin@clima.com",
                "emergencias@clima.com",
                "meteorologia@clima.com"
        ));

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public List<String> getRecipients() {
            return recipients;
        }

        public void setRecipients(List<String> recipients) {
            this.recipients = recipients;
        }
    }
}
