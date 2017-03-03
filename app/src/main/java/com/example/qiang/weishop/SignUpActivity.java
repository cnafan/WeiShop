package com.example.qiang.weishop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private Button local;
    private EditText phone_sum;
    private TextView manager_guifan;
    private TextView service_xieyi;
    private Button sign_up_main;

    void initial() {
        sign_up_main = (Button) findViewById(R.id.sign_up_main);
        local = (Button) findViewById(R.id.local_sum);
        phone_sum = (EditText) findViewById(R.id.phone_sum);
        manager_guifan = (TextView) findViewById(R.id.manager_guifan);
        service_xieyi = (TextView) findViewById(R.id.service_xieyi);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去掉原有标题

        initial();

        local.setOnClickListener(this);
        phone_sum.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                int len_phone = s.length();
                if (len_phone > 0) {
                    sign_up_main.setBackgroundResource(R.drawable.shape_dark);
                    sign_up_main.setTextColor((Color.parseColor("#FFFFFF")));
                } else {
                    sign_up_main.setBackgroundResource(R.drawable.shape_light);
                    sign_up_main.setTextColor((Color.parseColor("#F6D8DB")));
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
        manager_guifan.setClickable(true);
        manager_guifan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, BaseWebViewActivity.class));
            }
        });
        service_xieyi.setClickable(true);
        service_xieyi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignUpActivity.this, BaseWebViewActivity.class));
            }
        });
        sign_up_main.setOnClickListener(this);

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
                startActivityForResult(new Intent(SignUpActivity.this, CountryActivity.class), 0);
                break;
            case R.id.sign_up:
                startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                break;
            case R.id.sign_up_main:
                //使用POST方法向服务器发送数据
                PostThread postThread = new PostThread("zhang", phone_sum.getText().toString());
                postThread.start();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Button local_sum = (Button) findViewById(R.id.local_sum);
        TextView local_title = (TextView) findViewById(R.id.local_title);
        // Log.d("LoginMainActicity","backdata:"+data.getStringExtra("name"));
        if (data != null) {
            local_sum.setText(data.getStringExtra("name"));
            local_title.setText(data.getStringExtra("count"));
        }
    }

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

            InputStream inputStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://123.206.52.70:9900/register");
                urlConnection = (HttpURLConnection) url.openConnection();

                /* for Get request */
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestProperty("Accept", "application/json");
                //set headers and method
                Writer writer = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream(), "UTF-8"));
                String JsonDATA= new JsonObject().toString();
                writer.write(JsonDATA);
                int statusCode = urlConnection.getResponseCode();
                Log.d("SignUpActivity","code:"+statusCode);
                if (statusCode == 200) {
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    Gson gson = new Gson();
                    String str=gson.fromJson(String.valueOf(inputStream), String.class);
                    Log.d("SignUpActivity","response:"+str);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }
    }


}
