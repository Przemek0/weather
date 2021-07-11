package pl.piwonski.weather.domain.weather_data;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.piwonski.weather.model.WeatherData;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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

    public Optional<WeatherDataDto> read(long id) {
        final Optional<WeatherData> optWeatherData = weatherDataRepository.findById(id);
        return mapOptWD2OptWDDto(optWeatherData);
    }

    public WeatherDataDto update(long id, WeatherDataDto newWeatherDataDto) {
        final WeatherData weatherData = modelMapper.map(newWeatherDataDto, WeatherData.class);
        weatherData.setId(id);
        final WeatherData save = weatherDataRepository.save(weatherData);
        return modelMapper.map(save, WeatherDataDto.class);
    }

    public void delete(long id) {
        weatherDataRepository.deleteById(id);
    }

    public Optional<WeatherDataDto> getCurrentWeatherByCity(String cityName) {
        final Optional<WeatherData> optCurrentWeatherData = weatherDataRepository
                .findFirstByCity_NameOrderByDateDescTimeDesc(cityName);

        return mapOptWD2OptWDDto(optCurrentWeatherData);
    }

    public List<WeatherDataDto> getWeatherByCityAndDate(String cityName, LocalDate start, LocalDate end) {

        if (start == null) {
            start = LocalDate.EPOCH;
        }

        if (end == null) {
            end = LocalDate.now();
        }

        final List<WeatherData> weatherDataList = weatherDataRepository
                .findAllByCity_NameAndDateBetweenOrderByDateDescTimeDesc(cityName, start, end);

        final Type weatherDataDtoListType = weatherDataDtoListType();

        return modelMapper.map(weatherDataList, weatherDataDtoListType);
    }

    public List<WeatherDataDto> getCurrentWeatherByCityAndTime(String cityName, LocalTime start, LocalTime end) {

        if (start == null) {
            start = LocalTime.MIN;
        }

        if (end == null) {
            end = LocalTime.MAX;
        }

        LocalDate nowDate = LocalDate.now();

        final List<WeatherData> currentWeatherDataList = weatherDataRepository
                .findAllByCity_NameAndDateAndTimeBetweenOrderByTimeDesc(cityName, start, end, nowDate);

        final Type weatherDataDtoListType = weatherDataDtoListType();

        return modelMapper.map(currentWeatherDataList, weatherDataDtoListType);
    }

    private Type weatherDataDtoListType() {
        return new TypeToken<List<WeatherDataDto>>() {}.getType();
    }

    private Optional<WeatherDataDto> mapOptWD2OptWDDto(Optional<WeatherData> optWeatherData) {
        if (optWeatherData.isEmpty()) {
            return Optional.empty();
        }
        final WeatherData weatherData = optWeatherData.get();
        final WeatherDataDto weatherDataDto = modelMapper.map(weatherData, WeatherDataDto.class);
        return Optional.of(weatherDataDto);
    }
}
