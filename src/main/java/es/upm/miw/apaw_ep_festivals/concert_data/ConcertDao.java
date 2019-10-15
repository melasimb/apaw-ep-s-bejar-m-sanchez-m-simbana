package es.upm.miw.apaw_ep_festivals.concert_data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConcertDao extends MongoRepository<Concert, String> {

}
