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
import com.multshows.Activity.MyHomePager_Activity;
import com.multshows.Adapter.My_ReWordList_Adapter;
import com.multshows.Beans.My_ReWordList_Beans;
import com.multshows.Beans.ShowsReward;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
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
 * 我打赏的
 */
public class My_MetoReWord_Fragment extends Fragment{
    //上下文
    Context mContext;
    View mView;
    ListView mListView;
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    //数据集合
    List<ShowsReward> mList=new ArrayList<>();
    //适配器
    My_ReWordList_Adapter mListAdapter;
    private PullToRefresh mPullToRefresh;
    SaveSharedPreferences saveSharedPreferences=new SaveSharedPreferences();
    int pageIndexs=1;
    Gson mGson=new Gson();

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.listview_xml,null);
        mListView= (ListView) mView.findViewById(R.id.ListView_item_XML);
        mPullToRefresh= (PullToRefresh) mView.findViewById(R.id.ListView_item_XML_Refresh);
        mNoView=mView.findViewById(R.id.ReWordListtoMe_No_View);
        mNoImage= (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText= (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton= (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无数据!");
        mNoButton.setVisibility(View.GONE);
        initData();
        initListen();
        return mView;
    }

    /**
     * 数据
     */
    private void initData() {
        //My_ReWordList_Beans b1=new My_ReWordList_Beans(0,"ooo1","","0000001","20:01","10",true);

        mListAdapter=new My_ReWordList_Adapter(mContext,mList,1);
        mListView.setAdapter(mListAdapter);
        mListView.setEmptyView(mNoView);
        MetogetReWord(1);
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //回到首页
        mNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
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
                        MetogetReWord(pageIndexs);
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
                        MetogetReWord(pageIndexs);
                    }
                },2000);

            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, MyHomePager_Activity.class);
                if(mList.get(position).getAuthor()!=null)
                    saveSharedPreferences.Save_PREFS(mContext, "otherId", mList.get(position).getAuthor().getUserId());
                mContext.startActivity(intent);
            }
        });
    }
    /**
     * 我打赏的 记录
     */
    private void MetogetReWord(final int pageIndex){
        MyApplication myApplication= (MyApplication) getActivity().getApplication();
        OkHttpUtils.get().url(myApplication.getUrl()+"/Statis/GetUserRewardList")
                .addParams("userId", Login_Static.myUserID)
                .addParams("pageIndex", pageIndex+"")
                .addParams("pageSize", "20")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取我打赏的记录失败："+e.toString());
                        mPullToRefresh.onHeaderRefreshComplete();
                        mPullToRefresh.onFooterRefreshComplete();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取我打赏的记录："+response);
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
                                    ShowsReward showsReward=mGson.fromJson(jsonArray.getString(i),ShowsReward.class);
                                    mList.add(showsReward);
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
