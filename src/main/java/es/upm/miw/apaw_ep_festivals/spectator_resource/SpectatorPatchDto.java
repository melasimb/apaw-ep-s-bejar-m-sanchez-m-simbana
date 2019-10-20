package es.upm.miw.apaw_ep_festivals.spectator_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;

public class SpectatorPatchDto {

    private String path;

    private String newValue;

    public SpectatorPatchDto() {
        // empty for framework
    }

    public SpectatorPatchDto(String path, String newValue) {
        this.path = path;
        this.newValue = newValue;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public void validate() {
        if (this.path == null || this.path.isEmpty() || this.newValue == null || this.newValue.isEmpty()) {
            throw new BadRequestException("Incomplete UserPatchDto");
        }
    }

    @Override
    public String toString() {
        return "SpectatorPatchDto{" +
                "path='" + path + '\'' +
                ", newValue='" + newValue + '\'' +
                '}';
    }
}
