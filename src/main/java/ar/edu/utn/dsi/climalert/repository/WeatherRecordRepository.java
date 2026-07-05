package ar.edu.utn.dsi.climalert.repository;

import ar.edu.utn.dsi.climalert.domain.WeatherRecord;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRecordRepository extends JpaRepository<WeatherRecord, Long> {

    Optional<WeatherRecord> findTopByOrderByObservedAtDescCreatedAtDesc();
}
