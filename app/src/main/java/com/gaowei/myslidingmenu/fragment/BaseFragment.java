package com.gaowei.myslidingmenu.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 20160609 on 2017-05-19.
 */

public abstract class BaseFragment extends Fragment {
    public Activity mActivity;

    //fragment创建时
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    //初始化Fragment
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = initView();
        return view;
    }

    //fragment所依赖的Activity的Oncreate()方法执行完毕后
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    /**
     * 初始化布局
     *
     * @return
     */
    public abstract View initView();

    /**
     * 初始化数据
     */
    public abstract void initData();



}

