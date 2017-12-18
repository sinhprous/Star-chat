package com.example.sinh.starchat.Activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sinh.starchat.Model.User;
import com.example.sinh.starchat.R;
import com.example.sinh.starchat.Utils.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ConversationManagerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.activity_conversation_manager);

        getSupportActionBar().setTitle("Conversation");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchView = (SearchView) findViewById(R.id.search_view);
        recyclerView = (RecyclerView) findViewById(R.id.friend_list);

        ArrayList<User> data = new ArrayList<>();
        // TODO: load friend list
        data.add(new User("2", "hai", "", ""));
        data.add(new User("3", "khang", "", ""));
        data.add(new User("4", "long", "", ""));
        data.add(new User("5", "son", "", ""));

        RecyclerView.Adapter<FriendViewHolder> adapter = new FriendAdapter(getBaseContext(), data);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO: call api to search
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    class FriendViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        CheckBox checkBox;

        FriendViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.friend_card_image);
            textView = (TextView) v.findViewById(R.id.friend_card_detail);
            checkBox = (CheckBox) v.findViewById(R.id.friend_checkbox);
        }
    }

    class FriendItem {

    }

    class FriendAdapter extends RecyclerView.Adapter<FriendViewHolder> {
        Context context;
        ArrayList<User> data;

        FriendAdapter(Context context, ArrayList<User> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerView.indexOfChild(v);
                }
            });
            return new FriendViewHolder(v);
        }

        @Override
        public void onBindViewHolder(FriendViewHolder holder, int position) {
            Picasso.with(context).load(data.get(position).getAvatar()).transform(new CircleTransform()).into(holder.imageView);
            holder.textView.setText(data.get(position).getName());
            //holder.checkBox.setVisibility(View.INVISIBLE);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
