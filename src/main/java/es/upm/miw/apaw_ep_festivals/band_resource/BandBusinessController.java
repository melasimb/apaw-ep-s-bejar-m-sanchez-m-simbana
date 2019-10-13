package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.ConcertDao;
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

    public List<BandDto> findByRole(String role) {
        List<BandDto> bandDtos = new ArrayList<BandDto>();
        List<Band> bands = this.bandDao.findAll();
        for (Band band : bands) {
            Boolean add = false;
            List<Artist> artists = band.getArtists();
            for (Artist artist : artists) {
                if (artist.getRole() == role) {
                    add = true;
                }
            }
            if (add == true) {
                bandDtos.add(new BandDto(band));
            }
        }
        return bandDtos;
    }
}
