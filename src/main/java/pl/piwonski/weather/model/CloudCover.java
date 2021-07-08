package pl.piwonski.weather.model;

import javax.persistence.*;

@Table(name = "cloud_cover")
@Entity
public class CloudCover {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cloudCoverSeq")
    @SequenceGenerator(name = "cloudCoverSeq", sequenceName = "cloud_cover_seq", allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}