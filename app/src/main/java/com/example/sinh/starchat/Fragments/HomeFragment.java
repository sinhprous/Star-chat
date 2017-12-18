package com.example.sinh.starchat.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sinh.starchat.Activities.ChatActivity;
import com.example.sinh.starchat.Activities.ConversationManagerActivity;
import com.example.sinh.starchat.Activities.LoginActivity;
import com.example.sinh.starchat.Activities.NotiSoundSettingsActivity;
import com.example.sinh.starchat.Model.Dialog;
import com.example.sinh.starchat.Model.Message;
import com.example.sinh.starchat.Model.User;
import com.example.sinh.starchat.R;
import com.example.sinh.starchat.Utils.CircleTransform;
import com.example.sinh.starchat.Utils.PathUtil;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.commons.models.IDialog;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.net.URISyntaxException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

/**
 * Created by ADMIN on 12/2/2017.
 */

public class HomeFragment extends Fragment {
    final int GALLERY_REQUEST = 1;

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
        Log.d("create view", "aaaaa");
        switch (getArguments().getInt(ARG_OBJECT)) {
            case 0: {
                View v = inflater.inflate(R.layout.fragment_home, container, false);
                FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ConversationManagerActivity.class);
                        startActivity(intent);
                    }
                });

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
                View v = inflater.inflate(R.layout.fragment_friendlist, container, false);

                final SearchView searchView = (SearchView) v.findViewById(R.id.search_view);
                recyclerView = (RecyclerView) v.findViewById(R.id.friend_list);

                ArrayList<User> data = new ArrayList<>();
                // TODO: load friend list
                data.add(new User("2", "hai", "", ""));
                data.add(new User("3", "khang", "", ""));
                data.add(new User("4", "long", "", ""));
                data.add(new User("5", "son", "", ""));

                RecyclerView.Adapter<FriendViewHolder> adapter = new FriendAdapter(getContext(), data);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getBaseContext()));

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

                return v;
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
                //data.add(new SettingItem("file:///storage/emulated/0/Download/zing_meo_larry_4-1.jpg", LoginActivity.currentUser.getEmail()));

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                String path = PathUtil.getPath(getContext(), uri);
                String baseName = path.substring(path.lastIndexOf("/") + 1);
                Log.d("avatar", path);
                // TODO: call api to update ava
                LoginActivity.currentUser.setAvatar(path);//NOTE: avatar path can not be local path
                // reload this fragment
                FragmentTransaction ft =  getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(this);
                ft.attach(this);
                ft.commit();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
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

    class FriendAdapter extends RecyclerView.Adapter<FriendViewHolder>{
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
            holder.checkBox.setVisibility(View.INVISIBLE);
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
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
            this.imageRes = imageRes;
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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_setting, parent, false);
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
                        AlertDialog.Builder builderSingle = new AlertDialog.Builder(getActivity());
                        builderSingle.setIcon(R.drawable.selector);
                        builderSingle.setTitle("Select One Option: ");

                        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.select_dialog_singlechoice);
                        arrayAdapter.add("Change avatar");
                        arrayAdapter.add("Change name");
                        arrayAdapter.add("Change password");

                        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                                        photoPickerIntent.setType("image/*");
                                        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
                                        break;
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                }
                            }
                        });
                        builderSingle.show();
                    } else if (position == 3) { //log out
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.clear();
                        editor.commit();
                        LoginActivity.currentUser = null; // remove current user
                        Intent intent = new Intent(getActivity(), LoginActivity.class);

                        getActivity().getSupportFragmentManager().beginTransaction().detach(HomeFragment.this).commit();
                        getActivity().finish();
                        startActivity(intent);
                    }
                }
            });
            return new SettingViewHolder(v);
        }

        @Override
        public void onBindViewHolder(SettingViewHolder holder, int position) {
            if (position == 0) { // user avatar
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