package pl.piwonski.weather.domain.country;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


public class CountryDto {

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 2, max = 2)
    private String code;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
