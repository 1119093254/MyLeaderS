package com.multshows.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.multshows.Adapter.HomeFrament_Adapter;
import com.multshows.Fragment.Friends_friend_Fragment;
import com.multshows.Fragment.Friends_news_Fragment;
import com.multshows.Fragment.My_MetoReWord_Fragment;
import com.multshows.Fragment.My_ReWordtoMe_Fragment;
import com.multshows.R;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 打赏记录
 */
public class My_ReWordList_Activity extends AppCompatActivity {
    //上下文
    Context mContext;
    //控件
    ImageView mReturn;
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    ViewPager mViewPager;
    RadioGroup mGroup;
    RadioButton mLayout_1;
    RadioButton mLayout_2;
    //父布局
    //LinearLayout mParents;
    //适配器
    HomeFrament_Adapter mAdapter;
    //数据集合
    List<Fragment> mFramentList;
    //fragment
    My_ReWordtoMe_Fragment mReWordtoMeFragment;//打赏我的
    My_MetoReWord_Fragment mMetoReWordFragment;//我打赏的
    //工具
    FragmentManager mFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reword_list);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_ReWordList_Activity.this,R.color.app_topColor);
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_ReWordList_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_ReWordList_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.My_ReWordList_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.My_ReWordList_TopTitle);
        mViewPager= (ViewPager)findViewById(R.id.My_ReWordList_viewPager);
        mGroup= (RadioGroup)findViewById(R.id.My_ReWordList_radioGroup);
        mLayout_1= (RadioButton)findViewById(R.id.My_ReWordList_Layout1);
        mLayout_2= (RadioButton)findViewById(R.id.My_ReWordList_Layout2);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_ReWordList_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mFramentList=new ArrayList<>();
        mReWordtoMeFragment=new My_ReWordtoMe_Fragment();
        mMetoReWordFragment=new My_MetoReWord_Fragment();
        mFramentList.add(mReWordtoMeFragment);
        mFramentList.add(mMetoReWordFragment);
        //初始化适配器
        mFragmentManager = getSupportFragmentManager();
        mAdapter = new HomeFrament_Adapter(mFragmentManager, mFramentList);
        mViewPager.setAdapter(mAdapter);
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //RadioGroup事件监听，根据选中的不同进行viewpager界面切换
        mGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //viewPager监听事件
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动中
            }

            @Override
            public void onPageSelected(int position) {
                //滑动结束时
                switch (position) {
                    case 0:
                        mLayout_1.setChecked(true);
                        break;
                    case 1:
                        mLayout_2.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //滑动状态改变时（调用三次）
            }
        });
    }
    /**
     * 功能：ViewPager监听事件
     * 参数：无
     * 返回：无
     */
    private void resetViewPager(int checkId) {
        switch (checkId) {
            case R.id.My_ReWordList_Layout1:
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.My_ReWordList_Layout2:
                mViewPager.setCurrentItem(1, true);
                break;
        }
    }
}
