package es.upm.miw.apaw_ep_festivals.festival_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(FestivalResource.FESTIVALS)
public class FestivalResource {

    public static final String FESTIVALS = "/festivals";
    public static final String ID_ID = "/{id}";

    private FestivalBusinessController festivalBusinessController;

    @Autowired
    public FestivalResource(FestivalBusinessController festivalBusinessController) {
        this.festivalBusinessController = festivalBusinessController;
    }

    @PostMapping
    public FestivalBasicDto create(@RequestBody FestivalCreationDto festivalCreationDto) {
        festivalCreationDto.validate();
        return this.festivalBusinessController.create(festivalCreationDto);
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id) {
        if (id.isEmpty()) {
            throw new BadRequestException("Id is empty");
        }
        this.festivalBusinessController.delete(id);
    }
}
