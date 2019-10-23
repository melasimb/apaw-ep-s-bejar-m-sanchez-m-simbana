package es.upm.miw.apaw_ep_festivals.zone_data;

import es.upm.miw.apaw_ep_festivals.TestConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
class ZoneDaoIT {

    @Autowired
    private ZoneDao zoneDao;

    @Test
    void testCreate() {
        Zone zone = Zone.builder().byDefault().build();
        this.zoneDao.save(zone);
        Zone databaseZone = this.zoneDao.findById(zone.getId()).orElseGet(Assertions::fail);
        assertEquals("zone-1", databaseZone.getName());
    }
}