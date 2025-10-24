package ca.mcgill.ecse428.letterbook.dto;

import java.util.List;

public class BookSearchResultDTO {
    private String kind;
    private int totalItems;
    private List<BookItemDTO> items;
    private Integer nextStartIndex;
    private Integer pageSize;

    public BookSearchResultDTO() {
    }

    public BookSearchResultDTO(String kind, int totalItems, List<BookItemDTO> items, Integer nextStartIndex, Integer pageSize) {
        this.kind = kind;
        this.totalItems = totalItems;
        this.items = items;
        this.nextStartIndex = nextStartIndex;
        this.pageSize = pageSize;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<BookItemDTO> getItems() {
        return items;
    }

    public void setItems(List<BookItemDTO> items) {
        this.items = items;
    }

    public Integer getNextStartIndex() {
        return nextStartIndex;
    }

    public void setNextStartIndex(Integer nextStartIndex) {
        this.nextStartIndex = nextStartIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
