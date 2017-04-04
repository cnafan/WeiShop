package com.example.qiang.weishop;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;

public class MainNoteEditNoteActivity extends AppCompatActivity {
    SQLiteDatabase db;
    private EditText editText_edit_title;
    private EditText editText_edit_content;

    String note_title;
    String note_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_note_edit_note);

        editText_edit_title = (EditText) findViewById(R.id.edit_note_title);
        editText_edit_content = (EditText) findViewById(R.id.edit_note_content);

        String dirPath = "/data/data/" + getPackageName() + "/databases/";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        db = SQLiteDatabase.openOrCreateDatabase(dirPath + "note.db", null);

        Intent intent = getIntent();
        note_title = intent.getStringExtra("title");
        Log.i("MainNoteEditNote","note_title:"+note_title);
        note_content = intent.getStringExtra("content");
        Log.i("MainNoteEditNote","note_content:"+note_content);
        editText_edit_title.setText(note_title);
        editText_edit_content.setText(note_content);
    }

    public void edit_complete_click(View view) {
        String title = editText_edit_title.getText().toString();
        Log.i("MainNoteEditNote","title:"+title);
        String content = editText_edit_content.getText().toString();
        if (title.length() > 0) {
            String note_table = "UPDATE notetable SET title = '" + title + "' , content = '" + content + "' WHERE title = '" + note_title + "'";

            Log.i("MainNoteEditNote","sql:"+note_table);
            //执行SQL语句
            db.execSQL(note_table);
            setResult(0);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"标题不能为空",Toast.LENGTH_SHORT).show();
        }
    }

    public void edit_delete_note_click(View view) {
        String note_table = "DELETE FROM notetable WHERE title = '" + note_title + "' and content = '" + note_content + "'";
        //执行SQL语句
        db.execSQL(note_table);
        setResult(0);
        finish();
    }
}
