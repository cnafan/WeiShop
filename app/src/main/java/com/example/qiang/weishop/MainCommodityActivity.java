package com.example.qiang.weishop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainCommodityActivity extends AppCompatActivity implements View.OnClickListener{


    private ImageView imageView_joinweishop;
    private TextView textView_Sale;
    private TextView textView_Off_the_shelf;
    private TextView textView_classification;

    private ViewPager viewPager;


    //包裹点点的LinearLayout
    private ViewGroup group;
    private ImageView dotView;
    private ImageView[] dotViews;

    private List<ImageView> listviews;
    private int[] pics = {R.drawable.commodity_item1, R.drawable.commodity_item2, R.drawable.commodity_item3, R.drawable.commodity_item4, R.drawable.commodity_item5};

    void initial() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//设置返回
        getSupportActionBar().setDisplayShowTitleEnabled(false);//去掉原有标题

        textView_Sale = (TextView) findViewById(R.id.Sale);
        textView_Off_the_shelf = (TextView) findViewById(R.id.Off_the_shelf);
        textView_classification = (TextView) findViewById(R.id.classification);
        imageView_joinweishop=(ImageView)findViewById(R.id.commodity_joinweishop);
        imageView_joinweishop.setOnClickListener(this);


        viewPager = (ViewPager) findViewById(R.id.commodity_viewpager);
        listviews = new ArrayList<ImageView>();
        for (int i = 0; i < pics.length; i++) {
            ImageView imageView = new ImageView(MainCommodityActivity.this);
            ViewGroup.LayoutParams viewPagerImageViewParams = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            imageView.setLayoutParams(viewPagerImageViewParams);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(pics[i]);
            listviews.add(imageView);
        }
        //viewPager.setCurrentItem(499);


        group = (ViewGroup) findViewById(R.id.viewgroup);
        dotViews = new ImageView[listviews.size()];
        for (int i = 0; i < listviews.size(); i++) {
            dotView = new ImageView(MainCommodityActivity.this);
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
        //数据适配器
        PagerAdapter mPagerAdapter = new PagerAdapter() {

            @Override
            //获取当前窗体界面数
            public int getCount() {
                // TODO Auto-generated method stub
                return listviews.size();
            }

            @Override
            //断是否由对象生成界面
            public boolean isViewFromObject(View arg0, Object arg1) {
                // TODO Auto-generated method stub
                return arg0 == arg1;
            }

            //是从ViewGroup中移出当前View
            public void destroyItem(View arg0, int arg1, Object arg2) {
                ((ViewPager) arg0).removeView(listviews.get(arg1));
            }

            //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
            public Object instantiateItem(View arg0, int arg1) {
                ((ViewPager) arg0).addView(listviews.get(arg1));
                return listviews.get(arg1);
            }


        };

        //绑定适配器
        viewPager.setAdapter(mPagerAdapter);
        //设置监听
        viewPager.setOnPageChangeListener(new MyListener());
        //绑定监听事件

    }

    // 加载底部圆点
    /*
    private void initPoint() {
        //这里实例化LinearLayout
        vg = (ViewGroup) findViewById(R.id.guide_ll_point);
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        for (int i = 0;i<size;i++){
            iv_point = new ImageView(this);
            iv_point.setLayoutParams(new ViewGroup.LayoutParams(20,20));
            iv_point.setPadding(30,0,30,0);//left,top,right,bottom
            ivPointArray[i] = iv_point;
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0){
                iv_point.setBackgroundResource(R.mipmap.full_holo);
            }else{
                iv_point.setBackgroundResource(R.mipmap.empty_holo);
            }
            //将数组中的ImageView加入到ViewGroup
            vg.addView(ivPointArray[i]);
        }

    }
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_commodity);

        initial();
    }

    public void commodity_toolbar_sale_clidk(View view) {

        textView_classification.setBackground(getResources().getDrawable(R.drawable.shape_commodity_title_left));
        textView_Sale.setBackground(getResources().getDrawable(R.drawable.shape_commodity_title_right));
        textView_Off_the_shelf.setBackground(getResources().getDrawable(R.drawable.shape_commodity_title_left));
        textView_Sale.setTextColor(getResources().getColor(R.color.colorPrimary));
        textView_Off_the_shelf.setTextColor(getResources().getColor(R.color.white));
        textView_classification.setTextColor(getResources().getColor(R.color.white));
    }

    public void commodity_toolbar_Off_the_shelf_clidk(View view) {
        textView_classification.setBackground(getResources().getDrawable(R.drawable.shape_commodity_title_left));
        textView_Sale.setBackground(getResources().getDrawable(R.drawable.shape_commodity_title_left));
        textView_Off_the_shelf.setBackground(getResources().getDrawable(R.drawable.shape_commodity_title_right));
        textView_Sale.setTextColor(getResources().getColor(R.color.white));
        textView_Off_the_shelf.setTextColor(getResources().getColor(R.color.colorPrimary));
        textView_classification.setTextColor(getResources().getColor(R.color.white));
    }

    public void commodity_toolbar_classification_clidk(View view) {
        textView_Sale.setBackground(getResources().getDrawable(R.drawable.shape_commodity_title_left));
        textView_Off_the_shelf.setBackground(getResources().getDrawable(R.drawable.shape_commodity_title_left));
        textView_classification.setBackground(getResources().getDrawable(R.drawable.shape_commodity_title_right));
        textView_Sale.setTextColor(getResources().getColor(R.color.white));
        textView_Off_the_shelf.setTextColor(getResources().getColor(R.color.white));
        textView_classification.setTextColor(getResources().getColor(R.color.colorPrimary));
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

    public void commodity_Sale_add_clidk(View view) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.commodity_joinweishop:
                startActivity(new Intent(MainCommodityActivity.this,JoinWeishopActivity.class));
                break;
        }
    }

    public void commodity_Sale_supply_from_click(View view) {
        startActivity(new Intent(MainCommodityActivity.this,MainSupplyFromActivity.class));
    }

    public void commodity_Sale_move_taobao_click(View view) {
        startActivity(new Intent(MainCommodityActivity.this,TaobaoMoveActivity.class));
    }
/*
    class ViewPagerAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (listviews.get(position % listviews.size()).getParent() != null) {
                ((ViewPager) listviews.get(position % listviews.size())
                        .getParent()).removeView(listviews.get(position
                        % listviews.size()));
            }
            ((ViewPager) container).addView(
                    listviews.get(position % listviews.size()), 0);
            return listviews.get(position % listviews.size());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            //Warning：不要在这里调用removeView
        }

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

    }
    */

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

            arg0 = arg0 % listviews.size();

            int c_id = arg0;
            for (int i = 0; i < listviews.size(); i++) {
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
