package es.upm.miw.apaw_ep_festivals.zone_data;

import es.upm.miw.apaw_ep_festivals.TestConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
class ZoneBuilderIT {

    @Test
    void testZoneBuilder() {
        Zone zone = Zone.builder().name("zone-1").genre("genre-1").capacity(3000).adaptedDisabled(true).build();
        assertEquals("zone-1", zone.getName());
        assertEquals("genre-1", zone.getGenre());
        assertEquals("3000", zone.getCapacity().toString());
        assertEquals(true, zone.getAdaptedDisabled());
    }

    @Test
    void testZoneDtoBuilderByDefault() {
        Zone zone = Zone.builder().byDefault().build();
        assertEquals("zone-1", zone.getName());
        assertEquals("genre-1", zone.getGenre());
        assertEquals("100", zone.getCapacity().toString());
        assertEquals(true, zone.getAdaptedDisabled());
    }
}