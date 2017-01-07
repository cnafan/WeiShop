package com.example.qiang.weishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    public Button test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initial();

        test.setOnClickListener(this);
    }

    void initial(){
        test=(Button)findViewById(R.id.button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                Snackbar.make(v,"",Snackbar.LENGTH_SHORT).show();
                break;

        }
    }
}

