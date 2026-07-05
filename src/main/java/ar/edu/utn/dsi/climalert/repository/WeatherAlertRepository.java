package ar.edu.utn.dsi.climalert.repository;

import ar.edu.utn.dsi.climalert.domain.WeatherAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherAlertRepository extends JpaRepository<WeatherAlert, Long> {

    boolean existsByWeatherRecord_Id(Long weatherRecordId);
}
