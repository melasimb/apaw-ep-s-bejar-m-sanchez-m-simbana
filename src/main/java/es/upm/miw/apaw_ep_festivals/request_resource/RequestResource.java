package es.upm.miw.apaw_ep_festivals.request_resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(RequestResource.REQUESTS)
public class RequestResource {

    public static final String REQUESTS = "/requests";
    static final String ID_ID = "/{id}";

    private RequestBusinessController requestBusinessController;

    @Autowired
    public RequestResource(RequestBusinessController requestBusinessController) {
        this.requestBusinessController = requestBusinessController;
    }

    @PatchMapping(value = ID_ID)
    public void patch(@PathVariable String id, @RequestBody RequestPatchDto requestPatchDto) {
        requestPatchDto.validate();
        this.requestBusinessController.patch(id, requestPatchDto);
    }
}
