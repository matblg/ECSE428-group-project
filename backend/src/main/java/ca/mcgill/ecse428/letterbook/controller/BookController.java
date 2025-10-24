package ca.mcgill.ecse428.letterbook.controller;

import ca.mcgill.ecse428.letterbook.dto.BookSearchResultDTO;
import ca.mcgill.ecse428.letterbook.services.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public BookSearchResultDTO searchBooks(@RequestParam("query") String query) {
        return searchService.searchBooks(query);
    }
}
