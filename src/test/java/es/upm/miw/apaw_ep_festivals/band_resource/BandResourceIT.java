package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import es.upm.miw.apaw_ep_festivals.concert_resource.ConcertDto;
import es.upm.miw.apaw_ep_festivals.concert_resource.ConcertResource;
import es.upm.miw.apaw_ep_festivals.zone_data.Zone;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

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
    void testFindByRole() {
        List<Artist> artistsBandOne = new ArrayList<>();
        artistsBandOne.add(new Artist("Alecia Beth", LocalDateTime.now(), "singer"));
        createBand("band-1", artistsBandOne);
        List<Artist> artistsBandTwo = new ArrayList<>();
        artistsBandTwo.add(new Artist("Carlos Santana", LocalDateTime.now(), "guitar player"));
        createBand("band-2", artistsBandTwo);
        List<Artist> artistsBandThree = new ArrayList<>();
        artistsBandThree.add(new Artist("Alicia Keys", LocalDateTime.now(), "singer"));
        createBand("band-3", artistsBandThree);
        List<BandDto> bands = this.webTestClient
                .get().uri(uriBuilder -> uriBuilder.path(BandResource.BANDS + BandResource.SEARCH)
                        .queryParam("q", "role:singer")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BandDto.class)
                .returnResult().getResponseBody();
        assertFalse(bands.isEmpty());
        String roleBandOne = bands.get(0).getArtists().get(0).getRole();
        String roleBandThree = bands.get(1).getArtists().get(0).getRole();
        assertEquals(2, bands.size());
        assertEquals("singer", roleBandOne);
        assertEquals("singer", roleBandThree);
    }

    @Test
    void testFindByRoleException() {
        this.webTestClient
                .get().uri(uriBuilder -> uriBuilder.path(BandResource.BANDS + BandResource.SEARCH)
                .queryParam("q", "role=singer")
                .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUpdateArtistsName() {
        List<Artist> artistsBandThree = new ArrayList<>();
        artistsBandThree.add(new Artist("Alicia Keys", LocalDateTime.now(), "singer"));
        BandBasicDto bandBasicDto = createBand("band-4", artistsBandThree);
        BandDto bandDto = this.webTestClient
                .patch().uri(BandResource.BANDS + BandResource.ID_ID + BandResource.ARTISTS, bandBasicDto.getId())
                .body(BodyInserters.fromObject(new BandPatchDto("Alicia Keys", "Alicia K")))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BandDto.class)
                .returnResult().getResponseBody();
        assertEquals("Alicia K", bandDto.getArtists().get(0).getName());
    }

    @Test
    void testUpdateArtistNameException() {
        List<Artist> artistsBandThree = new ArrayList<>();
        artistsBandThree.add(new Artist("Alicia Keys", LocalDateTime.now(), "singer"));
        BandBasicDto bandBasicDto = createBand("band-4", artistsBandThree);
        this.webTestClient
                .patch().uri(BandResource.BANDS + BandResource.ID_ID + BandResource.ARTISTS, bandBasicDto.getId())
                .body(BodyInserters.fromObject(new BandPatchDto("", "Alicia K")))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUpdateArtistNameIdException() {
        this.webTestClient
                .patch().uri(BandResource.BANDS + BandResource.ID_ID + BandResource.ARTISTS, "1234")
                .body(BodyInserters.fromObject(new BandPatchDto("Alicia Keys", "Alicia K")))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testCreateConcertsNull() {
        List<Artist> artists = new ArrayList<>();
        LocalDateTime birthday = LocalDateTime.of(1995, 4, 17, 11, 0);
        artists.add(new Artist("Alecia Beth", birthday, "singer"));
        BandBasicDto bandBasicDto = this.createBand("Pink", artists);
        assertEquals("Pink", bandBasicDto.getName());
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

    @Test
    void testClassBand() {
        Band band = new Band("Bowling for soup", null, null);
        LocalDateTime birthday = LocalDateTime.of(1982, 4, 17, 5, 0);
        LocalDateTime concertDate = LocalDateTime.of(2019, 7, 12, 18, 45);
        List<Artist> artist = new ArrayList<>();
        artist.add(new Artist("Dave", birthday, "singer"));
        band.setArtists(artist);
        List<Concert> concerts = new ArrayList<>();
        concerts.add(new Concert(concertDate, 33, new Zone("Zone Pop", "pop", 1000, false)));
        band.setConcerts(concerts);
        band.setName("Bowling For Soup");
        assertEquals(concerts, band.getConcerts());
        assertEquals(artist, band.getArtists());
        assertEquals("Bowling For Soup", band.getName());
    }
}