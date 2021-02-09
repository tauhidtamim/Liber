package com.inxs5859.liber.HelperClasses;

//THIS CLASS IS FOR SHELF READ RECYCLER-VIEW

public class BookRead {

    String Author, Title, Thumbnail, Review, Date;

    public BookRead() {
    }

    public BookRead(String author, String title, String thumbnail, String review, String date) {
        Author = author;
        Title = title;
        Thumbnail = thumbnail;
        Review = review;
        Date = date;
    }

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

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
