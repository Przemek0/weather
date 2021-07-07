package pl.piwonski.weather.domain.weather_data;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.piwonski.weather.model.WeatherData;

@Service
public class WeatherDataService {
    private final WeatherDataRepository weatherDataRepository;
    private final ModelMapper modelMapper;

    public WeatherDataService(WeatherDataRepository weatherDataRepository, ModelMapper modelMapper) {
        this.weatherDataRepository = weatherDataRepository;
        this.modelMapper = modelMapper;
    }

    public WeatherDataDto create(WeatherDataDto weatherDataDto) {
        final WeatherData weatherData = modelMapper.map(weatherDataDto, WeatherData.class);
        final WeatherData save = weatherDataRepository.save(weatherData);
        return modelMapper.map(save, WeatherDataDto.class);
    }
}
