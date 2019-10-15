package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import es.upm.miw.apaw_ep_festivals.concert_data.ConcertDao;
import es.upm.miw.apaw_ep_festivals.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private Band findBandByIdAssured(String id) {
        return this.bandDao.findById(id).orElseThrow(() -> new NotFoundException("Band id: " + id));
    }

    public void patch(String id, BandPatchDto bandPatchDto) {
        Band band = this.findBandByIdAssured(id);
        List<Artist> artists = band.getArtists().stream().map(artist -> {
            if (artist.getName().equals(bandPatchDto.getOldName())) {
                artist.setName(bandPatchDto.getNewName());
            }
            return artist;
        }).collect(Collectors.toList());
        band.setArtists(artists);
        this.bandDao.save(band);
    }
}
