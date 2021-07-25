package pl.piwonski.weather.domain.city;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.piwonski.weather.domain.country.CountryDto;
import pl.piwonski.weather.domain.country.CountryService;
import pl.piwonski.weather.model.City;

import java.lang.reflect.Type;
import java.util.Optional;

@Service
public class CityService {
    private final CityRepository cityRepository;
    private final ModelMapper modelMapper;

    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public CityDto create(CityDto cityDto) {
        final City mappedCity = modelMapper.map(cityDto, City.class);
        final City savedCity = cityRepository.save(mappedCity);
        return modelMapper.map(savedCity, CityDto.class);
    }

    public Optional<CityDto> read(long id) {
        final Optional<City> optCity = cityRepository.findById(id);

        if (optCity.isPresent()) {
            final City city = optCity.get();
            final CityDto mappedCityDto = modelMapper.map(city, CityDto.class);
            return Optional.of(mappedCityDto);
        }

        return Optional.empty();
    }

    public CityDto update(long id, CityDto newCityDto) {
        final City mappedCity = modelMapper.map(newCityDto, City.class);
        mappedCity.setId(id);
        final City savedCity = cityRepository.save(mappedCity);
        return modelMapper.map(savedCity, CityDto.class);
    }

    public void delete(long id) {
        cityRepository.deleteById(id);
    }

    public boolean existsByName(String city) {
        return cityRepository.existsByName(city);
    }
}
