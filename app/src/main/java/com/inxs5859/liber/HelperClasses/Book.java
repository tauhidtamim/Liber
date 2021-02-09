package com.inxs5859.liber.HelperClasses;

//THIS CLASS IS FOR SHELF TO READ, CURRENT READ RECYCLER-VIEWS

public class Book {

    String Author, Title, Thumbnail;

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public Book(String author, String title, String thumbnail) {
        this.Author = author;
        this.Title = title;
        this.Thumbnail = thumbnail;
    }

    public Book() {
    }
}
