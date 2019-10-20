package es.upm.miw.apaw_ep_festivals.zone_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import es.upm.miw.apaw_ep_festivals.concert_data.ConcertDao;
import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;
import es.upm.miw.apaw_ep_festivals.exceptions.NotFoundException;
import es.upm.miw.apaw_ep_festivals.zone_data.Zone;
import es.upm.miw.apaw_ep_festivals.zone_data.ZoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ZoneBusinessController {

    private ZoneDao zoneDao;
    private ConcertDao concertDao;
    private EmitterProcessor<String> emitter;

    @Autowired
    public ZoneBusinessController(ZoneDao zoneDao, ConcertDao concertDao) {
        this.zoneDao = zoneDao;
        this.concertDao = concertDao;
        this.emitter = EmitterProcessor.create();
    }

    public Flux<String> publisher() {
        return this.emitter;
    }

    public ZoneDto create(ZoneDto zoneDto) {
        Zone zone = new Zone(zoneDto.getName(), zoneDto.getGenre(), zoneDto.getCapacity(), zoneDto.getAdaptedDisabled());
        this.zoneDao.save(zone);
        ZoneDto zoneDtoReturn = new ZoneDto(zone);
        this.emitter.onNext("The following zone has been added: " + zoneDtoReturn.getBasicInformation());
        return zoneDtoReturn;
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

    public void delete(String id) {
        Zone zone = this.findZoneByIdAssured(id);
        List<Concert> concertList = this.concertDao.findAll().stream()
                .filter(concert -> concert.getZone().getId().equals(id))
                .collect(Collectors.toList());
        if (!concertList.isEmpty()) {
            throw new BadRequestException("Zone with id: " + id + " can't be deleted because it is assigned to a concert.");
        } else {
            this.zoneDao.delete(zone);
        }
    }
}
