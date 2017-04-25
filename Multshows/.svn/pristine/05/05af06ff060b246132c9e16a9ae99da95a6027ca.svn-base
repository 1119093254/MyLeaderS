package com.multshows.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.Friends_Order_Adapter;
import com.multshows.Beans.Message;
import com.multshows.Beans.Order;
import com.multshows.Beans.UserBase;
import com.multshows.Beans.UserMessageTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.MyApplication;
import com.multshows.Views.PullToRefresh;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 消息——订单消息
 */
public class Friends_Order_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    ListView mListView;
    TextView mTitle;//标题
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    private PullToRefresh mPullToRefresh;
    //适配器
    Friends_Order_Adapter mOrderAdapter;
    //数据集合
    List<Message> mList=new ArrayList<>();
    Gson mGson=new Gson();
    int pageIndexs=1;
    Intent mIntent;
    String type="";//传来的参数类型
    MyApplication mMyApplication;
    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();
    String mom_type="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_order);
        //沉浸式
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Friends_Order_Activity.this, R.color.app_topColor);
        mMyApplication= (MyApplication) getApplication();
        mom_type=mSharedPreferences.Get_PREFS(Friends_Order_Activity.this,"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.Friends_Order_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Friends_Order_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Friends_Order_ParentsLayout);
        mTitle= (TextView) findViewById(R.id.Friends_Order_Title);
        mListView= (ListView) findViewById(R.id.Friends_Order_ListView);
        mPullToRefresh= (PullToRefresh) findViewById(R.id.Friends_Order_Refresh);
        mNoView=findViewById(R.id.Friends_Order_No_View);
        mNoImage= (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText= (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton= (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无消息!");
        mNoButton.setVisibility(View.GONE);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Friends_Order_Activity.this,mReturnLayout,mParentLayout,mReturn,mTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mIntent=getIntent();
        type=mIntent.getStringExtra("type");
        mOrderAdapter=new Friends_Order_Adapter(Friends_Order_Activity.this,mList,type);
        mListView.setAdapter(mOrderAdapter);
        mListView.setEmptyView(mNoView);
        if("task".equals(type)){
            //任务消息
            setMessage("5");
            mTitle.setText("任务消息");
            getOrderMessage(pageIndexs,5);
        }else {
            //订单消息
            setMessage("4");
            mTitle.setText("订单消息");
            getOrderMessage(pageIndexs,4);
        }
    }



    /**
     * 事件监听
     */
    private void initListen() {
        //返回事件
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //下拉加载
        mPullToRefresh.setOnHeaderRefreshListener(new PullToRefresh.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefresh view) {
                pageIndexs=1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if("task".equals(type)){
                            //任务消息
                            getOrderMessage(pageIndexs,5);
                        }else {
                            //订单消息
                            getOrderMessage(pageIndexs,4);
                        }

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
                        if("task".equals(type)){
                            //任务消息
                            getOrderMessage(pageIndexs,5);
                        }else {
                            //订单消息
                            getOrderMessage(pageIndexs,4);
                        }
                    }
                },2000);

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!"task".equals(type)) {
                    Order mOrder = mList.get(position).getOrders();
                    UserBase buy = mOrder.getBuyer();
                    if(buy!=null) {
                        if (Login_Static.myUserID.equals(buy.getUserId())) {
                            //我是买家
                            Intent intent = new Intent(Friends_Order_Activity.this, OrderInformation_Activity.class);
                            intent.putExtra("orderId", mOrder.getId());
                            startActivity(intent);
                        } else {
                            //我是卖家
                            Intent intent = new Intent(Friends_Order_Activity.this, OrderInformation_out_Activity.class);
                            intent.putExtra("orderId", mOrder.getId());
                            startActivity(intent);
                        }
                    }
                }else {
                    CLog.e("testing","返回到这里了www！"+mom_type);
                    if("mom".equals(mom_type)){
                        Intent intent = new Intent(Friends_Order_Activity.this, Task_Show_Activity.class);
                        intent.putExtra("positions",1);
                        startActivity(intent);
                    }else if("baby".equals(mom_type)){
                        CLog.e("testing","返回到这里了！");
                        mIntent.putExtra("results","task");
                        setResult(9,mIntent);
                        finish();
                    }

                }
            }
        });

    }

    /**
     * 订单消息列表
     */
    private void getOrderMessage(final int pageIndex,int flag){
        UserMessageTerm userMessageTerm=new UserMessageTerm();
        userMessageTerm.setType(flag);
        userMessageTerm.setReceiverId(Login_Static.myUserID);
        userMessageTerm.setPageIndex(pageIndex);
        userMessageTerm.setPageSize(20);
        String data=mGson.toJson(userMessageTerm);
        CLog.e("testing","获取订单消息列表data"+data);
        OKHttp.OkHttpPost("/User/SelectUserMessage", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","获取订单消息列表失败"+e.toString());
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","获取订单消息列表data"+response);
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
                try {
                    if(Json_Utils.getCode(response)==0){
                        if (pageIndex == 1) {
                            mList.clear();
                        }
                        pageIndexs++;
                        JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                        Message message;
                        for (int i = 0; i < jsonArray.length(); i++) {
                           message=mGson.fromJson(jsonArray.getString(i),Message.class);
                            mList.add(message);
                        }
                        mOrderAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    /**
     * 更新消息状态为已读
     */
    private void setMessage(String types){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/User/ChangeMessageRead")
                .addParams("type",types)
                .addParams("userId", Login_Static.myUserID)
                .addParams("msgtype","0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","更新消息状态为已读失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","更新消息状态为已读"+response);
                    }
                });

    }
}
