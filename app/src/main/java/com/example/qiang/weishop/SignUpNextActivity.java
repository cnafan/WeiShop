package com.example.qiang.weishop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpNextActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler mhandle = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //完成主界面更新,拿到数据
                    //String data = (String)msg.obj;
                    Log.d("SignUpNextActivity", "登录成功.");
                    //Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    Intent next = new Intent(SignUpNextActivity.this, MainActivity.class);
                    next.putExtra("phone", from_signup_phone);
                    next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(next);
                    break;
                case 1:
                    Log.d("SignUpNextActivity", "密码不正确.");
                    Toast.makeText(getApplicationContext(), "密码不正确", Toast.LENGTH_SHORT).show();
                    edittextWithDel_set_password_sum.setText("");
                    break;
                case 2:
                    Log.d("SignUpNextActivity", "该用户还未注册.");
                    Toast.makeText(getApplicationContext(), "该用户还未注册", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    };

    private Button button_sign_up_main_next;
    private EdittextWithDel edittextWithDel_verification_code_sum;
    private EdittextWithDel edittextWithDel_set_password_sum;
    private TextView textView_cant_receive_code;
    private Button button_reacquire;

    private String from_signup_phone;

    void initial() {
        edittextWithDel_set_password_sum = (EdittextWithDel) findViewById(R.id.set_password_sum);
        button_sign_up_main_next = (Button) findViewById(R.id.sign_up_main_next);
        edittextWithDel_verification_code_sum = (EdittextWithDel) findViewById(R.id.verification_code_sum);
        textView_cant_receive_code = (TextView) findViewById(R.id.cant_receive_code);
        button_reacquire = (Button) findViewById(R.id.reacquire);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_next);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去掉原有标题

        initial();

        //edittextWithDel_set_password_sum.setInputType(EditorInfo.TYPE_TEXT_VARIATION_PASSWORD);
        Intent intent = getIntent();//getIntent将该项目中包含的原始intent检索出来，将检索出来的intent赋值给一个Intent类型的变量intent
        Bundle bundle = intent.getExtras();//.getExtras()得到intent所附带的额外数据
        from_signup_phone = bundle.getString("phone");//getString()返回指定key的值


        button_sign_up_main_next.setOnClickListener(this);
        button_sign_up_main_next.setClickable(false);
        textView_cant_receive_code.setClickable(true);
        textView_cant_receive_code.setOnClickListener(this);
        button_reacquire.setOnClickListener(this);

        edittextWithDel_verification_code_sum.setText(get_random_code());
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_up_main_next:
                int len_password = edittextWithDel_set_password_sum.getText().length();
                if (len_password > 5 && len_password < 16) {

                    //使用POST方法向服务器发送数据
                    Log.d("SignUpNextActivity", "线程启动中");
                    PostThread postThread = new PostThread(mhandle, 0, from_signup_phone, edittextWithDel_set_password_sum.getText().toString(), "ooo");
                    postThread.start();
                } else {
                    Toast.makeText(this, "请输入6-16位密码", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.reacquire:
                edittextWithDel_verification_code_sum.setText(get_random_code());

        }
    }

    public String get_random_code() {
        String random_code = "";
        for (int i = 0; i < 6; i++) {
            random_code += String.valueOf((int) (Math.random() * 10));
        }
        return random_code;
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
