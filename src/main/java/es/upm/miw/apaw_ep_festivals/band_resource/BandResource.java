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
    public static final String ID_ID = "{id}";

    private BandBusinessController bandBusinessController;

    @Autowired
    public BandResource(BandBusinessController bandBusinessController) {
        this.bandBusinessController = bandBusinessController;
    }

    @GetMapping(value = SEARCH)
    public List<BandDto> find(@RequestParam String q) {
        if (!"role".equals(q.split(":=")[0])) {
            throw new BadRequestException("query param role is incorrect");
        }
        return this.bandBusinessController.findByRole(q.split(":=")[1]);
    }

    @PatchMapping(value = ID_ID)
    public void patch(@PathVariable String id, @RequestBody BandPatchDto bandPatchDto) {
        bandPatchDto.validate();
        this.bandBusinessController.patch(id, bandPatchDto);
    }
}
