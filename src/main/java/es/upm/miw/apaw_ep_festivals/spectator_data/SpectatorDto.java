package es.upm.miw.apaw_ep_festivals.spectator_data;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;

import java.time.LocalDateTime;

public class SpectatorDto {

    private String id;

    private String name;

    private String surname;

    private LocalDateTime birthday;

    public SpectatorDto() {
    }

    public SpectatorDto(String name, String surname, LocalDateTime birthday) {
        this.name = name;
        this.surname = surname;
        this.birthday = birthday;
    }

    public SpectatorDto(Spectator spectator) {
        this.id = spectator.getId();
        this.name = spectator.getName();
        this.surname = spectator.getSurname();
        this.birthday = spectator.getBirthday();
    }

    public static Builder builder() {
        return new Builder();
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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public void validate() {
        if (name == null || surname == null || birthday == null) {
            throw new BadRequestException("Incomplete SpectatorDto. ");
        }
    }

    @Override
    public String toString() {
        return "SpectatorDto{" +
                "id='" + id + '\'' +
                ", name=" + name +
                ", surname='" + surname +
                ", birthday='" + birthday + '\'' +
                '}';
    }

    public String toStringWithoutId() {
        return "SpectatorDto{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthday='" + birthday + '\'' +
                '}';
    }

    public static class Builder {

        private SpectatorDto spectatorDto;

        private Builder() {
            this.spectatorDto = new SpectatorDto();
        }

        public Builder name(String name) {
            this.spectatorDto.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.spectatorDto.surname = surname;
            return this;
        }

        public Builder birthday(LocalDateTime birthday) {
            this.spectatorDto.birthday = birthday;
            return this;
        }

        public Builder byDefault() {
            Builder builder = new Builder();
            return builder.name("Sonia").surname("Béjar").birthday(LocalDateTime.of(1995, 4, 17, 11, 0));
        }

        public SpectatorDto build() {
            return this.spectatorDto;
        }

    }
}
