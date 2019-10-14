package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.condert_data.Concert;
import es.upm.miw.apaw_ep_festivals.condert_data.ConcertDao;
import es.upm.miw.apaw_ep_festivals.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BandBusinessController {

    private BandDao bandDao;

    private ConcertDao concertDao;

    @Autowired
    public BandBusinessController(BandDao bandDao, ConcertDao concertDao) {
        this.bandDao = bandDao;
        this.concertDao = concertDao;
    }

    public BandBasicDto create(BandCreationDto bandCreationDto) {
        List<Concert> concerts = new ArrayList<>();
        if (bandCreationDto.getConcertsId() != null) {
            bandCreationDto.getConcertsId().forEach((final String id) -> {
                Concert concert = this.concertDao.findById(id).orElseThrow(() -> new NotFoundException("Concert id: " + id));
                concerts.add(concert);
            });
        }
        Band band = new Band(bandCreationDto.getName(), bandCreationDto.getArtists(), concerts);
        this.bandDao.save(band);
        return new BandBasicDto(band);
    }

}

