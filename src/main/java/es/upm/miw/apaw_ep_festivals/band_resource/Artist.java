package es.upm.miw.apaw_ep_festivals.band_resource;

import java.time.LocalDateTime;

public class Artist {

    private String name;

    private LocalDateTime birthday;

    private String rol;

    public Artist(String name, LocalDateTime birthday, String rol) {
        this.name = name;
        this.birthday = birthday;
        this.rol = rol;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", rol='" + rol + '\'' +
                '}';
    }
}
