package es.upm.miw.apaw_ep_festivals.concert_data;

import es.upm.miw.apaw_ep_festivals.TestConfig;
import es.upm.miw.apaw_ep_festivals.zone_data.Zone;
import es.upm.miw.apaw_ep_festivals.zone_data.ZoneDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
class ConcertDaoIT {

    @Autowired
    private ConcertDao concertDao;

    @Autowired
    private ZoneDao zoneDao;

    @Test
    void testCreate() {
        Zone zone = new Zone("Zone G", "Classic", 200, true);
        this.zoneDao.save(zone);
        Concert concert = new Concert(LocalDateTime.now(), 120, zone);
        this.concertDao.save(concert);
        Concert databaseConcert = this.concertDao.findById(concert.getId()).orElseGet(Assertions::fail);
        assertEquals("120", databaseConcert.getDuration().toString());
    }

}