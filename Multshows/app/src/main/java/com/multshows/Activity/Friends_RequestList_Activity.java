package com.multshows.Activity;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.Friends_RequestList_Adapter;
import com.multshows.Beans.Friends_RequestList_Beans;
import com.multshows.Beans.Friends_friend_Beans;
import com.multshows.Beans.UserFriendTerm;
import com.multshows.Beans.UserFriend_Beans;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Views.MyApplication;
import com.multshows.Views.PullToRefresh;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import okhttp3.Call;

/**
 * 好友申请列表
 */
public class Friends_RequestList_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    ListView mListView;
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    //适配器
    Friends_RequestList_Adapter mRequestListAdapter;
    //数据集合
    List<UserFriend_Beans> mList = new ArrayList<>();
    Gson mGson = new Gson();
    private PullToRefresh mPullToRefresh;
    int pageIndexs = 1;
    MyApplication mMyApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_requestlist);
        MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(Friends_RequestList_Activity.this, R.color.app_topColor);
        mMyApplication= (MyApplication) getApplication();
        setMessage();
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.friends_requestList_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.friends_requestList_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.friends_requestList_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.friends_requestList_TopTitle);
        mListView = (ListView) findViewById(R.id.friends_requestList_ListView);
        mPullToRefresh = (PullToRefresh) findViewById(R.id.friends_requestList_Refresh);
        mNoView = findViewById(R.id.friends_requestList_No_View);
        mNoImage = (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText = (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton = (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无申请消息!");
        mNoButton.setVisibility(View.GONE);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Friends_RequestList_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mRequestListAdapter = new Friends_RequestList_Adapter(Friends_RequestList_Activity.this, mList);
        mListView.setAdapter(mRequestListAdapter);
        mListView.setEmptyView(mNoView);
        getRequestList(1);
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
                pageIndexs = 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRequestList(pageIndexs);
                    }
                }, 2000);


            }
        });

        mPullToRefresh.setOnFooterRefreshListener(new PullToRefresh.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefresh view) {
                CLog.e("testing", pageIndexs + "页码");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRequestList(pageIndexs);
                    }
                }, 2000);

            }
        });

    }

    /**
     * 好友申请访问服务器
     */
    private void getRequestList(final int pageIndex) {
        UserFriendTerm userFriendTerm = new UserFriendTerm();
        userFriendTerm.setUserId(Login_Static.myUserID);
        userFriendTerm.setPageSize(20);
        userFriendTerm.setPageIndex(pageIndex);
        String data = mGson.toJson(userFriendTerm);
        CLog.e("testing", "获取好友申请列表data:" + data);
        OKHttp.OkHttpPost("/User/GetFriendList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "获取好友申请列表失败：" + e.toString());
                //关闭刷新
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "获取好友申请列表：" + response);
                //关闭刷新
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
                            UserFriend_Beans bean = mGson.fromJson(jsonArray.getString(i), UserFriend_Beans.class);
                            //IsSelf  1我发出的请求(不显示) 0他人发给我的(显示)
                            //if (bean.getState() != 1 && bean.getIsSelf() == 0) {
                            if (bean.getIsSelf() == 0) {
                                mList.add(bean);
                            }
                        }
                        mRequestListAdapter.notifyDataSetChanged();
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
    private void setMessage(){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/User/ChangeMessageRead")
                .addParams("type","6")
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
