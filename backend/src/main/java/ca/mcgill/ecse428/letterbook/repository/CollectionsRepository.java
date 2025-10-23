package ca.mcgill.ecse428.letterbook.repository;

import ca.mcgill.ecse428.letterbook.model.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.UUID;

public interface CollectionsRepository extends MongoRepository<Collection, UUID> {

    List<Collection> findByOwnerId(UUID ownerId);

    List<Collection> findByName(String name);

    boolean existsByNameAndOwnerId(String name, UUID ownerId);

}
