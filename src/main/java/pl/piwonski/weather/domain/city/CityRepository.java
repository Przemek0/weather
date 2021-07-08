package pl.piwonski.weather.domain.city;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piwonski.weather.model.City;

public interface CityRepository extends JpaRepository<City, Long> {
    boolean existsByName(String city);
}