package pl.piwonski.weather.domain.country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.piwonski.weather.model.Country;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    void shouldCreateCountryAndReturnCountryDto() {
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

    @Test
    void shouldReadCountryAndReturnOptionalOfCountryDto() {
        //given
        final Country country = new Country();
        final Optional<Country> optCountry = Optional.of(country);
        final CountryDto expectedResult = new CountryDto();

        given(countryRepository.findById(eq(1)))
                .willReturn(optCountry);
        given(modelMapper.map(eq(country), eq(CountryDto.class)))
                .willReturn(expectedResult);

        //when
        var result = countryService.read(1);

        //then
        assertTrue(result.isPresent());
        assertSame(expectedResult, result.get());
    }

    @Test
    void shouldReadCountryAndReturnEmptyOptional() {
        //given
        given(countryRepository.findById(eq(1)))
                .willReturn(Optional.empty());

        //when
        var result = countryService.read(1);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldUpdateCountryAndReturnCountryDto() {
        //given
        final Country country = new Country();
        final CountryDto expectedResult = new CountryDto();

        given(modelMapper.map(expectedResult, Country.class)).willReturn(country);
        given(countryRepository.save(country)).willReturn(country);
        given(modelMapper.map(country, CountryDto.class)).willReturn(expectedResult);

        //when
        var result = countryService.update(1, expectedResult);

        //then
        assertSame(expectedResult, result);
    }

    @Test
    void verifyDeleteByIdNumberOfInvocationsEqualsOne() {
        //given

        //when
        countryService.delete(1);

        //then
        verify(countryRepository, times(1)).deleteById(1);
    }
}