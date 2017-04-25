package com.multshows.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.Group_List_Adapter;
import com.multshows.Beans.GroupInfo;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 群聊列表 Activity
 */
public class GroupList_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    MyListView mListView;
    RelativeLayout mSearchLayout;//搜索框
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    //适配器
    Group_List_Adapter mGroupListAdapter;
    //数据集合
    List<GroupInfo> mList=new ArrayList<>();

    MyApplication mMyApplication;
    Gson mGson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);
        //沉浸式
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(GroupList_Activity.this, R.color.app_topColor);
        mMyApplication= (MyApplication) getApplication();
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.Group_List_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Group_List_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Group_List_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.Group_List_TopTitle);
        mListView= (MyListView) findViewById(R.id.Group_List_MyListView);
        mSearchLayout= (RelativeLayout) findViewById(R.id.Group_List_SearchLayout);
        mNoView=findViewById(R.id.Group_List_No_View);
        mNoImage= (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText= (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton= (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("暂无群聊!");
        mNoButton.setVisibility(View.GONE);
        mListView.setEmptyView(mNoView);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(GroupList_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mGroupListAdapter=new Group_List_Adapter(GroupList_Activity.this,mList);
        mListView.setAdapter(mGroupListAdapter);
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //搜索
        mSearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GroupList_Activity.this,Search_Activity.class);
                intent.putExtra("SearchType","group");
                startActivity(intent);
            }
        });
        //
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(GroupList_Activity.this,Group_Chat_Activity.class);
                intent.putExtra("groupId", mList.get(position).getRCGroupId());
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGroupList();
    }

    //获取群聊列表
    private void getGroupList(){
        if(Login_Static.myUserID!=null && !"".equals(Login_Static.myUserID)) {
            OkHttpUtils.get().url(mMyApplication.getUrl() + "/GroupChat/ListGroup")
                    .addParams("currentUserId", Login_Static.myUserID)//当前用户id
                    .addParams("groupName", "")//群名称
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            CLog.e("testing", "获取群聊列表失败" + e.toString());
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            CLog.e("testing", "获取群聊列表" + response);
                            try {
                                if (Json_Utils.getCode(response) == 0) {
                                    mList.clear();
                                    JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        GroupInfo info = mGson.fromJson(jsonArray.getString(i), GroupInfo.class);
                                        mList.add(info);
                                    }
                                    mGroupListAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
        }

    }
}
