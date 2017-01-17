package com.example.qiang.weishop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ForgotPwdActivity extends AppCompatActivity implements View.OnClickListener {


    private Button local;
    private EditText phone_sum;
    private Button forgotpwd_main;

    void initial() {
        local = (Button) findViewById(R.id.local_sum);
        phone_sum = (EditText) findViewById(R.id.phone_sum);
        forgotpwd_main = (Button) findViewById(R.id.log_in_main);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pwd);

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
                    forgotpwd_main.setBackgroundResource(R.drawable.shape_dark);
                    forgotpwd_main.setTextColor((Color.parseColor("#FFFFFF")));
                } else {

                    forgotpwd_main.setBackgroundResource(R.drawable.shape_light);
                    forgotpwd_main.setTextColor((Color.parseColor("#F6D8DB")));
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
                startActivityForResult(new Intent(ForgotPwdActivity.this, CountryActivity.class), 0);
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
}
