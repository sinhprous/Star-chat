package com.example.sinh.starchat.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sinh.starchat.Activities.ChatActivity;
import com.example.sinh.starchat.Activities.LoginActivity;
import com.example.sinh.starchat.Activities.NotiSoundSettingsActivity;
import com.example.sinh.starchat.Model.Dialog;
import com.example.sinh.starchat.Model.Message;
import com.example.sinh.starchat.Model.User;
import com.example.sinh.starchat.R;
import com.example.sinh.starchat.Utils.CircleTransform;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;

/**
 * Created by ADMIN on 12/2/2017.
 */

public class HomeFragment extends Fragment {
    public static final String ARG_OBJECT = "object";

    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;

    public static HomeFragment newInstance(int position) {
        HomeFragment frag = new HomeFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_OBJECT, position);
        frag.setArguments(args);
        return (frag);
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        switch (getArguments().getInt(ARG_OBJECT)) {
            case 0: {
                View v = inflater.inflate(R.layout.fragment_home, container, false);
                FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
                // TODO: add conversation

                DialogsListAdapter dialogsListAdapter = new DialogsListAdapter<>(new ImageLoader() {
                    @Override
                    public void loadImage(ImageView imageView, String url) {
                        //If you using another library - write here your way to load image
                        Picasso.with(getActivity().getBaseContext()).load(url).into(imageView);
                    }
                });

                DialogsList dialogsListView = (DialogsList) v.findViewById(R.id.dialog_list);
                dialogsListView.setAdapter(dialogsListAdapter);

                dialogsListAdapter.setOnDialogClickListener(new DialogsListAdapter.OnDialogClickListener() {
                    @Override
                    public void onDialogClick(IDialog dialog) {
                        Intent intent = new Intent(getActivity().getBaseContext(), ChatActivity.class);
                        intent.putExtra("Conversation name", dialog.getDialogName());
                        startActivity(intent);
                    }
                });

                ArrayList<User> users = new ArrayList<>();
                users.add(new User("1", "sinh", "", ""));
                users.add(new User("2", "hai", "", ""));
                // TODO: load conversations (maybe register recevier to update when new message come, change name)
                dialogsListAdapter.addItem(new Dialog("123", null, "project", users, new Message("12454", "may gio hop?", users.get(0), System.currentTimeMillis()), 23));

                return v;
            }
            case 1: {
                // do logic here
                // TODO: load friend list
                break;
            }
            case 2: {
                // do logic here
                // TODO: modify personal info option
                View v = inflater.inflate(R.layout.fragment_setting, container, false);
                recyclerView = (RecyclerView) v.findViewById(R.id.setting_choices);

                ArrayList<SettingItem> data = new ArrayList<>();
                sharedPreferences = getActivity().getSharedPreferences(LoginActivity.CURRENT_USER_PREF, Context.MODE_PRIVATE);

                // add settings item
                data.add(new SettingItem(LoginActivity.currentUser.getAvatar(), LoginActivity.currentUser.getEmail()));

                data.add(new SettingItem(R.drawable.bell, "Notification and sound"));

                data.add(new SettingItem(R.drawable.person_icon, "Update personal information"));

                data.add(new SettingItem(R.drawable.logout, "Log out"));

                RecyclerView.Adapter<SettingViewHolder> adapter = new SettingAdapter(getContext(), data);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

                return v;
            }
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    class SettingViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        SettingViewHolder(View v) {
            super(v);
            imageView = (ImageView) v.findViewById(R.id.setting_card_image);
            textView = (TextView) v.findViewById(R.id.setting_card_detail);
        }
    }

    class SettingItem {
        String imageUrl;
        int imageRes;
        String detail;

        SettingItem(String imageUrl, String detail) {
            this.imageUrl = imageUrl;
            this.detail = detail;
        }

        SettingItem(int imageRes, String detail) {
            this.imageRes= imageRes;
            this.detail = detail;
        }
    }

    class SettingAdapter extends RecyclerView.Adapter<SettingViewHolder> {
        Context context;
        ArrayList<SettingItem> data;

        SettingAdapter(Context context, ArrayList<SettingItem> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public SettingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.setting_item, parent, false);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = recyclerView.indexOfChild(v);
                    if (position == 1) { // detail
                        Intent i = new Intent(getActivity(), NotiSoundSettingsActivity.class);
                        i.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, NotiSoundSettingsActivity.NotificationPreferenceFragment.class.getName());
                        i.putExtra(PreferenceActivity.EXTRA_NO_HEADERS, true);
                        startActivity(i);
                    } else if (position == 2) { // update info
                        // TODO
                    } else if (position == 3) { //log out
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        LoginActivity.currentUser = null; // remove current user
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });
            return new SettingViewHolder(v);
        }

        @Override
        public void onBindViewHolder(SettingViewHolder holder, int position) {
            if (position==0) { // user avatar
                Picasso.with(context).load(data.get(position).imageUrl).transform(new CircleTransform()).into(holder.imageView);

                holder.textView.setText(data.get(position).detail);
                holder.textView.setTypeface(holder.textView.getTypeface(), Typeface.BOLD);
            } else {  // settings icon
                Picasso.with(context).load(data.get(position).imageRes).transform(new CircleTransform()).into(holder.imageView);
                holder.imageView.setColorFilter(
                        new PorterDuffColorFilter(holder.imageView.getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_IN));
                holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                holder.imageView.setScaleX(0.5f);
                holder.imageView.setScaleY(0.5f);

                holder.textView.setText(data.get(position).detail);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}