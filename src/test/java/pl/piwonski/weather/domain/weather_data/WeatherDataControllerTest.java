package pl.piwonski.weather.domain.weather_data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.piwonski.weather.domain.city.CityDto;
import pl.piwonski.weather.domain.city.CityService;
import pl.piwonski.weather.domain.country.CountryDto;
import pl.piwonski.weather.model.CloudCover;

import java.time.*;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WeatherDataController.class)
class WeatherDataControllerTest {

    @MockBean
    private WeatherDataService weatherDataService;

    @MockBean
    private CityService cityService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCurrentWeatherReturnsNotFound() throws Exception {
        //given
        final String cityName = "City";

        given(cityService.existsByName(eq(cityName)))
                .willReturn(false);

        //when
        final ResultActions resultActions = mockMvc.perform(
                get("/weathers/current")
                        .param("city", cityName)
        );

        //then
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    void getCurrentWeatherReturnsBadRequest() throws Exception {
        //given
        final String exampleCity = "Example city";
        given(cityService.existsByName(eq(exampleCity)))
                .willReturn(true);
        given(weatherDataService.getCurrentWeatherByCity(eq(exampleCity)))
                .willReturn(Optional.empty());

        //when
        final ResultActions resultActions = mockMvc.perform(
                get("/weathers/current")
                        .param("city", exampleCity)
        );

        //then
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    void getCurrentWeatherReturnsWeather() throws Exception {
        //given
        final WeatherDataDto weatherDataDto = fixtureWeatherData();
        final String cityName = "City";
        final String json = objectMapper.writeValueAsString(weatherDataDto);

        given(cityService.existsByName(eq(cityName)))
                .willReturn(true);
        given(weatherDataService.getCurrentWeatherByCity(eq(cityName)))
                .willReturn(Optional.of(weatherDataDto));

        //when
        final ResultActions resultActions = mockMvc.perform(
                get("/weathers/current")
                        .param("city", cityName)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        System.out.println(json);
    }

    @Test
    void getHistoryWeatherReturnsNotFound() throws Exception {
        //given
        final String cityName = "City";
        given(cityService.existsByName(eq(cityName)))
                .willReturn(false);

        //when
        final ResultActions resultActions = mockMvc.perform(
                get("/weathers/history")
                        .param("city", cityName)
        );

        //then
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    void getHistoryWeatherReturnsBadRequestWrongDate() throws Exception {
        //given
        final String cityName = "City";
        final LocalDate start = LocalDate.of(2021, 6, 30);
        final LocalDate end = LocalDate.of(2021, 5, 1);

        given(cityService.existsByName(eq(cityName)))
                .willReturn(true);

        //when
        final ResultActions resultActions = mockMvc.perform(
                get("/weathers/history")
                        .param("city", cityName)
                        .param("start", "30.06.2021")
                        .param("end", "01.05.2021")
        );

        //then
        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    void getHistoryWeatherReturnsListOfWeather() throws Exception {
        //given
        final String cityName = "City";
        final LocalDate start = null;
        final LocalDate end = null;
        final List<WeatherDataDto> weatherDataDtoList = List.of(fixtureWeatherData());
        final String json = objectMapper.writeValueAsString(weatherDataDtoList);

        given(cityService.existsByName(eq(cityName)))
                .willReturn(true);
        given(weatherDataService.getWeatherByCityAndDate(cityName, start, end))
                .willReturn(weatherDataDtoList);

        //when
        final ResultActions resultActions = mockMvc.perform(
                get("/weathers/history")
                        .param("city", cityName)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }

    @Test
    void getCurrentWeatherByTimeReturnsNotFound() throws Exception {
        //given
        final String cityName = "City";

        given(cityService.existsByName(eq(cityName)))
                .willReturn(false);

        //when
        final ResultActions resultActions = mockMvc.perform(
                get("/weathers/current/time")
                        .param("city", cityName)
        );

        //then
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    void getCurrentWeatherByTimeReturnsBadRequestWrongTime() throws Exception {
        //given
        final String cityName = "City";
        final LocalTime start = LocalTime.MAX;
        final LocalTime end = LocalTime.MIN;

        given(cityService.existsByName(eq(cityName)))
                .willReturn(true);

        //when
        final ResultActions resultActions = mockMvc.perform(
                get("/weathers/current/time")
                        .param("city", cityName)
                        .param("start", "23:59")
                        .param("end", "00:00")
        );

        //then
        resultActions
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCurrentWeatherByTimeReturnsListOfWeather() throws Exception {
        //given
        final String cityName = "City";

        final List<WeatherDataDto> weatherDataDtoList = List.of(fixtureWeatherData());
        final String json = objectMapper.writeValueAsString(weatherDataDtoList);

        given(cityService.existsByName(eq(cityName)))
                .willReturn(true);
        given(weatherDataService.getCurrentWeatherByCityAndTime(eq(cityName), any(LocalTime.class), any(LocalTime.class)))
                .willReturn(weatherDataDtoList);

        //when
        final ResultActions resultActions = mockMvc.perform(
                get("/weathers/current/time")
                        .param("city", cityName)
                        .param("start", "00:00")
                        .param("end", "23:59")
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(json));
    }


    private WeatherDataDto fixtureWeatherData() {
        final WeatherDataDto weatherDataDto = new WeatherDataDto();
        weatherDataDto.setDate(LocalDate.of(2021, 7, 7));
        weatherDataDto.setTime(LocalTime.of(14, 0));
        weatherDataDto.setAtmosphericPressure(1000);
        weatherDataDto.setTemperature(32.5f);
        weatherDataDto.setWindDirectionDeg(180);
        weatherDataDto.setWindSpeed(10);
        final CloudCover cloudCover = new CloudCover();
        cloudCover.setId(1);
        cloudCover.setName("cloudless");
        weatherDataDto.setCloudCover(cloudCover);
        final CityDto cityDto = new CityDto();
        cityDto.setName("Example city");
        final CountryDto countryDto = new CountryDto();
        countryDto.setName("Example country");
        countryDto.setCode("EC");
        cityDto.setCountry(countryDto);
        cityDto.setZipCode("00000");
        weatherDataDto.setCity(cityDto);
        return weatherDataDto;
    }

}