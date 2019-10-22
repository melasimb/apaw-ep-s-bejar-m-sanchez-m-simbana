package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import es.upm.miw.apaw_ep_festivals.concert_data.ConcertDao;
import es.upm.miw.apaw_ep_festivals.concert_data.ConcertDto;
import es.upm.miw.apaw_ep_festivals.exceptions.NotFoundException;
import es.upm.miw.apaw_ep_festivals.zone_data.Zone;
import es.upm.miw.apaw_ep_festivals.zone_data.ZoneDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class BandBusinessController {

    private BandDao bandDao;

    private ConcertDao concertDao;

    private ZoneDao zoneDao;

    private EmitterProcessor<String> emitter;

    @Autowired
    public BandBusinessController(BandDao bandDao, ConcertDao concertDao, ZoneDao zoneDao) {
        this.bandDao = bandDao;
        this.concertDao = concertDao;
        this.zoneDao = zoneDao;
        this.emitter = EmitterProcessor.create();
    }

    public Flux<String> publisher() {
        return this.emitter;
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
        this.emitter.onNext(band.getName());
        return new BandBasicDto(band);
    }

    public Boolean findBandByRole(Band band, String role) {
        List<Artist> artists = band.getArtists().stream().filter(artist -> artist.getRole().equals(role)).collect(Collectors.toList());
        return !artists.isEmpty();
    }

    public List<BandDto> findByRole(String role) {
        return this.bandDao.findAll().stream().filter(band -> this.findBandByRole(band, role)).map(BandDto::new).collect(Collectors.toList());
    }

    private Band findBandByIdAssured(String id) {
        return this.bandDao.findById(id).orElseThrow(() -> new NotFoundException("Band id: " + id));
    }

    public BandDto updateArtistsName(String id, BandPatchDto bandPatchDto) {
        Band band = this.findBandByIdAssured(id);
        List<Artist> artists = band.getArtists().stream().map(artist -> {
            if (artist.getName().equals(bandPatchDto.getOldName())) {
                artist.setName(bandPatchDto.getNewName());
            }
            return artist;
        }).collect(Collectors.toList());
        band.setArtists(artists);
        this.bandDao.save(band);
        return new BandDto(band);
    }

    public List<Artist> getArtists(String id) {
        Band band = this.findBandByIdAssured(id);
        return band.getArtists();
    }

    public void delete(String id) {
        Band band = this.findBandByIdAssured(id);
        this.bandDao.delete(band);
    }

    public List<BandDto> findByConcertDate(LocalDateTime concertDate) {
        return this.bandDao.findAll().stream()
                .filter(band -> band.getConcerts().stream().anyMatch((concert -> concert.getDate().getDayOfYear() == concertDate.getDayOfYear())))
                .map(BandDto::new)
                .collect(Collectors.toList());
    }

    public void updateConcert(String idBand, String idConcert, ConcertDto concertDto) {
        Band band = this.findBandByIdAssured(idBand);
        Zone zone = this.zoneDao.findById(concertDto.getZoneId()).orElseThrow(() -> new NotFoundException("Zone id: " + concertDto.getZoneId()));
        Optional<Concert> optionalConcert = band.getConcerts().stream().filter(concertToChange -> concertToChange.getId().equals(idConcert)).findFirst();
        if (!optionalConcert.isPresent()) {
            throw new NotFoundException("Concert is not present in Band: " + idBand);
        }
        Concert concert = optionalConcert.get();
        concert.setZone(zone);
        concert.setDuration(concertDto.getDuration());
        concert.setDate(concertDto.getDate());
        this.concertDao.save(concert);
    }
}
