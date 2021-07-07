package pl.piwonski.weather.domain.weatherdata;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piwonski.weather.model.WeatherData;

import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    Optional<WeatherData> findFirstByCity_NameAllIgnoreCaseOrderByDateDescTimeDesc(String name);

}