package ca.mcgill.ecse428.letterbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse428.letterbook.dto.BookSearchResultDTO;
import ca.mcgill.ecse428.letterbook.services.SearchService;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/search")
    public BookSearchResultDTO searchBooks(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "author", required = false) String author,
            @RequestParam(value = "isbn", required = false) String isbn) {
        return searchService.searchBooks(title, author, isbn);
    }
}
