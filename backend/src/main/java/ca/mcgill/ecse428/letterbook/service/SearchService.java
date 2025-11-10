package ca.mcgill.ecse428.letterbook.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import ca.mcgill.ecse428.letterbook.dto.BookSearchResultDTO;

@Service
public class SearchService {

    private final RestClient restClient;
    private final String apiKey;

    public SearchService(RestClient.Builder restBuilder,
                         @Value("API_HERE") String apiKey) {
        this.restClient = restBuilder.baseUrl("https://www.googleapis.com/books/v1").build();
        this.apiKey = apiKey;
    }

    public BookSearchResultDTO searchBooks(String title, String author, String isbn) {
        // Build query string based on provided parameters
        StringBuilder queryBuilder = new StringBuilder();

        if (title != null && !title.trim().isEmpty()) {
            queryBuilder.append("intitle:").append(title);
        }

        if (author != null && !author.trim().isEmpty()) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append("+");
            }
            queryBuilder.append("inauthor:").append(author);
        }

        if (isbn != null && !isbn.trim().isEmpty()) {
            if (queryBuilder.length() > 0) {
                queryBuilder.append("+");
            }
            queryBuilder.append("isbn:").append(isbn);
        }

        // If no parameters provided, return empty result
        if (queryBuilder.length() == 0) {
            BookSearchResultDTO emptyResult = new BookSearchResultDTO();
            emptyResult.setTotalItems(0);
            emptyResult.setItems(List.of());
            return emptyResult;
        }

        String query = queryBuilder.toString();

        String url = UriComponentsBuilder.fromPath("/volumes")
                .queryParam("q", query)
                .queryParam("maxResults", 5)
                .queryParam("fields",
                        "kind,totalItems,items(id,volumeInfo(title,authors,publishedDate,description,pageCount," +
                                "categories,language,averageRating,ratingsCount,imageLinks,infoLink,previewLink))")
                .queryParam("key", apiKey)
                .toUriString();

        BookSearchResultDTO result = restClient.get()
                .uri(url)
                .retrieve()
                .body(BookSearchResultDTO.class);

        if (result != null && result.getItems() != null) {
            result.getItems().removeIf(item ->
                    item.getVolumeInfo() == null ||
                    item.getVolumeInfo().getTitle() == null);

            for (var item : result.getItems()) {
                var info = item.getVolumeInfo();
                if (info.getAuthors() == null || info.getAuthors().isEmpty())
                    info.setAuthors(List.of("Unknown Author"));
                if (info.getDescription() == null)
                    info.setDescription("No description available.");
                if (info.getLanguage() == null)
                    info.setLanguage("Unknown");
                if (info.getImageLinks() != null && info.getImageLinks().getThumbnail() == null)
                    info.getImageLinks().setThumbnail("");
            }
        }

        return result;
    }
}
