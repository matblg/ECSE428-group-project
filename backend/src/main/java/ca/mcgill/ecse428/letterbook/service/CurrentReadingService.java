package ca.mcgill.ecse428.letterbook.service;

import ca.mcgill.ecse428.letterbook.dto.BookItemDTO;
import ca.mcgill.ecse428.letterbook.exception.BadRequestException;
import ca.mcgill.ecse428.letterbook.model.User;
import ca.mcgill.ecse428.letterbook.model.UserBook;
import ca.mcgill.ecse428.letterbook.repository.UserBookRepository;
import ca.mcgill.ecse428.letterbook.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CurrentReadingService {

    private final UserBookRepository userBookRepo;
    private final UserRepository userRepo;

    @Autowired
    public CurrentReadingService(UserBookRepository userBookRepo, UserRepository userRepo) {
        this.userBookRepo = userBookRepo;
        this.userRepo = userRepo;
    }

    @Transactional
    public String markBookAsReading(String bookIsbn, BookItemDTO bookReq, String userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new BadRequestException("User not found: " + userId)
        );

        user.getCurrentlyReading().add(bookReq.getId());

        // Create and save the book
        UserBook book = UserBook.builder()
                .isbn(bookIsbn)
                .currentlyReading(true)
                .author(bookReq.getVolumeInfo().getAuthors().getFirst())
                .title(bookReq.getVolumeInfo().getTitle())
                .ownerId(userId)
                .completed(false)
                .build();
        UserBook saved = userBookRepo.save(book);

        // Link the book to the ones being read
        user.getAllBooks().add(saved.getId());
        user.getCurrentlyReading().add(saved.getId());
        userRepo.save(user);

        return "Marked the following book as reading: " + bookIsbn + ".";
    }

    @Transactional
    public String unmarkBookAsReading(String bookId, String userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new BadRequestException("User not found: " + userId)
        );

        if (!userBookRepo.existsByOwnerIdAndIsbn(userId, bookId)) {
            throw new BadRequestException("Book with ISBN " + bookId + " does not exist for user " + userId);
        }

        if (!user.getCurrentlyReading().contains(bookId)) {
            throw new BadRequestException("Book with ISBN " + bookId + " is not currently being read by user " + userId);
        }

        UserBook book = userBookRepo.findByOwnerIdAndIsbn(userId, bookId).orElseThrow(
                () -> new IllegalStateException(
                        "Possible concurrent issue: book with ISBN " + bookId + " does not exist for user " + userId
                )
        );

        book.setCurrentlyReading(false);
        book.setCompleted(false);
        userBookRepo.save(book);

        user.getCurrentlyReading().remove(bookId);
        userRepo.save(user);

        return "Unmarked the following book as reading: " + bookId + ".";
    }

    @Transactional
    public String markBookAsFinished(String bookId, String userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new BadRequestException("User not found: " + userId)
        );

        if (!userBookRepo.existsByOwnerIdAndIsbn(userId, bookId)) {
            throw new BadRequestException("Book with ISBN " + bookId + " does not exist for user " + userId);
        }

        if (!user.getCurrentlyReading().contains(bookId)) {
            throw new BadRequestException("Book with ISBN " + bookId + " is not currently being read by user " + userId);
        }

        UserBook book = userBookRepo.findByOwnerIdAndIsbn(userId, bookId).orElseThrow(
                () -> new IllegalStateException(
                        "Possible concurrent issue: book with ISBN " + bookId + " does not exist for user " + userId
                )
        );

        book.setCurrentlyReading(false);
        book.setCompleted(true);
        userBookRepo.save(book);

        user.getCurrentlyReading().remove(bookId);
        userRepo.save(user);

        return "Marked the following book as finished: " + bookId + ".";
    }

}
