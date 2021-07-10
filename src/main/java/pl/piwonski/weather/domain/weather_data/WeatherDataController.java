package pl.piwonski.weather.domain.weather_data;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.piwonski.weather.domain.city.CityService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/weather")
@Validated
public class WeatherDataController {
    private final WeatherDataService weatherDataService;
    private final CityService cityService;

    public WeatherDataController(WeatherDataService weatherDataService, CityService cityService) {
        this.weatherDataService = weatherDataService;
        this.cityService = cityService;
    }

    @GetMapping("/current")
    public WeatherDataDto getCurrentWeather(@RequestParam @NotNull String city) {
        if (!cityService.existsByName(city)) {
            throw badRequestCityNotFound(city);
        }

        return weatherDataService.getCurrentWeatherByCity(city)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Weather for " + city + " is not found."));
    }

    @GetMapping("/history")
    public List<WeatherDataDto> getWeather(
            @RequestParam
                    String city,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd.MM.yyyy")
                    LocalDate start,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd.MM.yyyy")
                    LocalDate end
    ) {
        if (!cityService.existsByName(city)) {
            throw badRequestCityNotFound(city);
        }

        if (start != null && end != null && start.isAfter(end)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The start date can't be after the end date.");
        }

        return weatherDataService.
                getWeatherByCityAndDate(city, start, end);
    }

    private ResponseStatusException badRequestCityNotFound(String cityName) {
        return new ResponseStatusException(HttpStatus.BAD_REQUEST, cityName + " not found.");
    }
}
