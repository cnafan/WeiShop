package com.example.qiang.weishop;

import android.content.Intent;
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

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d(TAG, "pa");
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
            dotView = new ImageView(LoginActivity.this);
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
        button_wechat=(Button)findViewById(R.id.wechat_login);
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

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.log_in:
                startActivity(new Intent(LoginActivity.this, LoginMainActivity.class));
                break;
            case R.id.sign_up:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;
            case R.id.wechat_login:
                Snackbar.make(getWindow().getDecorView(),"wechat",Snackbar.LENGTH_SHORT).show();
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
