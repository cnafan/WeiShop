package com.example.qiang.weishop;

/**
 * Created by qiang on 2017/4/3.
 */

public class NoteEntity {
    private String notetitle;
    private String notecontent;
    private String notetime;

    NoteEntity() {
        this.notetitle = "china";
        this.notecontent = "a";
        this.notetime = "2017年04月03日 11:17";
    }

    NoteEntity(String name, String count,String time) {
        this.notetitle = name;
        this.notecontent = count;
        this.notetime=time;
    }

    int getItemId() {
        return 0;
    }

    String getnotetitle() {
        return this.notetitle;
    }
    String getnotecontent() {
        return this.notecontent;
    }
    String getnotetime() {
        return this.notetime;
    }

}
