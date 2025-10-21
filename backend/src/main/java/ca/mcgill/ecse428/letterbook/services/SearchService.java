package ca.mcgill.ecse428.letterbook.services;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SearchService {
    private final RestClient restClient;

    public SearchService(RestClient.Builder restBuilder){
        this.restClient = restBuilder.baseUrl("https://example.org").build();


    }
}
