package com.multshows.Activity;

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

import com.multshows.Adapter.HomeFrament_Adapter;
import com.multshows.Fragment.My_OrderList_Purchase_Fragment;
import com.multshows.Fragment.My_OrderList_SoldOut_Fragment;
import com.multshows.R;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.SaveSharedPreferences;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订单
 */
public class My_OrderList_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    ViewPager mViewPager;
    RadioGroup mGroup;
    RadioButton mLayout_1;//买入
    RadioButton mLayout_2;//卖出
    //父布局
    //LinearLayout mParents;
    //适配器
    HomeFrament_Adapter mAdapter;
    //数据集合
    List<Fragment> mFramentList;
    //fragment
    My_OrderList_Purchase_Fragment mPurchaseFragment;//买入
    My_OrderList_SoldOut_Fragment mSoldOutFragment;//卖出
    //工具
    FragmentManager mFragmentManager;
    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();
    String type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_OrderList_Activity.this,R.color.app_topColor);
        initView();
        type=mSharedPreferences.Get_PREFS(My_OrderList_Activity.this,"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        if("mom".equals(type)) {
            mLayout_1.setBackground(null);
            mLayout_2.setBackground(null);
            mLayout_1.setTextColor(getResources().getColor(R.color.white));
            mLayout_2.setTextColor(getResources().getColor(R.color.white2));
        }

        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_OrderList_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_OrderList_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.My_OrderList_ParentsLayout);
        mViewPager= (ViewPager)findViewById(R.id.My_OrderList_viewPager);
        mGroup= (RadioGroup)findViewById(R.id.My_OrderList_radioGroup);
        mLayout_1= (RadioButton)findViewById(R.id.My_OrderList_Layout1);
        mLayout_2= (RadioButton)findViewById(R.id.My_OrderList_Layout2);
        /**
         * 动态改变布局颜色
         *
         */
        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_OrderList_Activity.this,mReturnLayout,mParentLayout,mReturn,null);
    }

    /**
     * 数据处理
     */
    private void initData() {

        //if("mom".equals(type)) {
            //妈妈端
            mFramentList=new ArrayList<>();
            mPurchaseFragment=new My_OrderList_Purchase_Fragment();
            mSoldOutFragment=new My_OrderList_SoldOut_Fragment();
            mFramentList.add(mPurchaseFragment);
            mFramentList.add(mSoldOutFragment);
            //初始化适配器
            mFragmentManager = getSupportFragmentManager();
            mAdapter = new HomeFrament_Adapter(mFragmentManager, mFramentList);
            mViewPager.setAdapter(mAdapter);
       /* }else if("baby".equals(type)){
            //宝宝端
            mLayout_2.setVisibility(View.GONE);
            mLayout_1.setTextColor(getResources().getColor(R.color.text_black));
            mFramentList=new ArrayList<>();
            mPurchaseFragment=new My_OrderList_Purchase_Fragment();
            mFramentList.add(mPurchaseFragment);
            //初始化适配器
            mFragmentManager = getSupportFragmentManager();
            mAdapter = new HomeFrament_Adapter(mFragmentManager, mFramentList);
            mViewPager.setAdapter(mAdapter);
        }*/

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
            case R.id.My_OrderList_Layout1:
                if("mom".equals(type)) {
                    mLayout_1.setBackground(null);
                    mLayout_2.setBackground(null);
                    mLayout_1.setTextColor(getResources().getColor(R.color.white));
                    mLayout_2.setTextColor(getResources().getColor(R.color.white2));
                }
                mViewPager.setCurrentItem(0, true);
                break;
            case R.id.My_OrderList_Layout2:
                if("mom".equals(type)) {
                    mLayout_1.setBackground(null);
                    mLayout_2.setBackground(null);
                    mLayout_1.setTextColor(getResources().getColor(R.color.white2));
                    mLayout_2.setTextColor(getResources().getColor(R.color.white));
                }
                mViewPager.setCurrentItem(1, true);
                break;
        }
    }

}
