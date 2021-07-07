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
    void create() {
        //given
        final City city = new City();
        final CityDto expectedResult = new CityDto();

        given(modelMapper.map(expectedResult, City.class)).willReturn(city);
        given(cityRepository.save(city)).willReturn(city);
        given(modelMapper.map(city, CityDto.class)).willReturn(expectedResult);

        //when
        final var result = cityService.create(expectedResult);

        //then
        assertSame(expectedResult, result);
    }

    @Test
    void read() {
        //given
        final City city = new City();
        final Optional<City> optCity = Optional.of(city);
        final CityDto cityDto = new CityDto();
        final Optional<CityDto> expectedResult = Optional.of(cityDto);
        final Type optCityDtoType = new TypeToken<Optional<CityDto>>() {}.getType();

        given(cityRepository.findById(eq(1L))).willReturn(optCity);
        given(modelMapper.map(optCity, optCityDtoType)).willReturn(expectedResult);

        //when
        final var result = cityService.read(1L);

        //then
        assertSame(expectedResult, result);
    }
}