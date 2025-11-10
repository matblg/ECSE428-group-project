package ca.mcgill.ecse428.letterbook.repository;

import ca.mcgill.ecse428.letterbook.model.Collection;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface CollectionsRepository extends MongoRepository<Collection, String> {

    List<Collection> findByOwnerId(String ownerId);

    List<Collection> findByName(String name);

    boolean existsByNameAndOwnerId(String name, String ownerId);

}
