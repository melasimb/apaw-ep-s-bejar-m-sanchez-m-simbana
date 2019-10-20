package es.upm.miw.apaw_ep_festivals.request_resource;

import es.upm.miw.apaw_ep_festivals.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class RequestBusinessController {

    private RequestDao requestDao;

    @Autowired
    public RequestBusinessController(RequestDao requestDao) {
        this.requestDao = requestDao;
    }

    public void patch(String id, RequestPatchDto requestPatchDto) {
        Request request = this.requestDao.findById(id).orElseThrow(() -> new NotFoundException("Request id: " + id));
        if (requestPatchDto.getTitle() != null && !requestPatchDto.getTitle().isEmpty()) {
            System.out.println("Change title: " + requestPatchDto.getTitle());
            request.setTitle(requestPatchDto.getTitle());
        }
        if (requestPatchDto.getDescription() != null && !requestPatchDto.getDescription().isEmpty()) {
            System.out.println("Change description: " + requestPatchDto.getDescription());
            request.setDescription(requestPatchDto.getDescription());
        }

        this.requestDao.save(request);
    }
}
