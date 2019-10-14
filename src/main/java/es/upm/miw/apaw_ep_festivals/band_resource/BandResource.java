package es.upm.miw.apaw_ep_festivals.band_resource;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(BandResource.BANDS)
public class BandResource {

    static final String BANDS = "/bands";

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
}
