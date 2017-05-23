package com.gaowei.myslidingmenu.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gaowei.myslidingmenu.R;
import com.gaowei.myslidingmenu.activity.EditNoteActivity;
import com.gaowei.myslidingmenu.adapter.MyAdapter;
import com.gaowei.myslidingmenu.domain.DiaryItem;
import com.gaowei.myslidingmenu.util.DbUtil;

import java.util.ArrayList;

public class NoteList extends BaseFragment {

    private RelativeLayout mLayoutList;
    private ListView mListView;
    private ImageButton mBtnAdd;

    ArrayList<DiaryItem> items;

    MyAdapter myAdapter;
    DbUtil util;

    private String flag; // 标识是更新还是添加
    public final static String SER_KEY = "com.xiaoming.slience.ser";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        // 我的日记界面
        mLayoutList = (RelativeLayout) view.findViewById(R.id.layout_list);
        mListView = (ListView) view.findViewById(R.id.listView);
        mBtnAdd = (ImageButton) view.findViewById(R.id.add_diary);
        initData();

        // 添加日记
        addBtnOnClick();
        return view ;
    }

    @Override
    public View initView() {
        return null;
    }

    @Override
    public void initData() {
        util = new DbUtil(getActivity());

        items = util.getAllData();

        myAdapter = new MyAdapter(items, getActivity());

        mListView.setAdapter(myAdapter);
        // 点击查看详细内容
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long line) {
                String id = items.get(position).getId();
                String date = items.get(position).getDate();
                String week = items.get(position).getWeek();
                String title = items.get(position).getTitle();
                String content = items.get(position).getContent();
                // Log.i("my", id+":"+title+":"+content);
                flag = "1";

                DiaryItem mDiary = new DiaryItem();
                mDiary.setId(id);
                mDiary.setDate(date);
                mDiary.setWeek(week);
                mDiary.setTitle(title);
                mDiary.setContent(content);

                Intent intent = new Intent(getActivity(),
                        EditNoteActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable(SER_KEY, mDiary);
                intent.putExtras(mBundle);
                intent.putExtra("flag", flag);
                startActivity(intent);
            }
        });
        // 长按弹出删除提示
        mListView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu arg0, View arg1, ContextMenu.ContextMenuInfo arg2) {
                arg0.setHeaderTitle("是否删除");
                arg0.add(0, 0, 0, "删除");
                arg0.add(0, 1, 0, "取消");

            }
        });
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // item.getItemId()：点击了菜单栏里面的第几个项目");
        if (item.getItemId() == 0) {
            int selectedPosition = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo()).position;// 获取点击了第几行

            String id = items.get(selectedPosition).getId();
            // 删除日记
            util.delete(id);
            items.remove(items.get(selectedPosition));
            myAdapter.notifyDataSetChanged();

        }

        return super.onContextItemSelected(item);
    }
    // 跳转至添加新日记
    private void addBtnOnClick() {
        mBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "0";
                Intent intent = new Intent();
                intent.setClass(getActivity(),EditNoteActivity.class);
                intent.putExtra("flag", flag);
                startActivity(intent);
            }
        });
    }
}
