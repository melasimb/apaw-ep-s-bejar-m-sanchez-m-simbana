package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(BandResource.BANDS)
public class BandResource {

    public static final String BANDS = "/bands";
    public static final String SEARCH = "/search";

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
}
