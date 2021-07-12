package pl.piwonski.weather.domain.country;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piwonski.weather.model.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
    boolean existsByName(String name);

    boolean existsByCode(String code);
}