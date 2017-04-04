package com.example.qiang.weishop;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CountryActivity extends AppCompatActivity {

    private static final int NORMAL_ITEM = 0;
    private static final int GROUP_ITEM = 1;

    public RecyclerView recyclerView;
    public List<CountryEntity> datas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去掉原有标题

        initView();

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

    private void initData() {
        // NORMAL_ITEM :0

        datas = new ArrayList<>();
        String dirPath = "/data/data/" + CountryActivity.this.getPackageName() + "/databases/";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dirPath + "country.db", null);

        Cursor cursor = db.query("countrytable", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
//遍历游标
            Log.d("CountryActivity", "getcount:" + cursor.getCount());
            for (int i = 0; i < cursor.getCount(); i++) {
                Log.d("CountryActivity", "i:" + i);
                cursor.moveToPosition(i);
//获得ID
                int type = cursor.getInt(1);
//获得用户名
                Log.d("CountryActivity", "type:" + cursor.getInt(1));
                String name = cursor.getString(2);
                Log.d("CountryActivity", "name:" + cursor.getString(2));
                int count = cursor.getInt(3);
                Log.d("CountryActivity", "count:" + cursor.getInt(3));
                datas.add(new CountryEntity(type, name, count));
            }
        }
    }


    private void initView() {
        initData();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        CountryAdapter adapter = new CountryAdapter(datas);
        recyclerView.setAdapter(adapter);
    }


    public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        List<CountryEntity> mDataList;

        CountryAdapter(List<CountryEntity> datas) {
            this.mDataList = datas;
            Log.d("CountryActivity", "datas.size=" + datas.size());
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
            if (i == NORMAL_ITEM) {
                return new NormalItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.country_withoutfirst, viewGroup, false));
            } else {
                return new GroupItemHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.country_withfirst, viewGroup, false));
            }
        }

        /**
         * 绑定ViewHolder的数据。
         *
         * @param holder
         * @param i      数据源list的下标
         */
        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int i) {
            if (holder instanceof NormalItemHolder) {
                NormalItemHolder h = (NormalItemHolder) holder;
                h.itemsum_normal.setText((datas.get(i)).getPublishDate());
                h.itemcount_normal.setText("+" + (datas.get(i)).getCountrycount());
            } else if (holder instanceof GroupItemHolder) {
                GroupItemHolder h = (GroupItemHolder) holder;
                h.itemfirst.setText((datas.get(i)).getItemFirst());
                h.itemsum_group.setText((datas.get(i)).getPublishDate());
                h.itemcount_group.setText("+" + (datas.get(i)).getCountrycount());
            }
        }

        @Override
        public int getItemCount() {
            return mDataList.size();
        }

        /**
         * 决定元素的布局使用哪种类型
         *
         * @param position 数据源List的下标
         * @return 一个int型标志，传递给onCreateViewHolder的第二个参数
         */
        @Override
        public int getItemViewType(int position) {
            //第一个都显示
            if (position == 0)
                return GROUP_ITEM;
            String currentDate = mDataList.get(position).getPublishDate();
            int prevIndex = position - 1;
            boolean isDifferent = !(checkFirst(mDataList.get(prevIndex).getPublishDate()) == (checkFirst(currentDate)));
            Log.d("CountryActivity","pre:"+mDataList.get(prevIndex).getPublishDate()+",now:"+currentDate);
            Log.d("CountryActivity","prefirst:"+(char)(checkFirst(mDataList.get(prevIndex).getPublishDate()))+",now:"+(char)(checkFirst(currentDate)));
            return isDifferent ? GROUP_ITEM : NORMAL_ITEM;
        }

        int checkFirst(String s) {
            return (int)(new ChineseCharToEn().getFirstLetter(s).charAt(0));
        }

        @Override
        public long getItemId(int position) {
            return mDataList.get(position).getItemId();
        }

        /**
         * 标准
         */
        class NormalItemHolder extends RecyclerView.ViewHolder {
            TextView itemsum_normal;
            TextView itemcount_normal;

            NormalItemHolder(View itemView) {
                super(itemView);
                itemsum_normal = (TextView) itemView.findViewById(R.id.country_item_normal_sum);
                itemcount_normal = (TextView) itemView.findViewById(R.id.country_item_normal_count);
                itemView.findViewById(R.id.country_withoutfirst).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent backintent = new Intent();
                        Log.d("CountryActivity", "putstring:" + itemsum_normal.getText());
                        backintent.putExtra("name", itemsum_normal.getText());
                        backintent.putExtra("count", itemcount_normal.getText());
                        setResult(0, backintent);
                        finish();
                    }
                });
            }
        }

        /**
         * 带首字母
         */
        class GroupItemHolder extends RecyclerView.ViewHolder {
            TextView itemfirst;
            TextView itemsum_group;
            TextView itemcount_group;

            GroupItemHolder(View itemView) {
                super(itemView);
                itemfirst = (TextView) itemView.findViewById(R.id.country_first);
                itemsum_group = (TextView) itemView.findViewById(R.id.country_item_group_sum);
                itemcount_group = (TextView) itemView.findViewById(R.id.country_item_group_count);
                itemView.findViewById(R.id.country_withfirst).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent backintent = new Intent();
                        Log.d("CountryActivity", "putstring:" + itemsum_group.getText());
                        backintent.putExtra("name", itemsum_group.getText());
                        backintent.putExtra("count",itemcount_group.getText());
                        setResult(0, backintent);
                        finish();
                    }
                });
            }
        }
    }
}
