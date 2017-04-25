package com.multshows.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.multshows.R;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.SaveSharedPreferences;

import java.util.ArrayList;


/**
 * 描述：导航页
 * 作者：李萧萧
 * 时间：2016/11/11
 */
public class GuideActivity extends AppCompatActivity {
    //设置导航页的图片
    RelativeLayout skipLayout;
    private static final int[] mImageIds = new int[]{R.drawable.start_page1,
            R.drawable.start_page2, R.drawable.start_page3, R.drawable.start_page4, R.drawable.start_page5};
    ViewPager mViewPager;//滑动
    ArrayList<ImageView> mImageViewList;//数据源
    Button mButton;//立即体验按钮

    ImageView mReturn;//返回
    RelativeLayout mLayout;//隐藏布局
    int type=0;
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_activity_guide);
        mLayout= (RelativeLayout) findViewById(R.id.vp_returnLayout);
        mReturn= (ImageView) findViewById(R.id.vp_return);
        mViewPager = (ViewPager) findViewById(R.id.vp_guide);
        mButton = (Button) findViewById(R.id.guide_button);
        skipLayout= (RelativeLayout) findViewById(R.id.skipLayout);
        Intent intent=getIntent();
        type=intent.getIntExtra("type",0);
        if(type!=2) {
            Intent intents = new Intent();
            mSave.Save_PREFS(GuideActivity.this, "Type", "baby");
            intents.setClass(GuideActivity.this, MainActivity.class);
            startActivity(intents);
            finish();
          /*  if (mSave.Get_PREFS(GuideActivity.this, "first").equals("yes")) {
                //判断是否安装 app第一次进入

            } else {
                mSave.Save_PREFS(GuideActivity.this, "first", "yes");
            }*/
        }else {
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage(GuideActivity.this, R.color.app_topColor);
            mLayout.setVisibility(View.VISIBLE);
            mButton.setVisibility(View.GONE);
            skipLayout.setVisibility(View.GONE);
        }
        initViews();
        initListeners();
        mViewPager.setAdapter(new GuideAdapter());
        mViewPager.setOnPageChangeListener(new GuidePageListemer());
    }

    private void initListeners() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转页面
                Intent intent = new Intent();
                mSave.Save_PREFS(GuideActivity.this, "Type", "baby");
                intent.setClass(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        skipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转页面
                Intent intent = new Intent();
                intent.setClass(GuideActivity.this, MainActivity.class);
                mSave.Save_PREFS(GuideActivity.this, "Type", "baby");
                startActivity(intent);
                finish();
            }
        });
    }


    //viewpager的滑动监听GuidePageListener
    class GuidePageListemer implements ViewPager.OnPageChangeListener {
        //滑动事件
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //某个页面被选中
        @Override
        public void onPageSelected(int position) {
            if (type != 2) {
                if (position == mImageIds.length - 1) {// 最后一个页面
                    skipLayout.setVisibility(View.INVISIBLE);
                    //判断不是从设置进入
                    mButton.setVisibility(View.VISIBLE);// 显示开始体验的按钮
            } else {
                skipLayout.setVisibility(View.VISIBLE);
                mButton.setVisibility(View.INVISIBLE);
            }
        }
        }

        //滑动状态发生变化
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    /**
     * 初始化界面
     */
    private void initViews() {
        mImageViewList = new ArrayList<>();
        ImageView image;
        // 初始化引导页的5个页面
        for (int i = 0; i < mImageIds.length; i++) {
            image = new ImageView(this);
            image.setBackgroundResource(mImageIds[i]);// 设置引导页背景
            mImageViewList.add(image);
        }
    }

    /**
     * 数据适配器
     */
    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImageIds.length;
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImageViewList.get(position));
            return mImageViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
