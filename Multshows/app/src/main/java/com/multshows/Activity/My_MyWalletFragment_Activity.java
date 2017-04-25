package com.multshows.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.UserAssetTerm;
import com.multshows.Fragment.My_Information_BabyShip_Fragment;
import com.multshows.Fragment.My_Information_NickName_Fragment;
import com.multshows.Fragment.My_Information_SexChange_Fragment;
import com.multshows.Fragment.My_Kiting_Fragment;
import com.multshows.Fragment.My_Recharge_Fragment;
import com.multshows.Fragment.My_TradingList_Fragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import okhttp3.Call;

/**
 * 我的钱包——总activity
 */
public class My_MyWalletFragment_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTitle;//标题
    FrameLayout mFragment;
    //工具
    FragmentManager mFragmentManager;
    //fragment
    My_Recharge_Fragment mRechargeFragment;//充值
    My_Kiting_Fragment mKitingFragment;//提现
    My_TradingList_Fragment mTradingListFragment;//交易记录

    //传来的类型
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_my_wallet_fragment);
        EventBus.getDefault().register(My_MyWalletFragment_Activity.this);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_MyWalletFragment_Activity.this,R.color.app_topColor);
        initView();
        initData();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_MyWallet_Fragment_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_MyWallet_Fragment_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.My_MyWallet_Fragment_ParentsLayout);
        mTitle= (TextView) findViewById(R.id.My_MyWallet_Fragment_title);
        mFragment= (FrameLayout) findViewById(R.id.My_MyWallet_Fragment_fragment);
        mFragmentManager=getSupportFragmentManager();

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_MyWalletFragment_Activity.this,mReturnLayout,mParentLayout,mReturn,mTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        Intent intent=getIntent();
        type=intent.getStringExtra("MyWallet_type");
        if(type.equals("Recharge")){
            mTitle.setText("充值");
            mRechargeFragment=new My_Recharge_Fragment();
            mFragmentManager.beginTransaction().replace(R.id.My_MyWallet_Fragment_fragment,mRechargeFragment).commit();
        }else if(type.equals("Kiting")){
            mTitle.setText("提现");
            mKitingFragment=new My_Kiting_Fragment();
            mFragmentManager.beginTransaction().replace(R.id.My_MyWallet_Fragment_fragment,mKitingFragment).commit();
        }else if(type.equals("TradingList")){
            mTitle.setText("交易记录");
            mTradingListFragment=new My_TradingList_Fragment();
            mFragmentManager.beginTransaction().replace(R.id.My_MyWallet_Fragment_fragment,mTradingListFragment).commit();
        }


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
    }
    @Subscribe
    public void onEvent(ChatEvent_Model model) {
        if("ReChange".equals(model.getFlag())){
            CLog.e("testing","111");
            mTitle.setText("交易记录");
            mTradingListFragment=new My_TradingList_Fragment();
            mFragmentManager.beginTransaction().replace(R.id.My_MyWallet_Fragment_fragment,mTradingListFragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(My_MyWalletFragment_Activity.this);
    }
}
