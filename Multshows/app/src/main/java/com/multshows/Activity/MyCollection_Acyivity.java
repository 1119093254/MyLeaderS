package com.multshows.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
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
import com.multshows.Adapter.MyCollection_Adapter;
import com.multshows.Adapter.WorkAll_Adapter;
import com.multshows.Beans.Shows;
import com.multshows.Beans.ShowsBase;
import com.multshows.Beans.ShowsFavourite;
import com.multshows.Beans.ShowsFavouriteTerm;
import com.multshows.Beans.ShowsTerm;
import com.multshows.Beans.UserBase;
import com.multshows.Beans.WorkAll_Beans;
import com.multshows.Beans.WorkComment_Beans;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.PullToRefresh;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 我的收藏
 */
public class MyCollection_Acyivity extends AppCompatActivity {

    @Bind(R.id.MyCollection_return)
    ImageView mMyCollectionReturn;//返回图标
    @Bind(R.id.MyCollectionListview)
    ListView mMyCollectionListview;//收藏显示ListView
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    PullToRefresh mPullToRefresh;
    int pageIndexs=1;//页数

    MyCollection_Adapter mAdapter;//我的收藏 适配器
    List<WorkAll_Beans> mList=new ArrayList<>();
    Gson mGson=new Gson();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collection_acyivity);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(MyCollection_Acyivity.this,R.color.app_topColor);
        ButterKnife.bind(this);
        initView();
        initData();
        initListen();
        getservciesData(1);
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturnLayout= (RelativeLayout) findViewById(R.id.my_toplayout);
        mParentLayout= (LinearLayout) findViewById(R.id.myActivityLayout);
        mTopTitle= (TextView) findViewById(R.id.MyCollection_TopTitle);
        mPullToRefresh= (PullToRefresh) findViewById(R.id.MyCollection_Refresh);
        mNoView=findViewById(R.id.My_Collection_No_View);
        mNoImage= (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText= (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton= (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无数据!");
        mNoButton.setVisibility(View.GONE);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(MyCollection_Acyivity.this,mReturnLayout,mParentLayout,mMyCollectionReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mAdapter=new MyCollection_Adapter(MyCollection_Acyivity.this,mList);
        mMyCollectionListview.setAdapter(mAdapter);
        mMyCollectionListview.setEmptyView(mNoView);


    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mMyCollectionReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //回到首页
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //下拉刷新
        mPullToRefresh.setOnHeaderRefreshListener(new PullToRefresh.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefresh view) {
                pageIndexs=1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getservciesData(pageIndexs);
                    }
                },2000);

            }
        });
        //上拉加载
        mPullToRefresh.setOnFooterRefreshListener(new PullToRefresh.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefresh view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getservciesData(pageIndexs);
                    }
                },2000);
            }
        });

    }

    @OnClick(R.id.MyCollection_return)
    public void onClick() {
        finish();
    }
    private void getservciesData(final int pageIndex) {
        ShowsFavouriteTerm mShowsTerm=new ShowsFavouriteTerm();
        mShowsTerm.setUserId(Login_Static.myUserID);
        mShowsTerm.setPageIndex(pageIndex);
        mShowsTerm.setPageSize(20);
        mShowsTerm.setState(1);
        String data=mGson.toJson(mShowsTerm);
        CLog.e("data",data);
        OKHttp.OkHttpPost("/Shows/GetListFavourite", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","收藏列表获取失败"+e.toString());
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testinggg","收藏列表获取:"+response);
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
                try {
                    if(Json_Utils.getCode(response)==0){
                        if(pageIndex==1){
                            mList.clear();
                        }
                        pageIndexs++;
                        JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject array=new JSONObject(jsonArray.getString(i));
                            ShowsFavourite showsFavourite=mGson.fromJson(array.toString(),ShowsFavourite.class);
                            ShowsBase show=showsFavourite.getShows();//作品详情
                            UserBase Author=showsFavourite.getAuthor();//作者信息

                            List<String> imageStrinig3=new ArrayList<>();
                            //对获取到的图片进行处理包装
                            if(show.getShowsPhoto()!=null){
                                String[] imag = show.getShowsPhoto().split(",");
                                CLog.e("",""+imag.length);
                                for (int m = 0 ; m <imag.length ; m++ ) {
                                    imageStrinig3.add(imag[m]);
                                }
                            }
                            String header="";
                            if(Author.getPortrait()!=null && !"null".equals(Author.getPortrait())){
                                header=SubString_Utils.getImageUrl(Author.getPortrait());
                            }

                            WorkAll_Beans beans=new WorkAll_Beans(show.getId(),Author.getNickName(),-1, "",Author.getUserId(),header,0,showsFavourite.getRecordTime()+"",show
                                    .getSerialNumber(),show.getTitle(), "",imageStrinig3,null,null,null,null,null,1);
                            if (!show.getVideoName().equals("")) {
                                beans.setIsVideo(1);
                                beans.setVideoName(show.getVideoName());
                            }
                            mList.add(beans);
                        }
                        mAdapter=new MyCollection_Adapter(MyCollection_Acyivity.this,mList);
                        mMyCollectionListview.setAdapter(mAdapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

}