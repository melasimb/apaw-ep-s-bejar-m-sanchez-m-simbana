package es.upm.miw.apaw_ep_festivals.band_resource;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BandDao extends MongoRepository<Band, String> {
}
