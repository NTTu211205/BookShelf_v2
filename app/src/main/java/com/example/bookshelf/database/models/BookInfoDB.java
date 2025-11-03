//package com.example.bookshelf.database.models;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//@Entity(tableName = "BookInfo")
//public class BookInfoDB {
//    @PrimaryKey(autoGenerate = true)
//    private long bookInfoId;
//    @ColumnInfo(index = true)
//    private String bookId;
//    @ColumnInfo(name="title")
//    private String title;
//    @ColumnInfo(name="authors")
//    private String authors;
//    @ColumnInfo(name="publisher")
//    private String publisher;
//    @ColumnInfo(name="publishedDate")
//    private String publishedDate;
//    @ColumnInfo(name="description")
//    private String description;
//    @ColumnInfo(name="pageCount")
//    private int numPage;
//    @ColumnInfo(name="printType")
//    private String printType;
//    @ColumnInfo(name="categories")
//    private String catrgories;
//    @ColumnInfo(name="language")
//    private String language;
//    @ColumnInfo(name="previewLink")
//    private String previewLink;
//
//
//
//    // Constructor
//    public BookInfoDB(String bookId, String title, String authors, String publisher, String publishedDate, String description, int numPage, String printType, String catrgories, String language, String previewLink) {
//        this.bookId = bookId;
//        this.title = title;
//        this.authors = authors;
//        this.publisher = publisher;
//        this.publishedDate = publishedDate;
//        this.description = description;
//        this.numPage = numPage;
//        this.printType = printType;
//        this.catrgories = catrgories;
//        this.language = language;
//        this.previewLink = previewLink;
//    }
//
//
//
//    // Getter - Setter
//
//    public long getBookInfoId() {
//        return bookInfoId;
//    }
//
//    public void setBookInfoId(long bookInfoId) {
//        this.bookInfoId = bookInfoId;
//    }
//
//    public String getBookId() {
//        return bookId;
//    }
//
//    public void setBookId(String bookId) {
//        this.bookId = bookId;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getAuthors() {
//        return authors;
//    }
//
//    public void setAuthors(String authors) {
//        this.authors = authors;
//    }
//
//    public String getPublisher() {
//        return publisher;
//    }
//
//    public void setPublisher(String publisher) {
//        this.publisher = publisher;
//    }
//
//    public String getPublishedDate() {
//        return publishedDate;
//    }
//
//    public void setPublishedDate(String publishedDate) {
//        this.publishedDate = publishedDate;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public int getNumPage() {
//        return numPage;
//    }
//
//    public void setNumPage(int numPage) {
//        this.numPage = numPage;
//    }
//
//    public String getPrintType() {
//        return printType;
//    }
//
//    public void setPrintType(String printType) {
//        this.printType = printType;
//    }
//
//    public String getCatrgories() {
//        return catrgories;
//    }
//
//    public void setCatrgories(String catrgories) {
//        this.catrgories = catrgories;
//    }
//
//    public String getLanguage() {
//        return language;
//    }
//
//    public void setLanguage(String language) {
//        this.language = language;
//    }
//
//    public String getPreviewLink() {
//        return previewLink;
//    }
//
//    public void setPreviewLink(String previewLink) {
//        this.previewLink = previewLink;
//    }
//}
