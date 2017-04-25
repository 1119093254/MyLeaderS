package com.multshows.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.google.gson.Gson;
import com.multshows.Adapter.HomeFrament_Adapter;
import com.multshows.Adapter.Task_Nolist_Adapter;
import com.multshows.Adapter.Task_list_Adapter;
import com.multshows.Beans.Task;
import com.multshows.Beans.TaskList_Beans;
import com.multshows.Beans.TaskTerm;
import com.multshows.Fragment.base.ScrollAbleFragment;
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

import butterknife.ButterKnife;
import okhttp3.Call;

/**
        * 描述：任务待未完成界面
        * 作者：李萧萧
        * 时间：2016.9/27
        */
public class Home_TaskListNoComplete_Fragment extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer {

    private ListView mListview;
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    PullToRefresh mPullToRefresh;
    List<Task> mList=new ArrayList<>();
    Task_Nolist_Adapter mAdapter;
    private boolean init;//记录界面是否为第一次加载

    int pageIndexs=1;//页数
    Gson mGson=new Gson();
    public static Home_TaskListNoComplete_Fragment newInstance() {
        Home_TaskListNoComplete_Fragment listFragment = new Home_TaskListNoComplete_Fragment();
        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        mListview = (ListView) view.findViewById(R.id.AllworkListview);
        mPullToRefresh= (PullToRefresh) view.findViewById(R.id.Fragment_Work_Refresh);
        mNoView=view.findViewById(R.id.Fragment_Work_No_View);
        mNoImage= (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText= (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton= (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无数据!");
        mNoButton.setVisibility(View.GONE);
        mListview.setEmptyView(mNoView);
        init = true;//第一次加载
       // mListview.setAdapter(new MyAdapter(getActivity(), strlist));
        mListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
                        getOkTaskList(pageIndexs);
                    }
                },2000);
            }
        });
        //上拉加载
        mPullToRefresh.setOnFooterRefreshListener(new PullToRefresh.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefresh view) {
                CLog.e("testing",pageIndexs+"页码");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getOkTaskList(pageIndexs);
                    }
                },2000);

            }
        });
        return view;
    }


    @Override
    public View getScrollableView() {
        return mListview;
    }
    /**
     * 功能：该frament第一次显示的时候，加载网络数据
     * * 参数：无
     * 返回：无
     */
    private void getservciesData() {
        mAdapter=new Task_Nolist_Adapter(getActivity(),mList);
        mListview.setAdapter(mAdapter);
        getOkTaskList(1);
    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (getUserVisibleHint()) {
            getdata();
        }
    }

    /**
     * 功能：加载网络数据
     * 参数：无
     * 返回：无
     */
    private void getdata() {
        if (init) {
            init = false;//第一次加载数据后，设置为false,不在加载数据
            getservciesData();
        }
    }

    /**
     * 功能：该界面是否为显示状态
     * 参数：isVisibleToUser 是否正在显示
     * 返回：无
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        //界面显示的时候加载数据
        if (isVisibleToUser) {
            getdata();
        }
    }

    /**
     * 获取待未完成任务列表
     * @param pageIndex 页数
     */
    private void getOkTaskList(final int pageIndex){
        TaskTerm taskTerm=new TaskTerm();
        taskTerm.setType(1);
        taskTerm.setState(1);
        taskTerm.setPublisherId(Login_Static.myUserID);
        taskTerm.setPageIndex(pageIndex);
        taskTerm.setPageSize(20);
        String data=mGson.toJson(taskTerm);
        CLog.e("testing",data);
        OKHttp.OkHttpPost("/Task/ListTask", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","获取待未完成任务列表失败"+e.toString());
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","获取待未完成任务列表"+response);
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
                            Task task=mGson.fromJson(jsonArray.getString(i),Task.class);
                            mList.add(task);
                        }

                        mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
