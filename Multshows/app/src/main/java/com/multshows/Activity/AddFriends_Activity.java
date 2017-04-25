package com.multshows.Activity;

import android.app.Dialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.My_AddFriends_Adapter;
import com.multshows.Beans.User;
import com.multshows.Beans.UserTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.HintText_Dialog;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 添加好友界面
 */
public class AddFriends_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    ListView mListView;
    EditText mEditText;//输入框
    TextView mSearch;//搜索
    //适配器
    My_AddFriends_Adapter mAddFriendsAdapter;
    //数据集合
    List<User> mList=new ArrayList<>();
    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();
    Gson mGson=new Gson();
    Dialog mDialog;
    String text;//搜素内容
    int pageIndexs=1;//页数
    String type="";//判断家长端还是宝宝端标志
    String edText="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friends);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(AddFriends_Activity.this,R.color.app_topColor);
        type=mSharedPreferences.Get_PREFS(AddFriends_Activity.this,"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.Add_Friends_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Add_Friends_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Add_Friends_ParentLayout);
        mTopTitle= (TextView) findViewById(R.id.Add_Friends_TopTitle);
        mListView= (ListView) findViewById(R.id.Add_Friends_ListView);
        mEditText= (EditText) findViewById(R.id.Add_Friends_editText);
        mSearch= (TextView) findViewById(R.id.Add_Friends_Search);
        mDialog=new HintText_Dialog(AddFriends_Activity.this,R.style.MyDialog);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(AddFriends_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mAddFriendsAdapter=new My_AddFriends_Adapter(AddFriends_Activity.this,mList);
        mListView.setAdapter(mAddFriendsAdapter);
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideInputManager_Utils.hideInputManager(AddFriends_Activity.this);
                finish();
            }
        });
        //输入框监听
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if("".equals(mEditText.getText().toString())){
                    mSearch.setVisibility(View.INVISIBLE);
                    mList.clear();
                    mAddFriendsAdapter.notifyDataSetChanged();
                }else {
                    mSearch.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //搜索
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("mom".equals(type)) {
                    getSearchFriends(mEditText.getText().toString(),0);
                }else if("baby".equals(type)){
                    getSearchFriends(mEditText.getText().toString(),1);
                }

            }
        });
    }
    /**
     * 获取搜索内容
     */
    private void getSearchFriends(final String text, final int flags){
        mDialog.show();
        HintText_Dialog hintDialog=new HintText_Dialog(AddFriends_Activity.this,"正在搜索...","");
        UserTerm userTerm=new UserTerm();
        userTerm.setMobile(text);
        userTerm.setPageIndex(1);
        userTerm.setPageSize(20);
        String data=mGson.toJson(userTerm);
        CLog.e("testing","精确搜索好友data"+data);
        OKHttp.OkHttpPost("/User/GetUserList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","精确搜索好友失败"+e.toString());
                HintText_Dialog hintDialog=new HintText_Dialog(AddFriends_Activity.this,"搜索失败","fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                    }
                }, Login_Static.hintTime);

            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","精确搜索好友"+response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        mList.clear();
                        HintText_Dialog hintDialog=new HintText_Dialog(AddFriends_Activity.this,"搜索成功","successs");
                        mDialog.dismiss();

                        JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            User bean=mGson.fromJson(jsonArray.getString(i),User.class);
                            //（搜索过滤）宝宝端只能搜到宝宝，妈妈端只能搜到妈妈  flags标志是妈妈还是宝宝
                            if(bean.getUserType()==flags){
                                bean.setMobile(text);
                                mList.add(bean);
                            }
                        }
                        mAddFriendsAdapter.notifyDataSetChanged();
                    }else {
                        HintText_Dialog hintDialog=new HintText_Dialog(AddFriends_Activity.this
                                ,Json_Utils.getMessage(response),"fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
