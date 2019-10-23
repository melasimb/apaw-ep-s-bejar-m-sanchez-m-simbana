package es.upm.miw.apaw_ep_festivals.spectator_resource;

import es.upm.miw.apaw_ep_festivals.TestConfig;
import es.upm.miw.apaw_ep_festivals.spectator_data.SpectatorDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
public class SpectatorDtoBuilderIT {

    @Test
    void testSpectatorDtoBuilder() {
        LocalDateTime now = LocalDateTime.now();
        SpectatorDto spectatorDto = SpectatorDto.builder().name("Tony").surname("Pacheco").birthday(now).build();
        assertEquals("Tony", spectatorDto.getName());
        assertEquals("Pacheco", spectatorDto.getSurname());
        assertEquals(now, spectatorDto.getBirthday());
    }

    @Test
    void testSpectatorDtoBuilderByDefault() {
        SpectatorDto spectatorDtoDto = SpectatorDto.builder().byDefault().build();
        assertEquals("Sonia", spectatorDtoDto.getName());
        assertEquals("Béjar", spectatorDtoDto.getSurname());
        assertEquals(LocalDateTime.of(1995, 4, 17, 11, 0), spectatorDtoDto.getBirthday());
    }
}
