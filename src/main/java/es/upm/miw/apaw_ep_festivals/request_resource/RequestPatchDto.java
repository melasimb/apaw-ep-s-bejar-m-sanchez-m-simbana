package es.upm.miw.apaw_ep_festivals.request_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.BadRequestException;

public class RequestPatchDto {

    private String title;

    private String description;

    public RequestPatchDto() {
    }

    public RequestPatchDto(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void validate() {
        if ((this.title == null || this.title.isEmpty()) && (this.description == null || this.description.isEmpty())) {
            throw new BadRequestException("Incomplete RequestPatchDto");
        }
    }

    @Override
    public String toString() {
        return "RequestPatchDto{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
