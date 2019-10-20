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

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<Concert> getConcerts() {
        return concerts;
    }

    public void setConcerts(List<Concert> concerts) {
        this.concerts = concerts;
    }

    public List<Spectator> getSpectators() {
        return spectators;
    }

    public void setSpectators(List<Spectator> spectators) {
        this.spectators = spectators;
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
