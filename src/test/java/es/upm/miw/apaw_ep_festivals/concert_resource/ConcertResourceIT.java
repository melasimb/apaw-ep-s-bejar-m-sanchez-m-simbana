package es.upm.miw.apaw_ep_festivals.concert_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import es.upm.miw.apaw_ep_festivals.concert_data.ConcertDto;
import es.upm.miw.apaw_ep_festivals.zone_resource.ZoneDto;
import es.upm.miw.apaw_ep_festivals.zone_resource.ZoneResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
class ConcertResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    ConcertDto createConcert(LocalDateTime date) {
        ZoneDto zoneDto = new ZoneDto("Zone C", "Latin", 5000, true);

        String zoneId = this.webTestClient
                .post().uri(ZoneResource.ZONES)
                .body(BodyInserters.fromObject(zoneDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ZoneDto.class)
                .returnResult().getResponseBody().getId();

        return this.webTestClient
                .post().uri(ConcertResource.CONCERTS)
                .body(BodyInserters.fromObject(new ConcertDto(date, 120, zoneId)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ConcertDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    void testCreate() {
        LocalDateTime date = LocalDateTime.now();
        ConcertDto concertDto = this.createConcert(date);
        assertNotNull(concertDto);
    }

    @Test
    void testCreateZoneException() {
        ConcertDto concertDto = new ConcertDto(LocalDateTime.now(), 120, "no");
        this.webTestClient
                .post().uri(ConcertResource.CONCERTS)
                .body(BodyInserters.fromObject(concertDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateConcertException() {
        ConcertDto concertDto = new ConcertDto(null, null, null);
        this.webTestClient.post().uri(ConcertResource.CONCERTS)
                .body(BodyInserters.fromObject(concertDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}