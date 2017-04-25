package com.multshows.Fragment;

import android.content.Intent;
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

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.google.gson.Gson;
import com.multshows.Activity.Activity_Details_Activity;
import com.multshows.Adapter.MyHome_Activity_adapter;
import com.multshows.Beans.Activity;
import com.multshows.Beans.ActivityTerm;
import com.multshows.Beans.MyHome_Activity_Beans;
import com.multshows.Fragment.base.ScrollAbleFragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.DateDeal;
import com.multshows.Utils.Json_Utils;
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

import okhttp3.Call;

/**
        * 描述：我报名的活动
        * 作者：李萧萧
        * 时间：2016.9/27
        */
public class Home_AcyivityListMySignup_Fragment extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer {

    private ListView mAllTaskListview;
    MyHome_Activity_adapter mAdapter;
    List<MyHome_Activity_Beans> mList;
    private boolean init;//记录界面是否为第一次加载
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    private PullToRefresh mPullToRefresh;//刷新控件
    int pageIndexs = 1;//页码
    Gson mGson = new Gson();
    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();
    String type="";
    String MyID="";
    public static Home_AcyivityListMySignup_Fragment newInstance() {
        Home_AcyivityListMySignup_Fragment listFragment = new Home_AcyivityListMySignup_Fragment();
        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        type=mSharedPreferences.Get_PREFS(getActivity(),"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        if("mom".equals(type)){
            MyID=Login_Static.myUserID;
        }else {
            if(Login_Static.mAccount!=null)
            MyID=Login_Static.mAccount.getParentId();
        }
        mAllTaskListview = (ListView) view.findViewById(R.id.AllworkListview);
        mPullToRefresh= (PullToRefresh) view.findViewById(R.id.Fragment_Work_Refresh);
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
                if(mList.get(position).getState()==1){
                    Intent mIntent = new Intent(getActivity(), Activity_Details_Activity.class);
                    mIntent.putExtra("activityId", mList.get(position).getId());
                    startActivity(mIntent);
                }
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
                        GetListActivity();
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
                        GetListActivity();
                    }
                },2000);

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
        //TaskList_Beans beans=new TaskList_Beans("http://img06.tooopen.com/images/20160921/tooopen_sy_179583447187.jpg","拖地","本任务奖励金额为¥5.00",true,"");
        mList=new ArrayList<>();
        mAdapter=new MyHome_Activity_adapter(getActivity(),mList);
        mAllTaskListview.setAdapter(mAdapter);
        GetListActivity();
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

    private void GetListActivity() {
        ActivityTerm activityTerm = new ActivityTerm();
        activityTerm.setUserId(MyID);
        activityTerm.setCurrentUserId(MyID);
        activityTerm.setPageIndex(pageIndexs);
        activityTerm.setPageSize(20);
        activityTerm.setIsShowExpire(1);
        String data = mGson.toJson(activityTerm);
        CLog.e("data", data);
        OKHttp.OkHttpPost("/Activity/ListActivity", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "错误");
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "changeNickName:" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        if (pageIndexs == 1) {
                            mList.clear();
                        }
                        pageIndexs++;
                        mPullToRefresh.onHeaderRefreshComplete();
                        mPullToRefresh.onFooterRefreshComplete();
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            List<String> imageStrinig3 = new ArrayList<String>();
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            Activity activity = mGson.fromJson(array.toString(), Activity.class);
                            if (activity.getImgs() != null) {
                                String[] imag = activity.getImgs().split(",");
                                CLog.e("", "" + imag.length);
                                for (int m = 0; m < imag.length; m++) {
                                    imageStrinig3.add(SubString_Utils.getImageUrl(imag[m]));
                                }
                            }
                            MyHome_Activity_Beans beans = new MyHome_Activity_Beans(activity.getId(), activity.getBeginTimeStr().substring(0, 16), activity.getEndTimeStr().substring(0, 16), DateDeal.format(activity.getRecordTimeStr()), activity.getViews(), activity.getTitle(), imageStrinig3.get(0), activity.getState(),1);
                            mList.add(beans);

                        }
                        if (mAdapter == null) {
                            mAdapter = new MyHome_Activity_adapter(getActivity(), mList);
                            mAllTaskListview.setAdapter(mAdapter);
                        } else mAdapter.notifyDataSetChanged();

                    }else {
                        mPullToRefresh.onHeaderRefreshComplete();
                        mPullToRefresh.onFooterRefreshComplete();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
