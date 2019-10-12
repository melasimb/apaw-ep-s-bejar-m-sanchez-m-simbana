package es.upm.miw.apaw_ep_festivals.concert_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ConcertResource.CONCERTS)
public class ConcertResource {

    static final String CONCERTS = "/concerts";

    private ConcertBusinessController concertBusinessController;

    @Autowired
    public ConcertResource(ConcertBusinessController concertBusinessController) {
        this.concertBusinessController = concertBusinessController;
    }

    @PostMapping
    public ConcertDto create(@RequestBody ConcertDto concertDto) {
        concertDto.validate();
        return this.concertBusinessController.create(concertDto);
    }

}
