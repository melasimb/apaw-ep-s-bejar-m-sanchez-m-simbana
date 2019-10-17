package es.upm.miw.apaw_ep_festivals.band_resource;

public class BandBasicDto {

    private String id;

    private String name;

    public BandBasicDto() {
    }

    public BandBasicDto(Band band) {
        this.id = band.getId();
        this.name = band.getName();
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

    @Override
    public String toString() {
        return "BandBasicDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
