package es.upm.miw.apaw_ep_festivals.concert_resource;

import es.upm.miw.apaw_ep_festivals.concert_data.Concert;
import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;

import java.time.LocalDateTime;

public class ConcertDto {

    private String id;

    private LocalDateTime date;

    private Integer duration;

    private String zoneId;

    public ConcertDto() {
        //Empty for framework
    }

    public ConcertDto(LocalDateTime date, Integer duration, String zoneId) {
        this.date = date;
        this.duration = duration;
        this.zoneId = zoneId;
    }

    public ConcertDto(Concert concert) {
        this.id = concert.getId();
        this.date = concert.getDate();
        this.duration = concert.getDuration();
        this.zoneId = concert.getZone().getId();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getZoneId() {
        return zoneId;
    }

    public void setZoneId(String zoneId) {
        this.zoneId = zoneId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void validate() {
        if (date == null || duration == null || zoneId == null || zoneId.isEmpty()) {
            throw new BadRequestException("Incomplete ConcertDto. ");
        }
    }

    @Override
    public String toString() {
        return "ConcertDto{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", duration=" + duration +
                ", zoneId='" + zoneId + '\'' +
                '}';
    }
}
