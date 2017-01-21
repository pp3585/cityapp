package com.samples.pooja.cityapp.webhandlers;

/**
 * Model object for a news item.
 */

public class NewsItem {

    private String mTitle;
    private String mDescription;
    private String mImageSrc;
    private String mDate;
    private String mLink;

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String mDescription) {
        this.mDescription = mDescription;
        int startIndex = this.mDescription.indexOf("src") + 4;
        int endIndex = this.mDescription.indexOf("/>", startIndex);
        String imgSrc = this.mDescription.substring(startIndex, endIndex);
        this.setImageSrc(imgSrc);
    }

    public String getImageSrc() {
        return mImageSrc;
    }

    public void setImageSrc(String mImageSrc) {
        this.mImageSrc = mImageSrc;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String mDate) {
        this.mDate = mDate;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String mLink) {
        this.mLink = mLink;
    }

}
