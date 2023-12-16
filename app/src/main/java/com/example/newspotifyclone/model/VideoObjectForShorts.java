package com.example.newspotifyclone.model;

public class VideoObjectForShorts {
    private String link;
    private boolean like,dislike;
    private String channelName;
    private String content;
    private long duration_ms;

    public VideoObjectForShorts(boolean like, boolean dislike, String channelName, String content) {
        this.like = like;
        this.dislike = dislike;
        this.channelName = channelName;
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
    }

    public boolean isDislike() {
        return dislike;
    }

    public void setDislike(boolean dislike) {
        this.dislike = dislike;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(long duration_ms) {
        this.duration_ms = duration_ms;
    }
}
