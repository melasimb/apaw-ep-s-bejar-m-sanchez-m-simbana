package es.upm.miw.apaw_ep_festivals.festival_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
class FestivalResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    FestivalBasicDto createFestival(String name) {
        return this.webTestClient
                .post().uri(FestivalResource.FESTIVALS)
                .body(BodyInserters.fromObject(new FestivalCreationDto(name, 40.00, LocalDateTime.now(), LocalDateTime.now(), "Madrid")))
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
        FestivalCreationDto festivalCreationDto = new FestivalCreationDto("", 20.00, null, null, "Barcelona");
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