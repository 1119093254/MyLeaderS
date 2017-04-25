package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;

import android.app.AlertDialog.Builder;


import com.google.gson.Gson;
import com.mob.tools.utils.UIHandler;

import com.multshows.Beans.UserBase;
import com.multshows.Beans.UserLogin;
import com.multshows.Beans.UserPrimaryKey;
import com.multshows.Beans.UserTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.BaseAppManager;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.Verification_Utils;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;
import okhttp3.Call;

/**
 * 描述：登录界面
 * 作者：李萧萧
 * 时间：2016.9.1
 */
public class LoginActivity extends BaseActivity implements Handler.Callback,
        View.OnClickListener, PlatformActionListener {
    //第三方登录定义常量
    private static final int MSG_USERID_FOUND = 1;
    private static final int MSG_LOGIN = 2;
    private static final int MSG_AUTH_CANCEL = 3;
    private static final int MSG_AUTH_ERROR = 4;
    private static final int MSG_AUTH_COMPLETE = 5;
    private static final String TAG = "LoginActivity";
    @Bind(R.id.login_editText1)
    EditText mLoginEditText1;//手机号输入框
    @Bind(R.id.login_editText2)
    EditText mLoginEditText2;//密码输入框
    @Bind(R.id.login_imageView2)
    ImageView mPassword_invisibleImage;//密码显示图标
    @Bind(R.id.LoginClearImage)
    ImageView mLoginClearImage;//清除手机输入框图标
    @Bind(R.id.login_login_button)
    Button mLoginLoginButton;//登录按钮
    @Bind(R.id.login_register)
    TextView mLoginRegister;//注册文字
    @Bind(R.id.login_find_password)
    TextView mLoginFindPassword;//找回密码按钮
    @Bind(R.id.UserAgreement)
    TextView mUserAgreement;//用户协议
    @Bind(R.id.weiXin)
    LinearLayout mWeiXin;//微信登录区域点击
    @Bind(R.id.weiBo)
    LinearLayout mWeiBo;//微博登录区域点击
    @Bind(R.id.qq)
    LinearLayout mQq;//qq登录区域点击
    @Bind(R.id.Login_return)
    ImageView Login_return;//返回按钮
    int loginType=0;//登录方式记录 ，0正常登录 ，1微信登录 2微博登录 3QQ登录
    boolean isHidden = false;//记录密码框是否可见状态
    Gson gson = new Gson();
    PlatformDb mData;//第三方登录后返回的 信息
    Dialog mDialog;//消息提示框
    //数据集合
    List<UserBase> mList = new ArrayList<>();//宝宝集合
    Gson mGson = new Gson();
    public static LoginActivity instence = null;
    //两次两次返回
    private boolean isOnKeyBacking;
    private Handler mHandler;
    private Toast mBackToast;//连续点击两次，退出app提示
    MyApplication mMyApplication;
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    //EditText文本内容变化监听方法
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
//

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
//


        }

        @Override
        public void afterTextChanged(Editable s) {
//文本框输入后监听事件 手机格式，密码内容符合，登录按钮方可点击状态
            if (mLoginEditText1 != null && !mLoginEditText1.getText().toString().equals("")) {
                mLoginClearImage.setVisibility(View.VISIBLE);
                if (Verification_Utils.checkMobileNumber(mLoginEditText1.getText().toString()) && mLoginEditText2.getText().toString().length() > 0) {
                    mLoginLoginButton.setEnabled(true);
                    mLoginLoginButton.setTextColor(getResources().getColor(R.color.text_white));
                } else {
                    mLoginLoginButton.setEnabled(false);
                    mLoginLoginButton.setTextColor(getResources().getColor(R.color.text_gray));
                }
            } else {
                mLoginLoginButton.setEnabled(false);
                mLoginLoginButton.setTextColor(getResources().getColor(R.color.text_gray));
                mLoginClearImage.setVisibility(View.INVISIBLE);
            }
            if (mLoginEditText2.getText().toString().length() > 0) {
                mPassword_invisibleImage.setVisibility(View.VISIBLE);
            } else {
                mPassword_invisibleImage.setVisibility(View.GONE);
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置状态栏颜色
        MySystemBarTintManage_Utils.StatusBar(LoginActivity.this,R.color.green);
        mHandler = new Handler(Looper.getMainLooper());
        ButterKnife.bind(this);
        mMyApplication= (MyApplication) getApplication();
        instence=this;
        ShareSDK.initSDK(this);
        mDialog = new HintText_Dialog(LoginActivity.this, R.style.MyDialog);
        //对手机，密码输入框添加监听事件
        mLoginEditText1.addTextChangedListener(textWatcher);
        mLoginEditText2.addTextChangedListener(textWatcher);
        mLoginEditText1.setText(mSave.Get_PREFS(LoginActivity.this, "userAccount"));
        mLoginEditText2.setText(mSave.Get_PREFS(LoginActivity.this, "userpassword"));
        mLoginEditText1.setSelection(mLoginEditText1.getText().length());
    }

    @OnClick({R.id.login_editText1, R.id.login_editText2, R.id.login_imageView2, R.id.login_login_button, R.id.LoginClearImage, R.id.login_register, R.id.UserAgreement, R.id.login_find_password, R.id.weiXin, R.id.weiBo, R.id.qq,R.id.Login_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_editText1:
                break;
            case R.id.login_editText2://
                break;
            case R.id.login_imageView2://密码显示隐藏
                if (!isHidden) {
                    mPassword_invisibleImage.setImageResource(R.drawable.item_password_visible);
                    mLoginEditText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mLoginEditText2.setSelection(mLoginEditText2.length());
                } else {
                    mPassword_invisibleImage.setImageResource(R.drawable.item_password_invisible);
                    mLoginEditText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mLoginEditText2.setSelection(mLoginEditText2.length());
                }
                isHidden = !isHidden;
                break;
            case R.id.LoginClearImage:
                mLoginEditText1.setText("");//手机号框置空
                break;
            case R.id.Login_return:
                finish();//返回按钮
                break;
            case R.id.login_login_button://登录监听事件
                mDialog.show();
                HintText_Dialog hint = new HintText_Dialog(LoginActivity.this, "正在登录...", "");
                Intent mIntent;
                //登录
                UserLogin userLogin = new UserLogin();
                userLogin.setAccount(mLoginEditText1.getText().toString());
                userLogin.setPwd(mLoginEditText2.getText().toString());
                userLogin.setType(0);
                String data = gson.toJson(userLogin);
                CLog.e("testing", data);
                CLog.e("response", data);
                OKHttp.OkHttpPost("?type=2", "data", data, new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("response", ""+e.getMessage());
                        showError("登录失败", 0);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("response", response);
                        CLog.e("testing", "==" + response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                showError("登录成功", 1);
                                UserPrimaryKey beans = gson.fromJson(Json_Utils.getTemplate(response), UserPrimaryKey.class);
                                Login_Static.myUserAccount = beans.getAccountId();
                                Login_Static.myUserID = beans.getUserId();
                                JPushInterface.setAlias(LoginActivity.this, "领秀S"+beans.getUserId(), new TagAliasCallback() {
                                    @Override
                                    public void gotResult(int i, String s, Set<String> set) {
                                        Log.i("JPush", "Jpush status: " + i+s);//状态
                                    }
                                });//极光推送设置设备名称
                                mSave.Save_PREFS(LoginActivity.this, "userAccount", mLoginEditText1.getText().toString());
                                mSave.Save_PREFS(LoginActivity.this, "userpassword", mLoginEditText2.getText().toString());
                                mSave.Save_PREFS(LoginActivity.this, beans.getUserId()+"token", beans.getToken());
                                mSave.Save_PREFS(LoginActivity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                                mSave.Save_PREFS(LoginActivity.this, "Type", "mom");
                                mSave.Save_PREFS(LoginActivity.this, "Unlogin", "no");
                                if(beans.getType()==1){
                                    mSave.Save_PREFS(LoginActivity.this, "Vip", "yes");
                                    mSave.Save_PREFS(LoginActivity.this, "Type", "mom");
                                    Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                                    BaseAppManager.getInstance().clear();
                                    startActivity(mIntent);
                                    finish();
                                }else {
                                    mSave.Save_PREFS(LoginActivity.this, "Vip", "no");
                                    getBabyList();
                                }

                            } else {
                                //后期判断错误原因
                                showError(Json_Utils.getMessage(response), 0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });


                break;
            case R.id.login_register://注册点击事件
                mIntent = new Intent(LoginActivity.this, LoginRegisterActivity.class);
                mIntent.putExtra("IsThree", "no");//传值，yes代表是第三方登录
                mIntent.putExtra("Logintoken", "no");
                startActivity(mIntent);
                break;
            case R.id.login_find_password://找回密码点击事件
                mIntent = new Intent(LoginActivity.this, Login_FindPassword_Activity.class);
                startActivity(mIntent);
                break;
            case R.id.UserAgreement://用户协议点击事件
                mIntent = new Intent(LoginActivity.this, Login_Agreement_Activity.class);
                startActivity(mIntent);
                break;
            case R.id.weiXin://微信第三方登录点击事件
                loginType=1;
                mWeiXin.setEnabled(false);
                authorize(new Wechat(this));
                break;
            case R.id.weiBo://微博第三方登录点击事件
                mWeiBo.setEnabled(false);
                loginType=2;
                authorize(new SinaWeibo(this));
                break;
            case R.id.qq://qq第三方登录点击事件
                loginType=3;
                mQq.setEnabled(false);
                authorize(new QQ(this));
                break;
        }
    }


    //提示
    public void showError(final String errorString, final int is) {
        String isString = null;
        if (is == 1) {
            isString = "success";
        }
        if (is == 0) {
            isString = "fail";
        }
        HintText_Dialog hint = new HintText_Dialog(LoginActivity.this, errorString, isString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mWeiXin.setEnabled(true);
                mWeiBo.setEnabled(true);
                mQq.setEnabled(true);
                //得到返回值后，2秒后加载框消失
               /* if (is == 1) {
                    Intent mIntent = new Intent(LoginActivity.this, ChioceIdentity.class);
                    startActivity(mIntent);
                    finish();
                }*/
                if(is==0)
                mDialog.dismiss();
            }
        }, 1000);
    }
    //第三方登录回调
    private void authorize(Platform plat) {
        Log.i(TAG, "authorize执行了");
        if (plat.isValid()) {
            //
            String userId = plat.getDb().getUserId();
            if (userId != null) {
                UIHandler.sendEmptyMessage(MSG_USERID_FOUND, this);
                login(plat.getName(), userId, null);
                mData = plat.getDb();
                CLog.i(TAG, "id:" + userId);
                CLog.i(TAG, "getExpiresIn:" + plat.getDb().getExpiresIn());
                CLog.i(TAG, "getExpiresTime:" + plat.getDb().getExpiresTime());
                CLog.i(TAG, "getPlatformNname:"
                        + plat.getDb().getPlatformNname());
                CLog.i(TAG, "getPlatformVersion:"
                        + plat.getDb().getPlatformVersion());
                CLog.i(TAG, "getToken:" + plat.getDb().getToken());
                CLog.i(TAG, "getTokenSecret:" + plat.getDb().getTokenSecret());
                CLog.i(TAG, "getUserIcon:" + plat.getDb().getUserIcon());
                CLog.i(TAG, "getUserId:" + plat.getDb().getUserId());
                CLog.i(TAG, "getUserName:" + plat.getDb().getUserName());
                Three_Login(mData.getToken(), loginType);//调取第三方登录接口
                return;
            }
        }
        plat.setPlatformActionListener(this);
        plat.SSOSetting(false);
        plat.showUser(null);
    }
//
    private void login(String plat, String userId,
                       HashMap<String, Object> userInfo) {
        Log.i(TAG, "login执行了");
        Message msg = new Message();
        msg.what = MSG_LOGIN;
        msg.obj = plat;
        UIHandler.sendMessage(msg, this);
    }

    public void onComplete(Platform platform, int action,
                           HashMap<String, Object> res) {
        Log.i(TAG, "onComplete执行了");
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_COMPLETE, this);
            login(platform.getName(), platform.getDb().getUserId(), res);
        }
        if(loginType==3){
            authorize(new QQ(this));
        }
        if(loginType==1){
            authorize(new Wechat(this));
        }
        if(loginType==2){
            authorize(new SinaWeibo(this));
        }

        Log.i(TAG, res.toString());
    }

    public void onError(Platform platform, int action, Throwable t) {
        Log.i(TAG, "onError执行了");
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_ERROR, this);
        }
        t.printStackTrace();
    }

    public void onCancel(Platform platform, int action) {
        Log.i(TAG, "onCancel执行了");
        if (action == Platform.ACTION_USER_INFOR) {
            UIHandler.sendEmptyMessage(MSG_AUTH_CANCEL, this);
        }
    }

    public boolean handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_USERID_FOUND: {
               // Toast.makeText(this, R.string.userid_found, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_LOGIN: {

                //String text = getString(R.string.logining, msg.obj);
             //   Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                /*Builder builder = new Builder(this);
                builder.setTitle(R.string.if_register_needed);
                builder.setMessage(R.string.after_auth);
                builder.setPositiveButton(R.string.ok, null);
                builder.create().show();*/
            }
            break;
            case MSG_AUTH_CANCEL: {
                Toast.makeText(this, R.string.auth_cancel, Toast.LENGTH_SHORT)
                        .show();
            }
            break;
            case MSG_AUTH_ERROR: {
                Toast.makeText(this, R.string.auth_error, Toast.LENGTH_SHORT)
                        .show();
            }
            break;
            case MSG_AUTH_COMPLETE: {
               // Toast.makeText(this, R.string.auth_complete, Toast.LENGTH_SHORT).show();
            }
            break;
        }
        return false;
    }

    //type  1为微信登录 ，2为微博登录，3为QQ登录
    public void Three_Login(String token, int type) {
        //登录

        //HintText_Dialog hint = new HintText_Dialog(LoginActivity.this, "正在登录...", "");
        UserLogin userLogin = new UserLogin();
        userLogin.setAppKey(token);
        userLogin.setType(type);
        String data = gson.toJson(userLogin);
        CLog.e("testing", data);
        CLog.e("response", data);
        OKHttp.OkHttpPost("/User/Login", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                mDialog.show();
                showError("登录失败", 0);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("response", response);
                CLog.e("testing", "==" + response);
                JSONObject jsonObject = null;
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        UserPrimaryKey beans = gson.fromJson(Json_Utils.getTemplate(response), UserPrimaryKey.class);
                        if (beans.getIsBind() == 1) {
                            Login_Static.myUserAccount = beans.getAccountId();
                            Login_Static.myUserID = beans.getUserId();
                            JPushInterface.setAlias(LoginActivity.this, "领秀S"+beans.getUserId(), new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {
                                    Log.i("JPush", "Jpush status: " + i+s);//状态
                                }
                            });//极光推送设置设备名称
                            mSave.Save_PREFS(LoginActivity.this, "userAccount", mLoginEditText1.getText().toString());
                            mSave.Save_PREFS(LoginActivity.this, "userpassword", mLoginEditText2.getText().toString());
                            mSave.Save_PREFS(LoginActivity.this, beans.getUserId()+"token", beans.getToken());
                            mSave.Save_PREFS(LoginActivity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                            mSave.Save_PREFS(LoginActivity.this, "Unlogin", "no");
                            mDialog.show();
                            if(beans.getType()==1){
                                mSave.Save_PREFS(LoginActivity.this, "Vip", "yes");
                                mSave.Save_PREFS(LoginActivity.this, "Type", "mom");
                                Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                                BaseAppManager.getInstance().clear();
                                startActivity(mIntent);
                            }else {
                                mSave.Save_PREFS(LoginActivity.this, "Vip", "no");
                                getBabyList();
                            }
                            showError("登录成功", 1);
                        } else if (beans.getIsBind() == 0) {
                            Intent mIntent = new Intent(LoginActivity.this, LoginRegisterActivity.class);
                            mIntent.putExtra("IsThree", ""+loginType);//传值，yes代表是第三方登录
                            mIntent.putExtra("Logintoken", mData.getToken());//第三方登录 Token
                            startActivity(mIntent);
                           // mDialog.dismiss();
                        }
                    } else {
                        //后期判断错误原因
                        showError(Json_Utils.getMessage(response), 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Activity onKeyDown事件
     * */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode != KeyEvent.KEYCODE_BACK) {
            return false;
        }
        if(!mSave.Get_PREFS(LoginActivity.this,"Unlogin").equals("yes")){
            if(isOnKeyBacking) {
                mHandler.removeCallbacks(onBackTimeRunnable);
                if(mBackToast != null){
                    mBackToast.cancel();
                }
                finish();
                return true;
            } else {
                isOnKeyBacking = true;
                if(mBackToast == null) {
                    mBackToast = Toast.makeText(LoginActivity.this, "再按一次退出", 2000);
                }
                mBackToast.show();
                //延迟两秒的时间，把Runa ble发出去
                mHandler.postDelayed(onBackTimeRunnable, 2000);
                return true;
            }
        }else {
            finish();
        }
        return true;
    }

    private Runnable onBackTimeRunnable = new Runnable() {

        @Override
        public void run() {
            isOnKeyBacking = false;
            if(mBackToast != null){
                mBackToast.cancel();
            }
        }
    };

    @Override
    public void findViews() {

    }

    @Override
    public void addListeners() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mWeiXin.setEnabled(true);
        mQq.setEnabled(true);
        mWeiBo.setEnabled(true);
    }
    //获取宝宝列表,判断默认宝宝 ，不存在默认宝宝进入第一个宝宝
    private void getBabyList() {
        UserTerm userTerm = new UserTerm();
        userTerm.setUserId(Login_Static.myUserID);
        userTerm.setPageIndex(1);
        userTerm.setPageSize(10);
        String data = mGson.toJson(userTerm);
        CLog.e("testing", "获取宝贝列表data：" + data);
        OKHttp.OkHttpPost("?type=4","data", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "获取宝贝列表失败：" + e.toString());
                mDialog.dismiss();
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "获取宝贝列表：" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        mDialog.dismiss();
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        if (jsonArray.length() > 0) {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject array = new JSONObject(jsonArray.getString(i));
                                //CLog.e("baby",array.toString());
                                UserBase baby = mGson.fromJson(array.toString(), UserBase.class);
                                mList.add(baby);
                                if(baby.getIsDefault()==1){
                                    changeUser(mList.get(i).getUserId());
                                }else if(i==jsonArray.length()-1){
                                    changeUser(mList.get(0).getUserId());
                                }
                            }


                        }/*else {
                            mSave.Save_PREFS(getApplicationContext(),"Type","mom");
                            mSave.Save_PREFS(ChioceIdentity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                            Login_Static.flag = 0;
                            Intent mIntent = new Intent(ChioceIdentity.this, My_MyBaby_Activity.class);
                            startActivity(mIntent);
                        }*/

                    }
                    if (Json_Utils.getCode(response) == 1007) {
                        mSave.Save_PREFS(getApplicationContext(),"Type","mom");
                        Intent mIntent = new Intent(LoginActivity.this, My_BabyInformation_Activity.class);
                        mIntent.putExtra("Type",8);
                        mDialog.dismiss();
                        startActivity(mIntent);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //切换密码
    private void changeUser(String userId) {
        CLog.e("testing", "切换用户:" + userId);
        OkHttpUtils.get().url(mMyApplication.getmUrl()+"?type=6")
                .addParams("userid", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "切换用户登录失败" + e.toString());
                        HintText_Dialog hint = new HintText_Dialog(LoginActivity.this, "未知错误", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //得到返回值后，2秒后加载框消失
                                mDialog.dismiss();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "切换用户登录" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                HintText_Dialog hint = new HintText_Dialog(LoginActivity.this, "登录成功", "success");
                                final String error = Json_Utils.getTemplate(response);
                                UserPrimaryKey beans = mGson.fromJson(error, UserPrimaryKey.class);

                                Login_Static.myUserAccount = beans.getAccountId();
                                Login_Static.myUserID = beans.getUserId();
                                mDialog.dismiss();
                                mSave.Save_PREFS(LoginActivity.this, beans.getUserId()+"token", beans.getToken());
                                mSave.Save_PREFS(LoginActivity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                                mSave.Save_PREFS(LoginActivity.this, "Type", "baby");
                                mSave.Save_PREFS(LoginActivity.this, "Vip", "no");
                                Intent mIntent = new Intent(LoginActivity.this, MainActivity.class);
                                BaseAppManager.getInstance().clear();
                                startActivity(mIntent);

                            } else {
                                HintText_Dialog hint = new HintText_Dialog(LoginActivity.this, Json_Utils.getMessage(response), "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //得到返回值后，2秒后加载框消失
                                        mDialog.dismiss();
                                    }
                                }, 2000);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
