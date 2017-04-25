package com.multshows.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.My_Order_Logistics_Adapter;
import com.multshows.Beans.Logistics;
import com.multshows.Beans.Logistics_item;
import com.multshows.Beans.Order;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Views.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 我的订单——查看物流
 */
public class My_Order_Logistics_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mNumber;//快递编号
    TextView mCompany;//快递公司
    TextView mPhone;//官方电话
    TextView mTitle;//标题
    MyListView mMyListView;
    //适配器
    My_Order_Logistics_Adapter mLogisticsAdapter;
    //数据集合
    List<Logistics_item> mList=new ArrayList<>();
    Gson mGson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_logistics);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_Order_Logistics_Activity.this,R.color.app_topColor);
        initView();
        initData();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_Order_Logistics_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_Order_Logistics_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.My_Order_Logistics_ParentsLayout);
        mTitle= (TextView) findViewById(R.id.My_Order_Logistics_Title);
        mNumber= (TextView) findViewById(R.id.My_Order_Logistics_number);
        mCompany= (TextView) findViewById(R.id.My_Order_Logistics_company);
        mPhone= (TextView) findViewById(R.id.My_Order_Logistics_phone);
        mMyListView= (MyListView) findViewById(R.id.My_Order_Logistics_ListView);
        /**
         * 动态改变布局颜色
         *
         */
        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_Order_Logistics_Activity.this,mReturnLayout,mParentLayout,mReturn,mTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        Intent intent=getIntent();
        Order order= (Order) intent.getSerializableExtra("order");
        mLogisticsAdapter=new My_Order_Logistics_Adapter(My_Order_Logistics_Activity.this,mList);
        mMyListView.setAdapter(mLogisticsAdapter);
        //判断传来的order是不是为空
        if(order!=null){
            mNumber.setText(order.getExpressNumber());
            mCompany.setText(order.getExpressName());
            if(order.getSeller()!=null)
            mPhone.setText(order.getSeller().getMobile());
        }
        getLogistics(order.getExpressNumber());
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

    /**
     * 获取物流信息
     */
    private void getLogistics(String number){
        OkHttpUtils.get().url("http://ali-deliver.showapi.com/showapi_expInfo?com=auto&nu="+number)
                .addHeader("Authorization","APPCODE 52fad027983f4392a00cd09076b80df4")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取物流信息失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取物流信息"+response);
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(response);
                            int message=jsonObject.getInt("showapi_res_code");
                            if(message==0){
                                Logistics logistics=mGson.fromJson(jsonObject.getString("showapi_res_body"),Logistics.class);
                                for (int i = 0; i < logistics.getData().size(); i++) {
                                    Logistics_item bean=logistics.getData().get(i);
                                    mList.add(bean);
                                }
                                mLogisticsAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}
