package es.upm.miw.apaw_ep_festivals.zone_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
class ZoneResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        ZoneDto zoneDto = this.webTestClient
                .post().uri(ZoneResource.ZONES)
                .body(BodyInserters.fromObject(new ZoneDto("Zone A", "Pop", 300, false)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ZoneDto.class).returnResult().getResponseBody();
        assertNotNull(zoneDto);
        assertEquals("Zone A", zoneDto.getName());
        assertEquals("Pop", zoneDto.getGenre());
        assertEquals("300", zoneDto.getCapacity().toString());
        assertEquals(false, zoneDto.getAdaptedDisabled());
    }

    @Test
    void testCreateZoneException() {
        ZoneDto zoneDto = new ZoneDto("Zone A", null, null, false);
        this.webTestClient
                .post().uri(ZoneResource.ZONES)
                .body(BodyInserters.fromObject(zoneDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}