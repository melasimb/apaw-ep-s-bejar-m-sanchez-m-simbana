package es.upm.miw.apaw_ep_festivals.spectator_resource;

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
}
