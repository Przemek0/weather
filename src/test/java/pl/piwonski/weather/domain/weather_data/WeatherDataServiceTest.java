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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
        final WeatherDataDto expectedResult = new WeatherDataDto();

        given(weatherDataRepository.findById(eq(1L))).willReturn(optWeatherData);
        given(modelMapper.map(weatherData, WeatherDataDto.class)).willReturn(expectedResult);

        //when
        var result = weatherDataService.read(1L);

        //then
        assertTrue(result.isPresent());
        assertSame(expectedResult, result.get());
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

    @Test
    void delete() {
        //given

        //when
        weatherDataService.delete(1L);

        //then
        verify(weatherDataRepository, times(1)).deleteById(1L);
    }

    @Test
    void getCurrentWeatherByCity() {
        //given
        final WeatherData weatherData = new WeatherData();
        final Optional<WeatherData> optWeatherData = Optional.of(weatherData);
        final WeatherDataDto expectedResult = new WeatherDataDto();


        given(weatherDataRepository.findFirstByCity_NameOrderByDateDescTimeDesc("City"))
                .willReturn(optWeatherData);
        given(modelMapper.map(weatherData, WeatherDataDto.class))
                .willReturn(expectedResult);

        //when
        var result = weatherDataService
                .getCurrentWeatherByCity("City");

        var result2 = weatherDataService
                .getCurrentWeatherByCity("");

        //then
        assertFalse(result2.isPresent());
        assertTrue(result.isPresent());
        assertSame(expectedResult, result.get());
    }

    @Test
    void getWeatherByCityAndDate() {
        //given
        LocalDate start = null;
        LocalDate end = null;
        final String cityName = "City";
        final Type weatherDataDtoListType = new TypeToken<List<WeatherDataDto>>() {}.getType();
        final List<WeatherData> weatherDataList = List.of(new WeatherData());
        final List<WeatherDataDto> expectedResult = List.of(new WeatherDataDto());

        given(weatherDataRepository
                .findAllByCity_NameAndDateBetweenOrderByDateDescTimeDesc(
                        eq(cityName), any(LocalDate.class), any(LocalDate.class)
                )
        ).willReturn(weatherDataList);
        given(modelMapper.map(weatherDataList, weatherDataDtoListType))
                .willReturn(expectedResult);

        //when
        var result = weatherDataService.getWeatherByCityAndDate(cityName, start, end);

        //then
        assertSame(expectedResult, result);
    }
}