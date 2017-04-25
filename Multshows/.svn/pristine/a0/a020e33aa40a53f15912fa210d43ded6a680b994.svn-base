package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.My_MyBaby_Adapter;
import com.multshows.Beans.BabyAPI;
import com.multshows.Beans.UserBase;
import com.multshows.Beans.UserPrimaryKey;
import com.multshows.Beans.UserTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.BaseAppManager;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 我的——我的宝宝
 */
public class My_MyBaby_Activity extends BaseActivity {
    public static final int REQUEST_CODE = 1;
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    RelativeLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    ListView mListView;
    //空视图
    View mNoView;
    ImageView mNoImage;//空视图图标
    TextView mNoText;//空视图信息
    Button mNoButton;//空视图按钮
    RelativeLayout mAddBaby;//添加宝贝
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    MyApplication myApplication;
    Dialog mDialog;
    //数据集合
    List<UserBase> mList = new ArrayList<>();
    //适配器
    My_MyBaby_Adapter mBabyAdapter;
    Gson mGson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_my_baby);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_MyBaby_Activity.this, R.color.app_topColor);
        myApplication= (MyApplication) getApplication();
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.My_myBaby_return);
        mListView = (ListView) findViewById(R.id.My_myBaby_ListView);
        mAddBaby = (RelativeLayout) findViewById(R.id.My_myBaby_BottomLayout);
        mNoView = findViewById(R.id.My_myBaby_No_View);
        mNoImage = (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText = (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton = (Button) mNoView.findViewById(R.id.no_view_Button);
        mReturn= (ImageView) findViewById(R.id.My_myBaby_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_myBaby_TopLayout);
        mParentLayout= (RelativeLayout) findViewById(R.id.My_myBaby_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.My_myBaby_TopTitle);
        mListView= (ListView) findViewById(R.id.My_myBaby_ListView);
        mAddBaby= (RelativeLayout) findViewById(R.id.My_myBaby_BottomLayout);
        mNoView=findViewById(R.id.My_myBaby_No_View);
        mNoImage= (ImageView) mNoView.findViewById(R.id.no_view_image);
        mNoText= (TextView) mNoView.findViewById(R.id.no_view_text);
        mNoButton= (Button) mNoView.findViewById(R.id.no_view_Button);
        mNoImage.setImageResource(R.drawable.error_pic5);
        mNoText.setText("还没有添加宝宝哦~");
        mNoButton.setVisibility(View.GONE);
        mListView.setEmptyView(mNoView);
        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_MyBaby_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
        mReturnLayout.setBackgroundColor(getResources().getColor(R.color.app_topColor));
        MySystemBarTintManage_Utils tintManageUtils=new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(My_MyBaby_Activity.this,R.color.green);
    }

    /**
     * 数据处理
     */
    private void initData() {
        if (mSave.Get_PREFS(My_MyBaby_Activity.this, "Type").equals("unLogin")) {
            mTopTitle.setTextColor(getResources().getColor(R.color.white));
            mReturnLayout.setBackgroundColor(getResources().getColor(R.color.green));
            MySystemBarTintManage_Utils tintManageUtils=new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage(My_MyBaby_Activity.this,R.color.green);
            mReturn.setImageResource(R.drawable.index_return_arrow);
        }
        mDialog = new HintText_Dialog(My_MyBaby_Activity.this, R.style.MyDialog);
        mBabyAdapter = new My_MyBaby_Adapter(My_MyBaby_Activity.this, mList, mAddBaby);
        mListView.setAdapter(mBabyAdapter);
        getBabyList(1);
    }

    /**
     * 事件处理
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BaseAppManager.getInstance().removeActivity((My_MyBaby_Activity)mContext);
                finish();
                BaseAppManager.getInstance().removeActivity((My_MyBaby_Activity) mContext);
            }
        });
        //添加宝贝
        mAddBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_MyBaby_Activity.this, My_BabyInformation_Activity.class);
                intent.putExtra("Type", 1);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        //ListView点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (mSave.Get_PREFS(My_MyBaby_Activity.this, "Type").equals("unLogin")) {
                    /*mSave.Save_PREFS(getApplicationContext(),"Type","baby");
                    mSave.Save_PREFS(My_MyBaby_Activity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                    Login_Static.flag = 1;
                    Intent mIntent = new Intent(My_MyBaby_Activity.this,MainActivity.class);
                    startActivity(mIntent);*/
                    mDialog.show();
                    HintText_Dialog hint = new HintText_Dialog(My_MyBaby_Activity.this, "正在登录...", "");
                    changeUser(mList.get(i).getUserId());
                } else {
                    Intent intent = new Intent(My_MyBaby_Activity.this, My_BabyInformation_Activity.class);
                    intent.putExtra("My_MyBaby_Beans", mList.get(i));
                    intent.putExtra("Type", 0);
                    startActivity(intent);
                }

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getBabyList(1);
    }

    private void getBabyList(final int pageIndex) {
        UserTerm userTerm = new UserTerm();
        userTerm.setUserId(Login_Static.myUserID);
        userTerm.setPageIndex(pageIndex);
        userTerm.setPageSize(20);
        String data = mGson.toJson(userTerm);
        CLog.e("testing", "获取宝贝列表data：" + data);
        OKHttp.OkHttpPost("/User/GetUserList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "获取宝贝列表失败：" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "获取宝贝列表：" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        mList.clear();
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            //CLog.e("baby",array.toString());
                            UserBase baby = mGson.fromJson(array.toString(), UserBase.class);
                            mList.add(baby);
                        }
                        if (mList.size() < 2) {
                            mAddBaby.setVisibility(View.VISIBLE);
                        } else {
                            mAddBaby.setVisibility(View.GONE);
                        }
                        mBabyAdapter.notifyDataSetChanged();
                    } else if (Json_Utils.getCode(response) == 1007) {
                        mAddBaby.setVisibility(View.VISIBLE);
                    } else mAddBaby.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE:
                if (resultCode == 1) {
                        /*BabyAPI bean= (BabyAPI) data.getSerializableExtra("BabyAPI");
                        mList.add(bean);
                        mBabyAdapter.notifyDataSetChanged();*/
                    //getBabyList(1);
                }
                break;
        }
    }

    //切换密码
    private void changeUser(String userId) {
        CLog.e("testing", "切换用户:" + userId);

        OkHttpUtils.get().url(myApplication.getUrl() + "/User/SwitchUserStatusNoPwd")
                .addParams("userid", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "切换用户登录失败" + e.toString());
                        HintText_Dialog hint = new HintText_Dialog(My_MyBaby_Activity.this, "未知错误", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //得到返回值后，2秒后加载框消失
                                mDialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "切换用户登录" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                HintText_Dialog hint = new HintText_Dialog(My_MyBaby_Activity.this, "登录成功", "success");
                                final String error = Json_Utils.getTemplate(response);
                                UserPrimaryKey beans = mGson.fromJson(error, UserPrimaryKey.class);

                                Login_Static.myUserAccount = beans.getAccountId();
                                Login_Static.myUserID = beans.getUserId();
                                mDialog.dismiss();
                                mSave.Save_PREFS(My_MyBaby_Activity.this, "token", beans.getToken());
                                mSave.Save_PREFS(My_MyBaby_Activity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                                mSave.Save_PREFS(My_MyBaby_Activity.this, "Type", "baby");
                                Intent mIntent = new Intent(My_MyBaby_Activity.this, MainActivity.class);
                                BaseAppManager.getInstance().clear();
                                startActivity(mIntent);

                            } else {
                                HintText_Dialog hint = new HintText_Dialog(My_MyBaby_Activity.this, Json_Utils.getMessage(response), "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //得到返回值后，2秒后加载框消失
                                        mDialog.dismiss();
                                    }
                                }, Login_Static.hintTime);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void findViews() {

    }

    @Override
    public void addListeners() {

    }
}
