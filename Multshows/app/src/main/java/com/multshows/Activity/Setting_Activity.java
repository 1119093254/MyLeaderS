package com.multshows.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Beans.SystemVersion;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.ClearCache_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

/**
 * 设置(妈妈端)
 */
public class Setting_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    RelativeLayout mChangePassWord;//修改登录密码
    RelativeLayout mRechargePassWord;//设置支付密码
    RelativeLayout mInform;//通知
    RelativeLayout mCallUs;//关于我们
    RelativeLayout mClear;//清理缓存
    RelativeLayout mSuggestion;//意见反馈
    RelativeLayout mVersionNumber;//版本号
    RelativeLayout mSwitchNumber;//切换账号
    Button mButton;//退出登录
    ImageView mImageView;//通知按钮
    LinearLayout mCenter;
    LinearLayout mBottom;

    MyApplication mApplication ;
    TextView mVersion;//版本号
    TextView mCache;//缓存
    boolean flag = false;//通知开启标志
    Dialog_Hint dialogHint;
    String Notic_Flag = "on";
    Gson mGson = new Gson();
    String url = "";
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    String vip="";//vip用户判断标志
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Setting_Activity.this, R.color.app_topColor);
        String type=mSave.Get_PREFS(Setting_Activity.this,"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        vip=mSave.Get_PREFS(Setting_Activity.this,"Vip");
        mApplication= (MyApplication) getApplication();
        initView();
        if("mom".equals(type)) {
            if("yes".equals(vip)){
                mSwitchNumber.setVisibility(View.GONE);
            }else {
                mSwitchNumber.setVisibility(View.VISIBLE);
            }
        }else if("baby".equals(type)){
            mChangePassWord.setVisibility(View.GONE);
            mSwitchNumber.setVisibility(View.GONE);
            mCenter.setVisibility(View.GONE);
            mBottom.setVisibility(View.GONE);
        }

        initData();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.Setting_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Setting_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Setting_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.Setting_TopTitle);
        mChangePassWord = (RelativeLayout) findViewById(R.id.Setting_changePassWord);
        mRechargePassWord = (RelativeLayout) findViewById(R.id.Setting_RechargePassWord);
        mInform = (RelativeLayout) findViewById(R.id.Setting_inform);
        mCallUs = (RelativeLayout) findViewById(R.id.Setting_callUs);
        mClear = (RelativeLayout) findViewById(R.id.Setting_clear);
        mSuggestion = (RelativeLayout) findViewById(R.id.Setting_suggestion);
        mVersionNumber = (RelativeLayout) findViewById(R.id.Setting_versionNumber);
        mSwitchNumber = (RelativeLayout) findViewById(R.id.Setting_switchNumber);
        mButton = (Button) findViewById(R.id.Setting_button);
        mVersion = (TextView) findViewById(R.id.Setting_versionNumber_text);
        mCache = (TextView) findViewById(R.id.Setting_clear_text);
        mImageView = (ImageView) findViewById(R.id.Setting_inform_image);
        mCenter= (LinearLayout) findViewById(R.id.Setting_informCenter);
        mBottom= (LinearLayout) findViewById(R.id.Setting_informBottom);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Setting_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        try {
            mVersion.setText("V" + getVersionName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        GetSystemVersion();
        try {
            String cache = ClearCache_Utils.getTotalCacheSize(Setting_Activity.this);
            mCache.setText(cache);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Notic_Flag = mSave.Get_PREFS(Setting_Activity.this, "Notification");
        if ("on".equals(Notic_Flag)) {
            mImageView.setImageResource(R.drawable.switch_on);
        } else {

            mImageView.setImageResource(R.drawable.switch_off);
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
                finish();
            }
        });
        //修改登录密码
        mChangePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting_Activity.this, Setting_ChangePassword_Activity.class);
                startActivity(intent);
            }
        });
        //设置支付密码
        mRechargePassWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting_Activity.this, Login_FindPassword_Activity.class);
                intent.putExtra("PassWordType", 2);
                startActivity(intent);
            }
        });
        //联系我们
        mCallUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting_Activity.this, Setting_CallUs_Activity.class);
                startActivity(intent);
            }
        });
        //清理缓存
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogHint = new Dialog_Hint(Setting_Activity.this, 0, "确定清除缓存?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.hint_dialog_ok:
                                ClearCache_Utils.clearAllCache(Setting_Activity.this);
                                String cache = null;
                                try {
                                    cache = ClearCache_Utils.getTotalCacheSize(Setting_Activity.this);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                mCache.setText(cache);
                                dialogHint.dismiss();
                                break;
                        }
                    }
                });
                dialogHint.show();
            }
        });
        //意见反馈
        mSuggestion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting_Activity.this, Setting_Suggestion_Activity.class);
                startActivity(intent);
            }
        });
        //版本号
        mVersionNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!url.equals("")){
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(url);
                    intent.setData(content_url);
                    startActivity(intent);
                }

            }
        });
        //更换切换密码
        mSwitchNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting_Activity.this, Login_FindPassword_Activity.class);
                intent.putExtra("PassWordType",1);
                startActivity(intent);
            }
        });

        //退出登录
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Setting_Activity.this, MainActivity.class);
                startActivity(intent);
                if (Login_Static.mActivityList.size() != 0)
                    Login_Static.mActivityList.get(0).finish();
                mSave.Save_PREFS(Setting_Activity.this, "userAccount", "");
                mSave.Save_PREFS(Setting_Activity.this, "userpassword","");
                mSave.Save_PREFS(Setting_Activity.this, "Unlogin", "yes");
                mSave.Save_PREFS(Setting_Activity.this, "Type", "baby");
                finish();
            }
        });
        //通知按钮
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("on".equals(Notic_Flag)) {
                    Notic_Flag = "off";
                    JPushInterface.stopPush(getApplicationContext());
                    mImageView.setImageResource(R.drawable.switch_off);
                    mSave.Save_PREFS(Setting_Activity.this, "Notification", Notic_Flag);
                } else {
                    Notic_Flag = "on";
                    JPushInterface.resumePush(getApplicationContext());
                    mImageView.setImageResource(R.drawable.switch_on);
                    mSave.Save_PREFS(Setting_Activity.this, "Notification", Notic_Flag);
                }
            }
        });
    }

    //获取版本信息
    public void GetSystemVersion() {

        OkHttpUtils.get().url(mApplication.getUrl() + "/User/GetSystemVersion")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                JSONObject jsonArray = new JSONObject(Json_Utils.getTemplate(response));
                                CLog.e("baby", jsonArray.length() + "");
                                SystemVersion mSystemVersion = mGson.fromJson(jsonArray.toString(), SystemVersion.class);
                                if ((mSystemVersion.getVersionNnumber().equals(getVersionName()))) {

                                } else {
                                    CLog.e("version", getVersionName());
                                    String[] aa = getVersionName().toString().split("\\.");
                                    String[] bb = mSystemVersion.getVersionNnumber().split("\\.");
                                    boolean isNew = false;
                                    for (int i = 0; i < aa.length; i++) {
                                        if(Integer.parseInt(aa[i])<Integer.parseInt(bb[i])){
                                            isNew=true;
                                            break;
                                        }else if(Integer.parseInt(aa[i])!=Integer.parseInt(bb[i])){
                                            break;
                                        }
                                    }
                                    if (!mSystemVersion.getVersionNnumber().equals("") && isNew) {
                                        mVersion.setText("New V"+mSystemVersion.getVersionNnumber());
                                        mVersion.setTextColor(Color.RED);
                                        url = mSystemVersion.getUpdateAddress();
                                    }
                                }
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /*
* 获取当前程序的版本号
*/
    private String getVersionName() throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packInfo.versionName;
    }
}
