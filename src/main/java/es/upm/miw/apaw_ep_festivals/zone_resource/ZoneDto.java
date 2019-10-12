package es.upm.miw.apaw_ep_festivals.zone_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;

public class ZoneDto {

    private String id;

    private String name;

    private String genre;

    private Integer capacity;

    private Boolean adaptedDisabled;

    public ZoneDto() {
        //empty for framework
    }

    public ZoneDto(String name, String genre, Integer capacity, Boolean adaptedDisabled) {
        this.name = name;
        this.genre = genre;
        this.capacity = capacity;
        this.adaptedDisabled = adaptedDisabled;
    }

    public ZoneDto(Zone zone) {
        this.id = zone.getId();
        this.name = zone.getName();
        this.genre = zone.getGenre();
        this.capacity = zone.getCapacity();
        this.adaptedDisabled = zone.getAdaptedDisabled();
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Boolean getAdaptedDisabled() {
        return adaptedDisabled;
    }

    public void setAdaptedDisabled(Boolean adaptedDisabled) {
        this.adaptedDisabled = adaptedDisabled;
    }

    public void validate() {
        if (name == null || name.isEmpty() || genre == null || genre.isEmpty() || capacity == null || adaptedDisabled == null) {
            throw new BadRequestException("Incomplete ZoneDto. ");
        }
    }

    @Override
    public String toString() {
        return "ZoneDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", capacity=" + capacity +
                ", adaptedDisabled=" + adaptedDisabled +
                '}';
    }
}
