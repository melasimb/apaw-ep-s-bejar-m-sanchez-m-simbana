package es.upm.miw.apaw_ep_festivals.zone_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import es.upm.miw.apaw_ep_festivals.concert_resource.ConcertDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
class ZoneResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        ZoneDto zoneDto = createZone("Zone A");
        assertNotNull(zoneDto);
        assertEquals("Zone A", zoneDto.getName());
        assertEquals("Pop", zoneDto.getGenre());
        assertEquals("300", zoneDto.getCapacity().toString());
        assertEquals(false, zoneDto.getAdaptedDisabled());
    }

    ZoneDto createZone(String name) {
        ZoneDto zoneDto = new ZoneDto(name, "Pop", 300, false);
        return this.webTestClient
                .post().uri(ZoneResource.ZONES)
                .body(BodyInserters.fromObject(zoneDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ZoneDto.class).returnResult().getResponseBody();
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

    @Test
    void testPutZone() {
        String id = createZone("Zone B").getId();
        ZoneDto zoneDto = new ZoneDto("Oasis", "Rock", 500, true);
        this.webTestClient
                .put().uri(ZoneResource.ZONES + ZoneResource.ID_ID, id)
                .body(BodyInserters.fromObject(zoneDto))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testPutZoneBadRequestException() {
        String id = createZone("Zone C").getId();
        this.webTestClient
                .put().uri(ZoneResource.ZONES + ZoneResource.ID_ID, id)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testPutZoneNotFoundException() {
        ZoneDto zoneDto = new ZoneDto("Oasis", "Rock", 500, true);
        this.webTestClient
                .put().uri(ZoneResource.ZONES + ZoneResource.ID_ID, "noId")
                .body(BodyInserters.fromObject(zoneDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    ConcertDto createConcert(LocalDateTime date) {
        ZoneDto zoneDto = new ZoneDto("Zone C", "Latin", 5000, true);

        String zoneId = this.webTestClient
                .post().uri(ZoneResource.ZONES)
                .body(BodyInserters.fromObject(zoneDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ZoneDto.class)
                .returnResult().getResponseBody().getId();

        ConcertDto concertDto = this.webTestClient
                .post().uri("/concerts")
                .body(BodyInserters.fromObject(new ConcertDto(date, 120, zoneId)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ConcertDto.class)
                .returnResult().getResponseBody();

        return concertDto;
    }

    @Test
    void testDeleteZone() {
        String id = createZone("Zone S").getId();
        this.webTestClient
                .delete().uri(ZoneResource.ZONES + ZoneResource.ID_ID, id)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteZoneBadRequestException() {
        String id = createConcert(LocalDateTime.now()).getZoneId();
        this.webTestClient
                .delete().uri(ZoneResource.ZONES + ZoneResource.ID_ID, id)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDeleteZoneNotFoundException() {
        this.webTestClient
                .delete().uri(ZoneResource.ZONES + ZoneResource.ID_ID, "no id")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }
}