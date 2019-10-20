package es.upm.miw.apaw_ep_festivals.request_resource;

import es.upm.miw.apaw_ep_festivals.ApiTestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ApiTestConfig
public class RequestResourceIT {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RequestDao requestDao;

    @BeforeEach
    void before() {
        Request request = new Request("title", "description");
        this.requestDao.save(request);
    }

    @Test
    void testUserUpdateUserPatchDtoException() {
        String idRequest = this.requestDao.findAll().get(0).getId();
        this.webTestClient
                .patch().uri(RequestResource.REQUESTS + RequestResource.ID_ID, idRequest)
                .body(BodyInserters.fromObject(new RequestPatchDto("", "")))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void testUserUpdateIdException() {
        this.webTestClient
                .patch().uri(RequestResource.REQUESTS + RequestResource.ID_ID, "no")
                .body(BodyInserters.fromObject(new RequestPatchDto("title", "description")))
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void testUserUpdate() {
        String idRequest = this.requestDao.findAll().get(0).getId();
        this.webTestClient
                .patch().uri(RequestResource.REQUESTS + RequestResource.ID_ID, idRequest)
                .body(BodyInserters.fromObject(new RequestPatchDto("updated title", "update description")))
                .exchange()
                .expectStatus().isOk();
        Request request = this.requestDao.findById(idRequest).get();
        assertEquals("updated title", request.getTitle());
        assertEquals("update description", request.getDescription());
    }
}
