package es.upm.miw.apaw_ep_festivals.festival_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import es.upm.miw.apaw_ep_festivals.spectator_data.Spectator;

import java.time.LocalDateTime;
import java.util.List;

public class FestivalFullDto {

    private String id;

    private String name;

    private Double price;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String city;

    private List<Concert> concerts;

    private List<Spectator> spectators;

    public FestivalFullDto() {
        // Empty for framework
    }

    public FestivalFullDto(Festival festival) {
        this.id = festival.getId();
        this.name = festival.getName();
        this.price = festival.getPrice();
        this.startDate = festival.getStartDate();
        this.endDate = festival.getEndDate();
        this.city = festival.getCity();
        this.concerts = festival.getConcerts();
        this.spectators = festival.getSpectators();
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
        return "FestivalFullDto{" +
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
