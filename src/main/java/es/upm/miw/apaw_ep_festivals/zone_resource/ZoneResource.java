package es.upm.miw.apaw_ep_festivals.zone_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ZoneResource.ZONE)
public class ZoneResource {

    static final String ZONE = "/zone";

    private ZoneBusinessController zoneBusinessController;

    @Autowired
    public ZoneResource(ZoneBusinessController zoneBusinessController) {
        this.zoneBusinessController = zoneBusinessController;
    }

    @PostMapping
    public ZoneDto create(@RequestBody ZoneDto zoneDto) {
        zoneDto.validate();
        return this.zoneBusinessController.create(zoneDto);
    }
}
