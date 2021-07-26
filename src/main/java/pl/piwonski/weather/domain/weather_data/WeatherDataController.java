package pl.piwonski.weather.domain.weather_data;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.piwonski.weather.domain.city.CityService;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/weathers")
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

        ifCityNotExistsThrowNotFound(city);

        return weatherDataService.getCurrentWeatherByCity(city)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Weather for " + city + " is not found."));
    }

    @GetMapping("/current/time")
    public List<WeatherDataDto> getCurrentWeatherByTime(
            @RequestParam
                    String city,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "HH:mm")
                    LocalTime startTime,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "HH:mm")
                    LocalTime endTime
    ) {
        ifCityNotExistsThrowNotFound(city);

        if (startTime != null && endTime != null && startTime.isAfter(endTime)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The start time can't be after the end time.");
        }

        return weatherDataService.
                getCurrentWeatherByCityAndTime(city, startTime, endTime);
    }

    @GetMapping("/history")
    public List<WeatherDataDto> getWeather(
            @RequestParam
                    String city,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd.MM.yyyy")
                    LocalDate startDate,
            @RequestParam(required = false)
            @DateTimeFormat(pattern = "dd.MM.yyyy")
                    LocalDate endDate
    ) {
        ifCityNotExistsThrowNotFound(city);

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The start date can't be after the end date.");
        }

        return weatherDataService.
                getWeatherByCityAndDate(city, startDate, endDate);
    }

    private void ifCityNotExistsThrowNotFound(String city) {
        if (!cityService.existsByName(city)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, city + " not found.");
        }
    }
}
