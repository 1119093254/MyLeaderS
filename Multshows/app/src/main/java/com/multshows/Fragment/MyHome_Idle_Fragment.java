package com.multshows.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
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
import com.multshows.Activity.Idle_ProduceDetails_Activity;
import com.multshows.Adapter.MyHome_Idle_adapter;
import com.multshows.Beans.MyHome_Idle_Beans;
import com.multshows.Beans.Shows;
import com.multshows.Beans.ShowsTerm;
import com.multshows.Fragment.base.ScrollAbleFragment;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
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
 * 描述：我的主页  闲置fragment
 * 作者：李萧萧
 * 时间：2016.9/27
 */
public class MyHome_Idle_Fragment extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer {
    SaveSharedPreferences saveSharedPreferences;
    private ListView mAllworkListview;
    private PullToRefresh mPullToRefresh;//刷新控件
    int pageIndexs = 1;//页码
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    private boolean init;//记录界面是否为第一次加载
    MyHome_Idle_adapter mAdapter;
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    List<MyHome_Idle_Beans> mList;
    MyHome_Idle_Beans top;
    Gson mGson = new Gson();

    public static MyHome_Idle_Fragment newInstance() {
        MyHome_Idle_Fragment listFragment = new MyHome_Idle_Fragment();
        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        mAllworkListview = (ListView) view.findViewById(R.id.AllworkListview);
        mPullToRefresh = (PullToRefresh) view.findViewById(R.id.Fragment_Work_Refresh);
        mNoView = view.findViewById(R.id.Fragment_Work_No_View);
        mNoImage = (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText = (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton = (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无数据!");
        mNoButton.setVisibility(View.GONE);
        mAllworkListview.setEmptyView(mNoView);
        init = true;//第一次加载
        // mListview.setAdapter(new MyAdapter(getActivity(), strlist));
        mAllworkListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent mIntent = new Intent(getActivity(), Idle_ProduceDetails_Activity.class);
                mSave.Save_PREFS(getActivity(),"idleFresh","yesno");//判断是否从闲置列表跳进来的
                mIntent.putExtra("IdleID", mList.get(position).getId());
                startActivity(mIntent);
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
                        GetIdleShows();
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
                        GetIdleShows();
                    }
                }, 2000);

            }
        });
        return view;
    }

    //获取闲置列表
    private void GetIdleShows() {
        ShowsTerm mShowsTerm = new ShowsTerm();
        if (saveSharedPreferences.Get_PREFS(getActivity(), "ismy").equals("yes")) {
            mShowsTerm.setIsSelf(1);
            mShowsTerm.setUserid(saveSharedPreferences.Get_PREFS(getActivity(), "otherId"));
        }else {
            mShowsTerm.setUserid(Login_Static.myUserID);
            mShowsTerm.setLocalId(Login_Static.myUserID);
        }
        mShowsTerm.setPageIndex(pageIndexs);
        mShowsTerm.setPageSize(20);
        mShowsTerm.setType(2);
        mShowsTerm.setIsOver(0);
        mShowsTerm.setIsShow(-1);


        String data = mGson.toJson(mShowsTerm);
        CLog.e("data", data);
        OKHttp.OkHttpPost("/Shows/GetListShows", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "错误");
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testinggg", response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        mPullToRefresh.onHeaderRefreshComplete();
                        mPullToRefresh.onFooterRefreshComplete();
                        if (pageIndexs == 1) {
                            mList.clear();
                            if (!saveSharedPreferences.Get_PREFS(getActivity(), "ismy").equals("yes")) {
                                mList.add(top);
                            }

                        }
                        pageIndexs++;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            Shows Shows = mGson.fromJson(array.toString(), Shows.class);
                            List<String> imageStrinig3 = new ArrayList<>();
                            //对获取到的图片进行处理包装
                            if (Shows.getShowsPhoto() != null) {
                                String[] imag = Shows.getShowsPhoto().split(",");
                                CLog.e("", "" + imag.length);
                                for (int m = 0; m < imag.length; m++) {
                                    imageStrinig3.add(imag[m]);
                                }
                            }
                            MyHome_Idle_Beans beans = new MyHome_Idle_Beans(Shows.getId(), Shows.getUser().getUserId(), Shows.getUser().getNickName(), SubString_Utils.getImageUrl(Shows.getUser().getPortrait()),Shows.getViews(), Shows.getPrice(), Shows
                                    .getPublishTime() + "", imageStrinig3, Shows.getTitle(), Shows.getInfos(), Shows.getAddress(), Shows.getLikeUser(), Shows.getCommentCount(), 1);
                            if(Shows.getIsTrade()==0){
                                beans.setIsTrade(0);
                            }
                            mList.add(beans);
                            if (mAdapter == null) {
                                mAdapter = new MyHome_Idle_adapter(getActivity(), mList);
                                mAllworkListview.setAdapter(mAdapter);
                            } else mAdapter.notifyDataSetChanged();

                        }
                    } else {
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
        return mAllworkListview;
    }

    /**
     * 功能：该frament第一次显示的时候，加载网络数据
     * * 参数：无
     * 返回：无
     */
    private void getservciesData() {
        saveSharedPreferences = new SaveSharedPreferences();
        top = new MyHome_Idle_Beans(null, null, null, null, -1,0, null, null, null, null, null,null, 0, 0);
        mList = new ArrayList<>();
        if (!saveSharedPreferences.Get_PREFS(getActivity(), "ismy").equals("yes"))//如果不是我的主页的话，
            mList.add(top);
        GetIdleShows();

       /* imageStrinig.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        imageStrinig.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        imageStrinig.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        imageStrinig2.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        MyHome_Idle_Beans beans=new MyHome_Idle_Beans("李萧萧","http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1307/15/c0/23350946_1373868778475.jpg",200,"20:30",imageStrinig,"超显白，外面欧根杀定性，高腰蓬勃连衣裙，感兴趣的跟我留言吧！","江苏",10,20);
        MyHome_Idle_Beans beans1=new MyHome_Idle_Beans("李萧萧","http://img.pconline.com.cn/images/upload/upc/tx/wallpaper/1307/15/c0/23350946_1373868778475.jpg",200,"20:30",imageStrinig2,"超显白，外面欧根杀定性，高腰蓬勃连衣裙，感兴趣的跟我留言吧！","江苏",10,20);
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
            init = false;//第一次加载数据后，设置为false,不在加载数据
            getservciesData();
        } else {
            if(mList!=null&&!mSave.Get_PREFS(getActivity(),"WorkIdNum").equals("")){
                CLog.e("WorkId", mSave.Get_PREFS(getActivity(),"WorkIdNum"));
                UpdataViewNum(mSave.Get_PREFS(getActivity(),"WorkIdNum"));
            }
            CLog.e("mSave",""+mSave.Get_PREFS(getActivity(), "intent"));
            if (mSave.Get_PREFS(getActivity(), "intent").equals("idle")) {
                mList.clear();
                pageIndexs = 1;
                GetIdleShows();
                mSave.Save_PREFS(getActivity(), "intent", "no");
            }
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
    public void UpdataViewNum(String mWorkid){
        for (int i = 1; i < mList.size(); i++) {
            if(mList.get(i).getId().equals(mWorkid)){
                mList.get(i).setViews(mList.get(i).getViews()+1);
                mAdapter.notifyDataSetChanged();
                mSave.Save_PREFS(getActivity(),"WorkIdNum","");
                break;
            }
        }
    }
}
