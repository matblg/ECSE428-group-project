package ca.mcgill.ecse428.letterbook.dto;

import java.util.List;

public class BookSearchResultDTO {
    private String kind;
    private int totalItems;
    private List<BookItemDTO> items;

    public String getKind() { return kind; }
    public void setKind(String kind) { this.kind = kind; }

    public int getTotalItems() { return totalItems; }
    public void setTotalItems(int totalItems) { this.totalItems = totalItems; }

    public List<BookItemDTO> getItems() { return items; }
    public void setItems(List<BookItemDTO> items) { this.items = items; }
}
