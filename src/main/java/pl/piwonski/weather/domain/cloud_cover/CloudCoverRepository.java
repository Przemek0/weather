package pl.piwonski.weather.domain.cloud_cover;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.piwonski.weather.model.CloudCover;

public interface CloudCoverRepository extends JpaRepository<CloudCover, Integer> {
}