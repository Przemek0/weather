package pl.piwonski.weather.model;

import pl.piwonski.weather.model.enums.CloudCoverEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "weather_data")
@Entity
public class WeatherData {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "weatherDataSeq")
    @SequenceGenerator(name = "weatherDataSeq", sequenceName = "weather_data_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "temperature", nullable = false)
    private float temperature;

    @Column(name = "atmospheric_pressure", nullable = false)
    private float atmosphericPressure;

    @Column(name = "wind_direction_deg", nullable = false)
    private int windDirectionDeg;

    @Column(name = "wind_speed", nullable = false)
    private int windSpeed;

    @Enumerated(EnumType.STRING)
    @Column(name = "cloud_cover", nullable = false)
    private CloudCoverEnum cloudCover;

    @ManyToOne(optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public CloudCoverEnum getCloudCover() {
        return cloudCover;
    }

    public void setCloudCover(CloudCoverEnum cloudCoverEnum) {
        this.cloudCover = cloudCoverEnum;
    }

    public int getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(int windSpeed) {
        this.windSpeed = windSpeed;
    }

    public int getWindDirectionDeg() {
        return windDirectionDeg;
    }

    public void setWindDirectionDeg(int windDirectionDeg) {
        this.windDirectionDeg = windDirectionDeg;
    }

    public float getAtmosphericPressure() {
        return atmosphericPressure;
    }

    public void setAtmosphericPressure(float atmosphericPressure) {
        this.atmosphericPressure = atmosphericPressure;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}