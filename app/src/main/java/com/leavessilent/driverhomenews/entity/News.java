package com.leavessilent.driverhomenews.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Leavessilent on 2016/8/20.
 */
public class News implements Parcelable {

    private int id;
    private String contentt;
    private String title;
    private String editor;
    private String desc;
    private String icon;
    private String bigimgsrc;
    private String smallimgsrc;
    private String postdate;
    private int type;

    public News() {
    }

    public News(int id, String contentt, String title, String editor, String desc, String icon, String bigimgsrc, String smallimgsrc, String postdate, int type) {
        this.id = id;
        this.contentt = contentt;
        this.title = title;
        this.editor = editor;
        this.desc = desc;
        this.icon = icon;
        this.bigimgsrc = bigimgsrc;
        this.smallimgsrc = smallimgsrc;
        this.postdate = postdate;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContentt() {
        return contentt;
    }

    public void setContentt(String contentt) {
        this.contentt = contentt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBigimgsrc() {
        return bigimgsrc;
    }

    public void setBigimgsrc(String bigimgsrc) {
        this.bigimgsrc = bigimgsrc;
    }

    public String getSmallimgsrc() {
        return smallimgsrc;
    }

    public void setSmallimgsrc(String smallimgsrc) {
        this.smallimgsrc = smallimgsrc;
    }

    public String getPostdate() {
        return postdate;
    }

    public void setPostdate(String postdate) {
        this.postdate = postdate;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static Creator<News> getCREATOR() {
        return CREATOR;
    }

    protected News(Parcel in) {
        id = in.readInt();
        contentt = in.readString();
        title = in.readString();
        editor = in.readString();
        desc = in.readString();
        icon = in.readString();
        bigimgsrc = in.readString();
        smallimgsrc = in.readString();
        postdate = in.readString();
        type = in.readInt();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(contentt);
        dest.writeString(title);
        dest.writeString(editor);
        dest.writeString(desc);
        dest.writeString(icon);
        dest.writeString(bigimgsrc);
        dest.writeString(smallimgsrc);
        dest.writeString(postdate);
        dest.writeInt(type);
    }
}
