package pl.piwonski.weather.domain.weather_data;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.piwonski.weather.domain.city.CityService;

import javax.validation.constraints.NotNull;

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
    public WeatherDataDto getWeather(@RequestParam @NotNull String city) {
        if (!cityService.existsByName(city)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, city + " not found.");
        }

         return weatherDataService.getCurrentWeatherByCity(city)
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Weather for " + city + " is not found."));
    }
}
