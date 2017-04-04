package com.example.qiang.weishop;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainNoteAddNewNoteActivity extends AppCompatActivity {

    private Button button_release;
    private EditText editText_title;
    private EditText editText_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_note_add_new_note);

        editText_title=(EditText)findViewById(R.id.new_note_title);
        editText_content=(EditText)findViewById(R.id.new_note_content);
    }

    public void release_click(View view) {

        String dirPath = "/data/data/" + getPackageName() + "/databases/";
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dirPath + "note.db", null);
        //创建表SQL语句
        String note_table = "CREATE TABLE IF NOT EXISTS notetable(_id integer primary key autoincrement,title text,content text,time text)";
        //执行SQL语句
        db.execSQL(note_table);
        String new_note_title=editText_title.getText().toString();
        String new_note_content=editText_content.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        String time = format.format(new Date());
        insert(db, new_note_title,new_note_content,time);
        Log.d("MainNoteAddNewNote","editText:"+new_note_title+";"+new_note_content);
        setResult(0);
        finish();
    }
    private void insert(SQLiteDatabase db, String title, String content,String time) {
        //插入数据SQL语句
        String insert_sql = "insert into notetable(title,content,time) values('" + title + "','" + content+ "','" + time  + "')";
        //执行SQL语句
        db.execSQL(insert_sql);
    }
}
