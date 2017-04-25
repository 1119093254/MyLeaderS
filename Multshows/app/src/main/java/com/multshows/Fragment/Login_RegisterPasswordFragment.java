package com.multshows.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multshows.Activity.ChioceIdentity;
import com.multshows.Activity.LoginActivity;
import com.multshows.Activity.LoginRegisterActivity;
import com.multshows.Activity.MainActivity;
import com.multshows.Activity.My_BabyInformation_Activity;
import com.multshows.Activity.My_MyBaby_Activity;
import com.multshows.Activity.Setting_ChangePassword_new_Activity;
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

import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import okhttp3.Call;

/**
 * 描述：注册activity,加载不同的viewpage
 * 作者：李萧萧
 * 时间：2016.9/3
 */
public class Login_RegisterPasswordFragment extends Fragment {
    @Bind(R.id.password_Editext)
    EditText mpassword_Editext;//密码输入框 1
    @Bind(R.id.Surepassword_Editext)
    EditText Surempassword_Editext;//密码输入框2
    @Bind(R.id.password_Name)
    EditText mpassword_Name;//昵称输入框
    @Bind(R.id.Register)
    Button mNext;//注册按钮
    @Bind(R.id.password_invisible)
    ImageView mPassword_invisibleImage;//密码可见性图片图标
    @Bind(R.id.Surepassword_invisible)
    ImageView mSurePassword_invisibleImage;//确定密码按钮
    Login_RegisterVerificationFragment mVerificationFragment;
    FragmentTransaction mFragmentTransaction;
    FragmentManager mFragmentManager;
    private boolean init;//记录界面是否为第一次加载
    boolean isHidden = false;//记录密码框是否可见状态
    boolean isSureHidden = false;//记录确认密码框是否可见状态
    String phone;//接收手机号
    String verificationNum;//接收上一页验证码
    Dialog mDialog;//消息提示框
    String ExCode;//邀请码
    Gson mGson = new Gson();
    public String IsThree;//是否为第三方登录
    String Logintoken;//第三方token登录
    String Identity;//身份
    SaveSharedPreferences mSave=new SaveSharedPreferences();
    public Login_RegisterPasswordFragment() {
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
            if(mpassword_Editext!=null&&!mpassword_Editext.getText().toString().equals("")){
                mPassword_invisibleImage.setVisibility(View.VISIBLE);
                String text=mpassword_Name.getText().toString();
                String con=text.replace(" ","");
                if (mpassword_Editext.getText().toString().length()>=6&&!con.equals("")&&Surempassword_Editext.getText().toString().equals(mpassword_Editext.getText().toString())){
                    mNext.setEnabled(true);
                    mNext.setTextColor(getResources().getColor(R.color.text_white));
                }else {
                    mNext.setEnabled(false);
                    mNext.setTextColor(getResources().getColor(R.color.text_gray));
                }
            }else {
                mPassword_invisibleImage.setVisibility(View.INVISIBLE);
                mNext.setEnabled(false);
                mNext.setTextColor(getResources().getColor(R.color.text_gray));
            }
            if(Surempassword_Editext!=null&&!Surempassword_Editext.getText().toString().equals("")){
                mSurePassword_invisibleImage.setVisibility(View.VISIBLE);
            }else {
                mSurePassword_invisibleImage.setVisibility(View.INVISIBLE);
                mNext.setEnabled(false);
                mNext.setTextColor(getResources().getColor(R.color.text_gray));
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registerpassword, container, false);
        init = true;//第一次加载
        IsThree= (String) getActivity().getIntent().getSerializableExtra("IsThree");
        Logintoken= (String) getActivity().getIntent().getSerializableExtra("Logintoken");
        mDialog = new HintText_Dialog(getActivity(), R.style.MyDialog);
        mVerificationFragment=new Login_RegisterVerificationFragment();
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        ButterKnife.bind(this, view);
        mpassword_Editext.addTextChangedListener(textWatcher);
        Surempassword_Editext.addTextChangedListener(textWatcher);
        mpassword_Name.addTextChangedListener(textWatcher);
        mpassword_Name.requestFocus();
        return view;
    }

    /**
     * 功能：该frament第一次显示的时候，加载网络数据
     * * 参数：无
     * 返回：无
     */
    private void getservciesData() {
        Bundle bundle = getArguments();
        phone = bundle.getString("phone");
        ExCode = bundle.getString("ExCode");
        verificationNum = bundle.getString("verificationNum");
        Identity = bundle.getString("Identity");
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

    @OnClick({R.id.password_Editext, R.id.Register, R.id.password_invisible,R.id.Surepassword_invisible})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.password_Editext:
                break;
            case R.id.Register:
                if (IsThree.equals("no")) {
                    Register();
                }else{
                    ThreeRegister();
                }



                break;
            case R.id.password_invisible:
                if(!isHidden){
                    mPassword_invisibleImage.setImageResource(R.drawable.item_password_visible);
                    mpassword_Editext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mpassword_Editext.setSelection(mpassword_Editext.length());
                }else {
                    mPassword_invisibleImage.setImageResource(R.drawable.item_password_invisible);
                    mpassword_Editext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mpassword_Editext.setSelection(mpassword_Editext.length());
                }
                isHidden=!isHidden;
                break;
            case R.id.Surepassword_invisible:
                if(!isSureHidden){
                    mSurePassword_invisibleImage.setImageResource(R.drawable.item_password_visible);
                    Surempassword_Editext.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Surempassword_Editext.setSelection(mpassword_Editext.length());
                }else {
                    mSurePassword_invisibleImage.setImageResource(R.drawable.item_password_invisible);
                    Surempassword_Editext.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Surempassword_Editext.setSelection(mpassword_Editext.length());
                }
                isSureHidden=!isSureHidden;
                break;
        }
    }

    public void showError(final String errorString, final int is) {
        String isString=null;
        if(is==1){
            isString="success";
        }
        if(is==2){
            isString="success";
        }if(is==3){
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
                    Login();
                }
                if(is==3){
                    Three_Login(Logintoken,Integer.parseInt(IsThree));
                }
                mDialog.dismiss();
            }
        }, 2000);
    }
    public void Login(){
        //登录
        UserLogin userLogin=new UserLogin();
        userLogin.setAccount(phone);
        userLogin.setPwd(mpassword_Editext.getText().toString());
        userLogin.setType(0);
        String data=mGson.toJson(userLogin);
        CLog.e("testing",data);
        CLog.e("response",data);
        OKHttp.OkHttpPost("/User/Login", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("response",e.getMessage());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("response",response);
                JSONObject jsonObject = null;
                try {
                    if(Json_Utils.getCode(response)==0) {
                        UserPrimaryKey beans=mGson.fromJson(Json_Utils.getTemplate(response),UserPrimaryKey.class);
                        Login_Static.myUserAccount = beans.getAccountId();
                        Login_Static.myUserID=beans.getUserId();
                        JPushInterface.setAlias(getActivity(), "领秀S"+beans.getUserId(), new TagAliasCallback() {
                            @Override
                            public void gotResult(int i, String s, Set<String> set) {
                                Log.i("JPush", "Jpush status: " + i+s);//状态
                            }
                        });//极光推送设置设备名称
                        mSave.Save_PREFS(getActivity(),"userAccount",phone);
                        mSave.Save_PREFS(getActivity(),"userpassword",mpassword_Editext.getText().toString());
                        mSave.Save_PREFS(getActivity(),beans.getUserId()+"token",beans.getToken());
                 //       Log.e("getToken",beans.getToken());
                        mSave.Save_PREFS(getActivity(), "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                        if(Identity.equals("vip")){
                            mSave.Save_PREFS(getActivity(),"Type","mom");//进入的是妈妈端
                            mSave.Save_PREFS(getActivity(), "Vip", "yes");
                            mSave.Save_PREFS(getActivity(), "Unlogin", "no");
                            Intent mIntent = new Intent(getActivity(), MainActivity.class);
                            startActivity(mIntent);
                        }else {
                            mSave.Save_PREFS(getActivity(),"Type","mom");//进入的是妈妈端
                            Intent mIntent = new Intent(getActivity(), My_BabyInformation_Activity.class);
                            mIntent.putExtra("Type",8);
                            mSave.Save_PREFS(getActivity(), "Vip", "no");
                            startActivity(mIntent);
                        }
                        getActivity().finish();
                    }else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //第三方绑定、注册
    public void ThreeRegister(){
        UserRegister mUserRegister=new UserRegister();
        mUserRegister.setAccount(phone);
        mUserRegister.setPwd(mpassword_Editext.getText().toString());
        mUserRegister.setNickName(mpassword_Name.getText().toString());
        mUserRegister.setType(Integer.parseInt(IsThree));
        mUserRegister.setAppKey(Logintoken);
        if(Identity.equals("vip")){
            mUserRegister.setAccountType(1);
        }else {
            mUserRegister.setAccountType(2);
        }
        CLog.e("phone",phone+":"+mpassword_Editext.getText().toString());
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
                        showError("注册成功", 3);
                        jsonObject = new JSONObject(response);
                        JSONObject object = new JSONObject(jsonObject.getString("Template"));
                        CLog.e("testing", "AccountId:" + object.getString("AccountId"));
                        Login_Static.myUserAccount = object.getString("AccountId");
                        Login_Static.myUserID=object.getString("UserId");
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
    //注册
    public void Register(){
        UserRegister mUserRegister=new UserRegister();
        mUserRegister.setAccount(phone);
        mUserRegister.setPwd(mpassword_Editext.getText().toString());
        mUserRegister.setNickName(mpassword_Name.getText().toString());
        mUserRegister.setType(0);
        mUserRegister.setExCode(ExCode);
        if(Identity.equals("vip")){
            mUserRegister.setAccountType(1);
        }else {
            mUserRegister.setAccountType(2);
        }
        CLog.e("phone",phone+":"+mpassword_Editext.getText().toString());
        String data = mGson.toJson(mUserRegister);
        Log.e("data",phone+":"+data);
        mDialog.show();
        HintText_Dialog hint = new HintText_Dialog(getActivity(),"正在注册...", "");
        OKHttp.OkHttpPost("?type=3", "data", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id)  {
                CLog.e("call",e.toString());
                showError("注册失败", 0);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("response",""+response);
                try {
                    if(Json_Utils.getCode(response)==0) {
                        JSONObject jsonObject = null;
                        showError("注册成功", 2);
                        jsonObject = new JSONObject(response);
                        JSONObject object = new JSONObject(jsonObject.getString("Template"));
                        CLog.e("testing", "AccountId:" + object.getString("AccountId"));
                        Login_Static.myUserAccount = object.getString("AccountId");
                        Login_Static.myUserID=object.getString("UserId");
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
                            JPushInterface.setAlias(getActivity(), "领秀S"+beans.getUserId(), new TagAliasCallback() {
                                @Override
                                public void gotResult(int i, String s, Set<String> set) {
                                    Log.i("JPush", "Jpush status: " + i+s);//状态
                                }
                            });//极光推送设置设备名称
                            mSave.Save_PREFS(getActivity(), "userAccount", "");
                            mSave.Save_PREFS(getActivity(), "userpassword", "");
                            mSave.Save_PREFS(getActivity(), beans.getUserId()+"token", beans.getToken());
                            mSave.Save_PREFS(getActivity(), "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                            if(Identity.equals("vip")){
                                mSave.Save_PREFS(getActivity(),"Type","mom");//进入的是妈妈端
                                mSave.Save_PREFS(getActivity(), "Vip", "yes");
                                mSave.Save_PREFS(getActivity(), "Unlogin", "no");
                                Intent mIntent = new Intent(getActivity(), MainActivity.class);
                                BaseAppManager.getInstance().clear();
                                startActivity(mIntent);
                            }else {
                                mSave.Save_PREFS(getActivity(),"Type","mom");//进入的是妈妈端
                                Intent mIntent = new Intent(getActivity(), My_BabyInformation_Activity.class);
                                mIntent.putExtra("Type",8);
                                mSave.Save_PREFS(getActivity(), "Vip", "no");
                                mSave.Save_PREFS(getActivity(), "Unlogin", "no");
                                startActivity(mIntent);
                                BaseAppManager.getInstance().clear();
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
}