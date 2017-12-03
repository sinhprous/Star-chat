package com.example.sinh.starchat.IModel;

import com.example.sinh.starchat.IModel.Author;
import com.stfalcon.chatkit.commons.models.IMessage;

import java.util.Date;

/**
 * Created by ADMIN on 12/3/2017.
 */

public class Message implements IMessage {
    /*...*/
    String id, text;
    Author author;
    Date createdAt;

    public Message(String text) {
        this.text = text;
    }

    @Override
    public String getId() {
        return "1";
    }

    @Override
    public String getText() {
        return getUser().getId()+":"+text;
    }

    @Override
    public Author getUser() {
        return new Author();
    }

    @Override
    public Date getCreatedAt() {
        return new Date();
    }
}
