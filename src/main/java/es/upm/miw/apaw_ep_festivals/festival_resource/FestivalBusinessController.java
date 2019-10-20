package es.upm.miw.apaw_ep_festivals.festival_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import es.upm.miw.apaw_ep_festivals.concert_data.ConcertDao;
import es.upm.miw.apaw_ep_festivals.exceptions.NotFoundException;
import es.upm.miw.apaw_ep_festivals.spectator_data.Spectator;
import es.upm.miw.apaw_ep_festivals.spectator_data.SpectatorDao;
import es.upm.miw.apaw_ep_festivals.spectator_data.SpectatorDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FestivalBusinessController {

    private FestivalDao festivalDao;
    private ConcertDao concertDao;
    private SpectatorDao spectatorDao;

    @Autowired
    public FestivalBusinessController(FestivalDao festivalDao, SpectatorDao spectatorDao, ConcertDao concertDao) {
        this.festivalDao = festivalDao;
        this.spectatorDao = spectatorDao;
        this.concertDao = concertDao;
    }

    public FestivalBasicDto create(FestivalBasicDto festivalBasicDto) {
        Festival festival = new Festival(festivalBasicDto.getName(), festivalBasicDto.getPrice(), festivalBasicDto.getStartDate()
                , festivalBasicDto.getEndDate(), festivalBasicDto.getCity());
        this.festivalDao.save(festival);
        return new FestivalBasicDto(festival);
    }

    public void delete(String id) {
        this.festivalDao.deleteById(id);
    }

    public Festival findFestivalByIdAssured(String id) {
        return this.festivalDao.findById(id).orElseThrow(() -> new NotFoundException("Festival id: " + id));
    }

    public FestivalFullDto createSpectator(String id, SpectatorDto spectatorDto) {
        Festival festival = this.findFestivalByIdAssured(id);
        Spectator spectator = new Spectator(spectatorDto.getName(), spectatorDto.getSurname(), spectatorDto.getBirthday());
        this.spectatorDao.save(spectator);
        festival.getSpectators().add(spectator);
        return new FestivalFullDto(festival);
    }

    public List<Concert> findConcertsAdaptedDisabled(String id) {
        Festival festival = this.findFestivalByIdAssured(id);
        return festival.getConcerts().stream()
                .filter(concert -> concert.getZone().getAdaptedDisabled())
                .collect(Collectors.toList());
    }
}
