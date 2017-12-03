package com.example.sinh.starchat.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sinh.starchat.IModel.DefaultDialog;
import com.example.sinh.starchat.R;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

/**
 * Created by ADMIN on 12/2/2017.
 */

public class HomeFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    public static HomeFragment newInstance(int position) {
        HomeFragment frag = new HomeFragment();
        Bundle args=new Bundle();
        args.putInt(ARG_OBJECT, position);
        frag.setArguments(args);
        return(frag);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        switch (getArguments().getInt(ARG_OBJECT)) {
            case 0: {
                View v = inflater.inflate(R.layout.fragment_home, container, false);
                FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);

                DialogsListAdapter dialogsListAdapter = new DialogsListAdapter<>(new ImageLoader() {
                    @Override
                    public void loadImage(ImageView imageView, String url) {
                        //If you using another library - write here your way to load image
                        Picasso.with(getActivity().getBaseContext()).load(url).into(imageView);
                    }
                });

                DialogsList dialogsListView = (DialogsList) v.findViewById(R.id.dialog_list);
                dialogsListView.setAdapter(dialogsListAdapter);

                dialogsListAdapter.addItem(new DefaultDialog());

                return v;
            }
            case 1: {
                // do logic here
                break;}
            case 2: {
                // do logic here
                break;}
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}