package com.multshows.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Activity.Add_BankCord_Activity;
import com.multshows.Activity.BoundYMD_Activity;
import com.multshows.Activity.My_Kitting_Activity;
import com.multshows.Adapter.BoundBankList_Adapter;
import com.multshows.Beans.User;
import com.multshows.Beans.UserAuthentication;
import com.multshows.Beans.UserBank;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Views.HintText_Dialog;
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
 * 提现，一麻袋
 */
public class Kitting_BoundBankMom_Fragment extends Fragment{
    View mView;
    Context mContext;
    MyListView mListView;
    Button mAddBank;
    //适配器
    BoundBankList_Adapter mBankListAdapter;
    //数据集合
    List<UserBank> mList=new ArrayList<>();

    MyApplication myApplication=new MyApplication();
    Gson mGson=new Gson();
    MyApplication mMyApplication;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.kitting_bound_bank_mom,null);
        mMyApplication= (MyApplication) getActivity().getApplication();
        initView();
        initData();
        initListen();
        return mView;
    }

    private void initView() {
        mListView= (MyListView) mView.findViewById(R.id.Bound_BankList_Mom_MyListView);
        mAddBank= (Button) mView.findViewById(R.id.Bound_BankList_Mom_AddBank);
    }

    private void initData() {
        mBankListAdapter=new BoundBankList_Adapter(mContext,mList);
        mListView.setAdapter(mBankListAdapter);

    }

    private void initListen() {
        //添加银行卡
        mAddBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext,Add_BankCord_Activity.class);
                startActivity(intent);
            }
        });
        //
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CLog.e("testing","bankID:"+mList.get(position).getId());
                setDefault(mList.get(position).getId());
                Intent intent=new Intent(mContext, My_Kitting_Activity.class);
                intent.putExtra("UserBank",mList.get(position));
                getActivity().setResult(1,intent);
                getActivity().finish();
            }
        });

    }

    @Override
    public void onResume() {
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
