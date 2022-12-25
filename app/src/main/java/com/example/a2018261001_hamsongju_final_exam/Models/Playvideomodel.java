package com.example.a2018261001_hamsongju_final_exam.Models;

public class Playvideomodel {

    String channel_name, video_title, video_url, date, views;

    public Playvideomodel(String channel_name, String video_title, String video_url, String date, String views) {
        this.channel_name = channel_name;
        this.video_title = video_title;
        this.video_url = video_url;
        this.date = date;
        this.views = views;
    }

    public Playvideomodel(){}

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public String getVideo_title() {
        return video_title;
    }

    public void setVideo_title(String video_title) {
        this.video_title = video_title;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }
}
