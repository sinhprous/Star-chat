package com.example.sinh.starchat.Model;

import com.google.gson.annotations.SerializedName;
import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

/**
 * Created by ADMIN on 10/8/2017.
 */

public class Message implements IMessage{
    @SerializedName("id")
    String id;
    @SerializedName("text")
    String text;
    @SerializedName("user")
    User user;
    @SerializedName("time")
    long time; // miliseconds from 1970

    String file = null;

    public Message(String id, String text, User user, long time) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.time = time;
    }

    public Message(String id, String text, User user, long time, String file) {
        this.id = id;
        this.text = text;
        this.user = user;
        this.time = time;
        this.file = file;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getText() {
        return (file!=null)? "▼ "+this.text : this.text;
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public Date getCreatedAt() {
        return new Date(time);
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
