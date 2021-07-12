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
import java.time.*;
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

    @Mock
    private Clock clock;

    private WeatherDataService weatherDataService;

    @BeforeEach
    void setUp() {
        clock = Clock.fixed(Instant.parse("2021-07-07T23:59:59Z"), ZoneId.of("UTC"));
        weatherDataService = new WeatherDataService(weatherDataRepository, modelMapper, clock);
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
        final Type weatherDataDtoListType = getWeatherDataDtoListType();
        final List<WeatherData> weatherDataList = getWeatherDataList();
        final List<WeatherDataDto> expectedResult = getExpectedResult();

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

    @Test
    void getCurrentWeatherByCityAndTime() {
        //given
        LocalTime start = null;
        LocalTime end = null;
        final String cityName = "City";
        final Type weatherDataDtoListType = getWeatherDataDtoListType();
        final List<WeatherData> weatherDataList = getWeatherDataList();
        final List<WeatherDataDto> expectedResult = getExpectedResult();

        given(weatherDataRepository
                .findAllByCity_NameAndDateAndTimeBetweenOrderByTimeDesc(
                        eq(cityName), any(LocalTime.class), any(LocalTime.class), any(LocalDate.class)
                )
        ).willReturn(weatherDataList);

        given(modelMapper.map(weatherDataList, weatherDataDtoListType))
                .willReturn(expectedResult);

        //when
        var result = weatherDataService.getCurrentWeatherByCityAndTime(cityName, start, end);

        //then
        assertSame(expectedResult, result);
    }

    private List<WeatherDataDto> getExpectedResult() {
        return List.of(new WeatherDataDto());
    }

    private List<WeatherData> getWeatherDataList() {
        return List.of(new WeatherData());
    }

    private Type getWeatherDataDtoListType() {
        return new TypeToken<List<WeatherDataDto>>() {
        }.getType();
    }
}