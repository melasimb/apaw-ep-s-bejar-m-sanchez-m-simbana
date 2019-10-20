package es.upm.miw.apaw_ep_festivals.festival_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;
import es.upm.miw.apaw_ep_festivals.spectator_data.SpectatorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(FestivalResource.FESTIVALS)
public class FestivalResource {

    public static final String FESTIVALS = "/festivals";
    public static final String CONCERTS = "/concerts";
    public static final String ID_ID = "/{id}";
    public static final String SPECTATORS = "/spectators";
    public static final String SEARCH = "/search";

    private FestivalBusinessController festivalBusinessController;

    @Autowired
    public FestivalResource(FestivalBusinessController festivalBusinessController) {
        this.festivalBusinessController = festivalBusinessController;
    }

    @PostMapping
    public FestivalBasicDto create(@RequestBody FestivalBasicDto festivalBasicDto) {
        festivalBasicDto.validate();
        return this.festivalBusinessController.create(festivalBasicDto);
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id) {
        if (id.isEmpty()) {
            throw new BadRequestException("Id is empty");
        }
        this.festivalBusinessController.delete(id);
    }

    @PostMapping(value = ID_ID + SPECTATORS)
    public FestivalFullDto createSpectator(@PathVariable String id, @RequestBody SpectatorDto spectatorDto) {
        spectatorDto.validate();
        return this.festivalBusinessController.createSpectator(id, spectatorDto);
    }

    @GetMapping(value = ID_ID + CONCERTS + SEARCH)
    public List<Concert> find(@PathVariable String id, @RequestParam String q) {
        if (!"zone.adaptedDisabled".equals(q.split(":")[0])) {
            throw new BadRequestException("Query param role is incorrect");
        }
        return this.festivalBusinessController.findConcertsAdaptedDisabled(id);
    }
}
