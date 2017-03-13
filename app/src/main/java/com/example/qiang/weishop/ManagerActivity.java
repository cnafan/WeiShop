package com.example.qiang.weishop;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class ManagerActivity extends AppCompatActivity {

    public SharedPreferences pref_default;
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;

    private TextView textView_manager_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去掉原有标题


        initial();

        textView_manager_user_id.setText(pref.getString("user_id", ""));
    }

    void initial(){

        pref_default = getDefaultSharedPreferences(this);
        editor = getSharedPreferences("default", MODE_PRIVATE).edit();
        pref = getSharedPreferences("default", MODE_PRIVATE);

        textView_manager_user_id=(TextView)findViewById(R.id.manager_user_id);
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

}
