package com.multshows.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.multshows.Activity.LoginActivity;
import com.multshows.Activity.LoginRegisterActivity;
import com.multshows.Adapter.HomeFrament_Adapter;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.SaveSharedPreferences;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * 描述：好友模块
 * 作者：贾强胜
 * 时间:2016.9.21
 */
public class Friends_Fragment extends Fragment {
    private boolean init;//记录界面是否为第一次加载
    //上下文
    Context mContext;
    //控件
    ViewPager mViewPager;
    RadioGroup mGroup;
    RadioButton mNews;
    RadioButton mFriend;
    RadioButton mMyActivity;
    RelativeLayout mTopLayout;
    //父布局
    RelativeLayout mParents;
    //适配器
    HomeFrament_Adapter mAdapter;
    //数据集合
    List<Fragment> mFramentList;
    //fragment
    Friends_news_Fragment mNewsFragment;//消息界面
    Friends_friend_Fragment mFriendFragment;//好友界面
    MyHome_Activity_Fragment mActivityFragment;//活动界面
    LinearLayout mUnloginLayout;//未登录界面
    View mUnlogin;//未登录界面视图
    TextView mUnLoginHint;
    Button mLogin;//登录
    Button mZC;//注册
    //工具
    FragmentManager mFragmentManager;
    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();
    String type;
    String unlogin;
    View view;
    public Friends_Fragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_friends, container, false);
        init = true;//第一次加载
        mParents= (RelativeLayout) view.findViewById(R.id.friends_fragment_parent);
        type=mSharedPreferences.Get_PREFS(getActivity(),"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        initView(view);
        if("mom".equals(type)){
            //妈妈端
            mParents.setBackgroundColor(getResources().getColor(R.color.white));
            mTopLayout.setBackgroundColor(getResources().getColor(R.color.app_topColor));
            mFriend.setBackground(null);
            mNews.setBackground(null);
            mMyActivity.setBackground(null);
            mFriend.setTextColor(getResources().getColor(R.color.white2));
            mNews.setTextColor(getResources().getColor(R.color.white));
            mMyActivity.setTextColor(getResources().getColor(R.color.white2));
        }else if("baby".equals(type)){
            //宝宝端
            mParents.setBackgroundColor(getResources().getColor(R.color.background_alight));
            mTopLayout.setBackgroundResource(R.drawable.head_bgimg);
          //mParents.setBackgroundColor(getResources().getColor(R.color.background_alight_2));
        }
        unlogin=mSharedPreferences.Get_PREFS(getActivity(),"Unlogin");
        if("yes".equals(unlogin)){
            //未登录
            mViewPager.setVisibility(View.GONE);
            mUnloginLayout.setVisibility(View.VISIBLE);
        }else {
            //已登录
            mViewPager.setVisibility(View.VISIBLE);
            mUnloginLayout.setVisibility(View.GONE);
        }

        initData(view);
        initListen(view);
        return view;
    }

    /**
     * 初始化
     */
    private void initView(View view) {
        mViewPager= (ViewPager) view.findViewById(R.id.friends_fragment_viewPager);
        mGroup= (RadioGroup) view.findViewById(R.id.friends_fragment_radioGroup);
        mNews= (RadioButton) view.findViewById(R.id.friends_fragment_news);
        mFriend= (RadioButton) view.findViewById(R.id.friends_fragment_friends);
        mMyActivity= (RadioButton) view.findViewById(R.id.friends_fragment_Activity);
        mTopLayout= (RelativeLayout) view.findViewById(R.id.friends_fragment_radioGroupLayout);
        mUnloginLayout= (LinearLayout) view.findViewById(R.id.Unlogin_view);
        mUnlogin=view.findViewById(R.id.unlogin_views);
        mUnLoginHint= (TextView) mUnlogin.findViewById(R.id.UnLogin_text);
        mLogin= (Button) mUnlogin.findViewById(R.id.UnLogin_login);
        mZC= (Button) mUnlogin.findViewById(R.id.UnLogin_ZC);
        mUnLoginHint.setText("登陆后，别人给你发消息、好友验证消息,都会在这里收到通知");
    }

    /**
     * 数据处理
     */
    private void initData(View view) {
        mFramentList = new ArrayList<>();
        if("baby".equals(type)) {
            mNewsFragment = new Friends_news_Fragment();
            mFriendFragment = new Friends_friend_Fragment();
            mActivityFragment = new MyHome_Activity_Fragment();
            mFramentList.add(mNewsFragment);
            mFramentList.add(mFriendFragment);
            mFramentList.add(mActivityFragment);
            mMyActivity.setVisibility(View.VISIBLE);
        }else {
            mNewsFragment = new Friends_news_Fragment();
            mFriendFragment = new Friends_friend_Fragment();
            mFramentList.add(mNewsFragment);
            mFramentList.add(mFriendFragment);
            mMyActivity.setVisibility(View.GONE);
        }
        //初始化适配器
        mFragmentManager = getActivity().getSupportFragmentManager();
        mAdapter = new HomeFrament_Adapter(mFragmentManager, mFramentList);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(3);

    }

    /**
     * 事件监听
     */
    private void initListen(View view) {
        //登录
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, LoginActivity.class);
                startActivity(intent);
            }
        });
        //注册
        mZC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, LoginRegisterActivity.class);
                intent.putExtra("IsThree", "no");//传值，yes代表是第三方登录
                intent.putExtra("Logintoken", "no");
                startActivity(intent);
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
                        mNews.setChecked(true);
                        break;
                    case 1:
                        mFriend.setChecked(true);
                        break;
                    case 2:
                        mMyActivity.setChecked(true);
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
            case R.id.friends_fragment_news:
                if("mom".equals(type)) {
                    //妈妈端
                    mFriend.setTextColor(getResources().getColor(R.color.white2));
                    mNews.setTextColor(getResources().getColor(R.color.white));
                    mMyActivity.setTextColor(getResources().getColor(R.color.white2));
                }
                mViewPager.setCurrentItem(0, false);
                mUnLoginHint.setText("登陆后，别人给你发消息、好友验证消息,都会在这里收到通知");
                break;
            case R.id.friends_fragment_friends:
                if("mom".equals(type)) {
                    //妈妈端
                    mFriend.setTextColor(getResources().getColor(R.color.white));
                    mNews.setTextColor(getResources().getColor(R.color.white2));
                    mMyActivity.setTextColor(getResources().getColor(R.color.white2));
                }
                mViewPager.setCurrentItem(1, false);
                mUnLoginHint.setText("登陆后，在这里可以添加好友一起开启群聊增加互动");
                break;
            case R.id.friends_fragment_Activity:
                if("mom".equals(type)) {
                    //妈妈端
                    mFriend.setTextColor(getResources().getColor(R.color.white2));
                    mNews.setTextColor(getResources().getColor(R.color.white2));
                    mMyActivity.setTextColor(getResources().getColor(R.color.white));
                }
                mViewPager.setCurrentItem(2, false);
                mUnLoginHint.setText("登陆后，在这里可以报名活动、发布活动结交志同道合的朋友一起出游");
                break;
        }
    }

    /**
     * 功能：加载网络数据
     * 参数：无
     * 返回：无
     */
    private void getdata() {
      // mAdapter.notifyDataSetChanged();
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
