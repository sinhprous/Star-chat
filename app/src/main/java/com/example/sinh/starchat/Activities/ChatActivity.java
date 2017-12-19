package com.example.sinh.starchat.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sinh.starchat.Model.Message;
import com.example.sinh.starchat.Model.User;
import com.example.sinh.starchat.R;
import com.example.sinh.starchat.Utils.PathUtil;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.messages.MessageHolders;
import com.stfalcon.chatkit.messages.MessageInput;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.net.URISyntaxException;

public class ChatActivity extends AppCompatActivity {

    MessagesList messagesList;
    MessageInput inputView;
    MessagesListAdapter<Message> adapter;
    MessageHolders holders;
    String senderId = LoginActivity.currentUser.getId();
    int i = 0;

    static int PICKFILE_REQUEST_CODE = 1;
    String conversationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_chat);

        conversationName = getIntent().getStringExtra("Conversation name");
        getSupportActionBar().setTitle(conversationName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        messagesList = (MessagesList) findViewById(R.id.messagesList);
        inputView = (MessageInput) findViewById(R.id.input);

        //holders = new MessageHolders().registerContentType(1, MessageHolders.IncomingImageMessageViewHolder)

        holders = new MessageHolders();
        holders.setOutcomingTextHolder(CustomOutcomingMessageViewHolder.class);
        holders.setIncomingTextHolder(CustomIncomingMessageViewHolder.class);
        adapter = new MessagesListAdapter<>(senderId, holders, new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.with(getBaseContext()).load(url).into(imageView);
            }
        });
        adapter.setOnMessageLongClickListener(new MessagesListAdapter.OnMessageLongClickListener<Message>() {
            @Override
            public void onMessageLongClick(Message message) {
                // TODO: add android dialog for options: del, del after, copy...
                // TODO: call api to delete
                if (message.getUser().getId() == LoginActivity.currentUser.getId()) // chi duoc xoa tin nhan cua minh
                    displayOptionDialog(message);
            }
        });
        adapter.setOnMessageClickListener(new MessagesListAdapter.OnMessageClickListener<Message>() {
            @Override
            public void onMessageClick(Message message) {
                if (message.getFile() != null) {
                    displayDownloadDialog(message);
                }
            }
        });

        // TODO: load last messages
        messagesList.setAdapter(adapter);
        inputView.setInputListener(new MessageInput.InputListener() {
            @Override
            public boolean onSubmit(CharSequence input) {
                //validate and send message
                // senderId is me, others is others
                // TODO: call api to send, get message id, and pass id to message object
                adapter.addToStart(new Message(i++ + "", input.toString(), LoginActivity.currentUser, System.currentTimeMillis()), true);

                // simulate the conversation
                adapter.addToStart(new Message(i++ + "", input.toString(), new User("2", "hai", "hai@gmail.com", ""), System.currentTimeMillis()), true);

                return true;
            }
        });
        inputView.setAttachmentsListener(new MessageInput.AttachmentsListener() {
            @Override
            public void onAddAttachments() {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                try {
                    startActivityForResult(
                            Intent.createChooser(intent, "Select a File to Upload"),
                            PICKFILE_REQUEST_CODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    // Potentially direct the user to the Market with a Dialog
                    Toast.makeText(getBaseContext(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICKFILE_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            // TODO: call api to upload file, remember to check size
            //("▼")
            try {
                String path = PathUtil.getPath(getBaseContext(), uri);
                //Log.d("sinh", uri.toString());
                String baseName = path.substring(path.lastIndexOf("/") + 1);
                adapter.addToStart(new Message(i++ + "", baseName, LoginActivity.currentUser, System.currentTimeMillis(), path), true);
            } catch (URISyntaxException e) {
                Toast.makeText(getBaseContext(), "Error!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }

    void displayOptionDialog(final Message message) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ChatActivity.this);
        builderSingle.setIcon(R.drawable.selector);
        builderSingle.setTitle("Select One Option: ");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ChatActivity.this, android.R.layout.select_dialog_item);
        arrayAdapter.add("Delete");
        arrayAdapter.add("Delete after 10 minute");

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
                String strName = arrayAdapter.getItem(which);
                AlertDialog.Builder builderInner = new AlertDialog.Builder(ChatActivity.this);
                builderInner.setMessage(strName);
                builderInner.setTitle("Confirm");
                builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        if (which == 0) {
                            // TODO: call API to delete message
                            adapter.delete(message);
                        }
                        dialog.dismiss();
                    }
                });
                builderInner.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builderInner.show();
            }
        });
        builderSingle.show();
    }

    void displayDownloadDialog(Message message) {
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ChatActivity.this);
        builderSingle.setIcon(R.drawable.selector);
        builderSingle.setTitle("Download file");

        builderSingle.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO: call api to download attached file
                dialog.dismiss();
            }
        });

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.chat_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_change_conversation_name: {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Change conversation name");
                //alert.setMessage("");
                final EditText input = new EditText(this);
                input.setText(conversationName);
                alert.setView(input);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String newName = input.getText().toString();
                        // TODO: call api
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                alert.show();
            }
            break;
            case R.id.action_leave_conversation: {
                // TODO: call api
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                alert.setTitle("Confirm");
                alert.setMessage("Leave the conversation?");

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // TODO: call api
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });
                alert.show();
            }
            break;
            case R.id.action_manage_participants:
                // TODO:
                Intent intent = new Intent(ChatActivity.this, ConversationManagerActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

class CustomOutcomingMessageViewHolder extends MessagesListAdapter.OutcomingMessageViewHolder<Message> {

    public CustomOutcomingMessageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);
        // TODO: trang thai da gui chua va tin nhan
        time.setText(message.getUser().getName() + " " + time.getText());
    }
}

class CustomIncomingMessageViewHolder extends MessagesListAdapter.IncomingMessageViewHolder<Message> {

    public CustomIncomingMessageViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onBind(Message message) {
        super.onBind(message);
        // ten nguoi gui den va tin nhan
        time.setText(message.getUser().getName() + " " + time.getText());
    }
}