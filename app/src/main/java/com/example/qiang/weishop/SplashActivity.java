package com.example.qiang.weishop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class SplashActivity extends AppCompatActivity {

    static String TAG = "qiang";

    public SharedPreferences pref_default;
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;

    private TextView version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        version = (TextView) findViewById(R.id.version);
        version.setText(getAppVersion());

        //3s后，执行run方法启动主界面Activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pref_default = getDefaultSharedPreferences(SplashActivity.this);
                editor = getSharedPreferences("default", MODE_PRIVATE).edit();
                pref = getSharedPreferences("default", MODE_PRIVATE);

                boolean isfirstrun = pref.getBoolean("isfirstrun", true);
                Log.d(TAG,"isfirst:"+isfirstrun);
                if (isfirstrun) {
                    editor.putBoolean("isfirstrun", false);
                    editor.commit();
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
                //启动主Activity后销毁自身
                finish();
            }
        }, 2000);


    }

    public String getAppVersion() {
        PackageManager pm = getPackageManager();
        PackageInfo info = null;
        try {
            info = pm.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "version:" + info.versionName);
        return info.versionName;
    }
}
