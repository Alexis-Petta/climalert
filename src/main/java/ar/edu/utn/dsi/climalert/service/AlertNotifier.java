package ar.edu.utn.dsi.climalert.service;

import ar.edu.utn.dsi.climalert.domain.WeatherAlert;
import ar.edu.utn.dsi.climalert.domain.WeatherRecord;

public interface AlertNotifier {

    NotificationResult notify(WeatherAlert alert, WeatherRecord record);
}
