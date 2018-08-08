package com.example.ziu.a0808bookinfotabhomework;

public class BookInfo {

    private String title;
    private String author;

    public BookInfo(){

    }

    public BookInfo(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
