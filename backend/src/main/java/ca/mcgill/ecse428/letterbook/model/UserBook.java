package ca.mcgill.ecse428.letterbook.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Builder
@Getter
@Setter
@Data
@Document(collection = "books")
public class UserBook {

    /*
    * This defines a book object that is associated with a specific user,
    * NOT a model that would exhaustively represent all books in existence.
    */

    @Id
    private String id;

    private String title;

    private String author;

    private String isbn;

    private String ownerId;

    private boolean currentlyReading;

    private boolean completed;

}
