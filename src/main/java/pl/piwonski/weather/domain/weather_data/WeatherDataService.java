package pl.piwonski.weather.domain.weather_data;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.piwonski.weather.model.WeatherData;

import java.lang.reflect.Type;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WeatherDataService {
    private final WeatherDataRepository weatherDataRepository;
    private final ModelMapper modelMapper;
    private final Clock clock;

    public WeatherDataService(WeatherDataRepository weatherDataRepository, ModelMapper modelMapper, Clock clock) {
        this.weatherDataRepository = weatherDataRepository;
        this.modelMapper = modelMapper;
        this.clock = clock;
    }

    public WeatherDataDto create(WeatherDataDto weatherDataDto) {
        final WeatherData weatherData = modelMapper.map(weatherDataDto, WeatherData.class);
        final WeatherData save = weatherDataRepository.save(weatherData);
        return modelMapper.map(save, WeatherDataDto.class);
    }

    public Optional<WeatherDataDto> read(long id) {
        final Optional<WeatherData> optWeatherData = weatherDataRepository.findById(id);
        return mapOptWeatherDataToOptWeatherDataDto(optWeatherData);
    }

    public WeatherDataDto update(long id, WeatherDataDto newWeatherDataDto) {
        final WeatherData mappedWeatherData = modelMapper.map(newWeatherDataDto, WeatherData.class);
        mappedWeatherData.setId(id);
        final WeatherData savedWeatherData = weatherDataRepository.save(mappedWeatherData);
        return modelMapper.map(savedWeatherData, WeatherDataDto.class);
    }

    public void delete(long id) {
        weatherDataRepository.deleteById(id);
    }

    public Optional<WeatherDataDto> getCurrentWeatherByCity(String cityName) {
        final Optional<WeatherData> optCurrentWeatherData = weatherDataRepository
                .findFirstByCity_NameOrderByDateDescTimeDesc(cityName);

        return mapOptWeatherDataToOptWeatherDataDto(optCurrentWeatherData);
    }

    public List<WeatherDataDto> getWeatherByCityAndDate(String cityName, LocalDate startDate, LocalDate endDate) {

        if (startDate == null) {
            startDate = LocalDate.EPOCH;
        }

        if (endDate == null) {
            endDate = LocalDate.now();
        }

        final List<WeatherData> weatherDataList = weatherDataRepository
                .findAllByCity_NameAndDateBetweenOrderByDateDescTimeDesc(cityName, startDate, endDate);

        return mapWeatherDataListToWeatherDataDtoList(weatherDataList);
    }

    public List<WeatherDataDto> getCurrentWeatherByCityAndTime(String cityName, LocalTime startTime, LocalTime endTime) {

        if (startTime == null) {
            startTime = LocalTime.MIN;
        }

        if (endTime == null) {
            endTime = LocalTime.MAX;
        }

        LocalDate nowDate = LocalDate.now(clock);

        final List<WeatherData> currentWeatherDataList = weatherDataRepository
                .findAllByCity_NameAndTimeBetweenAndDateOrderByTimeDesc(cityName, startTime, endTime, nowDate);

        return mapWeatherDataListToWeatherDataDtoList(currentWeatherDataList);
    }

    private List<WeatherDataDto> mapWeatherDataListToWeatherDataDtoList(List<WeatherData> weatherDataList) {
        final Type weatherDataDtoListType = new TypeToken<List<WeatherDataDto>>() {}.getType();

        return modelMapper.map(weatherDataList, weatherDataDtoListType);
    }

    private Optional<WeatherDataDto> mapOptWeatherDataToOptWeatherDataDto(Optional<WeatherData> optWeatherData) {

        if (optWeatherData.isPresent()) {
            final WeatherData weatherData = optWeatherData.get();
            final WeatherDataDto weatherDataDto = modelMapper.map(weatherData, WeatherDataDto.class);
            return Optional.of(weatherDataDto);
        }

        return Optional.empty();
    }
}
