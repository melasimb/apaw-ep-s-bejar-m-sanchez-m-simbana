package es.upm.miw.apaw_ep_festivals.concert_data;

import es.upm.miw.apaw_ep_festivals.zone_data.Zone;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class Concert {

    @Id
    private String id;

    private LocalDateTime date;

    private Integer duration;

    @DBRef
    private Zone zone;

    public Concert() {
    }

    public Concert(LocalDateTime date, Integer duration, Zone zone) {
        this.date = date;
        this.duration = duration;
        this.zone = zone;
    }

    public String getId() {
        return id;
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

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    @Override
    public String toString() {
        return "Concert{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", duration=" + duration +
                ", zone=" + zone +
                '}';
    }
}
