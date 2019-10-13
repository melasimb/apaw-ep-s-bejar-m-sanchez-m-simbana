package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;

import java.util.List;

public class BandDto {

    private String id;

    private String name;

    private List<Artist> artists;

    private List<Concert> concerts;

    public BandDto() {
        // Empty for framework
    }

    public BandDto(Band band) {
        this.id = band.getId();
        this.name = band.getName();
        this.artists = band.getArtists();
        this.concerts = band.getConcerts();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<Concert> concerts) {
        this.concerts = concerts;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public void validate() {
        if (this.name == null || this.name.isEmpty() || this.artists == null) {
            throw new BadRequestException("Incomplete BandDto");
        }
    }

    @Override
    public String toString() {
        return "BandDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", artists=" + artists +
                ", concerts=" + concerts +
                '}';
    }
}
