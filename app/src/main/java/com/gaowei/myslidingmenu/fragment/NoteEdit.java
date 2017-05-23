package com.gaowei.myslidingmenu.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaowei.myslidingmenu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteEdit extends BaseFragment {

    public final static String SER_KEY = "com.xiaoming.slience.ser";
    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.music_main, container, false);
    }
}
