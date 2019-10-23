package es.upm.miw.apaw_ep_festivals.zone_resource;

import es.upm.miw.apaw_ep_festivals.TestConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestConfig
class ZoneDtoBuilderIT {

    @Test
    void testZoneDtoBuilder() {
        ZoneDto zoneDto = ZoneDto.builder().name("zone-1").genre("genre-1").capacity(3000).adaptedDisabled(false).build();
        assertEquals("zone-1", zoneDto.getName());
        assertEquals("genre-1", zoneDto.getGenre());
        assertEquals("3000", zoneDto.getCapacity().toString());
        assertEquals(false, zoneDto.getAdaptedDisabled());
    }

    @Test
    void testZoneDtoBuilderByDefault() {
        ZoneDto zoneDto = ZoneDto.builder().byDefault().build();
        assertEquals("zone-1", zoneDto.getName());
        assertEquals("genre-1", zoneDto.getGenre());
        assertEquals("100", zoneDto.getCapacity().toString());
        assertEquals(true, zoneDto.getAdaptedDisabled());
    }
}