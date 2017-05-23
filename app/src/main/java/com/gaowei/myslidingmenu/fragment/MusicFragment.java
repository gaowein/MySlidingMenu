package com.gaowei.myslidingmenu.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gaowei.myslidingmenu.R;

public class MusicFragment extends BaseFragment {
    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.music_main, container, false);
    }
}
