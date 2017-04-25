package com.multshows.Fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Activity.LoginActivity;
import com.multshows.Activity.MainActivity;
import com.multshows.Activity.Release_Work_Activity;
import com.multshows.Activity.WorkClassificationActivity;
import com.multshows.Adapter.CricleFriend_popupwindow_adapter;
import com.multshows.Adapter.HomeFrament_Adapter;
import com.multshows.Beans.EventBus_Model;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.CustomViewPager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by li on 2016/8/30
 * 李萧萧
 * 主页 fragment
 */
public class Home_Fragment extends Fragment {

    View mView;

    List<Fragment> mFramentList;
    Work_Fragment mWork_Fragment;//圈子 ,全部作品 fragment
    Attention_Fragment mAttention_Fragment;//作品，好友作品 fragment
    FragmentManager mFragmentManager;
    HomeFrament_Adapter mAdapter;// fragment适配器
    @Bind(R.id.home_RadioBtn1)
    RadioButton mHomeRadioBtn1;//圈子 全部作品按钮
    @Bind(R.id.home_RadioBtn2)
    RadioButton mHomeRadioBtn2;//作品，好友作品列表
    @Bind(R.id.home_Group)
    RadioGroup mHomeGroup;
    @Bind(R.id.ClassificationActivity)
    TextView mClassificationActivity;//分类按钮
    @Bind(R.id.SendWorkFriend)
    ImageView mSendWorkFriend;//宝宝端发布作品图标
    /*    @Bind(R.id.ClassificationImage)
        ImageView mClassificationImage;//宝宝端分类图标*/
    @Bind(R.id.homeFrament_Relayout)
    RelativeLayout mHomeFrament_Relayout;
    Gson mGson = new Gson();
    @Bind(R.id.home_viewpage)
    public CustomViewPager mHomeViewpage;
    @Bind(R.id.moreWorkClassImage)
    public ImageView moreWorkClassImage;//vip分类查询数据
    @Bind(R.id.AllWorkTextChioce)
    public TextView AllWorkTextChioce;//vip所有作品
    TextView CommentSent;
    TextView CommentSent2;
    private List<String> groups;
    private boolean init;//记录界面是否为第一次加载
    int mPosition = 0;//记录当前popupwindow选择的位置
    private PopupWindow popupWindow;
    private ListView lv_group;
    private View view;
    CricleFriend_popupwindow_adapter groupAdapter;
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();

    public Home_Fragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home, container, false);
        init = true;//第一次加载
        ButterKnife.bind(this, mView);
        CommentSent = (TextView) getActivity().findViewById(R.id.CommentSent);
        CommentSent2 = (TextView) getActivity().findViewById(R.id.CommentSent2);
        mHomeViewpage.setOffscreenPageLimit(2);
        mFramentList = new ArrayList<>();
        mWork_Fragment = new Work_Fragment();
        mAttention_Fragment = new Attention_Fragment();

        if (saveSharedPreferences.Get_PREFS(getActivity(), "Type").equals("baby")) {
            saveSharedPreferences.Save_PREFS(getActivity(), "Type", "baby");//代表此页页面
            mHomeRadioBtn1.setText("全部作品");
            mHomeRadioBtn2.setText("好友作品");

            mClassificationActivity.setTextColor(getResources().getColor(R.color.baby_radio));
            mClassificationActivity.setVisibility(View.VISIBLE);
            //   mClassificationImage.setVisibility(View.VISIBLE);
            mHomeFrament_Relayout.setBackgroundResource(R.drawable.head_bgimg);
            mFramentList.add(mWork_Fragment);
            mFramentList.add(mAttention_Fragment);
            mSendWorkFriend.setVisibility(View.VISIBLE);
        } else if (saveSharedPreferences.Get_PREFS(getActivity(), "Type").equals("mom")) {
            if (saveSharedPreferences.Get_PREFS(getActivity(), "Vip").equals("yes")) {
                mHomeRadioBtn1.setVisibility(View.GONE);
                moreWorkClassImage.setVisibility(View.VISIBLE);
                AllWorkTextChioce.setText("宝宝作品");
                mHomeRadioBtn1.setText("宝宝作品");
                AllWorkTextChioce.setVisibility(View.VISIBLE);
            } else {
                mHomeRadioBtn1.setText("作品");
                moreWorkClassImage.setVisibility(View.GONE);
                AllWorkTextChioce.setVisibility(View.GONE);
                mHomeRadioBtn1.setVisibility(View.VISIBLE);
            }
            mHomeRadioBtn2.setText("圈子");
            mHomeRadioBtn1.setBackground(null);
            mHomeRadioBtn2.setBackground(null);
            mHomeRadioBtn1.setTextColor(getResources().getColor(R.color.white));
            mHomeRadioBtn2.setTextColor(getResources().getColor(R.color.white2));
            mSendWorkFriend.setVisibility(View.GONE);
            mFramentList.add(mAttention_Fragment);
            mFramentList.add(mWork_Fragment);
        }

     /*   if (saveSharedPreferences.Get_PREFS(getActivity(), "Unlogin").equals("yes")) {
            mHomeRadioBtn2.setEnabled(false);
        } else mHomeRadioBtn2.setEnabled(true);*/
        //初始化适配器
        mFragmentManager = getActivity().getSupportFragmentManager();
        mAdapter = new HomeFrament_Adapter(mFragmentManager, mFramentList);
        mHomeViewpage.setAdapter(mAdapter);
        mHomeViewpage.setOffscreenPageLimit(2);
        if (saveSharedPreferences.Get_PREFS(getActivity(), "Type").equals("mom")) {
            mClassificationActivity.setVisibility(View.VISIBLE);

            CommentSent.setVisibility(View.INVISIBLE);
            CommentSent2.setVisibility(View.VISIBLE);
        } else {
            CommentSent.setVisibility(View.VISIBLE);
            CommentSent2.setVisibility(View.INVISIBLE);
        }
        groups = new ArrayList<>();
        initLister();
        return mView;
    }


    /**
     * 功能：该frament第一次显示的时候，加载网络数据
     * * 参数：无
     * 返回：无
     */
    private void getservciesData() {
        {

        }
    }

    private void initLister() {

        //RadioGroup事件监听，根据选中的不同进行viewpager界面切换
        mHomeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                resetViewPager(checkedId);
            }
        });
        //viewPager监听事件
        mHomeViewpage.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //滑动中
            }

            @Override
            public void onPageSelected(int position) {
                //滑动结束时
                switch (position) {
                    case 0:
                        mHomeRadioBtn1.setChecked(true);
                        break;
                    case 1:
                        mHomeRadioBtn2.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //滑动状态改变时（调用三次）
            }
        });
        mClassificationActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), WorkClassificationActivity.class);
                startActivity(mIntent);
            }
        });
       /* mClassificationImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(getActivity(), WorkClassificationActivity.class);
                startActivity(mIntent);
            }
        });*/

        mSendWorkFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!saveSharedPreferences.Get_PREFS(getActivity(), "Unlogin").equals("yes")) {
                    Intent mIntent = new Intent(getActivity(), Release_Work_Activity.class);
                    startActivity(mIntent);
                } else {
                    Intent mIntent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(mIntent);
                }

            }
        });
        AllWorkTextChioce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindow();
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
            case R.id.home_RadioBtn1:
                MainActivity.flag = false;
                if (saveSharedPreferences.Get_PREFS(getActivity(), "Type").equals("mom")) {
                    mHomeRadioBtn1.setTextColor(getResources().getColor(R.color.white));
                    mHomeRadioBtn2.setTextColor(getResources().getColor(R.color.white2));
                    CommentSent.setVisibility(View.INVISIBLE);
                    CommentSent2.setVisibility(View.VISIBLE);
                    if (saveSharedPreferences.Get_PREFS(getActivity(), "Vip").equals("yes")) {
                        AllWorkTextChioce.setVisibility(View.VISIBLE);
                        mHomeRadioBtn1.setVisibility(View.GONE);
                        moreWorkClassImage.setAlpha(100);
                    }
                } else {
                    CommentSent.setVisibility(View.VISIBLE);
                    CommentSent2.setVisibility(View.INVISIBLE);
                }
                mClassificationActivity.setVisibility(View.VISIBLE);
                mHomeViewpage.setCurrentItem(0, false);

                break;
            case R.id.home_RadioBtn2:
                if (saveSharedPreferences.Get_PREFS(getActivity(), "Type").equals("mom")) {
                    mHomeRadioBtn1.setTextColor(getResources().getColor(R.color.white2));
                    mHomeRadioBtn2.setTextColor(getResources().getColor(R.color.white));
                    CommentSent.setVisibility(View.VISIBLE);
                    CommentSent2.setVisibility(View.INVISIBLE);
                    mClassificationActivity.setVisibility(View.INVISIBLE);
                    if (saveSharedPreferences.Get_PREFS(getActivity(), "Vip").equals("yes")) {
                        AllWorkTextChioce.setVisibility(View.GONE);
                        mHomeRadioBtn1.setVisibility(View.VISIBLE);
                        moreWorkClassImage.setAlpha(80);
                        mHomeRadioBtn1.setText(AllWorkTextChioce.getText().toString());
                    }
                } else {
                    CommentSent.setVisibility(View.INVISIBLE);
                    CommentSent2.setVisibility(View.VISIBLE);
                    mClassificationActivity.setVisibility(View.VISIBLE);
                }
                MainActivity.flag = true;

                mHomeViewpage.setCurrentItem(1, false);
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
        if (saveSharedPreferences != null && saveSharedPreferences.Get_PREFS(getActivity(), "Change").equals("yes")) {
            saveSharedPreferences.Save_PREFS(getActivity(), "Change", "no");
            mHomeRadioBtn1.setText("作品");
            moreWorkClassImage.setVisibility(View.GONE);
            AllWorkTextChioce.setVisibility(View.GONE);
            mHomeRadioBtn1.setVisibility(View.VISIBLE);
            mHomeRadioBtn2.setText("圈子");
            mHomeRadioBtn1.setBackground(null);
            mHomeRadioBtn2.setBackground(null);
            mHomeRadioBtn1.setTextColor(getResources().getColor(R.color.white));
            mHomeRadioBtn2.setTextColor(getResources().getColor(R.color.white2));
            EventBus.getDefault().post(new EventBus_Model("作品"));
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

    private void showWindow() {
        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.work_popupwindow_list, null);
            lv_group = (ListView) view.findViewById(R.id.lvGroup);
            saveSharedPreferences.Save_PREFS(getActivity(), "idleFresh", "no");//判断是否从闲置列表跳进来的
            // 加载数据
            groups = new ArrayList<String>();
            groups.add("宝宝作品");
            groups.add("妈妈作品");
        }
        groupAdapter = new CricleFriend_popupwindow_adapter(getActivity(), groups, mPosition);
        lv_group.setAdapter(groupAdapter);
        // 创建一个PopuWidow对象
        popupWindow = new PopupWindow(view, Dp2Px(150), Dp2Px(130));
        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        int xPos = windowManager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth();
        // int xPos = windowManager.getDefaultDisplay().getWidth()/2 - popupWindow.getWidth()/2;
        CLog.e("coder", "windowManager.getDefaultDisplay().getWidth()/2:"
                + windowManager.getDefaultDisplay().getWidth() / 2);
        //
        CLog.e("coder", "windowManager.getDefaultDisplay().getWidth()/2" + windowManager.getDefaultDisplay().getWidth() / 2);
        CLog.e("coder", "popupWindow.getWidth()/2:" + popupWindow.getWidth() / 2);
        CLog.e("coder", "xPos:" + xPos);
        // popupWindow.showAsDropDown(parent,xPos, 25);
        popupWindow.showAsDropDown(mView.findViewById(R.id.AllWorkTextChioce), 0, 8);//
        lv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                //position不等于3和4时，字体背景位置相应变化

                if (position == 0) {
                    if (!AllWorkTextChioce.equals("宝宝作品")) {
                        AllWorkTextChioce.setText("宝宝作品");
                        EventBus.getDefault().post(new EventBus_Model("宝宝作品"));
                        mPosition = 0;
                    }

                    //relation = "friendship";
                } else if (position == 1) {
                    mPosition = 1;
                    //relation = "icare";
                    if (!AllWorkTextChioce.equals("妈妈作品")) {
                        AllWorkTextChioce.setText("妈妈作品");
                        EventBus.getDefault().post(new EventBus_Model("妈妈作品"));
                    }
                }
                if (popupWindow != null) {
                    popupWindow.dismiss();
                }
            }
        });
    }

    //dp 转变为px
    public int Dp2Px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //px转变为dp
    public int Px2Dp(float px) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
