package com.samples.pooja.cityapp.datamodels;

/**
 * Data model for news details page
 */
public class NewsDetail {

    private String mTitle;
    private String mImgSrc;
    private String mImgDesc;
    private String mDescription;
    private String mSource;
    private String mPubDate;

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        this.mSource = source;
    }

    public String getPubDate() {
        return mPubDate;
    }

    public void setPubDate(String pubDate) {
        this.mPubDate = pubDate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    public String getImgDesc() {
        return mImgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.mImgDesc = imgDesc;
    }

    public String getImgSrc() {
        return mImgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.mImgSrc = imgSrc;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }
}
