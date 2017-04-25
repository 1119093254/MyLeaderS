package com.multshows.Activity;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multshows.Beans.UserAPI;
import com.multshows.Fragment.My_Information_BabyShip_Fragment;
import com.multshows.Fragment.My_Information_NickName_Fragment;
import com.multshows.Fragment.My_Information_SexChange_Fragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 个人信息修改界面
 */
public class My_Information_Change_Activity extends AppCompatActivity implements My_Information_SexChange_Fragment.SendSexMessage{
    //控件
    ImageView mReturn;
    LinearLayout mParentLayout;//父布局
    TextView mTitle;
    FrameLayout mFragment;
    RelativeLayout mLayout;//返回布局
    //工具
    FragmentManager mFragmentManager;
    //fragment
    My_Information_BabyShip_Fragment mBabyShipFragment;
    My_Information_SexChange_Fragment mSexChangeFragment;
    My_Information_NickName_Fragment mNickNameFragment;
    //传来的类型
    String type;
    String name="";

    //修改性别传来的信息
    int sex=-1;
    Gson mGson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_inforation_change);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_Information_Change_Activity.this,R.color.app_topColor);
        initView();
        initData();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_Information_Change_return);
        mParentLayout= (LinearLayout) findViewById(R.id.My_Information_Change_ParentsLayout);
        mTitle= (TextView) findViewById(R.id.My_Information_Change_title);
        mFragment= (FrameLayout) findViewById(R.id.My_Information_Change_fragment);
        mLayout= (RelativeLayout) findViewById(R.id.My_Information_Change_returnLayout);
        mFragmentManager=getSupportFragmentManager();

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_Information_Change_Activity.this,mLayout,mParentLayout,mReturn,mTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        Intent intent=getIntent();
        Bundle bundle=new Bundle();
        type=intent.getStringExtra("Information_type");
        if(type.equals("BabyShip")){
            mTitle.setText("和宝贝关系");
            mBabyShipFragment=new My_Information_BabyShip_Fragment();
            mFragmentManager.beginTransaction().replace(R.id.My_Information_Change_fragment,mBabyShipFragment).commit();
        }else if(type.equals("SexChange")){
            //更改性别
            mTitle.setText("修改性别");
            mSexChangeFragment=new My_Information_SexChange_Fragment();
            mFragmentManager.beginTransaction().replace(R.id.My_Information_Change_fragment,mSexChangeFragment).commit();
        }else if(type.equals("NickName")){
            mLayout.setVisibility(View.GONE);
            //更换昵称
            name=intent.getStringExtra("name");
            mNickNameFragment=new My_Information_NickName_Fragment();
            bundle.putString("nickName",name);
            mNickNameFragment.setArguments(bundle);
            mFragmentManager.beginTransaction().replace(R.id.My_Information_Change_fragment,mNickNameFragment).commit();
        }

    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回（返回时访问网络修改性别，无论成功失败都会返回出去的）
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sex!=-1){
                    UserAPI userAPI = new UserAPI();
                    userAPI.setSex(sex);
                    userAPI.setUserId(Login_Static.myUserID);
                    String data=mGson.toJson(userAPI);
                    CLog.e("testing","修改性别失败data:"+data);
                    OKHttp.OkHttpPost("?type=7","data", data, new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            CLog.e("testing","修改性别失败:"+e.toString());
                            Toast.makeText(My_Information_Change_Activity.this, "修改失败", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            CLog.e("testing","修改性别:"+response);
                            try {
                                if(Json_Utils.getCode(response)==0){
                                    Login_Static.mAccount.setSex(sex);
                                    finish();
                                }else {
                                    Toast.makeText(My_Information_Change_Activity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }else {
                    finish();
                }
            }
        });
    }

    /**
     * 回调结果获取
     * @param sex
     */
    @Override
    public void setSexMessage(int sex) {
        this.sex=sex;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(sex!=-1){
                /**
                 * 通过按返回键
                 * 修改性别访问网络
                 */
                UserAPI userAPI = new UserAPI();
                userAPI.setSex(sex);
                userAPI.setUserId(Login_Static.myUserID);
                String data=mGson.toJson(userAPI);
                OKHttp.OkHttpPost("?type=7","data",  data, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","修改性别失败:"+e.toString());
                        Toast.makeText(My_Information_Change_Activity.this, "修改失败", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","修改性别:"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                Login_Static.mAccount.setSex(sex);
                                finish();
                            }else {
                                Toast.makeText(My_Information_Change_Activity.this, "修改失败", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }else {
                finish();
            }

        }
        return true;
    }
}
