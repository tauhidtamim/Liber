package com.inxs5859.liber.HelperClasses;

import java.util.ArrayList;

//THIS CLASS IS FOR SEARCH RECYCLERVIEW

public class BookInfo {

    private String mTitle;
    private String mAuthors;
    //private String mDescription;
    private String mThumbnail;
    private String mUrl;

    public BookInfo() {
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmAuthors() {
        return mAuthors;
    }

    public void setmAuthors(String mAuthors) {
        this.mAuthors = mAuthors;
    }


//    public String getmDescription() {
//        return mDescription;
//    }
//
//    public void setmDescription(String mDescription) {
//        this.mDescription = mDescription;
//    }


    public String getmThumbnail() {
        return mThumbnail;
    }

    public void setmThumbnail(String mThumbnail) {
        this.mThumbnail = mThumbnail;
    }

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public BookInfo(String mTitle, String mAuthors, String mThumbnail, String mUrl) {
        this.mTitle = mTitle;
        this.mAuthors = mAuthors;
        //this.mDescription = mDescription;
        this.mThumbnail = mThumbnail;
        this.mUrl = mUrl;
    }

}
