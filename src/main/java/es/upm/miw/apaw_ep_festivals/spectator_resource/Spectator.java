package es.upm.miw.apaw_ep_festivals.spectator_resource;

import nonapi.io.github.classgraph.json.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class Spectator {

    @Id
    private String id;

    private String name;

    private String surname;

    private LocalDateTime birthday;

    public Spectator(String name, String surname, LocalDateTime birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return "Spectator{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", surname='" + surname +
                ", birthday='" + birthday + '\'' +
                '}';
    }
}
