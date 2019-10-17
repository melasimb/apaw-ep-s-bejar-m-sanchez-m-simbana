package es.upm.miw.apaw_ep_festivals.spectator_data;

import es.upm.miw.apaw_ep_festivals.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
class SpectatorDaoIT {

    @Autowired
    private SpectatorDao spectatorDao;

    @Test
    void testCreate() {
        Spectator spectator = new Spectator("Melany", "Simbaña", LocalDateTime.now());
        this.spectatorDao.save(spectator);
        Spectator databaseSpectator = this.spectatorDao.findById(spectator.getId()).orElseGet(Assertions::fail);
        assertEquals("Melany", databaseSpectator.getName());
    }
}