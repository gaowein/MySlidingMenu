package com.gaowei.myslidingmenu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.gaowei.myslidingmenu.R;
import com.gaowei.myslidingmenu.domain.DiaryItem;

import java.util.List;

/**
 * 自定义ListView适配器
 * Created by 20160609 on 2017-05-19.
 */

public class MyAdapter extends BaseAdapter {
    private Context context;
    List<DiaryItem> items = null;

    public MyAdapter(List<DiaryItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public int getCount() {
        return this.items.size();
    }

    public Object getItem(int paramInt) {
        return null;
    }

    public long getItemId(int paramInt) {
        return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup) {
        if (paramView == null) {
            LayoutInflater localLayoutInflater = (LayoutInflater)context.getSystemService("layout_inflater");
            paramView = localLayoutInflater.inflate(R.layout.list_item, paramViewGroup, false);
            localLayoutInflater.inflate(R.layout.list_item, paramViewGroup, false);
        }

        TextView paramTitle = (TextView) paramView
                .findViewById(R.id.list_diary_title);
        TextView paramDate = (TextView) paramView
                .findViewById(R.id.list_diary_date);

        DiaryItem localDiaryItems = (DiaryItem) this.items.get(paramInt);
        paramTitle.setText(localDiaryItems.getTitle());
        paramDate.setText(localDiaryItems.getDate());

        return paramView;
    }
}
