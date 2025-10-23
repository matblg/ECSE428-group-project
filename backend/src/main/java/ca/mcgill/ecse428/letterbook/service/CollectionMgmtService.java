package ca.mcgill.ecse428.letterbook.service;

import ca.mcgill.ecse428.letterbook.exception.BadRequestException;
import ca.mcgill.ecse428.letterbook.model.Collection;
import ca.mcgill.ecse428.letterbook.repository.CollectionsRepository;
import ca.mcgill.ecse428.letterbook.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CollectionMgmtService {

    private final CollectionsRepository colRepo;
    private final UserRepository userRepository;

    @Autowired
    public CollectionMgmtService(
            CollectionsRepository collectionsRepository,
            UserRepository userRepository) {
        this.colRepo = collectionsRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String createCollection(String name, UUID ownerId) throws BadRequestException {
        if (colRepo.existsByNameAndOwnerId(name, ownerId)) {
            throw new BadRequestException(
                    "Collection with name " + name + " already exists for user " + ownerId
            );
        }

        Collection collection = Collection.builder()
                .name(name)
                .ownerId(ownerId)
                .build();

        colRepo.save(collection);

        return collection.getId().toString();
    }

}
