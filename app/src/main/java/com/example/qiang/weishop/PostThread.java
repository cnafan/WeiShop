package com.example.qiang.weishop;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.net.URLEncoder;

/**
 * Created by qiang on 2017/3/5.
 */
//子线程：使用POST方法向服务器发送用户名、密码等数据
class PostThread extends Thread implements Runnable {

    String name;
    String pwd;
    int type;
    String country;
    Handler handler;

    PostThread(Handler handler, int type, String name, String pwd, String country) {
        Log.d("PostThread", "type:" + type);
        this.name = URLEncoder.encode(name);
        this.pwd = URLEncoder.encode(pwd);
        this.type = (type);
        this.country = country;
        this.handler = handler;
    }

    @Override
    public void run() {
        Looper.prepare();
        Log.d("PostThread", "run");
        switch (type) {
            case 0:
                String response_signup = new PostToServer().PostToServer_signup(name, pwd, country);
                switch (response_signup) {
                    case "0":
                        Log.d("PostThread", "收到signup_handle");
                        //耗时操作，完成之后发送消息给Handler，完成UI更新；
                        handler.sendEmptyMessage(0);
                        Looper.loop();
                        break;
                    case "3":
                        Log.d("PostThread", "收到signup_handle");
                        //耗时操作，完成之后发送消息给Handler，完成UI更新；
                        handler.sendEmptyMessage(3);
                        Looper.loop();
                        break;
                }
            case 1:
                String response_login = new PostToServer().PostToServer_login(name, pwd, country);
                switch (response_login) {
                    case "0":
                        Log.d("PostThread", "收到login_handle");
                        //耗时操作，完成之后发送消息给Handler，完成UI更新；
                        handler.sendEmptyMessage(0);
                        Looper.loop();
                        break;
                    case "1":
                        Log.d("PostThread", "收到login_handle");
                        handler.sendEmptyMessage(1);
                        Looper.loop();
                        break;
                    case "2":
                        Log.d("PostThread", "收到login_handle");
                        handler.sendEmptyMessage(2);
                        Looper.loop();
                        break;

                }
                break;
        }

    }
}