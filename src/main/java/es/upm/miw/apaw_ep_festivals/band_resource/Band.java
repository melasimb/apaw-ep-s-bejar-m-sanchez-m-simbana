package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class Band {
    @Id
    private String id;

    private String name;

    private List<Artist> artists;

    @DBRef
    private List<Concert> concerts;

    public Band(String name, List<Artist> artists, List<Concert> concerts) {
        this.name = name;
        this.concerts = concerts;
        this.artists = artists;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Concert> getConcerts() {
        return concerts;
    }

    public List<Artist> getArtists() {
        return artists;
    }
}
