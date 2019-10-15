package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import es.upm.miw.apaw_ep_festivals.concert_resource.ConcertDto;
import es.upm.miw.apaw_ep_festivals.concert_resource.ConcertResource;
import es.upm.miw.apaw_ep_festivals.zone_resource.ZoneDto;
import es.upm.miw.apaw_ep_festivals.zone_resource.ZoneResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApiTestConfig
class BandResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    BandBasicDto createBand(String name, List<Artist> artists, LocalDateTime concertDate, Integer concertDuration) {
        List<String> concertsId = new ArrayList<>();
        String zoneId = this.webTestClient
                .post().uri(ZoneResource.ZONES)
                .body(BodyInserters.fromObject(new ZoneDto("Zone A", "Pop", 300, false)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ZoneDto.class).returnResult().getResponseBody().getId();
        String concertId = this.webTestClient
                .post().uri(ConcertResource.CONCERTS)
                .body(BodyInserters.fromObject(new ConcertDto(concertDate, concertDuration, zoneId)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ConcertDto.class)
                .returnResult().getResponseBody().getId();
        concertsId.add(concertId);
        return this.webTestClient
                .post().uri(BandResource.BANDS)
                .body(BodyInserters.fromObject(new BandCreationDto(name, artists, concertsId)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BandBasicDto.class)
                .returnResult().getResponseBody();
    }

    BandBasicDto createBand(String name, List<Artist> artists) {

        return this.webTestClient
                .post().uri(BandResource.BANDS)
                .body(BodyInserters.fromObject(new BandCreationDto(name, artists, null)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BandBasicDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    void testCreateConcertsNull() {
        List<Artist> artists = new ArrayList<>();
        LocalDateTime birthday = LocalDateTime.of(1995, 4, 17, 11, 0);
        artists.add(new Artist("Alecia Beth", birthday, "singer"));
        this.createBand("Pink", artists);
    }

    @Test
    void testCreate() {
        List<Artist> artists = new ArrayList<>();
        LocalDateTime birthday = LocalDateTime.of(1995, 4, 17, 11, 0);
        artists.add(new Artist("Chris Martin", birthday, "singer"));
        LocalDateTime concertDate = LocalDateTime.of(2019, 5, 15, 22, 30);
        this.createBand("Green day", artists, concertDate, 30);
    }

    @Test
    void testCreateConcertIdException() {
        List<Artist> artists = new ArrayList<>();
        LocalDateTime birthday = LocalDateTime.of(1995, 4, 17, 11, 0);
        artists.add(new Artist("Alecia Beth", birthday, "singer"));
        List<String> concertsId = new ArrayList<>();
        concertsId.add("123456");
        System.out.println(concertsId);
        this.webTestClient
                .post().uri(BandResource.BANDS)
                .body(BodyInserters.fromObject(new BandCreationDto("Pink", artists, concertsId)))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateBadException() {
        List<Artist> artists = new ArrayList<>();
        List<String> concertsId = new ArrayList<>();
        concertsId.add("123456");
        System.out.println(concertsId);
        this.webTestClient
                .post().uri(BandResource.BANDS)
                .body(BodyInserters.fromObject(new BandCreationDto("Pink", artists, concertsId)))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}