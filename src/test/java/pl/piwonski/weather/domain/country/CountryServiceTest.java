package pl.piwonski.weather.domain.country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.piwonski.weather.model.Country;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CountryServiceTest {
    @Mock
    private CountryRepository countryRepository;

    @Mock
    private ModelMapper modelMapper;

    private CountryService countryService;

    @BeforeEach
    void setUp() {
        countryService = new CountryService(countryRepository, modelMapper);
    }

    @Test
    void create() {
        //given
        final Country country = new Country();
        final CountryDto expectedResult = new CountryDto();
        given(modelMapper.map(expectedResult, Country.class)).willReturn(country);
        given(countryRepository.save(country)).willReturn(country);
        given(modelMapper.map(country, CountryDto.class)).willReturn(expectedResult);

        //when
        var result = countryService.create(expectedResult);

        //then
        assertSame(expectedResult, result);
    }
}