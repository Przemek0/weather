package pl.piwonski.weather.domain.city;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
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
        final City city = modelMapper.map(cityDto, City.class);
        final City save = cityRepository.save(city);
        return modelMapper.map(save, CityDto.class);
    }

    public Optional<CityDto> read(long id) {
        final Optional<City> optCity = cityRepository.findById(id);
        final Type optCityDtoType = new TypeToken<Optional<CityDto>>() {}.getType();
        return modelMapper.map(optCity, optCityDtoType);
    }

    public CityDto update(long id, CityDto newCityDto) {
        final City city = modelMapper.map(newCityDto, City.class);
        city.setId(id);
        final City save = cityRepository.save(city);
        return modelMapper.map(save, CityDto.class);
    }

    public void delete(long id) {
        cityRepository.deleteById(id);
    }
}
