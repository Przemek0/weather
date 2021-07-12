package pl.piwonski.weather.domain.country;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountryController.class)
class CountryControllerTest {

    @MockBean
    private CountryService countryService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getCountriesReturnsCountries() throws Exception {
        //given
        final List<CountryDto> countries = fixtureListOfCountries();
        final String json = objectMapper.writeValueAsString(countries);

        given(countryService.readAll())
                .willReturn(countries);

        //when
        final ResultActions resultActions = mockMvc
                .perform(
                        get("/countries/all")
                );

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(content().json(json));
    }

    @Test
    void createCountryReturnsCountry() throws Exception {
        //when
        final CountryDto countryDto = fixtureListOfCountries().get(0);
        final String json = objectMapper.writeValueAsString(countryDto);

        given(countryService.existsByCode(eq("PL")))
                .willReturn(false);
        given(countryService.existsByName("Poland"))
                .willReturn(false);
        given(countryService.create(countryDto))
                .willReturn(countryDto);

        //when
        final ResultActions resultActions = mockMvc
                .perform(
                        post("/countries/create")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                );

        //then
        resultActions
                .andExpect(status().isCreated());
    }

    private List<CountryDto> fixtureListOfCountries() {
        final CountryDto country1 = new CountryDto();
        country1.setName("Poland");
        country1.setCode("PL");
        final CountryDto country2 = new CountryDto();
        country2.setName("Germany");
        country2.setCode("DE");

        return List.of(country1, country2);
    }

}