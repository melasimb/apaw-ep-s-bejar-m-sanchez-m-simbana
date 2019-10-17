package es.upm.miw.apaw_ep_festivals.festival_resource;

import java.time.LocalDateTime;

public class FestivalBasicDto {

    private String id;

    private String name;

    private Double price;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String city;

    public FestivalBasicDto() {
        // empty for framework
    }

    public FestivalBasicDto(Festival festival) {
        this.id = festival.getId();
        this.name = festival.getName();
        this.price = festival.getPrice();
        this.startDate = festival.getStartDate();
        this.endDate = festival.getEndDate();
        this.city = festival.getCity();
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

    @Override
    public String toString() {
        return "FestivalBasicDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", city='" + city + '\'' +
                '}';
    }
}