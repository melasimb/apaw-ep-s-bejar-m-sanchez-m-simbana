package es.upm.miw.apaw_ep_festivals.festival_resource;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FestivalDao extends MongoRepository<Festival, String> {
}
