package es.upm.miw.apaw_ep_festivals.zone_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;

import java.time.LocalDateTime;

public class ZoneDto {

    private String id;

    private String name;

    private Double price;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String city;

    public void validate() {
        if (name == null || name.isEmpty() || price == null || startDate == null || endDate == null || city == null || city.isEmpty()) {
            throw new BadRequestException("Incomplete ZoneDto. ");
        }
    }

    public ZoneDto() {
        //empty for framework
    }

    public ZoneDto(String name, Double price, LocalDateTime startDate, LocalDateTime endDate, String city) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
    }

    public ZoneDto(Zone zone) {
        this.id = zone.getId();
        this.name = zone.getName();
        this.price = zone.getPrice();
        this.startDate = zone.getStartDate();
        this.endDate = zone.getEndDate();
        this.city = zone.getCity();
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
        return "ZoneDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", city='" + city + '\'' +
                '}';
    }
}
