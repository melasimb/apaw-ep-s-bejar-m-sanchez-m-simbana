package es.upm.miw.apaw_ep_festivals.zone_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ApiTestConfig
class ZoneResourceTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        LocalDateTime starDate = LocalDateTime.now();
        LocalDateTime endDate = LocalDateTime.now();
        ZoneDto zoneDto = this.webTestClient
                .post().uri(ZoneResource.ZONE)
                .body(BodyInserters.fromObject(new ZoneDto("Zone A", 20.0, starDate, endDate, "Madrid")))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ZoneDto.class).returnResult().getResponseBody();
        assertNotNull(zoneDto);
        assertEquals("Zone A", zoneDto.getName());
        assertEquals("20.0", zoneDto.getPrice().toString());
        assertEquals(starDate, zoneDto.getStartDate());
        assertEquals(endDate, zoneDto.getEndDate());
    }

    @Test
    void testCreateZoneException() {
        ZoneDto zoneDto = new ZoneDto("Zone A", 20.0, null, null, "Madrid");
        this.webTestClient
                .post().uri(ZoneResource.ZONE)
                .body(BodyInserters.fromObject(zoneDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}