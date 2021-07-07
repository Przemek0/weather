package pl.piwonski.weather.domain.country;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import pl.piwonski.weather.model.Country;

import java.lang.reflect.Type;
import java.util.Optional;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    public CountryService(CountryRepository countryRepository, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
    }

    public CountryDto create(CountryDto countryDto) {
        final Country country = modelMapper.map(countryDto, Country.class);
        final Country save = countryRepository.save(country);
        return modelMapper.map(save, CountryDto.class);
    }

    public Optional<CountryDto> read(int id) {
        final Optional<Country> optCountry = countryRepository.findById(id);
        final Type optCountryDtoType = new TypeToken<Optional<CountryDto>>() {
        }.getType();
        return modelMapper.map(optCountry, optCountryDtoType);
    }

    public CountryDto update(int id, CountryDto newCountryDto) {
        final Country country = modelMapper.map(newCountryDto, Country.class);
        country.setId(id);
        final Country save = countryRepository.save(country);
        return modelMapper.map(save, CountryDto.class);
    }

    public void delete(int id) {
        countryRepository.deleteById(id);
    }
}
