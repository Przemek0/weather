package pl.piwonski.weather.domain.city;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.piwonski.weather.model.City;

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
}
