package com.example.qiang.weishop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class LoginMainActivity extends AppCompatActivity implements View.OnClickListener {


    public SharedPreferences pref_default;
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;

    private Button local;
    private EditText phone_sum;
    private EditText password_sum;
    private Button log_in_main;
    private TextView textView_forgot;

    void initial() {
        local = (Button) findViewById(R.id.local_sum);
        textView_forgot = (TextView) findViewById(R.id.forgot_password);
        phone_sum = (EditText) findViewById(R.id.phone_sum);
        password_sum = (EditText) findViewById(R.id.password_sum);
        log_in_main = (Button) findViewById(R.id.log_in_main);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去掉原有标题

        pref_default = getDefaultSharedPreferences(this);
        editor = getSharedPreferences("default", MODE_PRIVATE).edit();
        pref = getSharedPreferences("default", MODE_PRIVATE);

        initial();
        local.setOnClickListener(this);
        phone_sum.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                int len_phone = s.length();
                int len_password = password_sum.getText().length();
                if (len_password > 0 && len_phone > 0) {
                    log_in_main.setBackgroundResource(R.drawable.shape_dark);
                    log_in_main.setTextColor((Color.parseColor("#FFFFFF")));
                } else {

                    log_in_main.setBackgroundResource(R.drawable.shape_light);
                    log_in_main.setTextColor((Color.parseColor("#F6D8DB")));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        password_sum.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                int len_password = s.length();
                int len_phone = phone_sum.getText().length();
                if (len_password > 0 && len_phone > 0) {
                    log_in_main.setBackgroundResource(R.drawable.shape_dark);
                    log_in_main.setTextColor((Color.parseColor("#FFFFFF")));
                } else {
                    log_in_main.setBackgroundResource(R.drawable.shape_light);
                    log_in_main.setTextColor((Color.parseColor("#F6D8DB")));
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }
        });
        //忘记密码
        textView_forgot.setClickable(true);
        textView_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "d", Snackbar.LENGTH_SHORT).show();
            }
        });

        //直接保存在默认内置存储内
        //用Activity.openOrCreateDatabase实现

        if (pref.getBoolean("isfirstinitcountry", true)) {

            editor.putBoolean("isfirstinitcountry", false);
            editor.apply();
            String dirPath = "/data/data/" + this.getPackageName() + "/databases/";
            File dir = new File(dirPath);
            if (!dir.exists())
                dir.mkdirs();
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dirPath + "country.db", null);
            //创建表SQL语句
            String stu_table = "CREATE TABLE IF NOT EXISTS countrytable(_id integer primary key autoincrement,type INTEGER,name text,count INTEGER)";
            //执行SQL语句
            db.execSQL(stu_table);

            insert(db, 1, "阿尔及利亚", 1322);
            insert(db, 0, "阿尔及利亚", 1322);
            insert(db, 0, "阿尔及利亚", 1322);
            insert(db, 0, "阿尔及利亚", 1322);
            insert(db, 0, "阿尔及利亚", 1322);
            insert(db, 0, "阿尔及利亚", 1322);
            insert(db, 0, "阿尔及利亚", 1322);
            insert(db, 0, "阿尔及利亚", 1322);
            insert(db, 0, "阿尔及利亚", 1322);
            insert(db, 1, "中国", 32424);

        }
    }

    private void insert(SQLiteDatabase db, int type, String name, int count) {
        //插入数据SQL语句
        String insert_sql = "insert into countrytable(type,name,count) values(" + type + ",'" + name + "'," + count + ")";
        //执行SQL语句
        db.execSQL(insert_sql);
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
        switch (v.getId()) {
            case R.id.local_sum:
                startActivityForResult(new Intent(LoginMainActivity.this, CountryActivity.class),0);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Button local_sum=(Button)findViewById(R.id.local_sum);
        TextView local_title=(TextView)findViewById(R.id.local_title);
       // Log.d("LoginMainActicity","backdata:"+data.getStringExtra("name"));
        local_sum.setText(data.getStringExtra("name"));
        local_title.setText(data.getStringExtra("count"));
    }

}
