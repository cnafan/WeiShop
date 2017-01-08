package com.example.qiang.weishop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public Button test;

    public SharedPreferences pref_default;
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();

        pref_default = getDefaultSharedPreferences(this);
        editor = getSharedPreferences("default", MODE_PRIVATE).edit();
        pref = getSharedPreferences("default", MODE_PRIVATE);

        test.setOnClickListener(this);
    }

    void initial() {
        test = (Button) findViewById(R.id.button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            default:
                break;
        }
    }
}

