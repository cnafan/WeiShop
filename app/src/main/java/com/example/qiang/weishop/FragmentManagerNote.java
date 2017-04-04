package com.example.qiang.weishop;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_CANCELED;
import static android.content.Context.MODE_PRIVATE;

public class FragmentManagerNote extends Fragment {

    public SharedPreferences pref_note;
    public SharedPreferences.Editor pref_note_editor;
    public RecyclerView recyclerView;
    public List<NoteEntity> datas;
    View view;
    private LinearLayout linearLayout_add_note;
    NoteAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_main_note_manager, null);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pref_note = getActivity().getSharedPreferences("note", MODE_PRIVATE);
        pref_note_editor = getActivity().getSharedPreferences("note", MODE_PRIVATE).edit();
        initView();

    }

    private void initData() {
        // NORMAL_ITEM :0
        datas = new ArrayList<NoteEntity>();
        String dirPath = "/data/data/" + getActivity().getPackageName() + "/databases/";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dirPath + "note.db", null);
        File dir = new File(dirPath);
        if (!dir.exists())
            dir.mkdirs();
        //创建表SQL语句
        String note_table = "CREATE TABLE IF NOT EXISTS notetable(_id integer primary key autoincrement,title text,content text,time text)";
        //执行SQL语句
        db.execSQL(note_table);
        Cursor cursor = db.query("notetable", null, null, null, null, null, null);
        List<NoteEntity> newlist = new ArrayList<>();
        if (cursor.moveToFirst()) {
//遍历游标
            //Log.d("CountryActivity", "getcount:" + cursor.getCount());
            for (int i = 0; i < cursor.getCount(); i++) {
                //Log.d("CountryActivity", "i:" + i);
                cursor.moveToPosition(i);
                //获得ID
                //int type = cursor.getInt(1);
                //获得用户名
                //Log.d("CountryActivity", "type:" + cursor.getInt(1));
                String title = cursor.getString(1);
                String content = cursor.getString(2);
                String time = cursor.getString(3);
                //Log.d("CountryActivity", "name:" + cursor.getString(2));
                //int count = cursor.getInt(2);
                datas.add(new NoteEntity(title, content, time));
            }
            for (int i=0;i<newlist.size();i++){
                Log.d("FragmentManagerNote", "newlist:" + datas.get(i).getnotetitle());
            }
        }
    }

    private void initView() {
        initData();
        linearLayout_add_note = (LinearLayout) view.findViewById(R.id.linearlayout_add_note);
        linearLayout_add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), MainNoteAddNewNoteActivity.class), 1);
            }
        });
        recyclerView = (RecyclerView) view.findViewById(R.id.note_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter(datas);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new ItemClickListener(recyclerView,
                new ItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.i("FragmentManagerNote","position:" + position);
                        Intent intent=new Intent(getActivity(),MainNoteEditNoteActivity.class);
                        intent.putExtra("title",datas.get(position).getnotetitle());
                        Log.i("FragmentManagerNote","send_title:" + datas.get(position).getnotetitle());
                        intent.putExtra("content",datas.get(position).getnotecontent());
                        Log.i("FragmentManagerNote","send_content:" + datas.get(position).getnotecontent());
                        startActivityForResult(intent,1);
                    }
                }));
    }

    private static class ItemClickListener extends RecyclerView.SimpleOnItemTouchListener {

        private OnItemClickListener clickListener;
        private GestureDetectorCompat gestureDetector;

        interface OnItemClickListener {
            void onItemClick(View view, int position);
        }

        ItemClickListener(final RecyclerView recyclerView,
                          OnItemClickListener listener) {
            this.clickListener = listener;
            gestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                    new GestureDetector.SimpleOnGestureListener() {
                        @Override
                        public boolean onSingleTapUp(MotionEvent e) {
                            View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                            //if (childView != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                            if (childView != null ) {
                                clickListener.onItemClick(childView, recyclerView.getChildAdapterPosition(childView));
                            }
                            return true;
                        }
                    });
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            gestureDetector.onTouchEvent(e);
        }
        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            gestureDetector.onTouchEvent(e);
            return false;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("FragmentManagerNote", "Code:" + requestCode + ";" + resultCode);
        if (requestCode == Activity.RESULT_FIRST_USER) {
            if (resultCode == RESULT_CANCELED) {
                initData();
                NoteAdapter newadapter = new NoteAdapter(datas);
                recyclerView.setAdapter(newadapter);
                newadapter.notifyDataSetChanged();
            }

        }
    }

    private class NoteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<NoteEntity> mDataList;

        NoteAdapter(List<NoteEntity> datas) {
            this.mDataList = datas;
        }

        /**
         * 渲染具体的ViewHolder
         *
         * @param viewGroup ViewHolder的容器
         * @param i         一个标志，我们根据该标志可以实现渲染不同类型的ViewHolder
         * @return
         */
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new NormalItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false));
        }

        /**
         * 绑定ViewHolder的数据。
         *
         * @param holder
         * @param i      数据源list的下标
         */
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
            NormalItemHolder h = (NormalItemHolder) holder;
            h.item_title.setText((datas.get(i)).getnotetitle());
            h.item_time.setText((datas.get(i)).getnotetime());

        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }


        @Override
        public long getItemId(int position) {
            return mDataList.get(position).getItemId();
        }

        /**
         * 标准
         */
        class NormalItemHolder extends RecyclerView.ViewHolder {
            TextView item_title;
            TextView item_time;

            NormalItemHolder(View itemView) {
                super(itemView);
                item_title = (TextView) itemView.findViewById(R.id.note_item_title);
                item_time = (TextView) itemView.findViewById(R.id.note_item_time);
            }
        }


    }

}