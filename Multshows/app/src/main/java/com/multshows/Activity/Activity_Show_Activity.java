package com.multshows.Activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.multshows.Adapter.HomeFrament_Adapter;
import com.multshows.Fragment.Home_AcyivityListMyRelease_Fragment;
import com.multshows.Fragment.Home_AcyivityListMySignup_Fragment;
import com.multshows.R;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.SaveSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 描述：查看我的活动列表
 * 作者：李萧萧
 * 时间：2016.10/8
 */
public class Activity_Show_Activity extends AppCompatActivity {
    //绑定id
    @Bind(R.id.Completed_return)
    ImageView mCompletedReturn;//返回图标
    @Bind(R.id.friends_fragment_parent)
    LinearLayout friends_fragment_parent;//最外层布局
    @Bind(R.id.ActivityTopLayout)
    RelativeLayout ActivityTopLayout;//顶部布局，用于换背景颜色
    @Bind(R.id.tasknoCompleted)
    RadioButton mTasknoCompleted;//我创建的活动导航
    @Bind(R.id.taskCompleted)
    RadioButton mTaskCompleted;//我报名的活动导航
    @Bind(R.id.Completed)
    RadioGroup mCompleted;
    @Bind(R.id.Completed_fragment_viewPager)
    ViewPager mCompletedFragmentViewPager;
    Home_AcyivityListMySignup_Fragment mCompleteFragment;//已完成界面
    Home_AcyivityListMyRelease_Fragment mNoCompleteFragment;//待未完成界面
    //工具
    FragmentManager mFragmentManager;
    //适配器
    HomeFrament_Adapter mAdapter;//fragment适配器
    //数据集合
    List<Fragment> mFramentList;//fragment集合
    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();//偏好设置使用
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_show);
        ButterKnife.bind(this);
        initData();//初始化数据
        initListen();//初始化监听事件
    }

    //返回图标点击事件
    @OnClick(R.id.Completed_return)
    public void onClick() {
        finish();
    }
    /**
     * 功能：事件监听
     *
     * 无参
     *
     * 返回值：空
     */
    private void initListen() {
        //RadioGroup事件监听，根据选中的不同进行viewpager界面切换
        mCompleted.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //viewPager滑动监听事件
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
                        mTasknoCompleted.setChecked(true);//设置我创建的活动单选按钮为选中状态
                        break;
                    case 1:
                        mTaskCompleted.setChecked(true);//设置我报名的活动单选按钮为选中状态
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
     * 功能：ViewPager监听事件，viewpager切换显示
     *  @param checkId      单选按钮id
     * 返回：无
     */
    private void resetViewPager(int checkId) {
        switch (checkId) {
            case R.id.tasknoCompleted:
                mCompletedFragmentViewPager.setCurrentItem(0, false);//设置显示我创建的活动
                break;
            case R.id.taskCompleted:
                mCompletedFragmentViewPager.setCurrentItem(1, false);//设置显示我报名的活动
                break;
        }
    }
    /**
     * 功能：数据处理
     *
     * 无参
     *
     * 返回值：空
     */
    private void initData() {
        //家长端，宝宝端界面显示区分设置
        if (mSharedPreferences.Get_PREFS(Activity_Show_Activity.this, "Type").equals("baby")) {//宝宝端界面设置
            mTaskCompleted.setText("我参与的活动");
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage_noColor(Activity_Show_Activity.this);//设置白色的通知栏
            ActivityTopLayout.setBackgroundResource(R.drawable.head_bgimg);
            mCompletedReturn.setImageResource(R.drawable.return_arrow);//返回按钮样式
            mTasknoCompleted.setTextColor(getResources().getColor(R.color.red_grey));//单选按钮颜色
            mTaskCompleted.setTextColor(getResources().getColor(R.color.red_grey));
            mTasknoCompleted.setBackgroundResource(R.drawable.radiobtn_background_check);
            mTaskCompleted.setBackgroundResource(R.drawable.radiobtn_background_check);
            mTasknoCompleted.setVisibility(View.GONE);//宝宝端隐藏我创建的活动
        }else  if (mSharedPreferences.Get_PREFS(Activity_Show_Activity.this, "Type").equals("mom")) {
            //家长端界面设置
            friends_fragment_parent.setBackgroundColor(getResources().getColor(R.color.white));//家长端背景设置白色
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage(Activity_Show_Activity.this, R.color.green);//通知栏设置成白色
        }
        mFramentList=new ArrayList<>();
        mCompleteFragment=new Home_AcyivityListMySignup_Fragment();//我创建的活动fragment初始化
        mNoCompleteFragment=new Home_AcyivityListMyRelease_Fragment();//我报名的活动fragment初始化
        //增加活动页面
        mFramentList.add(mNoCompleteFragment);
        mFramentList.add(mCompleteFragment);
        //初始化适配器
        mFragmentManager = getSupportFragmentManager();
        mAdapter = new HomeFrament_Adapter(mFragmentManager, mFramentList);
        mCompletedFragmentViewPager.setAdapter(mAdapter);
        if (mSharedPreferences.Get_PREFS(Activity_Show_Activity.this, "Type").equals("baby"))
        {//宝宝端显示我报名的互动界面
            mCompletedFragmentViewPager.setCurrentItem(1, false);
            mTaskCompleted.setTextColor(getResources().getColor(R.color.red_grey));
            mTaskCompleted.setBackgroundResource(R.drawable.radiobtn_background_check);
            mTaskCompleted.setChecked(true);
        }

    }
}
