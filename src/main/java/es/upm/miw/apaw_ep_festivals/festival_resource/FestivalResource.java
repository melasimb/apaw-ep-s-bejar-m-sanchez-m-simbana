package es.upm.miw.apaw_ep_festivals.festival_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;
import es.upm.miw.apaw_ep_festivals.spectator_data.SpectatorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(FestivalResource.FESTIVALS)
public class FestivalResource {

    public static final String FESTIVALS = "/festivals";
    public static final String ID_ID = "/{id}";
    public static final String SPECTATORS = "/spectators";

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
}
