package com.multshows.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.My_BankList_Adapter;
import com.multshows.Beans.UserBank;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 我的银行卡
 */
public class My_BankList_Activity extends AppCompatActivity {
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    MyListView mListView;
    Button mAddBank;//添加银行卡
    TextView mTake;//管理
    //适配器
    My_BankList_Adapter mBankListAdapter;
    //数据集合
    List<UserBank> mList=new ArrayList<>();
    int deleteType=0;

    MyApplication mMyApplication;
    Gson mGson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_bank_list);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_BankList_Activity.this,R.color.app_topColor);
        mMyApplication= (MyApplication) getApplication();
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_BankList_return);
        mListView= (MyListView) findViewById(R.id.My_BankList_MyListView);
        mAddBank= (Button) findViewById(R.id.My_BankList_AddBank);
        mTake= (TextView) findViewById(R.id.My_BankList_Take);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_BankList_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.My_BankList_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.My_BankList_TopTitle);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_BankList_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle,mTake);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mBankListAdapter=new My_BankList_Adapter(My_BankList_Activity.this,mList,deleteType);
        mListView.setAdapter(mBankListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBankList();
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
        //添加银行卡
        mAddBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(My_BankList_Activity.this,Add_BankCord_Activity.class);
                startActivity(intent);
            }
        });
        //管理
        mTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteType==0) {
                    mTake.setText("取消");
                    deleteType = 1;
                    mBankListAdapter=new My_BankList_Adapter(My_BankList_Activity.this,mList,deleteType);
                    mListView.setAdapter(mBankListAdapter);
                    mBankListAdapter.notifyDataSetChanged();

                }else {
                    mTake.setText("管理");
                    deleteType = 0;
                    mBankListAdapter=new My_BankList_Adapter(My_BankList_Activity.this,mList,deleteType);
                    mListView.setAdapter(mBankListAdapter);
                    mBankListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    /**
     * 获取银行卡列表
     */
    private void getBankList(){
        if(Login_Static.myUserID!=null && !"".equals(Login_Static.myUserID)) {
            OkHttpUtils.get().url(mMyApplication.getUrl() + "/User/GetUserBankList")
                    .addParams("userId", Login_Static.myUserID)
                    .addParams("isdefault", "-1")
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            CLog.e("testing", "获取银行卡列表失败" + e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            CLog.e("testing", "获取银行卡列表" + response);
                            try {
                                if (Json_Utils.getCode(response) == 0) {
                                    mList.clear();
                                    UserBank bean;
                                    JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        bean = mGson.fromJson(jsonArray.getString(i), UserBank.class);
                                        mList.add(bean);
                                    }
                                    mBankListAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

    }


}
