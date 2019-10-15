package es.upm.miw.apaw_ep_festivals.band_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;

public class BandPatchDto {

    private String oldName;

    private String newName;

    public BandPatchDto() {
        // empty for framework
    }

    public BandPatchDto(String oldName, String newName) {
        this.oldName = oldName;
        this.newName = newName;
    }

    public String getOldName() {
        return oldName;
    }

    public void setOldName(String oldName) {
        this.oldName = oldName;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public void validate() {
        if (this.oldName == null || this.oldName.isEmpty() || this.newName == null || this.newName.isEmpty()) {
            throw new BadRequestException("Incomplete BandPatchDto");
        }
    }

    @Override
    public String toString() {
        return "BandPatchDto{" +
                "oldName='" + oldName + '\'' +
                ", newName='" + newName + '\'' +
                '}';
    }
}
