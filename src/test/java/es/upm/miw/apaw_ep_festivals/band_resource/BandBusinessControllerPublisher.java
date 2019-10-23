package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiTestConfig
public class BandBusinessControllerPublisher {

    @Autowired
    BandBusinessController bandBusinessController;

    @Test
    void testPublisher() {
        List<Artist> artists = new ArrayList<>();
        artists.add(new Artist("Alice", LocalDateTime.now(), "main singer"));
        StepVerifier
                .create(bandBusinessController.publisher())
                .then(() -> {
                    BandCreationDto band1 = new BandCreationDto("Band 1", artists, null);
                    bandBusinessController.create(band1);
                })
                .then(() -> {
                    BandCreationDto band2 = new BandCreationDto("Band 2", artists, null);
                    bandBusinessController.create(band2);
                })
                .expectNext("Band 1")
                .then(() -> {
                    BandCreationDto band3 = new BandCreationDto("Band 3", artists, null);
                    bandBusinessController.create(band3);
                })
                .expectNext("Band 2")
                .thenCancel()
                .verify();
    }
}
