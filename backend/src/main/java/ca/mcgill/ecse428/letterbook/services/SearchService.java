package ca.mcgill.ecse428.letterbook.services;

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
                         @Value("ENTER YOUR API KEY") String apiKey) {
        this.restClient = restBuilder.baseUrl("https://www.googleapis.com/books/v1").build();
        this.apiKey = apiKey;
    }

    public BookSearchResultDTO searchBooks(String query) {
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
