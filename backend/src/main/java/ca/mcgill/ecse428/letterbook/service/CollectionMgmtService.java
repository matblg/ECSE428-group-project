package ca.mcgill.ecse428.letterbook.service;

import ca.mcgill.ecse428.letterbook.exception.BadRequestException;
import ca.mcgill.ecse428.letterbook.exception.NotFoundException;
import ca.mcgill.ecse428.letterbook.model.Collection;
import ca.mcgill.ecse428.letterbook.repository.CollectionsRepository;
import ca.mcgill.ecse428.letterbook.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public String createCollection(String name, String ownerId) throws BadRequestException {
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

    public Collection getCollectionById(String id) throws NotFoundException {
        return colRepo.findById(id).orElseThrow(() -> new NotFoundException("Collection not found: " + id));
    }

    @Transactional
    public String addBookToCollection(String collectionId, String isbn) throws BadRequestException {
        Collection collection = colRepo.findById(collectionId).orElseThrow(
                () -> new BadRequestException("Collection not found: " + collectionId)
        );
        if (collection.getBooksISBNs().contains(isbn)) {
            throw new BadRequestException("Book with ISBN " + isbn + " already exists in collection " + collectionId);
        }
        collection.getBooksISBNs().add(isbn);
        colRepo.save(collection);
        return collection.getId().toString();
    }

    @Transactional
    public String removeBookFromCollection(String collectionId, String isbn) throws BadRequestException {
        Collection collection = colRepo.findById(collectionId).orElseThrow(
                () -> new BadRequestException("Collection not found: " + collectionId)
        );
        if (!collection.getBooksISBNs().contains(isbn)) {
            throw new BadRequestException("Book with ISBN " + isbn + " does not exist in collection " + collectionId);
        }
        collection.getBooksISBNs().remove(isbn);
        return collection.getId().toString();
    }

}
