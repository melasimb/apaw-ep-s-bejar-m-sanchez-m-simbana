package es.upm.miw.apaw_ep_festivals.festival_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import es.upm.miw.apaw_ep_festivals.spectator_data.SpectatorDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
class FestivalResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    FestivalBasicDto createFestival(String name) {
        return this.webTestClient
                .post().uri(FestivalResource.FESTIVALS)
                .body(BodyInserters.fromObject(new FestivalBasicDto(name, 40.00, LocalDateTime.now(), LocalDateTime.now(), "Madrid")))
                .exchange()
                .expectStatus().isOk()
                .expectBody(FestivalBasicDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    void testCreate() {
        FestivalBasicDto festivalBasicDto = this.createFestival("festival-1");
        assertNotNull(festivalBasicDto);
    }

    @Test
    void testCreateFestivalException() {
        FestivalBasicDto festivalCreationDto = new FestivalBasicDto("", 20.00, null, null, "Barcelona");
        this.webTestClient
                .post().uri(FestivalResource.FESTIVALS)
                .body(BodyInserters.fromObject(festivalCreationDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testDelete() {
        FestivalBasicDto festivalBasicDto = this.createFestival("festival-2");
        this.webTestClient
                .delete().uri(FestivalResource.FESTIVALS + FestivalResource.ID_ID, festivalBasicDto.getId())
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testDeleteFestivalException() {
        this.webTestClient
                .delete().uri(FestivalResource.FESTIVALS + FestivalResource.ID_ID, "")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testCreateSpectator() {
        FestivalBasicDto festivalBasicDto = this.createFestival("festival-3");
        LocalDateTime birthday = LocalDateTime.now();
        SpectatorDto spectatorDto = new SpectatorDto("spectator-1", "spectator-1-surname", birthday);
        FestivalFullDto festivalFullDto = this.webTestClient
                .post().uri(FestivalResource.FESTIVALS + FestivalResource.ID_ID + FestivalResource.SPECTATORS, festivalBasicDto.getId())
                .body(BodyInserters.fromObject(spectatorDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(FestivalFullDto.class).returnResult().getResponseBody();
        String nameSpectator = festivalFullDto.getSpectators().get(0).getName();
        String surnameSpectator = festivalFullDto.getSpectators().get(0).getSurname();
        String birthdaySpectator = festivalFullDto.getSpectators().get(0).getBirthday().toString();
        assertEquals("spectator-1", nameSpectator);
        assertEquals("spectator-1-surname", surnameSpectator);
        assertEquals(birthday.toString(), birthdaySpectator);
    }
    void testSearchConcertsAdaptedDisabledNotFoundException() {
        String id = "no";
        this.webTestClient
                .get().uri(uriBuilder ->
                uriBuilder.path(FestivalResource.FESTIVALS + "/" + id + FestivalResource.CONCERTS + FestivalResource.SEARCH)
                        .queryParam("q", "zone.adaptedDisabled:true")
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testSearchConcertsAdaptedDisabledBadRequestException() {
        String id = "no";
        this.webTestClient
                .get().uri(uriBuilder ->
                uriBuilder.path(FestivalResource.FESTIVALS + "/" + id + FestivalResource.CONCERTS + FestivalResource.SEARCH)
                        .queryParam("q", "no")
                        .build())
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testSearchConcertsAdaptedDisabledEmpty() {
        FestivalBasicDto festivalBasicDto = createFestival("Viña Rock");
        String id = festivalBasicDto.getId();
        this.webTestClient
                .get().uri(uriBuilder ->
                uriBuilder.path(FestivalResource.FESTIVALS + "/" + id + FestivalResource.CONCERTS + FestivalResource.SEARCH)
                        .queryParam("q", "zone.adaptedDisabled:true")
                        .build())
                .exchange()
                .expectStatus().isOk();
    }
}