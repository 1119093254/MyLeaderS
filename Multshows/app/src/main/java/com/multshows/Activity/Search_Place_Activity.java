package com.multshows.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.SupportMapFragment;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.multshows.Adapter.Search_Place_Adapter;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.ChatEvent_Model2;
import com.multshows.Beans.MapEvent_Model;
import com.multshows.Beans.PoiItem_Beans;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Views.MyListView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索地点
 */
public class Search_Place_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    EditText mEditText;//搜索框
    TextView mSearch;//搜索按钮
    RelativeLayout mTopLayout;//定位地点布局
    TextView mLocalTion;//定位地点
    MyListView mListView;//搜索结果展示
    TextView mNoView;//空视图
    private PoiSearch.Query query;// Poi查询条件类
    private PoiSearch poiSearch;// POI搜索
    ProgressDialog mProgressDialog;


    //数据集合
    PoiItem_Beans mLocal;//定位地点对象
    List<PoiItem_Beans> mPoiList=new ArrayList<>();//周边数据集合
    List<PoiItem_Beans> mSearchList=new ArrayList<>();//搜索数据集合
    //适配器
    Search_Place_Adapter mSearchAdapter;

    String mText="";//搜索关键词
    int pageIndex=0;//页数
    Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_place);
        MySystemBarTintManage_Utils tintManageUtils=new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(Search_Place_Activity.this,R.color.app_topColor);
        initView();
        initData();
        initListen();
    }
    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.Search_Place_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Search_Place_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Search_Place_ParentsLayout);
        mEditText= (EditText) findViewById(R.id.Search_Place_editText);
        mTopLayout= (RelativeLayout) findViewById(R.id.Search_Place_topLayout);
        mSearch= (TextView) findViewById(R.id.Search_Place_Search);
        mLocalTion= (TextView) findViewById(R.id.Search_Place_Title);
        mListView= (MyListView) findViewById(R.id.Search_Place_ListView);
        mNoView= (TextView) findViewById(R.id.Search_Place_noView);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Search_Place_Activity.this,mReturnLayout,mParentLayout,mReturn,mSearch);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mIntent=getIntent();
        mPoiList= (List<PoiItem_Beans>) mIntent.getSerializableExtra("local");
        if(mPoiList!=null){
            if(mPoiList.size()!=0){
                mLocal=mPoiList.get(0);
                mLocalTion.setText(mLocal.getPoiTitle());
            }else {
                mTopLayout.setVisibility(View.GONE);
            }
        }
        mSearchAdapter = new Search_Place_Adapter(Search_Place_Activity.this,mSearchList);
        mListView.setAdapter(mSearchAdapter);
        mListView.setEmptyView(mNoView);
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
        //点击搜索按钮
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mText=mEditText.getText().toString();
                if("".equals(mText)){
                    Toast.makeText(Search_Place_Activity.this, "请输入关键字", Toast.LENGTH_SHORT).show();
                }else {
                    doSearchQuery();
                }
            }
        });
        mTopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapEvent_Model message=new MapEvent_Model();
                message.setIdle_Place(mLocal);
                EventBus.getDefault().post(message);
                if(Login_Static.mPlaceActivity.size()!=0) {
                    Login_Static.mPlaceActivity.get(0).finish();
                }
                finish();
            }
        });
        //listview点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MapEvent_Model message=new MapEvent_Model();
                message.setIdle_Place(mSearchList.get(position));
                EventBus.getDefault().post(message);
                if(Login_Static.mPlaceActivity.size()!=0) {
                    Login_Static.mPlaceActivity.get(0).finish();
                }
                finish();
            }
        });
    }

    /**
     * 开始进行poi搜索
     */
    protected void doSearchQuery() {
        showProgressDialog();// 显示进度框
        // 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
        //query = new PoiSearch.Query(mText, "", editCity.getText().toString());
        query = new PoiSearch.Query(mText,"");
        query.setPageSize(30);// 设置每页最多返回多少条poiitem
        query.setPageNum(pageIndex);// 设置查第一页
        //query.setCityLimit(true);

        poiSearch = new PoiSearch(Search_Place_Activity.this, query);
        //获取周边
        /*poiSearch.setBound(new PoiSearch.SearchBound(new LatLonPoint(mLocal.getLatitude(),
                mLocal.getLongitude()), 1000));//设置周边搜索的中心点以及半径*/
        poiSearch.setOnPoiSearchListener(mPoiSearchListener);
        poiSearch.searchPOIAsyn();
    }
    /**
     * 周边监听
     */
    PoiSearch.OnPoiSearchListener mPoiSearchListener=new PoiSearch.OnPoiSearchListener() {
        @Override
        public void onPoiSearched(PoiResult poiResult, int i) {
            dissmissProgressDialog();// 隐藏对话框
            if (i==1000) {
                //成功
                    if (poiResult != null && poiResult.getQuery() != null) {// 搜索poi的结果
                        if (poiResult.getQuery().equals(query)) {// 是否是同一条
                            if(pageIndex==0){
                                mSearchList.clear();
                            }
                            for (int n = 0; n < poiResult.getPois().size(); n++) {
                                PoiItem_Beans bean = new PoiItem_Beans(poiResult.getPois().get(n).getPoiId(),
                                        poiResult.getPois().get(n).getTitle(), poiResult.getPois().get(n).getSnippet()
                                        , poiResult.getPois().get(n).getLatLonPoint().getLatitude(),
                                        poiResult.getPois().get(n).getLatLonPoint().getLongitude(),
                                        poiResult.getPois().get(n).getProvinceName(),
                                        poiResult.getPois().get(n).getCityName(),
                                        poiResult.getPois().get(n).getAdName(),
                                        poiResult.getPois().get(n).getDistance(), false);
                                CLog.e("GD", bean.toString());
                                mSearchList.add(bean);
                            }
                            mSearchAdapter.notifyDataSetChanged();
                        }
                    }else {
                        Toast.makeText(Search_Place_Activity.this, "对不起,没有搜索到相关数据", Toast.LENGTH_SHORT).show();
                    }
            }else {
                CLog.e("GD","获取周边失败");
            }
        }

        @Override
        public void onPoiItemSearched(PoiItem poiItem, int i) {

        }
    };


    /**
     * 显示进度框
     */
    private void showProgressDialog() {
        if (mProgressDialog == null)
            mProgressDialog = new ProgressDialog(Search_Place_Activity.this);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage("正在搜索:\n" + mText);
        mProgressDialog.show();
    }

    /**
     * 隐藏进度框
     */
    private void dissmissProgressDialog() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
        }
    }

}
