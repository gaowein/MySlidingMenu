package com.gaowei.myslidingmenu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.gaowei.myslidingmenu.R;
import com.gaowei.myslidingmenu.util.MD5Util;
import com.gaowei.myslidingmenu.util.MyApplication;
import com.gaowei.myslidingmenu.util.Util;

import java.util.Timer;
import java.util.TimerTask;

public class SetPasswordActivity extends AppCompatActivity {

    private Button mBtnSetPwd;
    private EditText mSetPassword;
    private EditText mRepPassword;
    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        MyApplication.getInstance().addActivity(this);
        mSetPassword = (EditText) findViewById(R.id.et_set_pwd);// 设置密码文本框
        mBtnSetPwd = (Button) findViewById(R.id.btn_set_pwd_ok);// 确认设置密码
        mRepPassword = (EditText) findViewById(R.id.et_rep_pwd);

        mBtnSetPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String passwd = mSetPassword.getText().toString();
                String rePassword = mRepPassword.getText().toString();
                if (!passwd.equals(rePassword)) {
                    Toast.makeText(SetPasswordActivity.this, "两次密码不一致",
                            Toast.LENGTH_SHORT).show();
                } else {
                    // 加密
                    String setPasswd = MD5Util.MD5(passwd);

                    Util.savaData(SetPasswordActivity.this, setPasswd);
                    Intent intent = new Intent();
                    intent.setClass(SetPasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
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
