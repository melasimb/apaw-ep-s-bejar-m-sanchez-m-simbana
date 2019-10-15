package es.upm.miw.apaw_ep_festivals.spectator_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.NotFoundException;
import es.upm.miw.apaw_ep_festivals.spectator_data.Spectator;
import es.upm.miw.apaw_ep_festivals.spectator_data.SpectatorDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SpectatorBusinessController {

    private SpectatorDao spectatorDao;

    @Autowired
    public SpectatorBusinessController(SpectatorDao spectatorDao) {
        this.spectatorDao = spectatorDao;
    }

    public SpectatorDto create(SpectatorDto spectatorDto) {
        Spectator spectator = new Spectator(spectatorDto.getName(), spectatorDto.getSurname(), spectatorDto.getBirthday());
        this.spectatorDao.save(spectator);
        return new SpectatorDto(spectator);
    }

    public SpectatorDto readSpectator(String id) {
        return new SpectatorDto(this.findSpectatorByIdAssured(id));
    }

    private Spectator findSpectatorByIdAssured(String id) {
        return this.spectatorDao.findById(id).orElseThrow(() -> new NotFoundException("Spectator id: " + id));
    }
}
