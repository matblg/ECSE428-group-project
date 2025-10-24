package ca.mcgill.ecse428.letterbook.controller;

import ca.mcgill.ecse428.letterbook.model.Collection;
import ca.mcgill.ecse428.letterbook.service.CollectionMgmtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CollectionMgmtController {

    private final CollectionMgmtService colMgmtService;

    @Autowired
    public CollectionMgmtController(CollectionMgmtService colMgmtService) {
        this.colMgmtService = colMgmtService;
    }

    @PostMapping("collections/")
    private ResponseEntity<String> createCollection(String name, UUID ownerId) {
        String result = colMgmtService.createCollection(name, ownerId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("collections/")
    private ResponseEntity<List<Collection>> getCollectionsForUser(UUID ownerId) {
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    private ResponseEntity<String> addBookToCollection(UUID collectionId, String isbn) {
        String result = colMgmtService.addBookToCollection(collectionId, isbn);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    private ResponseEntity<String> removeBookFromCollection(UUID collectionId, String isbn) {
        String result = colMgmtService.removeBookFromCollection(collectionId, isbn);
        return ResponseEntity.ok(result);
    }

}
