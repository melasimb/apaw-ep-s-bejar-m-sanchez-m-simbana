package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.ConcertDto;
import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(BandResource.BANDS)
public class BandResource {

    public static final String BANDS = "/bands";
    public static final String CONCERTS = "/concerts";
    public static final String SEARCH = "/search";
    public static final String ID_ID = "/{id}";
    public static final String ARTISTS = "/artists";
    public static final String ID_CONCERT = "/{idConcert}";
    public static final String ID_BAND = "/{idBand}";

    private BandBusinessController bandBusinessController;

    @Autowired
    public BandResource(BandBusinessController bandBusinessController) {
        this.bandBusinessController = bandBusinessController;
    }

    @PostMapping
    public BandBasicDto create(@RequestBody(required = false) BandCreationDto bandCreationDto) {
        bandCreationDto.validate();
        return this.bandBusinessController.create(bandCreationDto);
    }

    @GetMapping(value = SEARCH)
    public List<BandDto> find(@RequestParam String q) {
        List<BandDto> bands;
        switch (q.split(":")[0]) {
            case "role":
                bands = this.bandBusinessController.findByRole(q.split(":")[1]);
                break;
            case "concerts.date":
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                if (!q.split(":", 2)[1].matches("^\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}")) {
                    throw new BadRequestException("date format is not correct (yyyy-MM-dd HH:mm)");
                }
                LocalDateTime date = LocalDateTime.parse(q.split(":", 2)[1], dateTimeFormatter);
                bands = this.bandBusinessController.findByConcertDate(date);
                break;
            default:
                throw new BadRequestException("query param is incorrect");
        }
        return bands;
    }

    @PatchMapping(value = ID_ID + ARTISTS)
    public BandDto updateArtistsName(@PathVariable String id, @RequestBody BandPatchDto bandPatchDto) {
        bandPatchDto.validate();
        return this.bandBusinessController.updateArtistsName(id, bandPatchDto);
    }

    @GetMapping(value = ID_ID + ARTISTS)
    public List<Artist> getArtistsByIdBand(@PathVariable String id) {
        return this.bandBusinessController.getArtists(id);
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id) {
        this.bandBusinessController.delete(id);
    }

    @PutMapping(value = ID_BAND + CONCERTS + ID_CONCERT)
    public void updateConcert(@PathVariable String idBand, @PathVariable String idConcert, @RequestBody ConcertDto concertDto) {
        concertDto.validate();
        this.bandBusinessController.updateConcert(idBand, idConcert, concertDto);
    }
}
