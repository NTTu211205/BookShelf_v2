package com.example.bookshelf.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookAPI {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("authors")
    @Expose
    private List<Author> authors;
    @SerializedName("summaries")
    @Expose
    private List<String> summaries;
    @SerializedName("editors")
    @Expose
    private List<Editor> editors;
    @SerializedName("translators")
    @Expose
    private List<Object> translators;
    @SerializedName("subjects")
    @Expose
    private List<String> subjects;
    @SerializedName("bookshelves")
    @Expose
    private List<String> bookshelves;
    @SerializedName("languages")
    @Expose
    private List<String> languages;
    @SerializedName("copyright")
    @Expose
    private Boolean copyright;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("formats")
    @Expose
    private Formats formats;
    @SerializedName("download_count")
    @Expose
    private Integer downloadCount;

    public BookAPI(String id, String title, List<Author> authors, List<String> summaries, List<Editor> editors, List<Object> translators, List<String> subjects, List<String> bookshelves, List<String> languages, Boolean copyright, String mediaType, Formats formats, Integer downloadCount) {
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
        this.formats = formats;
        this.downloadCount = downloadCount;
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
        if (authors.isEmpty()) {
            return "";
        }

        String stringAuthor = "";
        for (Author author: authors) {
            stringAuthor += author.getName() + ", ";
        }

        return stringAuthor.substring(0, stringAuthor.length() - 2);
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getSummaries() {
        if (summaries.isEmpty()) {
            return "";
        }

        String stringSummaries = "";
        for (String s: summaries) {
            stringSummaries += s + "\n";
        }
        return stringSummaries.substring(0, stringSummaries.length() - 2);
    }

    public void setSummaries(List<String> summaries) {
        this.summaries = summaries;
    }

    public String getEditors() {
        if (editors.isEmpty()) {
            return "";
        }

        String stringEditor = "";
        for (Editor e: editors) {
            stringEditor += e.getName() + ", ";
        }
        return stringEditor.substring(0, stringEditor.length() - 2);
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    public String getTranslators() {
        return translators.toString();
    }

    public void setTranslators(List<Object> translators) {
        this.translators = translators;
    }

    public String getSubjects() {
        if (subjects.isEmpty()) {
            return "";
        }

        String stringSubject = "";
        for (String s: subjects) {
            stringSubject += s + ", ";
        }

        return stringSubject.substring(0, stringSubject.length() - 2);
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getBookshelves() {
        return bookshelves.toString();
    }

    public void setBookshelves(List<String> bookshelves) {
        this.bookshelves = bookshelves;
    }

    public String getLanguages() {
        return languages.toString();
    }

    public void setLanguages(List<String> languages) {
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

    public Formats getFormats() {
        return formats;
    }

    public void setFormats(Formats formats) {
        this.formats = formats;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
}
