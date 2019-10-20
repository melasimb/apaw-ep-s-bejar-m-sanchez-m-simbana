package es.upm.miw.apaw_ep_festivals.zone_data;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Zone {

    @Id
    private String id;

    private String name;

    private String genre;

    private Integer capacity;

    private Boolean adaptedDisabled;

    public Zone() {
    }

    public Zone(String name, String genre, Integer capacity, Boolean adaptedDisabled) {
        this.name = name;
        this.genre = genre;
        this.capacity = capacity;
        this.adaptedDisabled = adaptedDisabled;
    }

    public String getId() {
        return id;
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

    @Override
    public String toString() {
        return "Zone{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", genre='" + genre + '\'' +
                ", capacity='" + capacity + '\'' +
                ", adaptedDisabled=" + adaptedDisabled +
                '}';
    }
}
