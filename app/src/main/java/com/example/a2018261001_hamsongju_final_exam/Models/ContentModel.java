package com.example.a2018261001_hamsongju_final_exam.Models;

public class ContentModel {
    String id, publisher, playlist, type, video, video_description, video_url, video_tags, video_title, date;
    long views;

    public ContentModel(String id, String publisher, String playlist, String type, String video, String video_description, String video_url, String video_tags, String video_title, String date, long views) {
        this.id = id;
        this.publisher = publisher;
        this.playlist = playlist;
        this.type = type;
        this.video = video;
        this.video_description = video_description;
        this.video_url = video_url;
        this.video_tags = video_tags;
        this.video_title = video_title;
        this.date = date;
        this.views = views;
    }

    public ContentModel() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPlaylist() {
        return playlist;
    }

    public void setPlaylist(String playlist) {
        this.playlist = playlist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideo_description() {
        return video_description;
    }

    public void setVideo_description(String video_description) {
        this.video_description = video_description;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getVideo_tags() {
        return video_tags;
    }

    public void setVideo_tags(String video_tags) {
        this.video_tags = video_tags;
    }

    public long getViews() {
        return views;
    }

    public void setViews(long views) {
        this.views = views;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
