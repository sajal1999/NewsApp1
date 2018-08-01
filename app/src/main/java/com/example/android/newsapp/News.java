package com.example.android.newsapp;

public class News {
    private String mTitleName;
    private String mDate;
    private String mUrl;
    private String mSectionName;
    private String mAuthorName;

    public News(String titleName, String sectionName, String authorName,String url,String date){
        mTitleName=titleName;
        mSectionName=sectionName;
        mAuthorName = authorName;
        mUrl = url;
        mDate = date;
    }

    public String getTitleName() {
        return mTitleName;
    }
    public String getDate() {
        return mDate;
    }
    public String getAuthorName() {
        return mAuthorName;
    }
    public String getUrl() {
        return mUrl;
    }
    public String getSectionName(){
        return mSectionName;
    }
}
