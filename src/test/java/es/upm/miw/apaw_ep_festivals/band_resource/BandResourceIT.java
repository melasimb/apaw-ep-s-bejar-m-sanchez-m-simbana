package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ApiTestConfig
class BandResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testSearch() {
        List<Band> bands = this.webTestClient
                .get().uri(uriBuilder -> uriBuilder.path(BandResource.BANDS + BandResource.SEARCH)
                        .queryParam("q", "role:pop")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Band.class)
                .returnResult().getResponseBody();
        assertFalse(bands.isEmpty());
    }
}