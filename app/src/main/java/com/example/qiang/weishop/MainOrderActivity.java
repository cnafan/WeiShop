package com.example.qiang.weishop;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainOrderActivity extends AppCompatActivity implements View.OnClickListener {

    private FragmentProcessing fragmentProcessing;
    private FragmentCompleted fragmentCompleted;
    private FragmentClosed fragmentClosed;
    private FragmentManager manager;

    private Button button_processing;
    private Button button_closed;
    private Button button_completed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_order);

        initial();
    }

    void initial() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去掉原有标题
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.order_search);
        linearLayout.setBackgroundColor(getResources().getColor(R.color.order_search));

        fragmentClosed = new FragmentClosed();
        fragmentProcessing = new FragmentProcessing();
        fragmentCompleted = new FragmentCompleted();

        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.processing_fragment, fragmentProcessing).commit();
        manager.beginTransaction().replace(R.id.closed_fragment, fragmentClosed).commit();
        manager.beginTransaction().replace(R.id.completed_fragment, fragmentCompleted).commit();

        button_processing=(Button) findViewById(R.id.button_processing);
        button_closed=(Button) findViewById(R.id.button_closed);
        button_completed = (Button) findViewById(R.id.button_completed);
        button_closed.setOnClickListener(this);
        button_completed.setOnClickListener(this);
        button_processing.setOnClickListener(this);
    }


    public void order_search_onclick(View view) {


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
            case R.id.button_processing:
                manager.beginTransaction().hide(fragmentClosed).hide(fragmentCompleted).show(fragmentProcessing).commit();
                break;
            case R.id.button_completed:
                manager.beginTransaction().hide(fragmentClosed).hide(fragmentProcessing).show(fragmentCompleted).commit();
                break;
            case R.id.button_closed:
                manager.beginTransaction().hide(fragmentProcessing).hide(fragmentCompleted).show(fragmentClosed).commit();
                break;
        }
    }
}
