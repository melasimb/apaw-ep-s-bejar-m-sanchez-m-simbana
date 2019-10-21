package es.upm.miw.apaw_ep_festivals.zone_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ZoneResource.ZONES)
public class ZoneResource {

    public static final String ZONES = "/zones";
    static final String ID_ID = "/{id}";

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

    @PutMapping(value = ID_ID)
    public void update(@PathVariable String id, @RequestBody ZoneDto zoneDto) {
        zoneDto.validate();
        this.zoneBusinessController.update(id, zoneDto);
    }

    @DeleteMapping(value = ID_ID)
    public void delete(@PathVariable String id) {
        this.zoneBusinessController.delete(id);
    }
}