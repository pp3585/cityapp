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

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        this.mDescription = description;
        if(description != null && !description.isEmpty()) {
            //Get description without HTML if HTML exists
            if(description.indexOf("</a>") != -1) {
                String descWoHtml = description.substring(description.indexOf("</a>") + 4);
                mDescription = descWoHtml;
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

    public void setImageSrc(String imageSrc) {
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
