package pl.piwonski.weather.domain.cloud_cover;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pl.piwonski.weather.model.CloudCover;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class CloudCoverService {
    private final CloudCoverRepository cloudCoverRepository;

    public CloudCoverService(CloudCoverRepository cloudCoverRepository) {
        this.cloudCoverRepository = cloudCoverRepository;
    }

    @PostConstruct
    @Transactional
    public void initializeCloudCoverTypes() {
        final List<CloudCover> cloudCoverList = getCloudCoverList();
        cloudCoverList.stream()
                .filter(cloudCover -> !cloudCoverRepository.existsByName(cloudCover.getName()))
                .forEach(cloudCoverRepository::save);
    }

    private List<CloudCover> getCloudCoverList() {
        final List<CloudCover> cloudCoverList = new ArrayList<>();

        final String[] cloudTypes = {"cloudless", "sunny", "scattered clouds", "slightly cloudy", "partly cloudy", "cloudy",
                        "mostly cloudy", "nearly overcast", "overcast", "sky obscured"};

        Arrays.stream(cloudTypes)
                .forEach(cloudType -> {
                    final CloudCover cloudCover = new CloudCover();
                    cloudCover.setName(cloudType);
                    cloudCoverList.add(cloudCover);
                });

        return cloudCoverList;
    }
}
