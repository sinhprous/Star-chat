package com.example.sinh.starchat.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sinh.starchat.R;

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
        return inflater.inflate(R.layout.activity_chat, container, false);
    }
}
