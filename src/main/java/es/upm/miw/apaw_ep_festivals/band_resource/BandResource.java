package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(BandResource.BANDS)
public class BandResource {

    public static final String BANDS = "/bands";
    public static final String SEARCH = "/search";
    public static final String ID_ID = "/{id}";
    public static final String ARTISTS = "/artists";

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
    public List<BandDto> findByRole(@RequestParam String q) {
        if (!"role".equals(q.split(":")[0])) {
            throw new BadRequestException("query param role is incorrect");
        }
        return this.bandBusinessController.findByRole(q.split(":")[1]);
    }

    @PatchMapping(value = ID_ID + ARTISTS)
    public BandDto updateArtistsName(@PathVariable String id, @RequestBody BandPatchDto bandPatchDto) {
        bandPatchDto.validate();
        return this.bandBusinessController.updateArtistsName(id, bandPatchDto);
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id) {
        this.bandBusinessController.delete(id);
    }
}
