package pl.piwonski.weather.domain.weather_data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;
import pl.piwonski.weather.domain.city.CityDto;
import pl.piwonski.weather.model.CloudCover;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class WeatherDataDto {

    @PastOrPresent
    @NotNull
    @JsonFormat(pattern = "dd.MM.yyyy")
    private LocalDate date;

    @PastOrPresent
    @NotNull
    @JsonFormat(pattern = "HH:mm")
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
    @Positive
    private CloudCover cloudCover;

    @NotNull
    @Positive
    private CityDto city;

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

    public CloudCover getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(CloudCover cloudCover) {
        this.cloudCover = cloudCover;
    }

    public CityDto getCity() {
        return city;
    }

    public void setCity(CityDto city) {
        this.city = city;
    }
}
