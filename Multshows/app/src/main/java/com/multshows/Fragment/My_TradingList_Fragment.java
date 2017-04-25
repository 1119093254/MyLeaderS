package com.multshows.Fragment;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multshows.Activity.HistoryList_Activity;
import com.multshows.Adapter.My_TradingList_Adapter;
import com.multshows.Beans.My_TradingList_Beans;
import com.multshows.Beans.OrderPaymentTerm;
import com.multshows.Beans.PayRecord;
import com.multshows.Beans.PayRecordTerm;
import com.multshows.Beans.UserAssetTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Views.PullToRefresh;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 交易记录
 */
public class My_TradingList_Fragment extends Fragment{
    //上下文
    Context mContext;
    View mView;
    //控件
    ListView mListView;
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    private PullToRefresh mPullToRefresh;
    int pageIndexs=1;
    //适配器
    My_TradingList_Adapter mListAdapter;
    //数据集合
    List<PayRecord> mList=new ArrayList<>();
    Gson mGson=new Gson();
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.my_trading_list_fragment,null);
        mListView= (ListView) mView.findViewById(R.id.my_TradingList_fragment_ListView);
        mPullToRefresh= (PullToRefresh) mView.findViewById(R.id.my_TradingList_fragment_Refresh);
        mNoView=mView.findViewById(R.id.my_TradingList_fragment_No_View);
        mNoImage= (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText= (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton= (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无交易记录!");
        mNoButton.setVisibility(View.GONE);
        mListView.setEmptyView(mNoView);
        initData();
        initListen();
        return mView;
    }

    /**
     * 数据
     */
    private void initData() {
        mListAdapter=new My_TradingList_Adapter(mContext,mList);
        mListView.setAdapter(mListAdapter);
        getPayment(1);
    }

    /**
     * 事件处理
     */
    private void initListen() {
        //下拉加载
        mPullToRefresh.setOnHeaderRefreshListener(new PullToRefresh.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefresh view) {
                pageIndexs=1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPayment(pageIndexs);
                    }
                },2000);


            }
        });

        mPullToRefresh.setOnFooterRefreshListener(new PullToRefresh.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefresh view) {
                CLog.e("testing",pageIndexs+"页码");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getPayment(pageIndexs);
                    }
                },2000);

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(mContext, HistoryList_Activity.class);
                intent.putExtra("PayRecord",mList.get(position));
                startActivity(intent);
            }
        });
    }


    /**
     * 获取交易单
     */
    private void getPayment(final int pageIndex){
        PayRecordTerm payRecordTerm=new PayRecordTerm();
        payRecordTerm.setUserId(Login_Static.myUserID);
        payRecordTerm.setPageIndex(pageIndex);
        payRecordTerm.setPageSize(20);
        payRecordTerm.setType("0");
        String data = mGson.toJson(payRecordTerm);
        CLog.e("testing","获取交易单列表data"+data);
        OKHttp.OkHttpPost("/Order/GetListPayRecord", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","获取交易单列表失败"+e.toString());
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","获取交易单列表"+response);
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
                try {
                    if(Json_Utils.getCode(response)==0){
                        if(pageIndex==1){
                            mList.clear();
                        }
                        pageIndexs++;
                        JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            PayRecord bean=mGson.fromJson(jsonArray.getString(i),PayRecord.class);
                            mList.add(bean);
                        }
                        mListAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
