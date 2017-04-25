package com.multshows.Fragment;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Activity.ChioceIdentity;
import com.multshows.Activity.LoginActivity;
import com.multshows.Activity.MainActivity;
import com.multshows.Activity.My_BabyInformation_Activity;
import com.multshows.Beans.UserBase;
import com.multshows.Beans.UserLogin;
import com.multshows.Beans.UserPrimaryKey;
import com.multshows.Beans.UserRegister;
import com.multshows.Beans.UserTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.BaseAppManager;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;

/**
 * 描述：输入验证码
 * 作者：李萧萧
 * 时间：2016.9/3
 */
public class Login_RegisterVerificationFragment extends Fragment {
    @Bind(R.id.verificationEditext)
    EditText mverificationEditext;
    @Bind(R.id.SetphoneText)
    TextView mSetphoneText;
    @Bind(R.id.verificationNext)
    Button mNext;
    @Bind(R.id.ClearverificationImage)
    ImageView mClearImage;
    @Bind(R.id.register_send)
    Button mRegisterSend;
    @Bind(R.id.register_send_two)
    Button mRegisterSendTwo;
    String mExCode;//邀请码
    MyApplication mApplication;
    String Identity;//身份
    private boolean init;//记录界面是否为第一次加载
    Dialog mDialog;//消息提示框
    int i = 60;
    public String IsThree;//是否为第三方登录
    String Logintoken;//第三方token登录
    List<UserBase> mList = new ArrayList<>();//宝宝集合
    private static final int VERIFICATION_CODE = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case VERIFICATION_CODE:
                    mverificationEditext.setText(msg.obj.toString());
                    mverificationEditext.setSelection(msg.obj.toString().trim().length());
                    break;
            }
        }
    };
    SaveSharedPreferences mSave=new SaveSharedPreferences();
    Gson mGson = new Gson();
    String IsExistMobile;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                if (i >= 10)
                    mRegisterSendTwo.setText(i + "秒");
                if (i > 0 && i < 10) {
                    mRegisterSendTwo.setText("0" + i + "秒");
                }
            } else if (msg.what == -8) {
                mRegisterSendTwo.setVisibility(View.GONE);
                mRegisterSend.setVisibility(View.VISIBLE);
                i = 60;
            } else {
                int event = msg.arg1;
          /*      int result = msg.arg2;
                Object data = msg.obj;*/
                CLog.e("event", "event=" + event);
            }
        }
    };

    public Login_RegisterVerificationFragment() {
    }

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
//
            if (mverificationEditext != null && !mverificationEditext.getText().toString().equals("")) {
                mNext.setEnabled(true);
                mNext.setTextColor(getResources().getColor(R.color.text_white));
            } else {
                mNext.setEnabled(false);
                mNext.setTextColor(getResources().getColor(R.color.text_gray));
            }


        }
    };
    Login_RegisterPasswordFragment mLogin_RegisterPasswordFragment;
    FragmentTransaction mFragmentTransaction;
    FragmentManager mFragmentManager;
    String phone;//接收上一页输入的手机号
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registerverification, container, false);
        init = true;//第一次加载
        ButterKnife.bind(this, view);
        mApplication= (MyApplication) getActivity().getApplication();
        SmsContentObserver smsContentObserver = new SmsContentObserver(mHandler);
        Uri smsUri = Uri.parse("content://sms");
        getActivity().getContentResolver().registerContentObserver(smsUri, true, smsContentObserver);
        Logintoken= (String) getActivity().getIntent().getSerializableExtra("Logintoken");
        IsThree= (String) getActivity().getIntent().getSerializableExtra("IsThree");
        mDialog = new HintText_Dialog(getActivity(), R.style.MyDialog);
        mLogin_RegisterPasswordFragment=new Login_RegisterPasswordFragment();
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mverificationEditext.addTextChangedListener(textWatcher);
        mverificationEditext.requestFocus();
        return view;
    }
//初始化数据
    private void initData() {
        Bundle bundle = getArguments();
        phone = bundle.getString("phone");
        mExCode = bundle.getString("ExCode");
        Identity = bundle.getString("Identity");
        IsExistMobile = bundle.getString("IsExistMobile");
        mSetphoneText.setText("请输入"+phone+"收到的短信验证码");
    }

    /**
     * 功能：该frament第一次显示的时候，加载网络数据
     * * 参数：无
     * 返回：无
     */
    private void getservciesData() {
        initData();//初始化数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (; i > 0; i--) {
                    handler.sendEmptyMessage(-9);
                    if (i <= 0) {
                        break;
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                handler.sendEmptyMessage(-8);
            }
        }).start();
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
       // ButterKnife.unbind(this);
    }

    @OnClick({R.id.verificationEditext, R.id.verificationNext, R.id.ClearverificationImage, R.id.register_send, R.id.register_send_two})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.verificationEditext:
               /* FragmentTransaction ft2 = getFragmentManager().beginTransaction();
                ft2.replace(R.id.RegisterFrameLayout, new Login_RegisterPhoneFragment());
                ft2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft2.addToBackStack(null);
                ft2.commit();*/
                break;
            case R.id.verificationNext:
                mDialog.show();
                HintText_Dialog hint = new HintText_Dialog(getActivity(),"正在验证...", "");
                CheckRandomSMS(phone,mverificationEditext.getText().toString());
                break;
            case R.id.ClearverificationImage:
                mverificationEditext.setText("");
                mClearImage.setVisibility(View.GONE);
                break;
            case R.id.register_send:
                mRegisterSendTwo.setVisibility(View.VISIBLE);
                mRegisterSend.setVisibility(View.GONE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for (; i > 0; i--) {
                            handler.sendEmptyMessage(-9);
                            if (i <= 0) {
                                break;
                            }
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        handler.sendEmptyMessage(-8);
                    }
                }).start();
                break;
            case R.id.register_send_two:

                break;
        }
    }
    public void CheckRandomSMS(String mPhone ,String CheckRandom){
        OkHttpUtils.get().url(mApplication.getUrl() + "/service/CheckRandomSMS")
                .addParams("mobile", mPhone)
                .addParams("code", CheckRandom)
                .addParams("type", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", e.getMessage());
                        showError("验证失败",0);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                if(IsExistMobile.equals("no"))
                                showError("验证成功",2);
                                else {
                                    ThreeRegister();
                                }
                            } else {
                                //后期判断错误原因
                                showError("验证码错误",0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    //发布结果提示
    public void showError(final String errorString, final int is) {
        String isString=null;
        if(is==1){
            isString="success";
        }
        if(is==2){
            isString="success";
        }
        if(is==0){
            isString="fail";
        }
        HintText_Dialog hint = new HintText_Dialog(getActivity(), errorString, isString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //得到返回值后，2秒后加载框消失
                if(is==2){
                    Bundle bundle = new Bundle();
                    bundle.putString("verificationNum", mverificationEditext.getText().toString());
                    bundle.putString("phone", phone);
                    bundle.putString("ExCode", mExCode);
                    bundle.putString("Identity", Identity);
                    mLogin_RegisterPasswordFragment.setArguments(bundle);
                    mFragmentTransaction.replace(R.id.RegisterFrameLayout, mLogin_RegisterPasswordFragment);
                    mFragmentTransaction.addToBackStack(null);
                    //提交事务
                    mFragmentTransaction.commit();
                }
                if(is==1){
                    Three_Login(Logintoken,Integer.parseInt(IsThree));
                }
                mDialog.dismiss();
            }
        }, 2000);

    }
    //第三方绑定、注册
    public void ThreeRegister(){
        UserRegister mUserRegister=new UserRegister();
        mUserRegister.setAccount(phone);
        mUserRegister.setPwd("");
        mUserRegister.setNickName("");
        mUserRegister.setType(Integer.parseInt(IsThree));
        mUserRegister.setAppKey(Logintoken);
        CLog.e("setAccountType",Identity);
        if(Identity.equals("vip")){
            mUserRegister.setAccountType(1);
        }else {
            mUserRegister.setAccountType(2);
        }
        String data = mGson.toJson(mUserRegister);
        mDialog.show();
        HintText_Dialog hint = new HintText_Dialog(getActivity(),"正在注册...", "");
        OKHttp.OkHttpPost("/User/Register", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id)  {showError("注册失败", 0);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("response",""+response);
                try {
                    if(Json_Utils.getCode(response)==0) {
                        JSONObject jsonObject = null;
                        showError("绑定成功",1);
                        jsonObject = new JSONObject(response);
                        JSONObject object = new JSONObject(jsonObject.getString("Template"));
                        CLog.e("testing", "AccountId:" + object.getString("AccountId"));
                        Login_Static.myUserAccount = object.getString("AccountId");
                        Login_Static.myUserID=object.getString("UserId");
                        JPushInterface.setAlias(getActivity(), "领秀S"+Login_Static.myUserID, new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                Log.i("JPush", "Jpush status: " + i+s);//状态
                            }
                        });//极光推送设置设备名称
                        mSave.Save_PREFS(getActivity(),"userAccount","");
                        mSave.Save_PREFS(getActivity(),"userpassword","");
                    }else {
                        //后期判断错误原因
                        showError("注册失败", 0);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }
    //type  1为微信登录 ，2为微博登录，3为QQ登录
    public void Three_Login(String token, int type) {
        //登录
        UserLogin userLogin = new UserLogin();
        userLogin.setAppKey(token);
        userLogin.setType(type);
        String data = mGson.toJson(userLogin);
        CLog.e("testing", data);
        CLog.e("response", data);
        OKHttp.OkHttpPost("/User/Login", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("response", e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("response", response);
                CLog.e("testing", "==" + response);
                JSONObject jsonObject = null;
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        UserPrimaryKey beans = mGson.fromJson(Json_Utils.getTemplate(response), UserPrimaryKey.class);
                        if (beans.getIsBind() == 1) {
                            Login_Static.myUserAccount = beans.getAccountId();
                            Login_Static.myUserID = beans.getUserId();
                            mSave.Save_PREFS(getActivity(), "userAccount", "");
                            mSave.Save_PREFS(getActivity(), "userpassword", "");
                            mSave.Save_PREFS(getActivity(), beans.getUserId()+"token", beans.getToken());
                            mSave.Save_PREFS(getActivity(), "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                            mSave.Save_PREFS(getActivity(), "Type", "mom");
                            if(Identity.equals("vip")){
                                mSave.Save_PREFS(getActivity(),"Type","mom");//进入的是妈妈端
                                mSave.Save_PREFS(getActivity(), "Vip", "yes");
                                mSave.Save_PREFS(getActivity(), "Unlogin", "no");
                                Intent mIntent = new Intent(getActivity(), MainActivity.class);
                                startActivity(mIntent);
                            }else {
                                mSave.Save_PREFS(getActivity(),"Type","mom");//进入的是妈妈端
                                mSave.Save_PREFS(getActivity(), "Vip", "no");
                                mSave.Save_PREFS(getActivity(), "Unlogin", "no");
                                getBabyList();
                            }
                            getActivity().finish();
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
    class SmsContentObserver extends ContentObserver {
        private Cursor cursor;
        public SmsContentObserver(Handler handler) {
            super(handler);
        }
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            //读取收件箱中指定号码的短信
            try {
                cursor=getActivity().managedQuery(Uri.parse("content://sms/inbox"),
                        new String[]{"_id", "address", "read", "body"},
                        "address=? and read=?", new String[]{"1069118424392", "0"}, "_id desc");//按id排序
                if (cursor!=null&&cursor.getCount()>0){
                    ContentValues values=new ContentValues();
                    values.put("read", "1");//修改短信为已读模式
                    cursor.moveToNext();
                    int smsBodyColumn=cursor.getColumnIndex("body");
                    String smsBody=cursor.getString(smsBodyColumn);
                    final String code = getVerificationCode(smsBody);
                    Message msg = new Message();
                    msg.what = VERIFICATION_CODE;
                    msg.obj = code;
                    mHandler.sendMessage(msg);
                }
            }catch (Exception e){

            }
            //在用managedQuery的时候，不能主动调用close()方法，否则在Android 4.0+的系统上会发生崩溃
            if (Build.VERSION.SDK_INT<14){
                cursor.close();
            }
        }
    }
    public String getVerificationCode(String str){
        String[] strarray=str.split("验证码：");
        CLog.e("smsBody",strarray[1].substring(0,6));
        return strarray[1].substring(0,6);
    }
    //获取宝宝列表,判断默认宝宝 ，不存在默认宝宝进入第一个宝宝
    private void getBabyList() {
        UserTerm userTerm = new UserTerm();
        userTerm.setUserId(Login_Static.myUserID);
        userTerm.setPageIndex(1);
        userTerm.setPageSize(10);
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
                        mSave.Save_PREFS(getActivity(),"Type","mom");
                        Intent mIntent = new Intent(getActivity(), My_BabyInformation_Activity.class);
                        mIntent.putExtra("Type",8);
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
        OkHttpUtils.get().url(mApplication.getUrl() + "/User/SwitchUserStatusNoPwd")
                .addParams("userid", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "切换用户登录失败" + e.toString());
                        HintText_Dialog hint = new HintText_Dialog(getActivity(), "未知错误", "fail");
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
                                HintText_Dialog hint = new HintText_Dialog(getActivity(), "登录成功", "success");
                                final String error = Json_Utils.getTemplate(response);
                                UserPrimaryKey beans = mGson.fromJson(error, UserPrimaryKey.class);
                                Login_Static.myUserAccount = beans.getAccountId();
                                Login_Static.myUserID = beans.getUserId();
                                mDialog.dismiss();
                                mSave.Save_PREFS(getActivity(), beans.getUserId()+"token", beans.getToken());
                                mSave.Save_PREFS(getActivity(), "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                                mSave.Save_PREFS(getActivity(), "Type", "baby");
                                mSave.Save_PREFS(getActivity(), "Vip", "no");
                                Intent mIntent = new Intent(getActivity(), MainActivity.class);
                                getActivity().finish();
                                BaseAppManager.getInstance().clear();
                                startActivity(mIntent);

                            } else {
                                HintText_Dialog hint = new HintText_Dialog(getActivity(), Json_Utils.getMessage(response), "fail");
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
