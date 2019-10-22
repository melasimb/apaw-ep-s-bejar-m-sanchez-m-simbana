package es.upm.miw.apaw_ep_festivals.spectator_resource;

import es.upm.miw.apaw_ep_festivals.spectator_data.SpectatorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(SpectatorResource.SPECTATORS)
public class SpectatorResource {

    static final String SPECTATORS = "/spectators";
    static final String ID_ID = "/{id}";

    private SpectatorBusinessController spectatorBusinessController;
    private EmitterProcessor<String> emitter;

    @Autowired
    public SpectatorResource(SpectatorBusinessController spectatorBusinessController) {
        this.spectatorBusinessController = spectatorBusinessController;
        this.emitter = EmitterProcessor.create();
    }

    public Flux<String> publisher() {
        return this.emitter;
    }

    @PostMapping
    public SpectatorDto create(@RequestBody SpectatorDto spectatorDto) {
        spectatorDto.validate();
        SpectatorDto spectatorDtoCreated = this.spectatorBusinessController.create(spectatorDto);
        this.emitter.onNext("The following Spectator has been added: " + spectatorDtoCreated.toStringWithoutId());
        return spectatorDtoCreated;
    }

    @GetMapping(value = ID_ID)
    public SpectatorDto readSpectator(@PathVariable String id) {
        return this.spectatorBusinessController.readSpectator(id);
    }

    @PatchMapping(value = ID_ID)
    public void patch(@PathVariable String id, @RequestBody SpectatorPatchDto spectatorPatchDto) {
        spectatorPatchDto.validate();
        this.spectatorBusinessController.patch(id, spectatorPatchDto);
    }
}
