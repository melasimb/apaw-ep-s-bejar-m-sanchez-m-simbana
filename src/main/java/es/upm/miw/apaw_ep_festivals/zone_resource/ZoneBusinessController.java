package es.upm.miw.apaw_ep_festivals.zone_resource;

import es.upm.miw.apaw_ep_festivals.zone_data.Zone;
import es.upm.miw.apaw_ep_festivals.zone_data.ZoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ZoneBusinessController {

    private ZoneDao zoneDao;

    @Autowired
    public ZoneBusinessController(ZoneDao zoneDao) {
        this.zoneDao = zoneDao;
    }

    public ZoneDto create(ZoneDto zoneDto) {
        Zone zone = new Zone(zoneDto.getName(), zoneDto.getGenre(), zoneDto.getCapacity(), zoneDto.getAdaptedDisabled());
        this.zoneDao.save(zone);
        return new ZoneDto(zone);
    }
}
