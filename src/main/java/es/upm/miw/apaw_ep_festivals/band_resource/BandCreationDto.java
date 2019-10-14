package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;

import java.util.List;

public class BandCreationDto {

    private String name;

    private List<Artist> artists;

    private List<String> concertsId;

    public BandCreationDto() {
    }

    public BandCreationDto(String name, List<Artist> artists, List<String> concertsId) {
        this.name = name;
        this.artists = artists;
        this.concertsId = concertsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Artist> getArtists() {
        return artists;
    }

    public void setArtists(List<Artist> artists) {
        this.artists = artists;
    }

    public List<String> getConcertsId() {
        return concertsId;
    }

    public void setConcertsId(List<String> concertsId) {
        this.concertsId = concertsId;
    }

    public void validate() {
        if (this.name == null || this.name.isEmpty() || this.artists == null || this.artists.isEmpty()) {
            throw new BadRequestException("Incomplete BandCreationDto");
        }
    }

    @Override
    public String toString() {
        return "BandCreationDto{" +
                "name='" + name + '\'' +
                ", artists=" + artists +
                ", concertsId=" + concertsId +
                '}';
    }
}
