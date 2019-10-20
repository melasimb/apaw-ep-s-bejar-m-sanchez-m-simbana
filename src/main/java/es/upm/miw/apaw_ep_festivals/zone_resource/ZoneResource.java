package es.upm.miw.apaw_ep_festivals.zone_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(ZoneResource.ZONES)
public class ZoneResource {

    public static final String ZONES = "/zones";
    static final String ID_ID = "/{id}";

    private ZoneBusinessController zoneBusinessController;
    private EmitterProcessor<String> emitter;

    @Autowired
    public ZoneResource(ZoneBusinessController zoneBusinessController) {
        this.zoneBusinessController = zoneBusinessController;
        this.emitter = EmitterProcessor.create();
    }

    public Flux<String> publisher() {
        return this.emitter;
    }

    @PostMapping
    public ZoneDto create(@RequestBody ZoneDto zoneDto) {
        zoneDto.validate();
        ZoneDto returnZoneDto = this.zoneBusinessController.create(zoneDto);
        this.emitter.onNext("The following zone has been added: " + returnZoneDto.getName() + ", " + returnZoneDto.getGenre() + ", " + returnZoneDto.getCapacity() + ", " + returnZoneDto.getAdaptedDisabled());
        return returnZoneDto;
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
