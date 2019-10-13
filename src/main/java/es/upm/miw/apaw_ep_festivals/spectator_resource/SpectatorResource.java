package es.upm.miw.apaw_ep_festivals.spectator_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(SpectatorResource.SPECTATORS)
public class SpectatorResource {

    static final String SPECTATORS = "/spectators";
    static final String ID_ID = "/{id}";

    private SpectatorBusinessController spectatorBusinessController;

    @Autowired
    public SpectatorResource(SpectatorBusinessController spectatorBusinessController) {
        this.spectatorBusinessController = spectatorBusinessController;
    }

    @PostMapping
    public SpectatorDto create(@RequestBody SpectatorDto spectatorDto) {
        spectatorDto.validate();
        return this.spectatorBusinessController.create(spectatorDto);
    }

    @GetMapping(value = ID_ID)
    public SpectatorDto readSpectator(@PathVariable String id) {
        return this.spectatorBusinessController.readSpectator(id);
    }
}
