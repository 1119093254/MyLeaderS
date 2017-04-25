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
import com.multshows.Adapter.MyHome_Idle_adapter;
import com.multshows.Adapter.MyHome_Task_adapter;
import com.multshows.Beans.MyHome_Idle_Beans;
import com.multshows.Beans.MyHome_Task_Beans;
import com.multshows.Beans.Task;
import com.multshows.Beans.TaskTerm;
import com.multshows.Fragment.base.ScrollAbleFragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.PullToRefresh;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import okhttp3.Call;

/**
        * 描述：我的主页  任务fragment
        * 作者：李萧萧
        * 时间：2016.9/27
        */
public class MyHome_Task_Fragment extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer {

    private ListView mAllTaskListview;
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    private boolean init;//记录界面是否为第一次加载
    MyHome_Task_adapter mAdapter;
    int pageIndexs=1;
    List<Task> mList=new ArrayList<>();
    private PullToRefresh mPullToRefresh;//刷新控件
    SaveSharedPreferences saveSharedPreferences=new SaveSharedPreferences();
    Gson mGson=new Gson();
    public static MyHome_Task_Fragment newInstance() {
        MyHome_Task_Fragment listFragment = new MyHome_Task_Fragment();
        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        init=true;
        mAllTaskListview = (ListView) view.findViewById(R.id.AllworkListview);
        mPullToRefresh = (PullToRefresh) view.findViewById(R.id.Fragment_Work_Refresh);
        mNoView=view.findViewById(R.id.Fragment_Work_No_View);
        mNoImage= (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText= (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton= (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无数据!");
        mNoButton.setVisibility(View.GONE);
        mAllTaskListview.setEmptyView(mNoView);


        init = true;//第一次加载
       // mListview.setAdapter(new MyAdapter(getActivity(), strlist));
        mAllTaskListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "点击item" + position, Toast.LENGTH_SHORT).show();
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
                        getTaskList(pageIndexs);
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
                        getTaskList(pageIndexs);
                    }
                }, 2000);

            }
        });
        return view;
    }

    @Override
    public View getScrollableView() {
        return mAllTaskListview;
    }
    /**
     * 功能：该frament第一次显示的时候，加载网络数据
     * * 参数：无
     * 返回：无
     */
    private void getservciesData() {
        getTaskList(pageIndexs);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    /**
     * 获取已完成任务列表
     */
    private void getTaskList(int pageIndex){
        TaskTerm taskTerm=new TaskTerm();
        taskTerm.setType(1);
        taskTerm.setState(3);
        taskTerm.setExecutorId(saveSharedPreferences.Get_PREFS(getActivity(),"otherId"));
        taskTerm.setPageIndex(pageIndex);
        taskTerm.setPageSize(20);
        String data=mGson.toJson(taskTerm);
        CLog.e("testing",data);
        OKHttp.OkHttpPost("/Task/ListTask", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
                CLog.e("testing","获取已确认任务列表失败"+e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","获取已确认任务列表"+response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        pageIndexs++;
                        JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Task task=mGson.fromJson(jsonArray.getString(i),Task.class);
                            mList.add(task);
                        }
                        mPullToRefresh.onHeaderRefreshComplete();
                        mPullToRefresh.onFooterRefreshComplete();
                        if(mAdapter==null){
                            mAdapter=new MyHome_Task_adapter(getActivity(),mList);
                            mAllTaskListview.setAdapter(mAdapter);
                        }else mAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
