package com.example.qiang.weishop;

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

public class LoginMainActivity extends AppCompatActivity implements View.OnClickListener {

    String TAG = "qiang";
    private Button local;
    private EditText phone_sum;
    private EditText password_sum;
    private Button log_in_main;


    void initial() {
        local = (Button) findViewById(R.id.local_sum);

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
                int  len_phone= phone_sum.getText().length();
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
                Snackbar.make(v, "lll", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }


}
