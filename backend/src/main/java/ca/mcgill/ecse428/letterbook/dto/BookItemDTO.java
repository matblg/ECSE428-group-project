package ca.mcgill.ecse428.letterbook.dto;

import java.util.List;

public class BookItemDTO {
    
    public String kind;
    public String id;
    public String etag;
    public String selfLink;

    public String title;
    public List<String> authors;
    public String publishedDate;
    public String thumbnail;
    public String description;
    public String infoLink;
    public String previewLink;
    public String isbn13;
    public Integer pageCount;
    public List<String> categories;
    public String language;
    public Double averageRating;
    public Integer ratingsCount;
}
