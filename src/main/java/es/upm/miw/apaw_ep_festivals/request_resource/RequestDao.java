package es.upm.miw.apaw_ep_festivals.request_resource;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RequestDao extends MongoRepository<Request, String> {
}