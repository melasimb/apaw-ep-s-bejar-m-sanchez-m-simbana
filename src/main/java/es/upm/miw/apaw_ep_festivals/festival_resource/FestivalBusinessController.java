package es.upm.miw.apaw_ep_festivals.festival_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import es.upm.miw.apaw_ep_festivals.concert_data.ConcertDao;
import es.upm.miw.apaw_ep_festivals.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FestivalBusinessController {

    private FestivalDao festivalDao;
    private ConcertDao concertDao;

    @Autowired
    public FestivalBusinessController(FestivalDao festivalDao, ConcertDao concertDao) {
        this.festivalDao = festivalDao;
        this.concertDao = concertDao;
    }

    public FestivalBasicDto create(FestivalCreationDto festivalCreationDto) {
        Festival festival = new Festival(festivalCreationDto.getName(), festivalCreationDto.getPrice(), festivalCreationDto.getStartDate()
                , festivalCreationDto.getEndDate(), festivalCreationDto.getCity());
        this.festivalDao.save(festival);
        return new FestivalBasicDto(festival);
    }

    public void delete(String id) {
        this.festivalDao.deleteById(id);
    }

    private Festival findFestivalByIdAssured(String id) {
        return this.festivalDao.findById(id).orElseThrow(() -> new NotFoundException("Festival id: " + id));
    }

    public List<Concert> findConcertsAdaptedDisabled(String id) {
        Festival festival = this.findFestivalByIdAssured(id);
        return festival.getConcerts().stream()
                .filter(concert -> concert.getZone().getAdaptedDisabled())
                .collect(Collectors.toList());
    }
}
