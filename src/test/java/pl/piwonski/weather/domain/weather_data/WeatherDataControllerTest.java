package pl.piwonski.weather.domain.weather_data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import pl.piwonski.weather.domain.city.CityDto;
import pl.piwonski.weather.domain.city.CityService;
import pl.piwonski.weather.domain.country.CountryDto;
import pl.piwonski.weather.model.CloudCover;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

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
    void getWeatherReturnsNotFound() throws Exception {
        //given
        given(cityService.existsByName(eq("Example city")))
                .willReturn(true);

        //when
        final ResultActions resultActions = mockMvc.perform(
                get("/weather")
                        .param("city", "Example city")
        );

        //then
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    void getWeatherReturnsNotFound2() throws Exception {
        //given
        final String exampleCity = "Example city";
        given(cityService.existsByName(eq(exampleCity)))
                .willReturn(false);
        given(weatherDataService.getCurrentWeatherByCity(eq(exampleCity)))
                .willReturn(Optional.empty());

        //when
        final ResultActions resultActions = mockMvc.perform(
                get("/weather")
                        .param("city", exampleCity)
        );

        //then
        resultActions
                .andExpect(status().isNotFound());
    }

    @Test
    void getWeatherReturnsWeather() throws Exception {
        //given
        final WeatherDataDto weatherDataDto = fixtureWeatherData();
        final String exampleCity = "Example city";
        final String json = objectMapper.writeValueAsString(weatherDataDto);

        given(cityService.existsByName(eq(exampleCity)))
                .willReturn(true);
        given(weatherDataService.getCurrentWeatherByCity(eq(exampleCity)))
                .willReturn(Optional.of(weatherDataDto));

        //when
        final ResultActions resultActions = mockMvc.perform(
                get("/weather/current")
                        .param("city", exampleCity)
        );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(json));
        System.out.println(json);
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