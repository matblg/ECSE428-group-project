package ca.mcgill.ecse428.letterbook.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SearchService {

    private final RestClient restClient;
    private final String apiKey;

    public SearchService(RestClient.Builder restBuilder,
            @Value("${google.books.api.key}") String apiKey) {
        this.restClient = restBuilder.baseUrl("https://www.googleapis.com/books/v1")
                .build();
        this.apiKey = apiKey;

    }

    
}
