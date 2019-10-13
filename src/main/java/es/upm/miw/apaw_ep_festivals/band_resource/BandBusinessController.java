package es.upm.miw.apaw_ep_festivals.band_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BandBusinessController {

    private BandDao bandDao;

    @Autowired
    public BandBusinessController(BandDao bandDao) {
        this.bandDao = bandDao;
    }

    public List<BandDto> findByRole(String role) {
        List<BandDto> bandDtos = new ArrayList<>();
        List<Band> bands = this.bandDao.findAll();
        for (Band band : bands) {
            boolean add = false;
            List<Artist> artists = band.getArtists();
            for (Artist artist : artists) {
                if (artist.getRole().equals(role)) {
                    add = true;
                }
            }
            if (add) {
                bandDtos.add(new BandDto(band));
            }
        }
        return bandDtos;
    }
}
