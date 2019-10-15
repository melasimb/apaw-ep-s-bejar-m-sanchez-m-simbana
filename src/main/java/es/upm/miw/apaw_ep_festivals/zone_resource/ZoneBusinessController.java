package es.upm.miw.apaw_ep_festivals.zone_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.NotFoundException;
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

    private Zone findZoneByIdAssured(String id) {
        return this.zoneDao.findById(id).orElseThrow(() -> new NotFoundException("Zone id: " + id));
    }

    public void update(String id, ZoneDto zoneDto) {
        Zone zone = this.findZoneByIdAssured(id);
        zone.setName(zoneDto.getName());
        zone.setGenre(zoneDto.getGenre());
        zone.setCapacity(zoneDto.getCapacity());
        zone.setAdaptedDisabled(zoneDto.getAdaptedDisabled());
        this.zoneDao.save(zone);
    }
}
