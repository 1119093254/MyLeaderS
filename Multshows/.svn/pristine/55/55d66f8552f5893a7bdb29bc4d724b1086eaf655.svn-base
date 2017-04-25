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
import android.widget.Toast;

import com.cpoopc.scrollablelayoutlib.ScrollableHelper;
import com.google.gson.Gson;
import com.multshows.Activity.Activity_Details_Activity;
import com.multshows.Adapter.MyHome_Activity_adapter;
import com.multshows.Adapter.WorkAll_Adapter;
import com.multshows.Beans.Activity;
import com.multshows.Beans.ActivityTerm;
import com.multshows.Beans.MyHome_Activity_Beans;
import com.multshows.Beans.Shows;
import com.multshows.Beans.WorkAll_Beans;
import com.multshows.Fragment.base.ScrollAbleFragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.ACache;
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

import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 描述：我的主页  活动fragment
 * 作者：李萧萧
 * 时间：2016.9/27
 */
public class MyHome_Activity_Fragment extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer {
    ACache mACache;//加入缓存数据
    private ListView mAllTaskListview;
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    private boolean init;//记录界面是否为第一次加载
    private PullToRefresh mPullToRefresh;//刷新控件
    int pageIndexs = 1;//页码
    MyHome_Activity_adapter mAdapter;
    MyHome_Activity_Beans top;
    List<MyHome_Activity_Beans> mList;
    Gson mGson = new Gson();
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    String types="";
    String MyID="";
    public static MyHome_Activity_Fragment newInstance() {
        MyHome_Activity_Fragment listFragment = new MyHome_Activity_Fragment();
        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        types=mSave.Get_PREFS(getActivity(),"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        /*if("mom".equals(types)){
            MyID=Login_Static.myUserID;
        }else {
            if(Login_Static.mAccount!=null)
                MyID=Login_Static.mAccount.getParentId();
        }*/
        mACache = ACache.get(getActivity());
        mAllTaskListview = (ListView) view.findViewById(R.id.AllworkListview);
        mPullToRefresh = (PullToRefresh) view.findViewById(R.id.Fragment_Work_Refresh);
        mNoView = view.findViewById(R.id.Fragment_Work_No_View);
        mNoImage = (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText = (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton = (Button) mNoView.findViewById(R.id.no_view_Button);
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
        //下拉刷新
        mPullToRefresh.setOnHeaderRefreshListener(new PullToRefresh.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefresh view) {
                pageIndexs = 1;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageIndexs = 1;
                        GetListActivity();
                    }
                }, 2000);
            }
        });
        //上拉加载
        mPullToRefresh.setOnFooterRefreshListener(new PullToRefresh.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefresh view) {
                CLog.e("testing", pageIndexs + "页码");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        GetListActivity();
                    }
                }, 2000);

            }
        });
        return view;
    }
    //获取活动列表集合
    private void GetListActivity() {
        ActivityTerm activityTerm = new ActivityTerm();
        activityTerm.setPageIndex(pageIndexs);
        activityTerm.setCurrentUserId(Login_Static.myUserID);
        activityTerm.setPageSize(20);
        activityTerm.setIsShowExpire(0);
        activityTerm.setState(1);
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
                           mACache.put("Activity" + pageIndexs, response);
                            mList.clear();
                            mList.add(top);
                        }
                        pageIndexs++;
                        mPullToRefresh.onHeaderRefreshComplete();
                        mPullToRefresh.onFooterRefreshComplete();
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            List<String> imageStrinig3 = new ArrayList<String>();
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            Activity activity = mGson.fromJson(array.toString(), Activity.class);
                            CLog.e("baby", "");
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
        GetListActivity();
      /*  MyHome_Activity_Beans beans=new MyHome_Activity_Beans("1","2016-08-31-07:00","09-02-18:00","2天前",200,"萌禾秀-向日葵家庭亲子旅发布啦！","http://upload.northnews.cn/2016/0831/1472608762418.png");
        MyHome_Activity_Beans beans1=new MyHome_Activity_Beans("2",2016-08-31-07:00","09-02-18:00","3天前",20,"萌禾秀-向日葵家庭亲子旅发布啦！","http://img2.imgtn.bdimg.com/it/u=1123549437,771957275&fm=11&gp=0.jpg");
        mList.add(beans);
        mList.add(beans1);
        mList.add(beans);
        mList.add(beans);*/

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
            mList = new ArrayList<>();
            top=new MyHome_Activity_Beans(null,null,null,null,0,null,null,0,0);
            init = false;//第一次加载数据后，设置为false,不在加载数据
            if (mACache.getAsString("Activity" + 1) != null) {
                String response = mACache.getAsString("Activity" + 1).toString();
                getLocalData(response);
            }
            getservciesData();
        }else {
            CLog.e("intent",""+mSave.Get_PREFS(getActivity(),"intent"));
            if(mSave.Get_PREFS(getActivity(),"intent").equals("activity")){
                mList.clear();
                pageIndexs=1;
                mList.add(top);
                getservciesData();
                mSave.Save_PREFS(getActivity(),"intent","no");
            }
        }

    }
    //获取本地作品/圈子list集合
    private void getLocalData(String response) {

        try {
            if (Json_Utils.getCode(response) == 0) {
                if (pageIndexs == 1) {
                    mList.clear();
                    mList.add(top);
                }
                JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                for (int i = 0; i < jsonArray.length(); i++) {
                    List<String> imageStrinig3 = new ArrayList<String>();
                    JSONObject array = new JSONObject(jsonArray.getString(i));
                    Activity activity = mGson.fromJson(array.toString(), Activity.class);
                    CLog.e("baby", "");
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

            }
        } catch (JSONException e) {
            e.printStackTrace();
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
}
