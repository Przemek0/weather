package pl.piwonski.weather.domain.weather_data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import pl.piwonski.weather.model.WeatherData;

import java.lang.reflect.Type;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class WeatherDataServiceTest {

    @Mock
    private WeatherDataRepository weatherDataRepository;

    @Mock
    private ModelMapper modelMapper;

    private WeatherDataService weatherDataService;

    @BeforeEach
    void setUp() {
        weatherDataService = new WeatherDataService(weatherDataRepository, modelMapper);
    }

    @Test
    void create() {
        //given
        final WeatherData weatherData = new WeatherData();
        final WeatherDataDto expectedResult = new WeatherDataDto();

        given(modelMapper.map(expectedResult, WeatherData.class)).willReturn(weatherData);
        given(weatherDataRepository.save(weatherData)).willReturn(weatherData);
        given(modelMapper.map(weatherData, WeatherDataDto.class)).willReturn(expectedResult);

        //when
        var result = weatherDataService.create(expectedResult);

        //then
        assertSame(expectedResult, result);
    }

    @Test
    void read() {
        //given
        final WeatherData weatherData = new WeatherData();
        final Optional<WeatherData> optWeatherData = Optional.of(weatherData);
        final WeatherDataDto weatherDataDto = new WeatherDataDto();
        final Optional<WeatherDataDto> expectedResult = Optional.of(weatherDataDto);
        final Type optWeatherDataDtoType = new TypeToken<Optional<WeatherDataDto>>() {}.getType();

        given(weatherDataRepository.findById(eq(1L))).willReturn(optWeatherData);
        given(modelMapper.map(optWeatherData, optWeatherDataDtoType)).willReturn(expectedResult);

        //when
        var result = weatherDataService.read(1L);

        //then
        assertSame(expectedResult, result);
    }

    @Test
    void update() {
        //given
        final WeatherData weatherData = new WeatherData();
        final WeatherDataDto expectedResult = new WeatherDataDto();

        given(modelMapper.map(expectedResult, WeatherData.class)).willReturn(weatherData);
        given(weatherDataRepository.save(weatherData)).willReturn(weatherData);
        given(modelMapper.map(weatherData, WeatherDataDto.class)).willReturn(expectedResult);

        //when
        var result = weatherDataService.update(1L, expectedResult);

        //then
        assertSame(expectedResult, result);
    }
}