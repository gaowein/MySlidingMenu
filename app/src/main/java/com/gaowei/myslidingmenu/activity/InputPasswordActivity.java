package com.gaowei.myslidingmenu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

public class InputPasswordActivity extends AppCompatActivity {

    private Button mBtnPwdOk;
    private Button mBtnCancel;
    private EditText mPassword;
    private String pwd;
    private static boolean mBackKeyPressed = false;//记录是否有首次按键

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        MyApplication.getInstance().addActivity(this);

        mPassword = (EditText) findViewById(R.id.et_pwd); // 填写密码文本框
        mBtnPwdOk = (Button) findViewById(R.id.btn_pwd_ok);// 确认密码
        mBtnCancel = (Button) findViewById(R.id.btn_pwd_cancel);// 取消
        pwd = MD5Util.JM(MD5Util.KL(Util.loadData(this)));// 解密

        //判断读取的密码是否为空
        if (!TextUtils.isEmpty(pwd)) {
            pwdBtnOnclick();
        } else {
            //跳转到SetPasswordActivity，处理相关操作（设置密码）
            Intent intent = new Intent();
            intent.setClass(InputPasswordActivity.this, SetPasswordActivity.class);
            startActivity(intent);
            finish();
        }
    }

    // 输入密码
    private void pwdBtnOnclick() {

        mBtnPwdOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = mPassword.getText().toString();
                //对输入的密码进行MD5加密后去匹配正确的密码
                String passwd = MD5Util.MD5(password);

                if (passwd.equals(pwd)) {
                    Intent intent = new Intent();
                    intent.setClass(InputPasswordActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 弹出提示，密码错误
                    Toast.makeText(InputPasswordActivity.this, "密码错误！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //用户点击了取消，退出
        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
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
