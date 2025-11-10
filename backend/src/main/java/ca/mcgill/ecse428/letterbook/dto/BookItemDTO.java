package ca.mcgill.ecse428.letterbook.dto;

public class BookItemDTO {

    private String id;
    private VolumeInfo volumeInfo;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public VolumeInfo getVolumeInfo() { return volumeInfo; }
    public void setVolumeInfo(VolumeInfo volumeInfo) { this.volumeInfo = volumeInfo; }

    public static class VolumeInfo {
        private String title;
        private java.util.List<String> authors;
        private String publishedDate;
        private String description;
        private Integer pageCount;
        private java.util.List<String> categories;
        private String language;
        private Double averageRating;
        private Integer ratingsCount;
        private ImageLinks imageLinks;
        private String infoLink;
        private String previewLink;

        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }

        public java.util.List<String> getAuthors() { return authors; }
        public void setAuthors(java.util.List<String> authors) { this.authors = authors; }

        public String getPublishedDate() { return publishedDate; }
        public void setPublishedDate(String publishedDate) { this.publishedDate = publishedDate; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public Integer getPageCount() { return pageCount; }
        public void setPageCount(Integer pageCount) { this.pageCount = pageCount; }

        public java.util.List<String> getCategories() { return categories; }
        public void setCategories(java.util.List<String> categories) { this.categories = categories; }

        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }

        public Double getAverageRating() { return averageRating; }
        public void setAverageRating(Double averageRating) { this.averageRating = averageRating; }

        public Integer getRatingsCount() { return ratingsCount; }
        public void setRatingsCount(Integer ratingsCount) { this.ratingsCount = ratingsCount; }

        public ImageLinks getImageLinks() { return imageLinks; }
        public void setImageLinks(ImageLinks imageLinks) { this.imageLinks = imageLinks; }

        public String getInfoLink() { return infoLink; }
        public void setInfoLink(String infoLink) { this.infoLink = infoLink; }

        public String getPreviewLink() { return previewLink; }
        public void setPreviewLink(String previewLink) { this.previewLink = previewLink; }
    }

    public static class ImageLinks {
        private String thumbnail;

        public String getThumbnail() { return thumbnail; }
        public void setThumbnail(String thumbnail) { this.thumbnail = thumbnail; }
    }
}
