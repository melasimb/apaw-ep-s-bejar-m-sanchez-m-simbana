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

    public String getGenre() {
        return genre;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Boolean getAdaptedDisabled() {
        return adaptedDisabled;
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
