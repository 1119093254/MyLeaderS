package com.multshows.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.multshows.Adapter.HomeFrament_Adapter;
import com.multshows.Fragment.Home_TaskListNoComplete_Fragment;
import com.multshows.Fragment.Home_TaskListYesComplete_Fragment;
import com.multshows.R;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 描述：已发布任务列表
 * 作者：李萧萧
 * 时间：2016.10/8
 */
public class Task_Show_Activity extends AppCompatActivity {

    @Bind(R.id.Completed_return)
    ImageView mCompletedReturn;//返回按钮
    @Bind(R.id.tasknoCompleted)
    RadioButton mTasknoCompleted;//未完成任务导航
    @Bind(R.id.taskCompleted)
    RadioButton mTaskCompleted;//已完成任务导航
    @Bind(R.id.Completed)
    RadioGroup mCompleted;
    @Bind(R.id.Completed_fragment_viewPager)
    ViewPager mCompletedFragmentViewPager;
    Home_TaskListYesComplete_Fragment mCompleteFragment;//已完成界面
    Home_TaskListNoComplete_Fragment mNoCompleteFragment;//待未完成界面
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    //工具
    FragmentManager mFragmentManager;
    //适配器
    HomeFrament_Adapter mAdapter;
    //数据集合
    List<Fragment> mFramentList;
    int position_=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_show);
        ButterKnife.bind(this);
        //设置状态栏颜色
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Task_Show_Activity.this,R.color.app_topColor);

        mReturnLayout= (RelativeLayout) findViewById(R.id.Completed_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.friends_fragment_parent);
        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Task_Show_Activity.this,mReturnLayout,mParentLayout,mCompletedReturn,null);
        //momORBabyUtils.isMomORBaby_Radio(Task_Show_Activity.this,mTaskCompleted,mTasknoCompleted);
        Intent intent_=getIntent();
        position_=intent_.getIntExtra("positions",0);
        initData();
        if(position_==1) {
            mCompletedFragmentViewPager.setCurrentItem(1);
            mTaskCompleted.setChecked(true);
        }
        initListen();
    }

    @OnClick(R.id.Completed_return)
    public void onClick() {
        finish();
    }
    /**
     * 事件监听
     */
    private void initListen() {
        //RadioGroup事件监听，根据选中的不同进行viewpager界面切换
        mCompleted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //viewPager监听事件
        mCompletedFragmentViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动中
            }

            @Override
            public void onPageSelected(int position) {
                //滑动结束时
                switch (position) {
                    case 0:
                        mTasknoCompleted.setChecked(true);
                        break;
                    case 1:
                        mTaskCompleted.setChecked(true);
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
            case R.id.tasknoCompleted:
                mCompletedFragmentViewPager.setCurrentItem(0, false);
                break;
            case R.id.taskCompleted:
                mCompletedFragmentViewPager.setCurrentItem(1, false);
                break;
        }
    }
    /**
     * 数据处理
     */
    private void initData() {
        mFramentList=new ArrayList<>();
        mCompleteFragment=new Home_TaskListYesComplete_Fragment();
        mNoCompleteFragment=new Home_TaskListNoComplete_Fragment();
        mFramentList.add(mNoCompleteFragment);
        mFramentList.add(mCompleteFragment);
        //初始化适配器
        mFragmentManager = getSupportFragmentManager();
        mAdapter = new HomeFrament_Adapter(mFragmentManager, mFramentList);
        mCompletedFragmentViewPager.setAdapter(mAdapter);
    }
}
