package com.example.sinh.starchat.Activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.sinh.starchat.R;
import com.stfalcon.chatkit.commons.models.IMessage;
import com.stfalcon.chatkit.commons.models.IUser;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.Date;

public class ChatActivity extends AppCompatActivity {

    MessagesList messagesList;
    MessageInput inputView;
    MessagesListAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        messagesList = (MessagesList) findViewById(R.id.messagesList);
        inputView = (MessageInput) findViewById(R.id.input);

        adapter = new MessagesListAdapter<>("0", null);
        messagesList.setAdapter(adapter);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                // author 0 is me
                adapter.addToStart(new Message(input.toString()), true);
                return true;
            }
        });
    }

}

class Message implements IMessage {
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


class Author implements IUser {

   /*...*/

    String id, name, avatar;
    static int i = 0;

    @Override
    public String getId() {
        return ""+(i++)%2;
    }

    @Override
    public String getName() {
        return "name";
    }

    @Override
    public String getAvatar() {
        return null;
    }
}