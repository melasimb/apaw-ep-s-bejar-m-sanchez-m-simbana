package es.upm.miw.apaw_ep_festivals.spectator_data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpectatorDao extends MongoRepository<Spectator, String> {
}
