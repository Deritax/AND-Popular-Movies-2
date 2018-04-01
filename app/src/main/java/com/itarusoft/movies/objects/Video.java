package com.itarusoft.movies.objects;

public class Video {

    private String title;

    private String videoKey;

    private String site;

    private String type;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public String getVideoKey() {
        return videoKey;
    }

    public String getSite() {
        return site;
    }

    public String getType() {
        return type;
    }

    public Video(String title, String videoKey, String site, String type){

        setTitle(title);
        setVideoKey(videoKey);
        setSite(site);
        setType(type);

    }
}
