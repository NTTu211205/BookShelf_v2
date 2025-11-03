package com.example.bookshelf.api.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Formats {
    @SerializedName("text/html")
    @Expose
    private String textHtml;
    @SerializedName("application/epub+zip")
    @Expose
    private String applicationEpubZip;
    @SerializedName("image/jpeg")
    @Expose
    private String imageJpeg;

    public Formats(String textHtml, String applicationEpubZip, String imageJpeg) {
        this.textHtml = textHtml;
        this.applicationEpubZip = applicationEpubZip;
        this.imageJpeg = imageJpeg;
    }

    public String getTextHtml() {
        return textHtml;
    }

    public void setTextHtml(String textHtml) {
        this.textHtml = textHtml;
    }

    public String getApplicationEpubZip() {
        return applicationEpubZip;
    }

    public void setApplicationEpubZip(String applicationEpubZip) {
        this.applicationEpubZip = applicationEpubZip;
    }

    public String getImageJpeg() {
        return imageJpeg;
    }

    public void setImageJpeg(String imageJpeg) {
        this.imageJpeg = imageJpeg;
    }
}
