package com.example.bookshelf.database.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="Book")
public class BookDB {
    @PrimaryKey(autoGenerate = true)
    private long bookId;
    @ColumnInfo(name="id")
    private String id;
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="authors")
    private String authors;
    @ColumnInfo(name="summaries")
    private String summaries;
    @ColumnInfo(name="editors")
    private String editors;
    @ColumnInfo(name="translators")
    private String translators;
    @ColumnInfo(name="subjects")
    private String subjects;
    @ColumnInfo(name="bookshelves")
    private String bookshelves;
    @ColumnInfo(name="languages")
    private String languages;
    @ColumnInfo(name="copyright")
    private Boolean copyright;
    @ColumnInfo(name="media_type")
    private String mediaType;
    @ColumnInfo(name="download_count")
    private Integer downloadCount;
    @ColumnInfo(name="link_book")
    private String linkBook;
    @ColumnInfo(name="link_thumbnail")
    private String linkThumbnail;

    public BookDB(String id, String title, String authors, String summaries, String editors, String translators, String subjects, String bookshelves, String languages, Boolean copyright, String mediaType, Integer downloadCount, String linkBook, String linkThumbnail) {
        this.id = id;
        this.title = title;
        this.authors = authors;
        this.summaries = summaries;
        this.editors = editors;
        this.translators = translators;
        this.subjects = subjects;
        this.bookshelves = bookshelves;
        this.languages = languages;
        this.copyright = copyright;
        this.mediaType = mediaType;
        this.downloadCount = downloadCount;
        this.linkBook = linkBook;
        this.linkThumbnail = linkThumbnail;
    }

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getSummaries() {
        return summaries;
    }

    public void setSummaries(String summaries) {
        this.summaries = summaries;
    }

    public String getEditors() {
        return editors;
    }

    public void setEditors(String editors) {
        this.editors = editors;
    }

    public String getTranslators() {
        return translators;
    }

    public void setTranslators(String translators) {
        this.translators = translators;
    }

    public String getSubjects() {
        return subjects;
    }

    public void setSubjects(String subjects) {
        this.subjects = subjects;
    }

    public String getBookshelves() {
        return bookshelves;
    }

    public void setBookshelves(String bookshelves) {
        this.bookshelves = bookshelves;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Boolean getCopyright() {
        return copyright;
    }

    public void setCopyright(Boolean copyright) {
        this.copyright = copyright;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getLinkBook() {
        return linkBook;
    }

    public void setLinkBook(String linkBook) {
        this.linkBook = linkBook;
    }

    public String getLinkThumbnail() {
        return linkThumbnail;
    }

    public void setLinkThumbnail(String linkThumbnail) {
        this.linkThumbnail = linkThumbnail;
    }
}
