package com.gaowei.myslidingmenu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gaowei.myslidingmenu.R;
import com.gaowei.myslidingmenu.fragment.BaseFragment;
import com.gaowei.myslidingmenu.fragment.MusicFragment;
import com.gaowei.myslidingmenu.fragment.NoteEdit;
import com.gaowei.myslidingmenu.fragment.NoteList;
import com.gaowei.myslidingmenu.fragment.PhotosFragment;
import com.gaowei.myslidingmenu.fragment.SettingFragment;
import com.gaowei.myslidingmenu.fragment.ShareFragment;
import com.gaowei.myslidingmenu.util.MyApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        private FrameLayout fl_content;
        private List<BaseFragment> mFragments;
        private Toolbar mToolbar;
        private TextView toolbarTitle;
        private static boolean mBackKeyPressed = false;//记录是否有首次按键
        private DrawerLayout drawer;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            MyApplication.getInstance().addActivity(this);

            initView();
            initData();
        }

    private void initView() {
        fl_content = (FrameLayout) findViewById(R.id.fl_content);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        Intent intert = getIntent();
        CharSequence title;
        int position = intert.getIntExtra("position",0);
        switch (position) {
            case 0:
                title = "我的日记";
                break;
            case 1:
                title = "知乎豆瓣";
                break;
            case 2:
                title = "精品美图";
                break;
            case 3:
                title = "流行音乐";
                break;
            case 4:
                title = "我的日记";
                break;
            case 5:
                title = "设置";
                break;
            default:
                title = "分享";
                break;
        }
        setCurrentFragment(position,title,true);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false); //标题栏返回键是否显示
            actionBar.setDisplayShowTitleEnabled(false); //标题栏标题是否显示
        }
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





    }

    private void initData() {
        mFragments = new ArrayList<>();

        mFragments.add(new NoteList());
        mFragments.add(new NoteEdit());
        mFragments.add(new PhotosFragment());
        mFragments.add(new MusicFragment());
        mFragments.add(new SettingFragment());
        mFragments.add(new ShareFragment());

    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if(!mBackKeyPressed){
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mBackKeyPressed = true;
                new Timer().schedule(new TimerTask() {//延时两秒，如果超出则擦错第一次按键记录
                    @Override
                    public void run() {
                        mBackKeyPressed = false;
                    }
                }, 2000);
            }
            else{//退出程序
                // 关闭所有activity
                MyApplication.getInstance().exit();
                int id = android.os.Process.myPid();
                if (id != 0) {
                    android.os.Process.killProcess(id);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_news:
                setCurrentFragment(0,item.getTitle(),false);
                break;
            case R.id.nav_dz:
                setCurrentFragment(1,item.getTitle(),false);
                break;
            case R.id.nav_photos:
                setCurrentFragment(2,item.getTitle(),false);
                break;
            case R.id.nav_music:
                setCurrentFragment(3,item.getTitle(),false);
                break;
            case R.id.nav_setting:
                setCurrentFragment(4,item.getTitle(),false);
                break;
            case R.id.nav_share:
                setCurrentFragment(5,item.getTitle(),false);
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_news:
                setCurrentFragment(0,item.getTitle(),false);
                break;
            case R.id.nav_dz:
                setCurrentFragment(1,item.getTitle(),false);
                break;
            case R.id.nav_photos:
                setCurrentFragment(2,item.getTitle(),false);
                break;
            case R.id.nav_music:
                setCurrentFragment(3,item.getTitle(),false);
                break;
            case R.id.nav_setting:
                setCurrentFragment(4,item.getTitle(),false);
                break;
            case R.id.nav_share:
                setCurrentFragment(5,item.getTitle(),false);
                break;
            default:
                break;
        }
        return true;
    }

    private void setCurrentFragment(int position,CharSequence title,boolean isInit) {
        drawer.closeDrawer(GravityCompat.START);
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction tr = fm.beginTransaction();
        if(isInit){
            tr.replace(R.id.fl_content,new NoteList());
        }else{
            tr.replace(R.id.fl_content, mFragments.get(position));
        }
        tr.commit();
        //设置toolbar标题
        toolbarTitle.setText(title);
    }
}
