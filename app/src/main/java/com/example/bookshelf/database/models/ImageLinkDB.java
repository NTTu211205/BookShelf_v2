//package com.example.bookshelf.database.models;
//
//import androidx.room.ColumnInfo;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//@Entity(tableName = "ImageLink")
//public class ImageLinkDB {
//    @PrimaryKey(autoGenerate = true)
//    private long imageLinkId;
//    @ColumnInfo(name="bookInfoId")
//    private String bookInfoId;
//    @ColumnInfo(name="smallThumbnail")
//    private String smallThumbnail;
//    @ColumnInfo(name="thumbnail")
//    private String thumbnail;
//    @ColumnInfo(name="small")
//    private String small;
//    @ColumnInfo(name="medium")
//    private String medium;
//    @ColumnInfo(name="large")
//    private String large;
//    @ColumnInfo(name="extraLarge")
//    private String extraLarge;
//
//
//
//    // Constructor
//    public ImageLinkDB(String bookInfoId, String smallThumbnail, String thumbnail, String small, String medium, String large, String extraLarge) {
//        this.bookInfoId = bookInfoId;
//        this.smallThumbnail = smallThumbnail;
//        this.thumbnail = thumbnail;
//        this.small = small;
//        this.medium = medium;
//        this.large = large;
//        this.extraLarge = extraLarge;
//    }
//
//
//
//    // Getter - Setter
//    public long getImageLinkId() {
//        return imageLinkId;
//    }
//
//    public void setImageLinkId(long imageLinkId) {
//        this.imageLinkId = imageLinkId;
//    }
//
//    public String getBookInfoId() {
//        return bookInfoId;
//    }
//
//    public void setBookInfoId(String bookInfoId) {
//        this.bookInfoId = bookInfoId;
//    }
//
//    public String getSmallThumbnail() {
//        return smallThumbnail;
//    }
//
//    public void setSmallThumbnail(String smallThumbnail) {
//        this.smallThumbnail = smallThumbnail;
//    }
//
//    public String getThumbnail() {
//        return thumbnail;
//    }
//
//    public void setThumbnail(String thumbnail) {
//        this.thumbnail = thumbnail;
//    }
//
//    public String getSmall() {
//        return small;
//    }
//
//    public void setSmall(String small) {
//        this.small = small;
//    }
//
//    public String getMedium() {
//        return medium;
//    }
//
//    public void setMedium(String medium) {
//        this.medium = medium;
//    }
//
//    public String getLarge() {
//        return large;
//    }
//
//    public void setLarge(String large) {
//        this.large = large;
//    }
//
//    public String getExtraLarge() {
//        return extraLarge;
//    }
//
//    public void setExtraLarge(String extraLarge) {
//        this.extraLarge = extraLarge;
//    }
//}
