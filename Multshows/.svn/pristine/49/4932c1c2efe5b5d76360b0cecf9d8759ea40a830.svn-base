package com.multshows.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.multshows.Beans.User;
import com.multshows.Beans.UserTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.BaseAppManager;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
/**
 * 描述：选择身份
 * 作者：李萧萧
 * 时间：2016.10.8
 */
public class ChioceIdentity extends BaseActivity {

    @Bind(R.id.ChioceBabyLayout)
    RelativeLayout mChioceBabyLayout;//选择宝宝布局
    @Bind(R.id.ChioceMomLayout)
    RelativeLayout mChioceMomLayout;//选择妈妈布局
    Gson mGson = new Gson();
    MyApplication myApplication ;
    SaveSharedPreferences mSave=new SaveSharedPreferences();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chioce_identity);
        ButterKnife.bind(this);//绑定id
        myApplication= (MyApplication) getApplication();
        MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(ChioceIdentity.this, R.color.green);//设置状态栏颜色
        getUserData();
    }
    @OnClick({R.id.ChioceBabyLayout, R.id.ChioceMomLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ChioceBabyLayout:
                getBabyList2();
                break;
            case R.id.ChioceMomLayout:
                getBabyList();
                break;
        }
    }

    /**
     * 功能：获取宝宝列表,这里判断是否有宝宝，无宝宝无需密码直接进入家长端
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/13 9:07
     */
    private void getBabyList() {
        //获取宝宝列表
        UserTerm userTerm = new UserTerm();
        userTerm.setUserId(Login_Static.myUserID);
        userTerm.setPageIndex(1);
        userTerm.setPageSize(10);
        String data = mGson.toJson(userTerm);
        CLog.e("testing", "获取宝贝列表data：" + data);
        OKHttp.OkHttpPost("/User/GetUserList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //访问错误回调（断网 或者服务器异常）
                CLog.e("testing", "获取宝贝列表失败：" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "获取宝贝列表：" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        //成功获取到数据并解析
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        if (jsonArray.length() > 0) {//有宝宝进入切换密码页，输入切换密码，切换带家长端（最初需求）
                            mSave.Save_PREFS(getApplicationContext(),"Type","unLogin");
                            mSave.Save_PREFS(ChioceIdentity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                            Intent mIntent = new Intent(ChioceIdentity.this, Setting_Change_Activity.class);
                            startActivity(mIntent);
                        }else {
                            //没有宝宝，不用输入切换密码，直接到家长端（最初需求）
                            mSave.Save_PREFS(getApplicationContext(),"Type","mom");
                            mSave.Save_PREFS(ChioceIdentity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                            Intent mIntent = new Intent(ChioceIdentity.this, MainActivity.class);
                            BaseAppManager.getInstance().clear();//关闭之前所有activity
                            finish();
                        }

                    }
                    if (Json_Utils.getCode(response) == 1007) {
                        //没有宝宝，不用输入切换密码，直接到家长端（最初需求）
                        Intent mIntent = new Intent(ChioceIdentity.this, MainActivity.class);
                        mIntent.putExtra("Type",3);
                        mSave.Save_PREFS(getApplicationContext(),"Type","mom");
                        mSave.Save_PREFS(ChioceIdentity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                        BaseAppManager.getInstance().clear();//关闭之前所有activity
                        startActivity(mIntent);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    /**
     * 功能：获取宝宝列表,这里判断是否有宝宝，无宝宝去添加宝宝页面
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/13 8:58
     */
    private void getBabyList2() {
        //获取宝宝列表 参数实体类
        UserTerm userTerm = new UserTerm();
        userTerm.setUserId(Login_Static.myUserID);
        userTerm.setPageIndex(1);
        userTerm.setPageSize(10);
        String data = mGson.toJson(userTerm);
        CLog.e("testing", "获取宝贝列表data：" + data);
        //获取宝宝列表接口调用 post请求
        OKHttp.OkHttpPost("/User/GetUserList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                //访问错误回调（断网 或者服务器异常）
                CLog.e("testing", "获取宝贝列表失败：" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "获取宝贝列表：" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        //成功获取到数据并解析
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        if (jsonArray.length() > 0) {//有宝宝，取宝宝列表选择宝宝
                            mSave.Save_PREFS(getApplicationContext(),"Type","unLogin");
                            Intent mIntent = new Intent(ChioceIdentity.this, My_MyBaby_Activity.class);
                            mIntent.putExtra("Type",3);
                            startActivity(mIntent);
                        }
                    }
                    //没有宝宝 ，宝宝列表界面 ，添加宝宝
                    if (Json_Utils.getCode(response) == 1007) {
                        mSave.Save_PREFS(getApplicationContext(),"Type","unLogin");
                        Intent mIntent = new Intent(ChioceIdentity.this, My_MyBaby_Activity.class);
                        mIntent.putExtra("Type",3);
                        startActivity(mIntent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * 功能：获取个人信息数据，保存家长数据
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/11 12:09
     */
    private void getUserData() {
        OkHttpUtils.get().url(myApplication.getUrl() + "/User/GetUser")
                .addParams("userid", Login_Static.myUserID)
                .addParams("targetuserid", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        //访问错误回调（断网 或者服务器异常）
                        CLog.e("testing", "个人信息 失败" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            CLog.e("testing", "个人信息：" + Json_Utils.getTemplate(response));
                            if (Json_Utils.getCode(response) == 0) {
                                User mAccount = mGson.fromJson(Json_Utils.getTemplate(response), User.class);
                                Login_Static.mAccount = mGson.fromJson(Json_Utils.getTemplate(response), User.class);
                                if (mAccount.getUserType() == 0 && "mom".equals(mSave.Get_PREFS(ChioceIdentity.this, "Type"))) {
                                    //妈妈信息
                                    mSave.Save_PREFS(ChioceIdentity.this, "momUserId", mAccount.getUserId());
                                    mSave.Save_PREFS(ChioceIdentity.this, "momNickName", mAccount.getNickName());
                                    mSave.Save_PREFS(ChioceIdentity.this, "momPic", mAccount.getPortrait());
                                    mSave.Save_PREFS(ChioceIdentity.this, "momPhone", mAccount.getMobile());
                                }
                                //mAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
/**
 * 功能：绑定id
 * 无参
 * 返回值：空
 * 作者：李萧萧
 * 时间：2017/2/11 12:09
 */
    @Override
    public void findViews() {

    }
    /**
     * 功能：监听事件
     * 无参
     * 返回值：空
     * 作者：李萧萧
     * 时间：2017/2/11 12:09
     */
    @Override
    public void addListeners() {

    }
}
