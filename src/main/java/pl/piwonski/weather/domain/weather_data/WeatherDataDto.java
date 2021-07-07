package pl.piwonski.weather.domain.weather_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import pl.piwonski.weather.domain.city.CityDto;
import pl.piwonski.weather.model.enums.CloudCoverEnum;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class WeatherDataDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private long id;

    @PastOrPresent
    @NotNull
    private LocalDate date;

    @PastOrPresent
    @NotNull
    private LocalTime time;

    @NotNull
    private float temperature;

    @NotNull
    @Positive
    private float atmosphericPressure;

    @NotNull
    @Min(0)
    @Max(360)
    private int windDirectionDeg;

    @NotNull
    @PositiveOrZero
    private int windSpeed;

    @NotNull
    private CloudCoverEnum cloudCover;

    @NotNull
    private CityDto city;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getAtmosphericPressure() {
        return atmosphericPressure;
    }

    public void setAtmosphericPressure(float atmosphericPressure) {
        this.atmosphericPressure = atmosphericPressure;
    }

    public int getWindDirectionDeg() {
        return windDirectionDeg;
    }

    public void setWindDirectionDeg(int windDirectionDeg) {
        this.windDirectionDeg = windDirectionDeg;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public CloudCoverEnum getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(CloudCoverEnum cloudCover) {
        this.cloudCover = cloudCover;
    }

    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }
}
