package com.multshows.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.My_Look_Adapter;
import com.multshows.Beans.My_Look_Beans;
import com.multshows.Beans.UserFootprint;
import com.multshows.Beans.UserFootprintTerm;
import com.multshows.Beans.UserFootprint_Add;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Views.PullToRefresh;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 我看过的
 */
public class My_MeLook_Activity extends AppCompatActivity {
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
    My_Look_Adapter mLookAdapter;
    //数据集合
    List<UserFootprint> mList=new ArrayList<>();
    private PullToRefresh mPullToRefresh;

    Gson mGson=new Gson();
    int pageIndexs=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_me_look);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_MeLook_Activity.this,R.color.app_topColor);
        initView();
        initData();
        initListen();
        //addLook("4az1A");
        //下拉加载
        mPullToRefresh.setOnHeaderRefreshListener(new PullToRefresh.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefresh view) {
                pageIndexs=1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getLookMe(pageIndexs);
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
                        getLookMe(pageIndexs);
                    }
                },2000);

            }
        });
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_MeLook_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_MeLook_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.My_MeLook_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.My_MeLook_TopTitle);
        mListView= (ListView) findViewById(R.id.My_MeLook_ListView);
        mNoView=findViewById(R.id.MeLook_No_View);
        mNoImage= (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText= (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton= (Button) mNoView.findViewById(R.id.no_view_Button);
        mPullToRefresh= (PullToRefresh) findViewById(R.id.My_MeLook_Refresh);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无数据!");
        mNoButton.setVisibility(View.GONE);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_MeLook_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        //My_Look_Beans b1=new My_Look_Beans("1","woqu1","http://www.bz55.com/uploads/allimg/130306/1-130306095613.jpg","",0);
        mLookAdapter=new My_Look_Adapter(My_MeLook_Activity.this,mList,0);
        mListView.setAdapter(mLookAdapter);
        mListView.setEmptyView(mNoView);
        getLookMe(1);
    }

    /**
     * 事件处理
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //获取我看过的列表
    private void getLookMe(final int pageIndex){
        UserFootprintTerm userFootprintTerm=new UserFootprintTerm();
        userFootprintTerm.setUserId(Login_Static.myUserID);
        userFootprintTerm.setType(1);
        userFootprintTerm.setPageIndex(pageIndex);
        userFootprintTerm.setPageSize(20);
        String data=mGson.toJson(userFootprintTerm);
        CLog.e("testing","获取我看过的列表data"+data);
        OKHttp.OkHttpPost("/User/GetFootList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","获取我看过的列表失败"+e.toString());
                //关闭刷新
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","获取我看过的列表"+response);
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
                            UserFootprint bean=mGson.fromJson(jsonArray.getString(i),UserFootprint.class);
                            mList.add(bean);
                        }
                        mLookAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}

