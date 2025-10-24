package ca.mcgill.ecse428.letterbook.services;

import ca.mcgill.ecse428.letterbook.dto.BookSearchResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class SearchService {

    private final RestClient restClient;
    private final String apiKey;

    public SearchService(RestClient.Builder restBuilder,
                         @Value("${google.books.api.key}") String apiKey) {
        this.restClient = restBuilder.baseUrl("https://www.googleapis.com/books/v1").build();
        this.apiKey = apiKey;
    }

    public BookSearchResultDTO searchBooks(String query) {
        String url = UriComponentsBuilder.fromPath("/volumes")
                .queryParam("q", query)
                .queryParam("maxResults", 5)
                .queryParam("key", apiKey)
                .toUriString();

        return restClient.get()
                .uri(url)
                .retrieve()
                .body(BookSearchResultDTO.class);
    }
}
