package pl.piwonski.weather.domain.weather_data;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class WeatherDataDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @PastOrPresent
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @PastOrPresent
    @NotNull
    @JsonFormat(pattern = "HH:mm:ss")
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
    private int cloudCover;

    @NotNull
    @Positive
    private long city;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public int getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(int cloudCover) {
        this.cloudCover = cloudCover;
    }

    public long getCity() {
        return city;
    }

    public void setCity(long city) {
        this.city = city;
    }
}
