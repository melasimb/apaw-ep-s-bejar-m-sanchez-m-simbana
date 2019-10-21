package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import es.upm.miw.apaw_ep_festivals.concert_data.ConcertDao;
import es.upm.miw.apaw_ep_festivals.concert_data.ConcertDto;
import es.upm.miw.apaw_ep_festivals.concert_resource.ConcertResource;
import es.upm.miw.apaw_ep_festivals.zone_data.Zone;
import es.upm.miw.apaw_ep_festivals.zone_data.ZoneDao;
import es.upm.miw.apaw_ep_festivals.zone_resource.ZoneDto;
import es.upm.miw.apaw_ep_festivals.zone_resource.ZoneResource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ApiTestConfig
class BandResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BandDao bandDao;

    @Autowired
    private ZoneDao zoneDao;

    @Autowired
    private ConcertDao concertDao;

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
    void testFindByConcertDate() {
        LocalDateTime dateQueryParam = LocalDateTime.of(2019, 11, 24, 0, 0);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateQueryParam = dateQueryParam.format(dateTimeFormatter);

        List<Artist> artistsScorpions = new ArrayList<>();
        artistsScorpions.add(new Artist("Klaus Miller", LocalDateTime.now(), "main singer"));
        LocalDateTime ScorpionsConcertDate = LocalDateTime.of(2019, 11, 24, 22, 45);
        createBand("Scorpions", artistsScorpions, ScorpionsConcertDate, 120);

        List<Artist> artistsTheCure = new ArrayList<>();
        artistsTheCure.add(new Artist("Robert Smith", LocalDateTime.now(), "main singer"));
        LocalDateTime TheCureConcertDate = LocalDateTime.of(2019, 12, 12, 22, 45);
        createBand("The Cure", artistsTheCure, TheCureConcertDate, 120);

        List<Artist> artistsTheOffspring = new ArrayList<>();
        artistsTheOffspring.add(new Artist("Dexter Holland", LocalDateTime.now(), "main singer"));
        LocalDateTime OffspringConcertDate = LocalDateTime.of(2019, 11, 24, 23, 45);
        createBand("The Offspring", artistsTheOffspring, OffspringConcertDate, 135);

        List<BandDto> bands = this.webTestClient
                .get().uri(uriBuilder -> uriBuilder.path(BandResource.BANDS + BandResource.SEARCH)
                        .queryParam("q", "concerts.date:" + formattedDateQueryParam)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BandDto.class)
                .returnResult().getResponseBody();
        assertFalse(bands.isEmpty());
        assertEquals(2, bands.size());
        assertTrue(bands.stream().anyMatch((bandDto -> bandDto.getName().equals("The Offspring"))));
        assertTrue(bands.stream().anyMatch((bandDto -> bandDto.getName().equals("Scorpions"))));
    }

    @Test
    void testFindByConcertDateException() {
        this.webTestClient
                .get().uri(uriBuilder -> uriBuilder.path(BandResource.BANDS + BandResource.SEARCH)
                .queryParam("q", "concerts.date:" + "bad formatted date")
                .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testFindByConcertDateEmptyResponse() {
        LocalDateTime dateQueryParam = LocalDateTime.of(2020, 10, 8, 0, 0);
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formattedDateQueryParam = dateQueryParam.format(dateTimeFormatter);

        List<BandDto> bands = this.webTestClient
                .get().uri(uriBuilder -> uriBuilder.path(BandResource.BANDS + BandResource.SEARCH)
                        .queryParam("q", "concerts.date:" + formattedDateQueryParam)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BandDto.class)
                .returnResult().getResponseBody();
        assertTrue(bands.isEmpty());
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

    @Test
    void testGetArtistsNotFoundException() {
        this.webTestClient
                .get().uri(BandResource.BANDS + BandResource.ID_ID + BandResource.ARTISTS, "no")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testGetArtistsOfBand() {
        List<Artist> artists = new ArrayList<>();
        LocalDateTime birthdayFlea = LocalDateTime.of(1962, 11, 1, 5, 0);
        LocalDateTime birthdayAnthony = LocalDateTime.of(1962, 10, 16, 5, 0);
        LocalDateTime birthdayChard = LocalDateTime.of(1961, 10, 25, 5, 0);
        LocalDateTime birthdayJosh = LocalDateTime.of(1979, 10, 3, 5, 0);
        artists.add(new Artist("Anthony Kiedis", birthdayAnthony, "singer"));
        artists.add(new Artist("Flea", birthdayFlea, "guitarist"));
        artists.add(new Artist("Chard Smith", birthdayChard, "drummer"));
        artists.add(new Artist("Josh Klinghoffer", birthdayJosh, "multi instrument"));
        String id = createBand("Red Hot Chilli Peppers", artists).getId();
        List<Artist> artistsReturned = this.webTestClient
                .get().uri(BandResource.BANDS + BandResource.ID_ID + BandResource.ARTISTS, id)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Artist.class)
                .returnResult().getResponseBody();
        assertTrue(artists.toString().equals(artistsReturned.toString()));
    }

    @Test
    void testDeleteBand() {
        List<Artist> artists = new ArrayList<>();
        LocalDateTime birthdayFirstSinger = LocalDateTime.of(1980, 5, 24, 5, 17);
        LocalDateTime birthdaySecondSinger = LocalDateTime.of(1979, 8, 3, 20, 30);
        artists.add(new Artist("Nick Thomas", birthdayFirstSinger, "singer"));
        artists.add(new Artist("Chris Salish", birthdaySecondSinger, "singer"));
        LocalDateTime concertDate = LocalDateTime.of(2019, 11, 28, 23, 45);

        String idBand = this.createBand("Twenty One Pilots", artists, concertDate, 30).getId();
        this.webTestClient
                .delete().uri(BandResource.BANDS + BandResource.ID_ID, idBand)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteBandNotFoundException() {
        this.webTestClient
                .delete().uri(BandResource.BANDS + BandResource.ID_ID, "id not found")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }


    @Test
    void testUpdateConcert() {
        List<Artist> artists = new ArrayList<>();
        LocalDateTime birthday = LocalDateTime.of(1997, 5, 24, 5, 17);
        artists.add(new Artist("Dave Smith", birthday, "first guitar"));
        LocalDateTime concertDate = LocalDateTime.of(2022, 11, 28, 23, 45);
        BandBasicDto bandBasicDto = createBand("Sum 41", artists, concertDate, 30);
        Concert concertBeforeChange = this.bandDao.findById(bandBasicDto.getId()).get().getConcerts().get(0);
        Zone zone = new Zone("ZoneToChange", "rock", 2000, true);
        this.zoneDao.save(zone);
        Concert concertAfterChange = new Concert(concertDate, 60, this.zoneDao.findAll().get(0));
        this.webTestClient
                .put().uri(BandResource.BANDS + BandResource.ID_BAND + BandResource.CONCERTS + BandResource.ID_CONCERT, bandBasicDto.getId(), concertBeforeChange.getId())
                .body(BodyInserters.fromObject(new ConcertDto(concertAfterChange)))
                .exchange()
                .expectStatus().isOk();
        Concert changedConcert = this.concertDao.findById(concertBeforeChange.getId()).get();
        assertEquals(Integer.valueOf(60), changedConcert.getDuration());
        assertEquals(concertAfterChange.getDate(), changedConcert.getDate());
        assertEquals(concertAfterChange.getZone().getId(), changedConcert.getZone().getId());
    }

    @Test
    void testUpdateConcertNotFoundException() {
        List<Artist> artists = new ArrayList<>();
        LocalDateTime birthday = LocalDateTime.of(1964, 5, 24, 5, 17);
        artists.add(new Artist("Brian May", birthday, "first guitar"));
        LocalDateTime concertDate = LocalDateTime.of(2020, 5, 7, 23, 45);
        BandBasicDto bandBasicDto = createBand("Queen", artists, concertDate, 30);
        Concert concertBeforeChange = this.bandDao.findById(bandBasicDto.getId()).get().getConcerts().get(0);
        Zone zone = new Zone("ZoneToChange", "pop-rock", 3000, false);
        this.zoneDao.save(zone);
        Concert concertAfterChange = new Concert(concertDate, 60, this.zoneDao.findAll().get(0));
        this.webTestClient
                .put().uri(BandResource.BANDS + BandResource.ID_BAND + BandResource.CONCERTS + BandResource.ID_CONCERT, bandBasicDto.getId(), "id not found")
                .body(BodyInserters.fromObject(new ConcertDto(concertAfterChange)))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testUpdateConcertBadRequestException() {
        List<Artist> artists = new ArrayList<>();
        LocalDateTime birthday = LocalDateTime.of(1957, 8, 12, 4, 17);
        artists.add(new Artist("Ben Moody", birthday, "first guitar"));
        LocalDateTime concertDate = LocalDateTime.of(2022, 11, 28, 23, 45);
        BandBasicDto bandBasicDto = createBand("Evanescence", artists, concertDate, 30);

        this.webTestClient
                .put().uri(BandResource.BANDS + BandResource.ID_BAND + BandResource.CONCERTS + BandResource.ID_CONCERT, bandBasicDto.getId(), "id not found")
                .body(BodyInserters.fromObject(new ConcertDto()))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}