public class BookInfo {
    private String bookName;
    private String author ;
    private String publication;
    private String year;
    private double costPerWeek;
    private String option;

    public BookInfo() {
    }

    public BookInfo(String bookName, String author, String publication, String year, double costPerWeek, String option) {
        this.bookName = bookName;
        this.author = author;
        this.publication = publication;
        this.year = year;
        this.costPerWeek = costPerWeek;
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

    public double getCostPerWeek() {
        return costPerWeek;
    }

    public void setCostPerWeek(double costPerWeek) {
        this.costPerWeek = costPerWeek;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }
}
