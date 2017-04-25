package com.multshows.Activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.UserAuthentication;
import com.multshows.Fragment.MyInformation_RealName_Fragment;
import com.multshows.Fragment.MyInformation_RealName_No_Fragment;
import com.multshows.Fragment.MyInformation_RealName_Yes_Fragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.CountDownTimerUtils;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.Verification_Utils;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import okhttp3.Call;

/**
 * 实名认证
 */
public class My_Information_RealName_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//取消
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    FrameLayout mFragment;
    //工具
    FragmentManager mFragmentManager;
    //fragment
    MyInformation_RealName_No_Fragment mNoFragment;
    MyInformation_RealName_Yes_Fragment mYesFragment;
    MyInformation_RealName_Fragment mRealNameFragment;

    Bundle build;
    MyApplication mMyApplication;
    Gson mGson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information_real_name);
        EventBus.getDefault().register(My_Information_RealName_Activity.this);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_Information_RealName_Activity.this,R.color.app_topColor);
        mMyApplication= (MyApplication) getApplication();
        initView();
        initData();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_Information_RealName_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_Information_RealName_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.My_Information_RealName_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.My_Information_RealName_title);
        mFragment= (FrameLayout) findViewById(R.id.My_Information_RealName_FrameLayout);
        mFragmentManager=getSupportFragmentManager();

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_Information_RealName_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        getState();




    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideInputManager_Utils.hideInputManager(My_Information_RealName_Activity.this);
                finish();
            }
        });
    }
    /**
     * 功能: 展示实名认证返回结果
     *
     * @param model 实名认证传来的信息
     * 作者: 贾强胜
     * 时间: 2017/2/13 9:28
     */
    @Subscribe
    public void onEvent(ChatEvent_Model model) {
       if("RenZheng".equals(model.getFlag())){
           mRealNameFragment=new MyInformation_RealName_Fragment();
           build = new Bundle();
           build.putInt("type", 6);
           mRealNameFragment.setArguments(build);
           mFragmentManager.beginTransaction().replace(R.id.My_Information_RealName_FrameLayout,mRealNameFragment).commit();
       }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(My_Information_RealName_Activity.this);
    }

    /**
     * 判断申请状态
     *
     */
    private void getState(){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/User/GetAuthen")
                .addParams("userId",Login_Static.myUserID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取用户实名认证失败"+e.toString());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取用户实名认证"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                UserAuthentication bean=mGson.fromJson(Json_Utils.getTemplate(response),UserAuthentication.class);
                                if(bean.getUserId()!=null && !"null".equals(bean.getUserId()) && !"".equals(bean.getUserId())) {
                                    if (bean.getIsAuthenticated() == 0) {
                                        //0未认证通过
                                        mNoFragment = new MyInformation_RealName_No_Fragment();
                                        mFragmentManager.beginTransaction().replace(R.id.My_Information_RealName_FrameLayout, mNoFragment).commit();
                                    } else if (bean.getIsAuthenticated() == 1) {
                                        //1认证通过
                                        mYesFragment = new MyInformation_RealName_Yes_Fragment();
                                        build = new Bundle();
                                        build.putInt("type", 1);
                                        mYesFragment.setArguments(build);
                                        mFragmentManager.beginTransaction().replace(R.id.My_Information_RealName_FrameLayout, mYesFragment).commit();
                                    } else if (bean.getIsAuthenticated() == 2) {
                                        //2认证中
                                        mYesFragment = new MyInformation_RealName_Yes_Fragment();
                                        build = new Bundle();
                                        build.putInt("type", 2);
                                        mYesFragment.setArguments(build);
                                        mFragmentManager.beginTransaction().replace(R.id.My_Information_RealName_FrameLayout, mYesFragment).commit();
                                    }
                                }else {
                                    //还没认证
                                    mRealNameFragment=new MyInformation_RealName_Fragment();
                                    build = new Bundle();
                                    build.putInt("type",0);
                                    mRealNameFragment.setArguments(build);
                                    mFragmentManager.beginTransaction().replace(R.id.My_Information_RealName_FrameLayout,mRealNameFragment).commit();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }


}
