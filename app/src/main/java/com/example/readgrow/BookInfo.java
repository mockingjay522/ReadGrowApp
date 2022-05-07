package com.example.readgrow;
public class BookInfo {
    private int bookID;
    private String bookName;
    private String author ;
    private String publication;
    private String year;
    private int status;
    private String cost;
    //private String option;

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public BookInfo() {
    }

    public BookInfo(int bookID, String bookName, String author, String publication, String year, int status, String cost) {
        this.bookID = bookID;
        this.bookName = bookName;
        this.author = author;
        this.publication = publication;
        this.year = year;
        this.status = status;
        this.cost = cost;
    }

    public BookInfo( String bookName, String author, String publication, String year, int status, String cost) {

        this.bookName = bookName;
        this.author = author;
        this.publication = publication;
        this.year = year;
        this.status = status;
        this.cost = cost;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBookID() {
        return bookID;
    }

    public void setBookID(int bookID) {
        this.bookID = bookID;
    }

    @Override
    public String toString() {
        return "bookID=" + bookID + ", book Name='" + bookName + '\'' + ", author='" + author + '\'' + ", year='" + year;
    }
}
