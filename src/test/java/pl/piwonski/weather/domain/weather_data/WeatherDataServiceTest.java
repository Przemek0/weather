package pl.piwonski.weather.domain.weather_data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.piwonski.weather.model.WeatherData;

import static org.junit.jupiter.api.Assertions.*;
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
}