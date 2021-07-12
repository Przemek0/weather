package pl.piwonski.weather.domain.country;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/countries")
public class CountryController {
    private final CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    public List<CountryDto> getCountries() {
        return countryService.readAll();
    }

    @PostMapping("/create")
    public CountryDto createCountry(@RequestBody @Valid CountryDto countryDto) {
        ifCountryNameExistsThrowBadRequest(countryDto.getName());
        ifCountryCodeExistsThrowBadRequest(countryDto.getCode());

        return countryService.create(countryDto);
    }

    @PutMapping("/edit/{id}")
    public CountryDto updateCountry(
            @RequestBody @Valid CountryDto countryDto,
            @PathVariable int id
    ) {
        if (!countryService.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country " + id + "doesn't exist.");
        }

        ifCountryNameExistsThrowBadRequest(countryDto.getName());
        ifCountryCodeExistsThrowBadRequest(countryDto.getCode());

        return countryService.update(id, countryDto);
    }

    private void ifCountryCodeExistsThrowBadRequest(String code) {
        if (countryService.existsByCode(code)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country " + code + " already exists.");
        }
    }

    private void ifCountryNameExistsThrowBadRequest(String countryName) {
        if (countryService.existsByName(countryName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Country " + countryName + " already exists.");
        }
    }
}
