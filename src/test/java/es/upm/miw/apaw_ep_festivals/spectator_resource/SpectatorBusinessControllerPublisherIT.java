package es.upm.miw.apaw_ep_festivals.spectator_resource;

import es.upm.miw.apaw_ep_festivals.TestConfig;
import es.upm.miw.apaw_ep_festivals.spectator_data.SpectatorDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import java.time.LocalDateTime;

@TestConfig
public class SpectatorBusinessControllerPublisherIT {

    @Autowired
    SpectatorResource spectatorBusinessControllerPublisher;

    @Test
    void testPublisher() {
        SpectatorDto spectatorDto = new SpectatorDto("Sonia", "Cazorla", LocalDateTime.now());
        StepVerifier
                .create(spectatorBusinessControllerPublisher.publisher())
                .then(() -> spectatorBusinessControllerPublisher.create(spectatorDto))
                .expectNext("The following Spectator has been added: " + spectatorDto.toStringWithoutId())
                .thenCancel()
                .verify();
    }
}
