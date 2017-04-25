package com.multshows.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.multshows.Activity.MainActivity;
import com.multshows.Adapter.HomeFrament_Adapter;
import com.multshows.R;
import com.multshows.Views.CustomViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 妈妈端发现 fragment
 */
public class Find_Fragment extends Fragment {
    List<Fragment> mFramentList;
    MyHome_Idle_Fragment mOf_Idle_Fragment;//闲置fragment界面
    Home_Task_Fragment mTask_Fragment;//妈妈端任务界面
    FragmentManager mFragmentManager;
    MyHome_Activity_Fragment mActivity_Fragment;//活动界面fragment
    HomeFrament_Adapter mAdapter;
    @Bind(R.id.find_RadioBtn1)
    RadioButton mFindRadioBtn1;//任务
    @Bind(R.id.find_RadioBtn2)
    RadioButton mFindRadioBtn2;//闲置
    @Bind(R.id.find_RadioBtn3)
    RadioButton mFindRadioBtn3;//活动
    @Bind(R.id.find_Group)
    RadioGroup mFindGroup;
    @Bind(R.id.find_viewpage)
    public CustomViewPager mFindViewpage;

    private boolean init;//记录界面是否为第一次加载

    public Find_Fragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find, container, false);
        init = true;//第一次加载
        ButterKnife.bind(this, view);
        mFindViewpage.setOffscreenPageLimit(3);
        mFramentList = new ArrayList<>();
        mOf_Idle_Fragment = new MyHome_Idle_Fragment();
        mTask_Fragment = new Home_Task_Fragment();
        mActivity_Fragment = new MyHome_Activity_Fragment();
        mFramentList.add(mTask_Fragment);
        mFramentList.add(mOf_Idle_Fragment);
        mFramentList.add(mActivity_Fragment);
        //初始化适配器
        mFragmentManager = getActivity().getSupportFragmentManager();
        mAdapter = new HomeFrament_Adapter(mFragmentManager, mFramentList);
        mFindViewpage.setAdapter(mAdapter);
        initLister();
        return view;
    }


    /**
     * 功能：该frament第一次显示的时候，加载网络数据
     * * 参数：无
     * 返回：无
     */
    private void getservciesData() {


    }

    private void initLister() {
        //RadioGroup事件监听，根据选中的不同进行viewpager界面切换
        mFindGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //viewPager监听事件
        mFindViewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动中
            }

            @Override
            public void onPageSelected(int position) {
                //滑动结束时
                switch (position) {
                    case 0:
                        mFindRadioBtn1.setChecked(true);
                        break;
                    case 1:
                        mFindRadioBtn2.setChecked(true);
                        break;
                    case 2:
                        mFindRadioBtn3.setChecked(true);
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
            case R.id.find_RadioBtn1:
                mFindViewpage.setCurrentItem(0, false);
                break;
            case R.id.find_RadioBtn2:
                mFindViewpage.setCurrentItem(1, false);
                break;
            case R.id.find_RadioBtn3:
                mFindViewpage.setCurrentItem(2, false);
                break;
        }
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (getUserVisibleHint()) {
            getdata();
        }
    }

    /**
     * 功能：加载网络数据
     * 参数：无
     * 返回：无
     */
    private void getdata() {
        if (init) {
            init = false;//第一次加载数据后，设置为false,不在加载数据
            getservciesData();
        }
    }

    /**
     * 功能：该界面是否为显示状态
     * 参数：isVisibleToUser 是否正在显示
     * 返回：无
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        //界面显示的时候加载数据

        if (isVisibleToUser) {
            getdata();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
