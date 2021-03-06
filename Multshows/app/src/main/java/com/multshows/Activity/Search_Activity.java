package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.Group_List_Adapter;
import com.multshows.Adapter.MyHome_Idle_adapter;
import com.multshows.Adapter.Search_Friends_Adapter;
import com.multshows.Beans.GroupInfo;
import com.multshows.Beans.MyHome_Idle_Beans;
import com.multshows.Beans.Shows;
import com.multshows.Beans.ShowsTerm;
import com.multshows.Beans.UserFriendTerm;
import com.multshows.Beans.UserFriend_Beans;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.PullToRefresh;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 搜索页
 */
public class Search_Activity extends AppCompatActivity{
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    RelativeLayout mParentLayout;//父布局
    TextView mSearch;//搜索
    EditText mEditText;//输入框
    EditText mEditText2;
    RelativeLayout mSearchLayout2;
    //LinearLayout mTopLayout;//历史布局显示
   // RelativeLayout mBeanLayout;//搜索数据布局
    //MyListView mMyListView;//历史搜索数据显示
    ListView mBeansListView;//搜索数据显示
    TextView mNoView;//空视图
    //数据集合
    List<UserFriend_Beans> mFriendsList=new ArrayList<>();
    List<MyHome_Idle_Beans> mIdleList=new ArrayList<>();
    List<GroupInfo> mGroupList=new ArrayList<>();
    //适配器
    Search_Friends_Adapter mFriendsAdapter;//查找好友适配器
    MyHome_Idle_adapter mIdleAdapter;//闲置适配器
    Group_List_Adapter mGroupListAdapter;//群聊适配器

    SaveSharedPreferences saveSharedPreferences=new SaveSharedPreferences();//偏好设置
    RelativeLayout mImageLayout;
    private PullToRefresh mPullToRefresh;
    int pageIndex_=1;//页数
    String mSearchType;
    Intent mIntent;
    Gson mGson=new Gson();
    Dialog mDialog;
    String text_="";
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //沉浸式
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Search_Activity.this, R.color.app_topColor);
        initView();
        type=saveSharedPreferences.Get_PREFS(Search_Activity.this,"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        if("mom".equals(type)) {
            mImageLayout.setVisibility(View.VISIBLE);
            mSearchLayout2.setVisibility(View.GONE);
        }else {
            mImageLayout.setVisibility(View.GONE);
            mSearchLayout2.setVisibility(View.VISIBLE);
        }
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mPullToRefresh= (PullToRefresh) findViewById(R.id.Search_Refresh);
        mImageLayout= (RelativeLayout) findViewById(R.id.Search_imageLayout);
        mReturn= (ImageView) findViewById(R.id.Search_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Search_ReturnLayout);
        mParentLayout= (RelativeLayout) findViewById(R.id.Search_ParentsLayout);
        mSearch= (TextView) findViewById(R.id.Search_Search);
        mEditText= (EditText) findViewById(R.id.Search_editText);
        mEditText2= (EditText) findViewById(R.id.Search_editText2);
        mSearchLayout2= (RelativeLayout) findViewById(R.id.Search_imageLayout2);
        //mMyListView= (MyListView) findViewById(R.id.Search_ListView);
        //mTopLayout= (LinearLayout) findViewById(R.id.Search_TopLayout);
        mBeansListView= (ListView) findViewById(R.id.Search_ListView_Beans);
        //mBeanLayout= (RelativeLayout) findViewById(R.id.Search_BeanListLayout);
        mNoView= (TextView) findViewById(R.id.Search_NoView);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Search_Activity.this,mReturnLayout,mParentLayout,mReturn,mSearch);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mIntent=getIntent();
        mSearchType=mIntent.getStringExtra("SearchType");
        if("friend".equals(mSearchType)){
            //好友搜索
            mFriendsAdapter=new Search_Friends_Adapter(Search_Activity.this,mFriendsList);
            mBeansListView.setAdapter(mFriendsAdapter);
        }else if("idle".equals(mSearchType)){
            //闲置搜索
            mIdleAdapter=new MyHome_Idle_adapter(Search_Activity.this,mIdleList);
            mBeansListView.setAdapter(mIdleAdapter);
        }else if("group".equals(mSearchType)){
            //群聊搜索
            mGroupListAdapter=new Group_List_Adapter(Search_Activity.this,mGroupList);
            mBeansListView.setAdapter(mGroupListAdapter);
        }
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //隐藏软键盘
                HideInputManager_Utils.hideInputManager(Search_Activity.this);
                finish();
            }
        });
      /*  mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==0){
                    mTopLayout.setVisibility(View.VISIBLE);
                    mBeanLayout.setVisibility(View.GONE);
                }
            }
        });*/
        mPullToRefresh.setOnHeaderRefreshListener(new PullToRefresh.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefresh view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageIndex_=1;
                        if("friend".equals(mSearchType)){
                            mFriendsList.clear();
                            getSearchFriend(1,text_,1);
                        }else if("idle".equals(mSearchType)){
                            //闲置搜索
                            GetIdleShows(1,text_,2,1);
                        }else if("group".equals(mSearchType)){
                            getGroupList(text_,1);
                        }
                    }
                },1000);
            }
        });
        mPullToRefresh.setOnFooterRefreshListener(new PullToRefresh.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefresh view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if("friend".equals(mSearchType)){
                            mFriendsList.clear();
                            getSearchFriend(pageIndex_,text_,1);
                        }else if("idle".equals(mSearchType)){
                            //闲置搜索
                            GetIdleShows(pageIndex_,text_,2,1);
                        }else if("group".equals(mSearchType)){
                            getGroupList(text_,1);
                        }
                    }
                },1000);
            }
        });
        //搜索
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBeansListView.setEmptyView(mNoView);
                if("mom".equals(type)) {
                    text_=mEditText.getText().toString();
                }else {
                    text_=mEditText2.getText().toString();
                }

                if("".equals(text_)){

                }else {
                    mDialog=new HintText_Dialog(Search_Activity.this,R.style.MyDialog);
                    mDialog.show();
                    HintText_Dialog mTextDialog=new HintText_Dialog(Search_Activity.this,
                            "努力搜索中...","");
                    if("friend".equals(mSearchType)){
                        mFriendsList.clear();
                        getSearchFriend(1,text_,0);
                    }else if("idle".equals(mSearchType)){
                        //闲置搜索
                        GetIdleShows(1,text_,2,0);
                    }else if("group".equals(mSearchType)){
                        getGroupList(text_,0);
                    }
                }
            }
        });

        //listview点击事件
        mBeansListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if("friend".equals(mSearchType)){
                    Intent intent=new Intent(Search_Activity.this,MyHomePager_Activity.class);
                    //是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                    saveSharedPreferences.Save_PREFS(Search_Activity.this, "otherId",
                            mFriendsList.get(position).getTargetUser().getUserId());
                    startActivity(intent);
                }else if("idle".equals(mSearchType)){
                    //闲置搜索
                    Intent mIntent = new Intent(Search_Activity.this, Idle_ProduceDetails_Activity.class);
                    mIntent.putExtra("IdleID", mIdleList.get(position).getId());
                    startActivity(mIntent);
                }else if("group".equals(mSearchType)){
                    Intent intent=new Intent(Search_Activity.this,Group_Chat_Activity.class);
                    intent.putExtra("groupId", mGroupList.get(position).getRCGroupId());
                    startActivity(intent);
                }
            }
        });


    }
    private void getSearchFriend(final int pageIndex, String name, final int flag){
        UserFriendTerm userFriendTerm=new UserFriendTerm();
        userFriendTerm.setUserId(Login_Static.myUserID);
        userFriendTerm.setState(1);
        userFriendTerm.setIsNickName(1);
        userFriendTerm.setNickName(name);
        userFriendTerm.setPageSize(20);
        userFriendTerm.setPageIndex(pageIndex);
        String data=mGson.toJson(userFriendTerm);
        CLog.e("testing",data);
        OKHttp.OkHttpPost("/User/GetFriendList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","查询好友列表失败："+e.toString());
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
                if(flag!=1) {
                    HintText_Dialog mTextDialog = new HintText_Dialog(Search_Activity.this,
                            "搜索失败", "fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                        }
                    }, Login_Static.hintTime);
                }

            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","查询好友列表："+response);
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
                try {
                    if(Json_Utils.getCode(response)==0) {
                        if(flag!=1) {
                            mDialog.dismiss();
                        }
                        if(pageIndex==1) {
                            mFriendsList.clear();
                        }
                        pageIndex_++;
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            UserFriend_Beans bean = mGson.fromJson(jsonArray.getString(i), UserFriend_Beans.class);
                            mFriendsList.add(bean);
                        }
                        mFriendsAdapter.notifyDataSetChanged();
                    }else {
                        if(flag!=1) {
                            HintText_Dialog mTextDialog = new HintText_Dialog(Search_Activity.this,
                                    Json_Utils.getMessage(response), "fail");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mDialog.dismiss();
                                }
                            }, Login_Static.hintTime);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
    //获取闲置列表
    private void GetIdleShows(final int pageIndex, String searchStr, final int flag, final int flag_) {
        ShowsTerm mShowsTerm=new ShowsTerm();
        mShowsTerm.setPageIndex(pageIndex);
        mShowsTerm.setPageSize(20);
        mShowsTerm.setTitile(searchStr);
        mShowsTerm.setType(flag);
        mShowsTerm.setIsOver(-1);
        mShowsTerm.setIsShow(-1);
        String data=mGson.toJson(mShowsTerm);
        CLog.e("data",data);
        OKHttp.OkHttpPost("/Shows/GetListShows", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","错误");
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
                if(flag_!=1) {
                    HintText_Dialog mTextDialog = new HintText_Dialog(Search_Activity.this,
                            "搜索失败", "fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                        }
                    }, Login_Static.hintTime);
                }
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testinggg",response);
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
                try {
                    if(Json_Utils.getCode(response)==0){
                        if(flag_!=1) {
                            mDialog.dismiss();
                        }
                        JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                        if(pageIndex==1){
                            mIdleList.clear();
                        }
                        pageIndex_++;
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject array=new JSONObject(jsonArray.getString(i));
                            Shows Shows=mGson.fromJson(array.toString(),Shows.class);
                            List<String> imageStrinig3=new ArrayList<>();
                            //对获取到的图片进行处理包装
                            if(Shows.getShowsPhoto()!=null){
                                String[] imag = Shows.getShowsPhoto().split(",");
                                CLog.e("",""+imag.length);
                                for (int m = 0 ; m <imag.length ; m++ ) {
                                    imageStrinig3.add(SubString_Utils.getImageUrl(imag[m]));
                                }
                            }
                            MyHome_Idle_Beans beans=new MyHome_Idle_Beans(Shows.getId(),Shows.getUser().getUserId(),Shows.getUser().getNickName(), SubString_Utils.getImageUrl(Shows.getUser().getPortrait()),Shows.getViews(), Shows.getPrice(),Shows
                                    .getPublishTime()+"",imageStrinig3, Shows.getTitle(),Shows.getInfos(),Shows.getAddress(),Shows.getLikeUser(),Shows.getCommentCount(),1);
                            beans.setIsTrade(Shows.getIsTrade());
                            mIdleList.add(beans);
                        }
                        mIdleAdapter.notifyDataSetChanged();
                    }else {
                        if(flag_!=1) {
                            HintText_Dialog mTextDialog = new HintText_Dialog(Search_Activity.this,
                                    Json_Utils.getMessage(response), "fail");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mDialog.dismiss();
                                }
                            }, Login_Static.hintTime);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //群聊搜索
    private void getGroupList(String name, final int flag){
        MyApplication myApplication= (MyApplication) getApplication();
        OkHttpUtils.get().url(myApplication.getUrl()+"/GroupChat/ListGroup")
                .addParams("currentUserId", Login_Static.myUserID)//当前用户id
                .addParams("groupName",name)//群名称
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取群聊列表失败"+e.toString());
                        mPullToRefresh.onHeaderRefreshComplete();
                        mPullToRefresh.onFooterRefreshComplete();
                        if(flag!=1) {
                            HintText_Dialog mTextDialog = new HintText_Dialog(Search_Activity.this,
                                    "搜索失败", "fail");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mDialog.dismiss();
                                }
                            }, Login_Static.hintTime);
                        }
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取群聊列表"+response);
                        mPullToRefresh.onHeaderRefreshComplete();
                        mPullToRefresh.onFooterRefreshComplete();
                        try {
                            if(Json_Utils.getCode(response)==0){
                                if(flag!=1) {
                                            mDialog.dismiss();
                                }
                                JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    GroupInfo info=mGson.fromJson(jsonArray.getString(i),GroupInfo.class);
                                    mGroupList.add(info);
                                }
                                mGroupListAdapter.notifyDataSetChanged();
                            }else {
                                if(flag!=1) {
                                    HintText_Dialog mTextDialog = new HintText_Dialog(Search_Activity.this,
                                            Json_Utils.getMessage(response), "fail");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDialog.dismiss();
                                        }
                                    }, Login_Static.hintTime);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

}
