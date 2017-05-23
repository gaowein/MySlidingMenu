package com.gaowei.myslidingmenu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gaowei.myslidingmenu.R;
import com.gaowei.myslidingmenu.domain.DiaryItem;
import com.gaowei.myslidingmenu.fragment.NoteEdit;
import com.gaowei.myslidingmenu.util.DbUtil;
import com.gaowei.myslidingmenu.util.MyApplication;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {

    private EditText mTextTitle;
    private EditText mTextContent;

    private TextView mTextDate;
    private TextView mTextWeek;
    private TextView mTVTitle;

    private String flag;
    private String mId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        MyApplication.getInstance().addActivity(this);

        initView();

        Intent intent = getIntent();
        //获取标识，0代表写新日记，1代表查看并修改选中的日记
        flag = intent.getStringExtra("flag");
        if (flag.equals("0")) {
            mTVTitle.setText("写日记");
            initDate();
        } else {
            initUpdateData();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_edit_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            //返回按钮
            case android.R.id.home:
                Intent intent = new Intent();
                intent.setClass(EditNoteActivity.this, MainActivity.class);
                intent.putExtra("position", 0);
                startActivity(intent);
                this.finish();
                return true;
            // 保存日记
            case R.id.btn_save:
                DbUtil db = new DbUtil(EditNoteActivity.this);

                String title = mTextTitle.getText().toString();
                String content = mTextContent.getText().toString();
                if (title.equals("") || content.equals("")) {
                    Toast.makeText(EditNoteActivity.this, "标题和内容都不能为空！", Toast.LENGTH_SHORT).show();
                } else {
                    if (flag.equals("0")) {
                        db.insert(title, content);
                        Toast.makeText(EditNoteActivity.this, "保存成功！", Toast.LENGTH_SHORT).show();

                    } else {
                        db.update(mId, title, content);
                        Toast.makeText(EditNoteActivity.this, "更新成功！", Toast.LENGTH_SHORT).show();

                    }

                }
        }
        return super.onOptionsItemSelected(item);
    }
    //初始化控件
    public void initView() {
        mTextTitle = (EditText) findViewById(R.id.et_title);
        mTextContent = (EditText) findViewById(R.id.et_content);
        mTextDate = (TextView) findViewById(R.id.tv_date);
        mTextWeek = (TextView) findViewById(R.id.tv_week);

        mTVTitle = (TextView) findViewById(R.id.toolbar_title);
    }
    // 加载当天日期
    private void initDate() {

        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(d);

        String week = DbUtil.getWeekOfDate(d);
        mTextDate.setText(date);
        mTextWeek.setText(week);
    }
    // 加载要更新的数据
    private void initUpdateData() {
        DiaryItem mDiary = (DiaryItem) getIntent().getSerializableExtra(
                NoteEdit.SER_KEY);
        mId = mDiary.getId();
        mTextDate.setText(mDiary.getDate());
        mTextWeek.setText(mDiary.getWeek());
        mTextTitle.setText(mDiary.getTitle());
        mTextContent.setText(mDiary.getContent());

        mTVTitle.setText("修改日记");
    }
    // 复写返回键功能
    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.setClass(EditNoteActivity.this, MainActivity.class);
        intent.putExtra("position", 0);
        startActivity(intent);
        this.finish();
    }

}
