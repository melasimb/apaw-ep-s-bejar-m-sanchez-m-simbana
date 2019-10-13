package es.upm.miw.apaw_ep_festivals.spectator_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@ApiTestConfig
public class SpectatorResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void testCreate() {
        LocalDateTime birthday = LocalDateTime.of(1995, 4, 17, 11, 0);
        SpectatorDto spectatorDto = this.webTestClient
                .post().uri(SpectatorResource.SPECTATORS)
                .body(BodyInserters.fromObject(new SpectatorDto("Sonia", "Béjar", birthday)))
                .exchange()
                .expectStatus().isOk()
                .expectBody(SpectatorDto.class).returnResult().getResponseBody();
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

    @Test
    void testCreateSpectatorException() {
        SpectatorDto suggestionDto = new SpectatorDto("Marcos", null, LocalDateTime.now());
        this.webTestClient
                .post().uri(SpectatorResource.SPECTATORS)
                .body(BodyInserters.fromObject(suggestionDto))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }
}
