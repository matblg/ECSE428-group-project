package ca.mcgill.ecse428.letterbook.repository;

import ca.mcgill.ecse428.letterbook.model.UserBook;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface UserBookRepository extends CrudRepository<UserBook, String> {

    Optional<UserBook> findByOwnerIdAndIsbn(String userId, String bookId);

    List<UserBook> findByOwnerIdAndTitle(String ownerId, String title);

    List<UserBook> findByOwnerIdAndAuthor(String ownerId, String author);

    List<UserBook> findByOwnerId(String ownerId);

    boolean existsByOwnerIdAndIsbn(String ownerId, String isbn);

}
