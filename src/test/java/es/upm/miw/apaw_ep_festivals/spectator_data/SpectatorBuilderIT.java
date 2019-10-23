package es.upm.miw.apaw_ep_festivals.spectator_data;

import es.upm.miw.apaw_ep_festivals.TestConfig;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
public class SpectatorBuilderIT {

    @Test
    void testSpectatorBuilder() {
        LocalDateTime date = LocalDateTime.of(1995, 3, 24, 9, 0);
        Spectator spectator = Spectator.builder().name("Tony").surname("Pacheco").birthday(date).build();
        assertEquals("Tony", spectator.getName());
        assertEquals("Pacheco", spectator.getSurname());
        assertEquals(date, spectator.getBirthday());
    }

    @Test
    void testSpectatorDtoBuilderByDefault() {
        LocalDateTime date = LocalDateTime.of(1995, 3, 24, 9, 0);
        Spectator spectator = Spectator.builder().byDefault().build();
        assertEquals("Tony", spectator.getName());
        assertEquals("Pacheco", spectator.getSurname());
        assertEquals(date, spectator.getBirthday());
    }
}
