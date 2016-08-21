package com.leavessilent.driverhomenews.entity;

/**
 * Created by Administrator on 2016/8/7.
 */

public class Comment {
    private String simtitle;
    private String content;
    private int id;
    private String username;
    private int support;
    private int oppose;
    private String postdate;
    private int type;

    public Comment(String simtitle, String content, int id, String username, int support, int oppose, String postdate, int type) {
        this.simtitle = simtitle;
        this.content = content;
        this.id = id;
        this.username = username;
        this.support = support;
        this.oppose = oppose;
        this.postdate = postdate;
        this.type = type;
    }

    public Comment() {
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSimtitle() {
        return simtitle;
    }

    public void setSimtitle(String simtitle) {
        this.simtitle = simtitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSupport() {
        return support;
    }

    public void setSupport(int support) {
        this.support = support;
    }

    public int getOppose() {
        return oppose;
    }

    public void setOppose(int oppose) {
        this.oppose = oppose;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }
}
