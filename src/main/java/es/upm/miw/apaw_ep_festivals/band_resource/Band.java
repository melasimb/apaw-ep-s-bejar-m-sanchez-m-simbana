package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.condert_data.Concert;
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
        this.artists = artists;
        this.concerts = concerts;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
