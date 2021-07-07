package pl.piwonski.weather.domain.city;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import pl.piwonski.weather.model.City;

import static org.junit.jupiter.api.Assertions.*;
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
}