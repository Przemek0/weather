package pl.piwonski.weather.domain.country;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import pl.piwonski.weather.model.Country;

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
}
