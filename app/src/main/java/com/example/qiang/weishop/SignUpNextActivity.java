package com.example.qiang.weishop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUpNextActivity extends AppCompatActivity implements View.OnClickListener {


    private Button button_sign_up_main_next;
    private EdittextWithDel edittextWithDel_verification_code_sum;
    private EdittextWithDel edittextWithDel_set_password_sum;
    private TextView textView_cant_receive_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_next);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去掉原有标题

        initial();
        button_sign_up_main_next.setClickable(false);
        textView_cant_receive_code.setClickable(true);
        textView_cant_receive_code.setOnClickListener(this);

        edittextWithDel_verification_code_sum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len_code = s.length();
                int len_pwd = edittextWithDel_set_password_sum.getText().length();
                if (len_pwd > 0 && len_code > 0) {
                    button_sign_up_main_next.setClickable(true);
                    button_sign_up_main_next.setBackgroundResource(R.drawable.shape_dark);
                    button_sign_up_main_next.setTextColor((Color.parseColor("#FFFFFF")));
                } else {
                    button_sign_up_main_next.setClickable(false);
                    button_sign_up_main_next.setBackgroundResource(R.drawable.shape_light);
                    button_sign_up_main_next.setTextColor((Color.parseColor("#F6D8DB")));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edittextWithDel_set_password_sum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len_pwd = s.length();
                int len_code = edittextWithDel_verification_code_sum.getText().length();
                if (len_pwd > 0 && len_code > 0) {
                    button_sign_up_main_next.setClickable(true);
                    button_sign_up_main_next.setBackgroundResource(R.drawable.shape_dark);
                    button_sign_up_main_next.setTextColor((Color.parseColor("#FFFFFF")));
                } else {
                    button_sign_up_main_next.setClickable(false);
                    button_sign_up_main_next.setBackgroundResource(R.drawable.shape_light);
                    button_sign_up_main_next.setTextColor((Color.parseColor("#F6D8DB")));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void initial() {
        edittextWithDel_set_password_sum = (EdittextWithDel) findViewById(R.id.set_password_sum);
        button_sign_up_main_next = (Button) findViewById(R.id.sign_up_main_next);
        edittextWithDel_verification_code_sum = (EdittextWithDel) findViewById(R.id.verification_code_sum);
        textView_cant_receive_code = (TextView) findViewById(R.id.cant_receive_code);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_main_next:
                //使用POST方法向服务器发送数据
                PostThread postThread = new PostThread(edittextWithDel_verification_code_sum.getText().toString(), edittextWithDel_set_password_sum.getText().toString());
                postThread.start();
        }
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
