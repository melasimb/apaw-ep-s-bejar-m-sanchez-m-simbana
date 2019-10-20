package es.upm.miw.apaw_ep_festivals.zone_resource;

import es.upm.miw.apaw_ep_festivals.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

@TestConfig
public class ZoneBusinessControllerPublisherIT {

    @Autowired
    ZoneBusinessController zoneBusinessControllerPublisher;

    @Test
    void testPublisher() {
        ZoneDto zoneDto = new ZoneDto("zone-1", "Pop", 300, false);
        StepVerifier
                .create(zoneBusinessControllerPublisher.publisher())
                .then(() -> zoneBusinessControllerPublisher.create(zoneDto))
                .expectNext("The following zone has been added: " + zoneDto.getBasicInformation())
                .thenCancel()
                .verify();
    }
}
