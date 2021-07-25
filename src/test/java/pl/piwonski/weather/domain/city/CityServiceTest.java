package pl.piwonski.weather.domain.city;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import pl.piwonski.weather.model.City;

import java.lang.reflect.Type;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CityServiceTest {
    @Mock
    private CityRepository cityRepository;

    @Mock
    private ModelMapper modelMapper;

    private CityService cityService;

    @BeforeEach
    void setUp() {
        cityService = new CityService(cityRepository, modelMapper);
    }

    @Test
    void shouldReturnCityDto() {
        //given
        final City city = new City();
        final CityDto expectedResult = new CityDto();

        given(modelMapper.map(expectedResult, City.class))
                .willReturn(city);
        given(cityRepository.save(city))
                .willReturn(city);
        given(modelMapper.map(city, CityDto.class))
                .willReturn(expectedResult);

        //when
        final var result = cityService.create(expectedResult);

        //then
        assertSame(expectedResult, result);
    }

    @Test
    void shouldReturnOptionalOfCityDto() {
        //given
        final City city = new City();
        final Optional<City> optCity = Optional.of(city);
        final CityDto expectedResult = new CityDto();
        final Optional<CityDto> optCityDto = Optional.of(expectedResult);

        given(cityRepository.findById(eq(1L)))
                .willReturn(optCity);
        given(modelMapper.map(city, CityDto.class))
                .willReturn(expectedResult);

        //when
        final var result = cityService.read(1L);

        //then
        assertTrue(result.isPresent());
        assertSame(expectedResult, result.get());
    }

    @Test
    void shouldReturnEmptyOptional() {
        //given

        given(cityRepository.findById(eq(1L)))
                .willReturn(Optional.empty());

        //when
        final var result = cityService.read(1L);

        //then
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldReturnUpdatedCityMappedToCityDto() {
        //given
        final City city = new City();
        final CityDto expectedResult = new CityDto();

        given(modelMapper.map(expectedResult, City.class)).willReturn(city);
        given(cityRepository.save(city)).willReturn(city);
        given(modelMapper.map(city, CityDto.class)).willReturn(expectedResult);

        //when
        final var result = cityService.update(1L, expectedResult);

        //then
        assertSame(expectedResult, result);
    }

    @Test
    void verifyNumberOfInvocationsOfDeleteByIdEqualsOne() {
        //given

        //when
        cityService.delete(1L);

        //then
        verify(cityRepository, times(1)).deleteById(1L);
    }
}