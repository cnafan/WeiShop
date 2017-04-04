package com.example.qiang.weishop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    static String TAG = "qiang";

    private ViewPager viewPager;
    private ArrayList<View> pageview;

    //包裹点点的LinearLayout
    private ViewGroup group;
    private ImageView dotView;
    private ImageView[] dotViews;

    private Button button_login;
    private Button button_signup;
    private Button button_wechat;

    int login_items[] = {R.layout.login_item1, R.layout.login_item2, R.layout.login_item3};

    public SharedPreferences pref_default;
    public SharedPreferences.Editor editor;
    public SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Log.d(TAG, "pa");

        pref = getSharedPreferences("data", MODE_PRIVATE);
        pref_default = getDefaultSharedPreferences(this);
        editor = getSharedPreferences("data", MODE_PRIVATE).edit();

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        //查找布局文件用LayoutInflater.inflate
        LayoutInflater inflater = getLayoutInflater();
        pageview = new ArrayList<>();
        for (int i = 0; i < login_items.length; i++) {
            //将view装入数组
            pageview.add(inflater.inflate(login_items[i], null));

        }
        group = (ViewGroup) findViewById(R.id.viewgroup);
        dotViews = new ImageView[pageview.size()];
        for (int i = 0; i < pageview.size(); i++) {
            dotView = new ImageView(WelcomeActivity.this);
            dotView.setLayoutParams(new ViewGroup.LayoutParams(55, 25));
            dotView.setPadding(0, 0, 0, 0);
            dotViews[i] = dotView;

            // 默认进入程序后第一张图片被选中;
            if (i == 0) {
                dotViews[i].setBackgroundResource(R.drawable.page_indicator_focused_fa);
            } else {
                dotViews[i].setBackgroundResource(R.drawable.page_indicator_unfocused_fa);
            }
            group.addView(dotViews[i]);
        }

        button_login = (Button) findViewById(R.id.log_in);
        button_login.setOnClickListener(this);
        button_signup = (Button) findViewById(R.id.sign_up);
        button_signup.setOnClickListener(this);
        button_wechat = (Button) findViewById(R.id.wechat_login);
        button_wechat.setOnClickListener(this);

        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return pageview.size();
            }

            @Override
            //断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            //是从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(pageview.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1) {
                ((ViewPager) arg0).addView(pageview.get(arg1));
                return pageview.get(arg1);
            }


        };
        //  dotview = (ViewGroup) inflater.inflate(R.layout.login_dot, null);
        // group是R.layou.main中的负责包裹小圆点的LinearLayout.

        //绑定适配器
        viewPager.setAdapter(mPagerAdapter);
        //设置监听
        viewPager.setOnPageChangeListener(new MyListener());
        //绑定监听事件


        //直接保存在默认内置存储内
        //用Activity.openOrCreateDatabase实现

        if (pref.getBoolean("isfirstinitcountry", true)) {
            editor.putBoolean("isfirstinitcountry", false);
            editor.apply();
            String dirPath = "/data/data/" + this.getPackageName() + "/databases/";
            File dir = new File(dirPath);
            if (!dir.exists())
                dir.mkdirs();
            SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dirPath + "country.db", null);
            //创建表SQL语句
            String stu_table = "CREATE TABLE IF NOT EXISTS countrytable(_id integer primary key autoincrement,type INTEGER,name text,count INTEGER)";
            //执行SQL语句
            db.execSQL(stu_table);

            insert(db, "阿尔巴尼亚", 355);
            insert(db, "阿尔及利亚", 213);
            insert(db, "阿富汗", 93);
            insert(db, "阿根廷", 54);
            insert(db, "爱尔兰", 353);
            insert(db, "埃及", 20);
            insert(db, "埃塞俄比亚", 251);
            insert(db, "爱沙尼亚", 372);
            insert(db, "阿拉伯联合酋长国", 971);
            insert(db, "巴巴多斯", 1246);
            insert(db, "朝鲜", 1246);
            insert(db, "赤道几内亚", 240);
            insert(db, "秘鲁", 45);
            insert(db, "加纳", 65);
            insert(db, "加拿大", 84);
            insert(db, "喀麦隆", 243);
            insert(db, "马达加斯加", 706);
            insert(db, "美国", 1);
            insert(db, "日本", 81);
            insert(db, "中国", 86);
            insert(db, "中国澳门特别行政区", 853);
            insert(db, "中国香港特别行政区", 852);
        }
    }

    private void insert(SQLiteDatabase db, String name, int count) {
        //插入数据SQL语句
        String insert_sql = "insert into countrytable(name,count) values('" + name + "'," + count + ")";
        //执行SQL语句
        db.execSQL(insert_sql);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_in:
                startActivity(new Intent(WelcomeActivity.this, LoginActivity.class));
                break;
            case R.id.sign_up:
                startActivity(new Intent(WelcomeActivity.this, SignUpActivity.class));
                break;
            case R.id.wechat_login:
                Snackbar.make(getWindow().getDecorView(), "wechat", Snackbar.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    class MyListener implements ViewPager.OnPageChangeListener {

        //当滑动状态改变时调用
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub
            //arg0=arg0%list.size();

        }

        //当当前页面被滑动时调用
        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        int[] dot_id = {R.drawable.page_indicator_focused_fa, R.drawable.page_indicator_unfocused_fa};

        //当新的页面被选中时调用
        @Override
        public void onPageSelected(int arg0) {

            arg0 = arg0 % pageview.size();

            int c_id = arg0;
            Log.d(TAG, "index:" + String.valueOf(c_id));
            for (int i = 0; i < pageview.size(); i++) {
                dotViews[arg0]
                        .setBackgroundResource(dot_id[0]);
                if (arg0 != i) {
                    dotViews[i]
                            .setBackgroundResource(dot_id[1]);
                }
            }

            Log.d("qiang", "当前是第" + c_id + "页");
        }
    }

}
