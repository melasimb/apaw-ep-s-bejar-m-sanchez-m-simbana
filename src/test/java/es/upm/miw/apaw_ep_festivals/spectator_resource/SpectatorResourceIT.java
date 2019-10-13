package es.upm.miw.apaw_ep_festivals.spectator_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ApiTestConfig
public class SpectatorResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        LocalDateTime birthday = LocalDateTime.of(1995, 4, 17, 11, 0);
        SpectatorDto spectatorDto = createSpectator("Sonia", "Béjar", birthday);
        assertNotNull(spectatorDto);
        assertEquals("Sonia", spectatorDto.getName());
        assertEquals("Béjar", spectatorDto.getSurname());
        assertEquals(birthday, spectatorDto.getBirthday());
        String spectatorToString = "SpectatorDto{" +
                "id='" + spectatorDto.getId() + '\'' +
                ", name=" + spectatorDto.getName() +
                ", surname='" + spectatorDto.getSurname() +
                ", birthday='" + spectatorDto.getBirthday() + '\'' +
                '}';
        assertEquals(spectatorToString, spectatorDto.toString());
    }

    SpectatorDto createSpectator(String name, String surname, LocalDateTime birthday) {
        SpectatorDto spectatorDto =
                new SpectatorDto(name, surname, birthday);
        return this.webTestClient
                .post().uri(SpectatorResource.SPECTATORS)
                .body(BodyInserters.fromObject(spectatorDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(SpectatorDto.class).returnResult().getResponseBody();
    }

    @Test
    void testCreateSpectatorException() {
        SpectatorDto suggestionDto = new SpectatorDto("Marcos", null, LocalDateTime.now());
        this.webTestClient
                .post().uri(SpectatorResource.SPECTATORS)
                .body(BodyInserters.fromObject(suggestionDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testReadSpectator() {
        LocalDateTime birthday = LocalDateTime.of(1995, 2, 23, 5, 30);
        String id = createSpectator("Mary", "Sánchez", birthday).getId();
        SpectatorDto spectatorDto = this.webTestClient
                .get().uri(SpectatorResource.SPECTATORS + SpectatorResource.ID_ID, id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(SpectatorDto.class)
                .returnResult().getResponseBody();
        assertEquals(id, spectatorDto.getId());
        assertEquals("Mary", spectatorDto.getName());
        assertEquals("Sánchez", spectatorDto.getSurname());
        assertEquals(birthday, spectatorDto.getBirthday());
    }

    @Test
    void testReadSpectatorException() {
        this.webTestClient
                .get().uri(SpectatorResource.SPECTATORS + SpectatorResource.ID_ID, "no")
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }
}
