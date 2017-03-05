package com.example.qiang.weishop;

import android.util.Log;

import java.net.URLEncoder;

/**
 * Created by qiang on 2017/3/5.
 */
//子线程：使用POST方法向服务器发送用户名、密码等数据
class PostThread extends Thread {

    String name;
    String pwd;

    PostThread(String name, String pwd) {
        this.name = URLEncoder.encode(name);
        this.pwd = URLEncoder.encode(pwd);
    }

    @Override
    public void run() {
        Log.d("SignUpActivity", "sendstart");
        String response = new PostToServer().PostToServer_signup(name, pwd);
        if (response == "0") {

        }
    }
}