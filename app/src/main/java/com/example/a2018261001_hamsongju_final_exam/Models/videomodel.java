package com.example.a2018261001_hamsongju_final_exam.Models;

public class videomodel {

    String video_description, video_title, video_url;

    public videomodel(String video_description, String video_title, String video_url) {
        this.video_description = video_description;
        this.video_title = video_title;
        this.video_url = video_url;
    }

    public videomodel(){

    }

    public String getVideo_description() {
        return video_description;
    }

    public void setVideo_description(String video_description) {
        this.video_description = video_description;
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
}
