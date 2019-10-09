package es.upm.miw.apaw_ep_festivals.spectator_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(SpectatorResource.SPECTATORS)
public class SpectatorResource {

    static final String SPECTATORS = "/spectators";

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
}
