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
import android.widget.TextView;

import com.multshows.Adapter.HomeFrament_Adapter;
import com.multshows.Fragment.Kitting_BoundBankMom_Fragment;
import com.multshows.Fragment.Kitting_BoundYMD_Fragment;
import com.multshows.R;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 妈妈端  提现到  界面
 */
public class Bound_BankList_Mom_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    RadioGroup mGroup;
    RadioButton mRadioButton1;//一麻袋
    RadioButton mRadioButton2;//银行卡
    ViewPager mViewPager;
    //适配器
    HomeFrament_Adapter mAdapter;
    //数据集合
    List<Fragment> mFramentList=new ArrayList<>();
    //fragment
    Kitting_BoundYMD_Fragment mYMDFragment;
    Kitting_BoundBankMom_Fragment mBankMomFragment;
    //工具
    FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_bank_list__mom);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Bound_BankList_Mom_Activity.this,R.color.app_topColor);
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.Bound_BankList_Mom_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Bound_BankList_Mom_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Bound_BankList_Mom_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.Bound_BankList_Mom_TopTitle);
        mGroup= (RadioGroup) findViewById(R.id.Bound_BankList_Mom_RadioGroup);
        mRadioButton1= (RadioButton) findViewById(R.id.Bound_BankList_Mom_RadioButton1);
        mRadioButton2= (RadioButton) findViewById(R.id.Bound_BankList_Mom_RadioButton2);
        mViewPager= (ViewPager) findViewById(R.id.Bound_BankList_Mom_ViewPager);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Bound_BankList_Mom_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mYMDFragment=new Kitting_BoundYMD_Fragment();
        mBankMomFragment=new Kitting_BoundBankMom_Fragment();
        mFramentList.add(mYMDFragment);
        mFramentList.add(mBankMomFragment);
        //初始化适配器
        mFragmentManager=getSupportFragmentManager();
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
            public void onClick(View v) {
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
                        mRadioButton1.setChecked(true);
                        break;
                    case 1:
                        mRadioButton2.setChecked(true);
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
            case R.id.Bound_BankList_Mom_RadioButton1:
                mViewPager.setCurrentItem(0, false);
                break;
            case R.id.Bound_BankList_Mom_RadioButton2:
                mViewPager.setCurrentItem(1, false);
                break;
        }
    }
}
