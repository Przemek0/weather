package pl.piwonski.weather.domain.weather_data;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piwonski.weather.model.WeatherData;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {

    Optional<WeatherData> findFirstByCity_NameOrderByDateDescTimeDesc(String cityName);

    List<WeatherData> findAllByCity_NameAndDateBetweenOrderByDateDescTimeDesc(String cityName, LocalDate from, LocalDate to);

}