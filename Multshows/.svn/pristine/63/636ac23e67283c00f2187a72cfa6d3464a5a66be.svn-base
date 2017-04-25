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
import com.multshows.Adapter.MyHome_Photo_adapter;

import com.multshows.Beans.MyHome_Idle_Beans;
import com.multshows.Beans.MyHome_Photo_Beans;
import com.multshows.Beans.Shows;
import com.multshows.Beans.ShowsTerm;
import com.multshows.Beans.UserAlbum;
import com.multshows.Beans.UserAlbumDate;
import com.multshows.Beans.UserAlbumTerm;
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
        * 描述：我的主页  相册fragment
        * 作者：李萧萧
        * 时间：2016.9/27
        */
public class MyHome_Photo_Fragment extends ScrollAbleFragment implements ScrollableHelper.ScrollableContainer {

    private ListView mAllTaskListview;
    private boolean init;//记录界面是否为第一次加载
    MyHome_Photo_adapter mAdapter;
    List<MyHome_Photo_Beans> mList;
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    int pageIndexs = 1;//页码
    Gson mGson = new Gson();
    private PullToRefresh mPullToRefresh;//刷新控件
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    public static MyHome_Photo_Fragment newInstance() {
        MyHome_Photo_Fragment listFragment = new MyHome_Photo_Fragment();
        return listFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_work, container, false);
        mAllTaskListview = (ListView) view.findViewById(R.id.AllworkListview);
        init = true;//第一次加载
        mPullToRefresh = (PullToRefresh) view.findViewById(R.id.Fragment_Work_Refresh);
        mNoView = view.findViewById(R.id.Fragment_Work_No_View);
        mNoImage = (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText = (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton = (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无数据!");
        mNoButton.setVisibility(View.GONE);
        mAllTaskListview.setEmptyView(mNoView);
       // mListview.setAdapter(new MyAdapter(getActivity(), strlist));
        mAllTaskListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "点击item" + position, Toast.LENGTH_SHORT).show();
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
                        GetPhoto();
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
                        GetPhoto();
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

        mList=new ArrayList<>();
 /*       List<String> imageStrinig=new ArrayList<>();
        List<String> imageStrinig2=new ArrayList<>();

        imageStrinig.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        imageStrinig.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        imageStrinig.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        imageStrinig2.add("http://img2.3lian.com/2014/c7/12/d/77.jpg");
        MyHome_Photo_Beans beans=new MyHome_Photo_Beans(1,2,0,imageStrinig);
        MyHome_Photo_Beans beans1=new MyHome_Photo_Beans(1,2,0,imageStrinig2);
        mList.add(beans);
        mList.add(beans1);
        mList.add(beans);
        mList.add(beans);*/
        GetPhoto();

    }
    //获取闲置列表
    private void GetPhoto() {
        UserAlbumTerm mUserAlbumTerm = new UserAlbumTerm();
        if (mSave.Get_PREFS(getActivity(), "ismy").equals("yes")) {
            mUserAlbumTerm.setUserId(mSave.Get_PREFS(getActivity(), "otherId"));
        }else {
            mUserAlbumTerm.setUserId(Login_Static.myUserID);
        }
        mUserAlbumTerm.setPageSize(20);
        mUserAlbumTerm.setPageIndex(pageIndexs);
        mUserAlbumTerm.setSoType(-1);

        String data = mGson.toJson(mUserAlbumTerm);
        CLog.e("data", data);
        OKHttp.OkHttpPost("/User/GetAlbumList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "错误");
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("GetPhoto", response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        mPullToRefresh.onHeaderRefreshComplete();
                        mPullToRefresh.onFooterRefreshComplete();
                        if (pageIndexs == 1) {
                            mList.clear();
                        }
                        pageIndexs++;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            UserAlbumDate mUserAlbumDate = mGson.fromJson(array.toString(), UserAlbumDate.class);
                            List<String> imageStrinig=new ArrayList<>();
                            for (int i1 = 0; i1 < mUserAlbumDate.getAlbumList().size(); i1++) {
                                imageStrinig.add(mUserAlbumDate.getAlbumList().get(i1).getPhotoName());
                            }
                            MyHome_Photo_Beans beans=new MyHome_Photo_Beans(mUserAlbumDate.getRecordTime().substring(5,7),mUserAlbumDate.getRecordTime().substring(8,10),mUserAlbumDate.getRecordTime().substring(0,4),imageStrinig);
                            mList.add(beans);
                        }
                        mAdapter=new MyHome_Photo_adapter(getActivity(),mList);
                        mAllTaskListview.setAdapter(mAdapter);
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
}
