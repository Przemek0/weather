package pl.piwonski.weather.domain.weather_data;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piwonski.weather.model.WeatherData;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
}