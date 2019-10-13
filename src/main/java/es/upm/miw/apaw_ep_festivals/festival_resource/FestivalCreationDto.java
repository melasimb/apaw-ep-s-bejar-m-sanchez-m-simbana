package es.upm.miw.apaw_ep_festivals.festival_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;

import java.time.LocalDateTime;

public class FestivalCreationDto {

    private String name;

    private Double price;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private String city;

    public FestivalCreationDto() {
        // empty for framework
    }

    public FestivalCreationDto(String name, Double price, LocalDateTime startDate, LocalDateTime endDate, String city) {
        this.name = name;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.city = city;
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

    public void validate() {
        if (this.name == null || this.name.isEmpty() || this.price == null || this.startDate == null || this.endDate == null
                || this.city == null) {
            throw new BadRequestException("Incomplete FestivalDto");
        }
    }

    @Override
    public String toString() {
        return "FestivalCreationDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", city='" + city + '\'' +
                '}';
    }
}
