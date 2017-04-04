package com.example.qiang.weishop;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import static com.example.qiang.weishop.R.id.fragment_manager_note;

public class MainNoteActivity extends AppCompatActivity implements View.OnClickListener{

    private FragmentManagerNote fragmentManagerNote;
    private FragmentNoteHeadline fragmentNoteHeadline;
    private FragmentManager manager;

    private TextView textViewHeadline;
    private TextView textViewManagernote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_note);

        initial();
    }

    void initial() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去掉原有标题

        textViewHeadline=(TextView)findViewById(R.id.weishop_headline);
        textViewManagernote=(TextView)findViewById(R.id.note_manager_note);
        textViewHeadline.setOnClickListener(this);
        textViewManagernote.setOnClickListener(this);

        fragmentManagerNote = new FragmentManagerNote();
        fragmentNoteHeadline = new FragmentNoteHeadline();

        manager = getSupportFragmentManager();
        manager.beginTransaction().add(R.id.fragment_headline, fragmentNoteHeadline).commit();
        manager.beginTransaction().replace(fragment_manager_note, fragmentManagerNote).commit();

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
        switch (v.getId()){
            case R.id.note_manager_note:
                manager.beginTransaction().hide(fragmentNoteHeadline).show(fragmentManagerNote).commit();
                break;
            case R.id.weishop_headline:
                manager.beginTransaction().hide(fragmentManagerNote).show(fragmentNoteHeadline).commit();
                break;
        }
    }
}
