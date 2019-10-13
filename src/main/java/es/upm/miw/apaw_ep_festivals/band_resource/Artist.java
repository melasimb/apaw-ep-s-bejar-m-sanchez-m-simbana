package es.upm.miw.apaw_ep_festivals.band_resource;

import java.time.LocalDateTime;

public class Artist {

    private String name;

    private LocalDateTime birthday;

    private String role;

    public Artist(String name, LocalDateTime birthday, String role) {
        this.name = name;
        this.birthday = birthday;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", role='" + role + '\'' +
                '}';
    }
}
