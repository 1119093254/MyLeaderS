package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.BoundBankList_Adapter;
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

public class BoundBankList_Activity extends AppCompatActivity {
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    MyListView mListView;
    Button mAddBank;//添加银行卡
    //适配器
    BoundBankList_Adapter mBankListAdapter;
    //数据集合
    List<UserBank> mList=new ArrayList<>();
    Gson mGson=new Gson();
    MyApplication mMyApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bound_banklist);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(BoundBankList_Activity.this,R.color.app_topColor);
        mMyApplication= (MyApplication) getApplication();
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.Bound_BankList_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Bound_BankList_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Bound_BankList_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.Bound_BankList_TopTitle);
        mListView= (MyListView) findViewById(R.id.Bound_BankList_MyListView);
        mAddBank= (Button) findViewById(R.id.Bound_BankList_AddBank);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(BoundBankList_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mBankListAdapter=new BoundBankList_Adapter(BoundBankList_Activity.this,mList);
        mListView.setAdapter(mBankListAdapter);

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
                Intent intent=new Intent(BoundBankList_Activity.this,Add_BankCord_Activity.class);
                startActivity(intent);
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setDefault(mList.get(position).getId());
                Intent intent=new Intent(BoundBankList_Activity.this, My_Kitting_Activity.class);
                intent.putExtra("UserBank",mList.get(position));
                setResult(3,intent);
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBankList();
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
    //设置默认银行卡
    private void setDefault(String bankID){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/User/SetDefaultBank")
                .addParams("bankId",bankID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","设置默认银行卡失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","设置默认银行卡"+response);
                    }
                });
    }
}
