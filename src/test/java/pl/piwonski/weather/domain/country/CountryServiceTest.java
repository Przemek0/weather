package pl.piwonski.weather.domain.country;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import pl.piwonski.weather.model.Country;

import java.lang.reflect.Type;
import java.util.List;
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

    @Test
    void read() {
        //given
        final Country country = new Country();
        final Optional<Country> optCountry = Optional.of(country);
        final CountryDto countryDto = new CountryDto();
        final Optional<CountryDto> expectedResult = Optional.of(countryDto);
        final Type optCountryDtoType = new TypeToken<Optional<CountryDto>>() {
        }.getType();

        given(countryRepository.findById(eq(1))).willReturn(optCountry);
        given(modelMapper.map(optCountry, optCountryDtoType)).willReturn(expectedResult);

        //when
        var result = countryService.read(1);

        //then
        assertSame(expectedResult, result);
    }

    @Test
    void update() {
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
    void delete() {
        //given

        //when
        countryService.delete(1);

        //then
        verify(countryRepository, times(1)).deleteById(1);
    }

    @Test
    void readAll() {
        //given
        final List<Country> all = List.of(new Country());
        final Type countryDtoListType = new TypeToken<List<CountryDto>>() {
        }.getType();

        final List<CountryDto> expectedResult = List.of(new CountryDto());

        given(countryRepository.findAll()).willReturn(all);
        given(modelMapper.map(all, countryDtoListType))
                .willReturn(expectedResult);

        //when
        var result = countryService.readAll();

        //then
        assertSame(expectedResult, result);
    }

    @Test
    void existsById() {
        //given
        given(countryRepository.existsById(eq(1)))
                .willReturn(true);
        given(countryRepository.existsById(eq(2)))
                .willReturn(false);

        //when
        var resultTrue = countryService.existsById(1);
        var resultFalse = countryService.existsById(2);

        //then
        assertTrue(resultTrue);
        assertFalse(resultFalse);
    }

    @Test
    void existsByName() {
        //given
        given(countryRepository.existsByName(eq("test1")))
                .willReturn(true);
        given(countryRepository.existsByName(eq("test2")))
                .willReturn(false);

        //when
        var resultTrue = countryService.existsByName("test1");
        var resultFalse = countryService.existsByName("test2");

        //then
        assertTrue(resultTrue);
        assertFalse(resultFalse);
    }

    @Test
    void existsByCode() {
        //given
        given(countryRepository.existsByCode(eq("US")))
                .willReturn(true);
        given(countryRepository.existsByCode(eq("PL")))
                .willReturn(false);

        //when
        var resultTrue = countryService.existsByCode("US");
        var resultFalse = countryService.existsByCode("PL");

        //then
        assertTrue(resultTrue);
        assertFalse(resultFalse);
    }
}