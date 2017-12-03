package com.example.sinh.starchat.IModel;

import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;

import java.util.ArrayList;

/**
 * Created by ADMIN on 12/3/2017.
 */

public class DefaultDialog implements IDialog {

    String id = "0", dialogPhoto, dialogName = "test dialog";
    IMessage lastMessage = new Message("dit con me may");
    ArrayList<IUser> users;
    int unreadCount = 0;

    public DefaultDialog() {
        users = new ArrayList<IUser>();
        users.add(new Author());
        users.add(new Author());
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getDialogPhoto() {
        return dialogPhoto;
    }

    @Override
    public String getDialogName() {
        return dialogName;
    }

    @Override
    public ArrayList<IUser> getUsers() {
        return users;
    }

    @Override
    public IMessage getLastMessage() {
        return lastMessage;
    }

    @Override
    public void setLastMessage(IMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public int getUnreadCount() {
        return unreadCount;
    }
}
