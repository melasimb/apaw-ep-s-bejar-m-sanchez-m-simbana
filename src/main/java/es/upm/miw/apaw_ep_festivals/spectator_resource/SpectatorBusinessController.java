package es.upm.miw.apaw_ep_festivals.spectator_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;
import es.upm.miw.apaw_ep_festivals.exceptions.NotFoundException;
import es.upm.miw.apaw_ep_festivals.spectator_data.Spectator;
import es.upm.miw.apaw_ep_festivals.spectator_data.SpectatorDao;
import es.upm.miw.apaw_ep_festivals.spectator_data.SpectatorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;

@Controller
public class SpectatorBusinessController {

    private SpectatorDao spectatorDao;
    private EmitterProcessor<String> emitter;

    @Autowired
    public SpectatorBusinessController(SpectatorDao spectatorDao) {
        this.spectatorDao = spectatorDao;
        this.emitter = EmitterProcessor.create();
    }

    public Flux<String> publisher() {
        return this.emitter;
    }

    public SpectatorDto create(SpectatorDto spectatorDto) {
        Spectator spectator = new Spectator(spectatorDto.getName(), spectatorDto.getSurname(), spectatorDto.getBirthday());
        this.spectatorDao.save(spectator);
        SpectatorDto spectatorDtoCreated = new SpectatorDto(spectator);
        this.emitter.onNext("The following Spectator has been added: " + spectatorDtoCreated.toStringWithoutId());
        return spectatorDtoCreated;
    }

    public SpectatorDto readSpectator(String id) {
        return new SpectatorDto(this.findSpectatorByIdAssured(id));
    }

    private Spectator findSpectatorByIdAssured(String id) {
        return this.spectatorDao.findById(id).orElseThrow(() -> new NotFoundException("Spectator id: " + id));
    }

    public void patch(String id, SpectatorPatchDto spectatorPatchDto) {
        Spectator spectator = this.findSpectatorByIdAssured(id);
        switch (spectatorPatchDto.getPath()) {
            case "name":
                spectator.setName(spectatorPatchDto.getNewValue());
                break;
            case "surname":
                spectator.setSurname(spectatorPatchDto.getNewValue());
                break;
            case "birthday":
                spectator.setBirthday(LocalDateTime.parse(spectatorPatchDto.getNewValue()));
                break;
            default:
                throw new BadRequestException("SpectatorPatchDto is invalid");
        }
        this.spectatorDao.save(spectator);
    }
}
