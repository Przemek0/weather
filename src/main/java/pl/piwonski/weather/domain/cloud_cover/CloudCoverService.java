package pl.piwonski.weather.domain.cloud_cover;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import pl.piwonski.weather.model.CloudCover;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
        cloudCoverList.forEach(c -> {
            if (!cloudCoverRepository.existsByName(c.getName())) {
                cloudCoverRepository.save(c);
            }
        });
    }

    private List<CloudCover> getCloudCoverList() {
        List<CloudCover> cloudCoverList = new ArrayList<>();

        final CloudCover cloudless = new CloudCover();
        cloudless.setName("cloudless");
        cloudCoverList.add(cloudless);

        final CloudCover sunny = new CloudCover();
        sunny.setName("sunny");
        cloudCoverList.add(sunny);

        final CloudCover scatteredClouds = new CloudCover();
        scatteredClouds.setName("scattered clouds");
        cloudCoverList.add(scatteredClouds);

        final CloudCover slightlyCloudy = new CloudCover();
        slightlyCloudy.setName("slightly cloudy");
        cloudCoverList.add(slightlyCloudy);

        final CloudCover partlyCloudy = new CloudCover();
        partlyCloudy.setName("partly cloudy");
        cloudCoverList.add(partlyCloudy);

        final CloudCover cloudy = new CloudCover();
        cloudy.setName("cloudy");
        cloudCoverList.add(cloudy);

        final CloudCover mostlyCloudy = new CloudCover();
        mostlyCloudy.setName("mostly cloudy");
        cloudCoverList.add(mostlyCloudy);

        final CloudCover nearlyOvercast = new CloudCover();
        nearlyOvercast.setName("nearly overcast");
        cloudCoverList.add(nearlyOvercast);

        final CloudCover overcast = new CloudCover();
        overcast.setName("overcast");
        cloudCoverList.add(overcast);

        final CloudCover skyObscured = new CloudCover();
        skyObscured.setName("sky obscured");
        cloudCoverList.add(skyObscured);

        return cloudCoverList;
    }
}
