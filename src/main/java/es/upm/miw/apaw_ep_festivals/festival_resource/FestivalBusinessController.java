package es.upm.miw.apaw_ep_festivals.festival_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class FestivalBusinessController {

    private FestivalDao festivalDao;

    @Autowired
    public FestivalBusinessController(FestivalDao festivalDao) {
        this.festivalDao = festivalDao;
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
}
