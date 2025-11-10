package ca.mcgill.ecse428.letterbook.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;


@Builder
@Getter
@Setter
@Data
@Document(collection = "users")
public class User {

    @Id
    private String id;

    private String username;

    private String email;

    private String password;

    private String bio;

    private List<String> currentlyReading;

    private List<String> allBooks;

}
