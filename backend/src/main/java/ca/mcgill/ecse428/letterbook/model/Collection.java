package ca.mcgill.ecse428.letterbook.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.UUID;

@Builder
@Getter
@Setter
@Data
@Document(collection = "collection")
public class Collection {

    @Id
    private UUID id;

    private String name;

    private String description;

    private List<String> booksISBNs;

    // MAP TO User
    private UUID ownerId;

}
