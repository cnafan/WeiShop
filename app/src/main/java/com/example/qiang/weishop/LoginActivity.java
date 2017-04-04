package com.example.qiang.weishop;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    //完成主界面更新,拿到数据
                    //String data = (String)msg.obj;
                    Log.d("LoginActivity", "handle处理中.");
                    Toast.makeText(getApplicationContext(),"登录成功",Toast.LENGTH_SHORT).show();
                    Intent next=new Intent(new Intent(LoginActivity.this, MainActivity.class));
                    editor.putString("user_id",phone_sum.getText().toString());
                    editor.putInt("login_status",1);
                    editor.commit();
                    next.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(next);
                    break;
                case 1:
                    //完成主界面更新,拿到数据
                    //String data = (String)msg.obj;
                    Log.d("LoginActivity", "handle处理中.");
                    Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();
                    password_sum.setText("");
                    break;
                case 2:
                    Log.d("LoginActivity", "handle处理中.");
                    Toast.makeText(getApplicationContext(),"此账户还未注册",Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

    };
    public SharedPreferences pref_default;
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;

    private Button local;
    private EditText phone_sum;
    private EditText password_sum;
    private Button button_login_submit;
    private TextView textView_forgot;

    void initial() {
        local = (Button) findViewById(R.id.local_sum);
        textView_forgot = (TextView) findViewById(R.id.forgot_password);
        phone_sum = (EditText) findViewById(R.id.phone_sum);
        password_sum = (EditText) findViewById(R.id.password_sum);
        button_login_submit = (Button) findViewById(R.id.login_submit);

        pref_default = getDefaultSharedPreferences(this);
        editor = getSharedPreferences("default", MODE_PRIVATE).edit();
        pref = getSharedPreferences("default", MODE_PRIVATE);
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

        button_login_submit.setOnClickListener(this);
        //login_submit.setClickable(false);
        local.setOnClickListener(this);
        phone_sum.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub
                int len_phone = s.length();
                int len_password = password_sum.getText().length();
                if (len_password > 0 && len_phone > 0) {
                    button_login_submit.setClickable(true);
                    button_login_submit.setBackgroundResource(R.drawable.shape_dark);
                    button_login_submit.setTextColor((Color.parseColor("#FFFFFF")));
                } else {

                    button_login_submit.setClickable(false);
                    button_login_submit.setBackgroundResource(R.drawable.shape_light);
                    button_login_submit.setTextColor((Color.parseColor("#F6D8DB")));
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

                    button_login_submit.setClickable(true);
                    button_login_submit.setBackgroundResource(R.drawable.shape_dark);
                    button_login_submit.setTextColor((Color.parseColor("#FFFFFF")));
                } else {

                    button_login_submit.setClickable(false);
                    button_login_submit.setBackgroundResource(R.drawable.shape_light);
                    button_login_submit.setTextColor((Color.parseColor("#F6D8DB")));
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
        textView_forgot.setOnClickListener(this);

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
                startActivityForResult(new Intent(LoginActivity.this, CountryActivity.class), 0);
                break;
            case R.id.forgot_password:
                Log.d("qiang","dsfaksf");
                startActivity(new Intent(LoginActivity.this, ForgotPwdActivity.class));
                break;
            case R.id.login_submit:
                //Snackbar.make(v,"qiang",Snackbar.LENGTH_SHORT).show();
                Log.d("LoginActivity", "开始处理了");
                PostThread postThreat= new PostThread(mHandler, 1, phone_sum.getText().toString(), password_sum.getText().toString(), local.getText().toString());
                postThreat.start();
                break;
            default:
                Log.d("LoginActivity", "click");
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
