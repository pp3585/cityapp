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

    public NewsItem(){

    }

    public NewsItem(String title, String description, String imageSrc, String date, String link){
        mTitle = title;
        mDescription = description;
        mImageSrc = imageSrc;
        mDate = date;
        mLink = link;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        if(description != null && !description.isEmpty()) {
            //Get description without HTML if HTML exists
            if(description.contains("</a>")) {
                mDescription = description.substring(description.indexOf("</a>") + 4);
                //Get image URL from the extracted HTML
                String html = description.substring(0, description.indexOf("</a>") + 3);
                int startIndex = html.indexOf("src") + 5;
                int endIndex = html.indexOf("\"", startIndex);
                String imgSrc = html.substring(startIndex, endIndex);
                this.setImageSrc(imgSrc);
            } else {
                mDescription = description;
            }

        }
    }

    public String getImageSrc() {
        return mImageSrc;
    }

    private void setImageSrc(String imageSrc) {
        this.mImageSrc = imageSrc;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        this.mLink = link;
    }

}
