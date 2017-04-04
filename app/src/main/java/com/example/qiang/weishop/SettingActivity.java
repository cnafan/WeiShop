package com.example.qiang.weishop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class SettingActivity extends AppCompatActivity  implements View.OnClickListener{

    public SharedPreferences pref_default;
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;
    private Button button_logout;
    private Handler mhandle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    pref_default = getDefaultSharedPreferences(SettingActivity.this);
                    editor = getSharedPreferences("default", MODE_PRIVATE).edit();
                    pref = getSharedPreferences("default", MODE_PRIVATE);
                    Intent intent=new Intent(getApplication(),WelcomeActivity.class);
                    intent.addFlags(FLAG_ACTIVITY_CLEAR_TASK|FLAG_ACTIVITY_NEW_TASK);
                    //只保留一个
                    startActivity(intent);
                    editor.putInt("log_status",0);
                    editor.commit();
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去掉原有标题
        button_logout=(Button)findViewById(R.id.log_out);
        button_logout.setOnClickListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_out:
                //使用POST方法向服务器发送数据
                Log.d("SettingActivity", "线程启动中");
                pref = getSharedPreferences("default", MODE_PRIVATE);
                PostThread postThread = new PostThread(mhandle,2,pref.getString("user_id", ""),"","");
                Log.i("SettingActivity","userid:"+pref.getString("user_id", ""));
                postThread.start();

                break;
        }
    }
}
