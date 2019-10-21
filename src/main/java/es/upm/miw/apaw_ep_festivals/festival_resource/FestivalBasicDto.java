package es.upm.miw.apaw_ep_festivals.festival_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;

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

    public FestivalBasicDto(String name, Double price, LocalDateTime startDate, LocalDateTime endDate, String city) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
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

    public void validate() {
        if (this.name == null || this.name.isEmpty() || this.price == null || this.startDate == null || this.endDate == null
                || this.city == null) {
            throw new BadRequestException("Incomplete FestivalDto");
        }
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