package es.upm.miw.apaw_ep_festivals.festival_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import es.upm.miw.apaw_ep_festivals.spectator_data.Spectator;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Document
public class Festival {

    @Id
    private String id;

    private String name;

    private Double price;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String city;

    @DBRef
    private List<Concert> concerts;

    @DBRef
    private List<Spectator> spectators;

    public Festival(String name, Double price, LocalDateTime startDate, LocalDateTime endDate, String city) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
        this.concerts = new ArrayList<>();
        this.spectators = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public String getCity() {
        return city;
    }

    public List<Concert> getConcerts() {
        return concerts;
    }

    public List<Spectator> getSpectators() {
        return spectators;
    }

    @Override
    public String toString() {
        return "Festival{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", city='" + city + '\'' +
                ", concerts=" + concerts +
                ", spectators=" + spectators +
                '}';
    }
}
