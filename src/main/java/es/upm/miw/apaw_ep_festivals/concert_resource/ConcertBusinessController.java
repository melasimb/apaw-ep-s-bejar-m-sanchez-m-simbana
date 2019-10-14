package es.upm.miw.apaw_ep_festivals.concert_resource;

import es.upm.miw.apaw_ep_festivals.condert_data.Concert;
import es.upm.miw.apaw_ep_festivals.condert_data.ConcertDao;
import es.upm.miw.apaw_ep_festivals.exceptions.NotFoundException;
import es.upm.miw.apaw_ep_festivals.zone_data.Zone;
import es.upm.miw.apaw_ep_festivals.zone_data.ZoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ConcertBusinessController {

    private ConcertDao concertDao;

    private ZoneDao zoneDao;

    @Autowired
    public ConcertBusinessController(ConcertDao concertDao, ZoneDao zoneDao) {
        this.concertDao = concertDao;
        this.zoneDao = zoneDao;
    }

    public ConcertDto create(ConcertDto concertDto) {
        Zone zone = this.zoneDao.findById(concertDto.getZoneId())
                .orElseThrow(() -> new NotFoundException("Zone id: " + concertDto.getZoneId()));
        Concert concert = new Concert(concertDto.getDate(), concertDto.getDuration(), zone);
        this.concertDao.save(concert);
        return new ConcertDto(concert);
    }
}
