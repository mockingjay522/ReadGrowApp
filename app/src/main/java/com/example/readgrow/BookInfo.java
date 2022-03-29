package com.example.readgrow;
public class BookInfo {
    private String bookName;
    private String author ;
    private String publication;
    private String year;
    private int status;
    private String option;

    public BookInfo() {
    }

    public BookInfo(String bookName, String author, String publication, String year, double costPerWeek, int status, String option) {
        this.bookName = bookName;
        this.author = author;
        this.publication = publication;
        this.year = year;
        this.status = status;
        this.option = option;
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

//    public double getCostPerWeek() {
//        return costPerWeek;
//    }
//
//    public void setCostPerWeek(double costPerWeek) {
//        this.costPerWeek = costPerWeek;
//    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
