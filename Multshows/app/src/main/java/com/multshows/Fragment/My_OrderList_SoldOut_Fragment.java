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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Activity.OrderInformation_Activity;
import com.multshows.Adapter.My_OrderList_Adapter;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.My_OrderList_Beans;
import com.multshows.Beans.Order;
import com.multshows.Beans.OrderEvent_Model;
import com.multshows.Beans.OrderTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Views.PullToRefresh;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 我的订单——买入
 */
public class My_OrderList_SoldOut_Fragment extends Fragment{
    View mView;
    //上下文
    Context mContext;
    ListView mListView;
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    //适配器
    My_OrderList_Adapter mOrderListAdapter;
    Gson mGson=new Gson();
    //数据集合
    List<Order> mList=new ArrayList<>();
    private PullToRefresh mPullToRefresh;
    int pageIndexs=1;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.listview_xml,null);
        initView(mView);
        initData(mView);
        initListen(mView);
        return mView;
    }

    /**
     * 初始化
     */
    private void initView(View view) {
        mListView= (ListView) view.findViewById(R.id.ListView_item_XML);
        mPullToRefresh= (PullToRefresh) view.findViewById(R.id.ListView_item_XML_Refresh);
        mNoView=view.findViewById(R.id.ReWordListtoMe_No_View);
        mNoImage= (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText= (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton= (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("还没有收到订单~");
        mNoButton.setVisibility(View.GONE);
        mListView.setEmptyView(mNoView);
    }

    /**
     * 数据处理
     */
    private void initData(View view) {
        mOrderListAdapter=new My_OrderList_Adapter(mContext,getActivity(),mList,1);
        mListView.setAdapter(mOrderListAdapter);
        getSoldOut(1);
    }

    /**
     * 事件监听
     */
    private void initListen(View view) {
        //下拉加载
        mPullToRefresh.setOnHeaderRefreshListener(new PullToRefresh.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefresh view) {
                pageIndexs=1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getSoldOut(pageIndexs);
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
                        getSoldOut(pageIndexs);
                    }
                },2000);

            }
        });
    }
    /*@Subscribe (threadMode = ThreadMode.MAIN)*/
   @Subscribe
    public void onEvent(OrderEvent_Model model) {
        int po=model.getPosition();
        if("Price".equals(model.getFlag())){
            CLog.e("testing","修改价格到了"+model.getPosition()+"||"+Double.parseDouble(model.getUrl())+":"+
                    Double.parseDouble(model.getIdle_Place()));
            mList.get(po).setPrice(Double.parseDouble(model.getUrl()));
            mList.get(po).setExpressFee(Double.parseDouble(model.getIdle_Place()));
            mList.get(po).setRealPrice(Double.parseDouble(model.getUrl())+
                    Double.parseDouble(model.getIdle_Place()));
            mOrderListAdapter.notifyDataSetChanged();
        }else if("Express".equals(model.getFlag())){
            CLog.e("testing","修改物流到了"+po+"||"+model.getUrl()+":"+
                    model.getIdle_Place());
            mList.get(po).setState(4);//"已发货"
            mList.get(po).setStateName("已发货");
            mList.get(po).setExpressName(model.getUrl());
            mList.get(po).setExpressNumber(model.getIdle_Place());
            mOrderListAdapter.notifyDataSetChanged();
        }else if("order_out".equals(model.getFlag())){
            mList.remove(po);
            mList.add(model.getOrder());
            mOrderListAdapter.notifyDataSetChanged();
        }
    }
    /**
     * 我卖出  的订单数据
     * @param pageIndex
     */
    private void getSoldOut(final int pageIndex){
        if(Login_Static.myUserID!=null && !"".equals(Login_Static.myUserID)) {
            OrderTerm orderTerm = new OrderTerm();
            orderTerm.setSellerId(Login_Static.myUserID);
            orderTerm.setPageIndex(pageIndex);
            orderTerm.setPageSize(20);
            String data = mGson.toJson(orderTerm);
            OKHttp.OkHttpPost("/Order/GetListOrder", "", data, new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    CLog.e("testing", "获取卖出订单失败：" + e.toString());
                    mPullToRefresh.onHeaderRefreshComplete();
                    mPullToRefresh.onFooterRefreshComplete();
                }

                @Override
                public void onResponse(String response, int id) {
                    CLog.e("testing", "获取卖出订单：" + response);
                    mPullToRefresh.onHeaderRefreshComplete();
                    mPullToRefresh.onFooterRefreshComplete();
                    try {
                        if (Json_Utils.getCode(response) == 0) {
                            if (pageIndex == 1) {
                                mList.clear();
                            }
                            pageIndexs++;
                            JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Order order = mGson.fromJson(jsonArray.getString(i), Order.class);
                                mList.add(order);
                            }

                            mOrderListAdapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
