package com.example.sinh.starchat.Model;

import com.google.gson.annotations.SerializedName;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ADMIN on 12/16/2017.
 */

public class Dialog implements IDialog {

    @SerializedName("id")
    String id;
    @SerializedName("dialogPhoto")
    String dialogPhoto;
    @SerializedName("dialogName")
    String dialogName;
    @SerializedName("users")
    ArrayList<User> users;
    @SerializedName("lastMessage")
    Message lastMessage;
    @SerializedName("unreadCount")
    int unreadCount;

    public Dialog(String id, String dialogPhoto, String dialogName, ArrayList<User> users, Message lastMessage, int unreadCount) {
        this.id = id;
        this.dialogPhoto = dialogPhoto;
        this.dialogName = dialogName;
        this.users = users;
        this.lastMessage = lastMessage;
        this.unreadCount = unreadCount;
    }

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getDialogPhoto() {
        return this.dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return this.dialogName;
    }

    @Override
    public List<User> getUsers() {
        return this.users;
    }

    @Override
    public IMessage getLastMessage() {
        return this.lastMessage;
    }

    @Override
    public void setLastMessage(IMessage message) {
        this.lastMessage = (Message) message;
    }

    @Override
    public int getUnreadCount() {
        return this.unreadCount;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDialogPhoto(String dialogPhoto) {
        this.dialogPhoto = dialogPhoto;
    }

    public void setDialogName(String dialogName) {
        this.dialogName = dialogName;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
