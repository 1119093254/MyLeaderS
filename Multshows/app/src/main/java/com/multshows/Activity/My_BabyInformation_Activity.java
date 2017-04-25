package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Beans.BabyAPI;
import com.multshows.Beans.UserAPI;
import com.multshows.Beans.UserBase;
import com.multshows.Beans.UserPrimaryKey;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.BaseAppManager;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.ChangeBirthdayDialog;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 编辑宝贝信息
 */
public class My_BabyInformation_Activity extends BaseActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mSave;//保存
    TextView mName;//昵称
    TextView mBirthday;//生日
    TextView mSex;//性别
    ImageView mIsDefault;//是否默认
    SimpleDraweeView mHeader;//头像
    RelativeLayout mNameLatout;//更换昵称
    RelativeLayout mBirthdayLatout;//更换生日
    RelativeLayout mSexLatout;//更换性别
    RelativeLayout mHeaderLayout;//更换头像
    UserBase bean;
    TextView mTitle;//主题
    int sex=0;
    String name="";
    String birthday="2000-1-1";
    String picHeader="";
    int babyFlag=0;//传入方式
    int isDefault_Flag=0;
    Gson mGson=new Gson();
    Dialog mDialog;
    MyApplication myApplication;
    SaveSharedPreferences mSaveSharedPreferences = new SaveSharedPreferences();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_baby_information);
        //沉浸式
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_BabyInformation_Activity.this, R.color.app_topColor);
        myApplication= (MyApplication)getApplication();
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_BabyInformation_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_BabyInformation_TopLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.My_BabyInforation_parentLayout);
        mSave= (TextView) findViewById(R.id.My_BabyInformation_save);
        mName= (TextView) findViewById(R.id.My_BabyInformation_name);
        mBirthday= (TextView) findViewById(R.id.My_BabyInformation_Birthday);
        mSex= (TextView) findViewById(R.id.My_BabyInformation_Sex);
        mHeader= (SimpleDraweeView) findViewById(R.id.My_BabyInformation_header);
        mNameLatout= (RelativeLayout) findViewById(R.id.My_BabyInformation_changName);
        mBirthdayLatout= (RelativeLayout) findViewById(R.id.My_BabyInformation_changBirthday);
        mSexLatout= (RelativeLayout) findViewById(R.id.My_BabyInformation_changSex);
        mHeaderLayout= (RelativeLayout) findViewById(R.id.My_BabyInformation_changHeader);
        mTitle= (TextView) findViewById(R.id.My_BabyInformation_Title);
        mIsDefault= (ImageView) findViewById(R.id.My_BabyInformation_IsDefault_Message);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_BabyInformation_Activity.this,mReturnLayout,mParentLayout,mReturn,mTitle,mSave);
    }

    /**
     * 数据处理
     */
    private void initData() {
        Intent intent=getIntent();
        bean= (UserBase) intent.getSerializableExtra("My_MyBaby_Beans");
        babyFlag=intent.getIntExtra("Type",0);
        if(babyFlag==8){
            mReturn.setVisibility(View.GONE);
        }else {
            mReturn.setVisibility(View.VISIBLE);
        }
        if(bean!=null) {
            mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(bean.getPortrait())));
            picHeader=bean.getPortrait();
            mName.setText(bean.getNickName());
            name=bean.getNickName();
            mBirthday.setText(Time_Now.getYearTime(bean.getBirthday()));
            if(bean.getSex()==0) {
                mSex.setText("保密");
            }else if(bean.getSex()==1){
                mSex.setText("男");
            }else if(bean.getSex()==2){
                mSex.setText("女");
            }
            if (bean.getIsDefault()==1){
                isDefault_Flag=1;
                mIsDefault.setImageResource(R.drawable.switch_on);
            }else {
                isDefault_Flag=0;
                mIsDefault.setImageResource(R.drawable.switch_off);
            }
        }else {
            mName.setText("");
            mName.setHint("请编写昵称");
        }
        if(babyFlag==1){
            mTitle.setText("完善宝宝信息");
        }else if(babyFlag==3||babyFlag==8){
            mTitle.setText("添加宝宝");
        }
        else if(babyFlag==4){
            mTitle.setText("添加宝宝");
        }else {
            mTitle.setText("编辑宝贝信息");
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
        mIsDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDefault_Flag==1){
                    isDefault_Flag=0;
                    mIsDefault.setImageResource(R.drawable.switch_off);
                }else{
                    isDefault_Flag=1;
                    mIsDefault.setImageResource(R.drawable.switch_on);
                }
            }
        });
        //保存
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nick=mName.getText().toString();
                String con=nick.replace(" ","");
                if (babyFlag==1) {
                    if("".equals(nick) && !"".equals(con)){
                        mName.setHint("请输入昵称");
                        mName.setHintTextColor(getResources().getColor(R.color.red));
                    }else if("".equals(mBirthday.getText().toString())){
                        mBirthday.setHint("请选择生日");
                        mBirthday.setHintTextColor(getResources().getColor(R.color.red));
                    }else if("".equals(mSex.getText().toString())){
                        mSex.setHint("请选择性别");
                        mSex.setHintTextColor(getResources().getColor(R.color.red));
                    }else {
                        mDialog=new HintText_Dialog(My_BabyInformation_Activity.this,R.style.MyDialog);
                        mDialog.show();
                        HintText_Dialog mTextDialog=new HintText_Dialog(My_BabyInformation_Activity.this,"添加中...","");
                        UserBase account = new UserBase();
                        account.setIsDefault(isDefault_Flag);
                        if(Login_Static.mAccount!=null)
                        account.setAccountId(Login_Static.mAccount.getAccountId());
                        account.setPortrait(picHeader);
                        account.setUserType(1);
                        account.setNickName(mName.getText().toString());
                        account.setBirthday(Time_Now.getIntYearTime(mBirthday.getText().toString()));
                        account.setSex(sex);
                        account.setParentId(Login_Static.myUserID);
                        String data = mGson.toJson(account);
                        CLog.e("texting", "添加宝宝" + data);
                        OKHttp.OkHttpPost("/User/addUser", "", data, new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                CLog.e("testing", "添加宝宝失败：" + e.toString());
                                HintText_Dialog mTextDialog=new HintText_Dialog(My_BabyInformation_Activity.this,"添加失败","fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                },Login_Static.hintTime);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                CLog.e("testing", "添加宝宝：" + response);
                                try {
                                    if(Json_Utils.getCode(response)==0){
                                        mSaveSharedPreferences.Save_PREFS(My_BabyInformation_Activity.this, "Vip", "no");
                                        mSaveSharedPreferences.Save_PREFS(My_BabyInformation_Activity.this,"Change","yes");
                                        HintText_Dialog mTextDialog=new HintText_Dialog(My_BabyInformation_Activity.this,"添加成功","success");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                                Intent intent = getIntent();
                                                BabyAPI babyAPI = new BabyAPI();
                                                babyAPI.setNickName(mName.getText().toString());
                                                babyAPI.setSex(sex);
                                                babyAPI.setBirthday(Time_Now.getIntYearTime(mBirthday.getText().toString()));
                                                CLog.e("babyAPI",mBirthday.getText().toString());
                                                babyAPI.setPortrait("");
                                                intent.putExtra("BabyAPI", babyAPI);
                                                setResult(1, intent);
                                                finish();
                                            }
                                        },Login_Static.hintTime);
                                    }else {
                                        HintText_Dialog mTextDialog=new HintText_Dialog(My_BabyInformation_Activity.this,
                                                Json_Utils.getMessage(response),"fail");
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
                }else if (babyFlag==3) {
                    if("".equals(nick) && !"".equals(con)){
                        mName.setHint("请输入昵称");
                        mName.setHintTextColor(getResources().getColor(R.color.red));
                    }else if("".equals(mBirthday.getText().toString())){
                        mBirthday.setHint("请选择生日");
                        mBirthday.setHintTextColor(getResources().getColor(R.color.red));
                    }else if("".equals(mSex.getText().toString())){
                        mSex.setHint("请选择性别");
                        mSex.setHintTextColor(getResources().getColor(R.color.red));
                    }else {
                        mDialog=new HintText_Dialog(My_BabyInformation_Activity.this,R.style.MyDialog);
                        mDialog.show();
                        HintText_Dialog mTextDialog=new HintText_Dialog(My_BabyInformation_Activity.this,"添加中...","");
                        UserBase account = new UserBase();
                        account.setIsDefault(isDefault_Flag);
                        account.setAccountId(Login_Static.myUserAccount);
                        account.setPortrait(picHeader);
                        account.setNickName(mName.getText().toString());
                        account.setBirthday(Time_Now.getIntYearTime(mBirthday.getText().toString()));
                        account.setSex(sex);
                        account.setParentId(Login_Static.myUserID);
                        String data = mGson.toJson(account);
                        CLog.e("texting", "添加宝宝" + data);
                        OKHttp.OkHttpPost("/User/addUser", "", data, new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                CLog.e("testing", "添加宝宝失败：" + e.toString());
                                HintText_Dialog mTextDialog=new HintText_Dialog(My_BabyInformation_Activity.this,
                                        "添加失败","fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                },Login_Static.hintTime);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                CLog.e("testing", "添加宝宝：" + response);
                                try {
                                    if(Json_Utils.getCode(response)==0){
                                        mSaveSharedPreferences.Save_PREFS(My_BabyInformation_Activity.this, "Vip", "no");
                                        mSaveSharedPreferences.Save_PREFS(My_BabyInformation_Activity.this,"Change","yes");
                                        HintText_Dialog mTextDialog=new HintText_Dialog(My_BabyInformation_Activity.this,
                                                "添加成功","success");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                                Intent intent = getIntent();
                                                BabyAPI babyAPI = new BabyAPI();
                                                babyAPI.setNickName(mName.getText().toString());
                                                babyAPI.setSex(sex);
                                                babyAPI.setBirthday(Time_Now.getIntYearTime(mBirthday.getText().toString()));
                                                babyAPI.setPortrait("");
                                                intent.setClass(My_BabyInformation_Activity.this,MainActivity.class);
                                                startActivity(intent);
                                                BaseAppManager.getInstance().removeActivity((LoginActivity)mContext);
                                                finish();
                                            }
                                        },Login_Static.hintTime);
                                    }else {
                                        HintText_Dialog mTextDialog=new HintText_Dialog(My_BabyInformation_Activity.this,
                                                Json_Utils.getMessage(response),"fail");
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
                }else if (babyFlag==4) {
                    if("".equals(nick) && !"".equals(con)){
                        mName.setHint("请输入昵称");
                        mName.setHintTextColor(getResources().getColor(R.color.red));
                    }else if("".equals(mBirthday.getText().toString())){
                        mBirthday.setHint("请选择生日");
                        mBirthday.setHintTextColor(getResources().getColor(R.color.red));
                    }else if("".equals(mSex.getText().toString())){
                        mSex.setHint("请选择性别");
                        mSex.setHintTextColor(getResources().getColor(R.color.red));
                    }else {
                        mDialog=new HintText_Dialog(My_BabyInformation_Activity.this,R.style.MyDialog);
                        mDialog.show();
                        HintText_Dialog mTextDialog=new HintText_Dialog(My_BabyInformation_Activity.this,"添加中...","");
                        UserBase account = new UserBase();
                        account.setIsDefault(isDefault_Flag);
                        account.setAccountId(Login_Static.myUserAccount);
                        account.setPortrait(picHeader);
                        account.setNickName(mName.getText().toString());
                        account.setBirthday(Time_Now.getIntYearTime(mBirthday.getText().toString()));
                        account.setSex(sex);
                        account.setParentId(Login_Static.myUserID);
                        String data = mGson.toJson(account);
                        CLog.e("texting", "添加宝宝" + data);
                        OKHttp.OkHttpPost("/User/addUser", "", data, new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                CLog.e("testing", "添加宝宝失败：" + e.toString());
                                HintText_Dialog mTextDialog=new HintText_Dialog(My_BabyInformation_Activity.this,
                                        "添加失败","fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                },Login_Static.hintTime);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                CLog.e("testing", "添加宝宝：" + response);
                                try {
                                    if(Json_Utils.getCode(response)==0){
                                        HintText_Dialog mTextDialog=new HintText_Dialog(My_BabyInformation_Activity.this,
                                                "添加成功","success");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                                mSaveSharedPreferences.Save_PREFS(My_BabyInformation_Activity.this, "Vip", "no");
                                                mSaveSharedPreferences.Save_PREFS(My_BabyInformation_Activity.this,"Change","yes");
                                                Intent intent = getIntent();
                                                BabyAPI babyAPI = new BabyAPI();
                                                babyAPI.setNickName(mName.getText().toString());
                                                babyAPI.setSex(sex);
                                                babyAPI.setBirthday(Time_Now.getIntYearTime(mBirthday.getText().toString()));
                                                babyAPI.setPortrait("");
                                                intent.setClass(My_BabyInformation_Activity.this,My_MyBaby_Activity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        },Login_Static.hintTime);
                                    }else {
                                        HintText_Dialog mTextDialog=new HintText_Dialog(My_BabyInformation_Activity.this,
                                                Json_Utils.getMessage(response),"fail");
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
                }else if(babyFlag==8){
                    if("".equals(nick) && !"".equals(con)){
                        mName.setHint("请输入昵称");
                        mName.setHintTextColor(getResources().getColor(R.color.red));
                    }else if("".equals(mBirthday.getText().toString())){
                        mBirthday.setHint("请选择生日");
                        mBirthday.setHintTextColor(getResources().getColor(R.color.red));
                    }else if("".equals(mSex.getText().toString())){
                        mSex.setHint("请选择性别");
                        mSex.setHintTextColor(getResources().getColor(R.color.red));
                    }else {
                        mDialog = new HintText_Dialog(My_BabyInformation_Activity.this, R.style.MyDialog);
                        mDialog.show();
                        HintText_Dialog mTextDialog = new HintText_Dialog(My_BabyInformation_Activity.this, "添加中...", "");
                        UserBase account = new UserBase();
                        account.setIsDefault(isDefault_Flag);
                        account.setAccountId(Login_Static.myUserAccount);
                        account.setPortrait(picHeader);
                        account.setNickName(mName.getText().toString());
                        account.setBirthday(Time_Now.getIntYearTime(mBirthday.getText().toString()));
                        account.setSex(sex);
                        account.setParentId(Login_Static.myUserID);
                        String data = mGson.toJson(account);
                        CLog.e("texting", "添加宝宝" + data);
                        OKHttp.OkHttpPost("?type=5", "data", data, new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                CLog.e("testing", "添加宝宝失败：" + e.toString());
                                HintText_Dialog mTextDialog = new HintText_Dialog(My_BabyInformation_Activity.this,
                                        "添加失败", "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                }, Login_Static.hintTime);
                            }

                            @Override
                            public void onResponse(final String response, int id) {
                                CLog.e("testing", "添加宝宝：" + response);
                                try {
                                    if (Json_Utils.getCode(response) == 0) {
                                        String ids = Json_Utils.getTemplate(response);
                                        changeUser(ids);

                                    } else {
                                        HintText_Dialog mTextDialog = new HintText_Dialog(My_BabyInformation_Activity.this,
                                                Json_Utils.getMessage(response), "fail");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
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
                }else {
                    if("".equals(nick) && !"".equals(con)){
                        mName.setHint("请输入昵称");
                        mName.setHintTextColor(getResources().getColor(R.color.red));
                    } else if ("".equals(mBirthday.getText().toString())) {
                        mBirthday.setHint("请选择生日");
                        mBirthday.setHintTextColor(getResources().getColor(R.color.red));
                    } else if ("".equals(mSex.getText().toString())) {
                        mSex.setHint("请选择性别");
                        mSex.setHintTextColor(getResources().getColor(R.color.red));
                    } else {
                        mDialog = new HintText_Dialog(My_BabyInformation_Activity.this, R.style.MyDialog);
                        mDialog.show();
                        HintText_Dialog mTextDialog = new HintText_Dialog(My_BabyInformation_Activity.this, "修改中...", "");
                        //修改保存
                        UserAPI userAPI = new UserAPI();
                        userAPI.setIsDefault(isDefault_Flag);
                        userAPI.setSex(sex);
                        userAPI.setUserId(bean.getUserId());
                        userAPI.setNickName(mName.getText().toString());
                        userAPI.setBirthday(Time_Now.getIntYearTime(mBirthday.getText().toString()));
                        userAPI.setPortrait(picHeader);
                        String data = mGson.toJson(userAPI);
                        CLog.e("data", data);
                        OKHttp.OkHttpPost("/User/UpdateUserInfo", "", data, new StringCallback() {
                            @Override
                            public void onError(Call call, Exception e, int id) {
                                HintText_Dialog mTextDialog = new HintText_Dialog(My_BabyInformation_Activity.this, "修改失败", "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                }, Login_Static.hintTime);
                            }

                            @Override
                            public void onResponse(String response, int id) {
                                CLog.e("testing", "changeNickName:" + response);
                                try {
                                    if (Json_Utils.getCode(response) == 0) {
                                        HintText_Dialog mTextDialog = new HintText_Dialog(My_BabyInformation_Activity.this,
                                                "修改成功", "success");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                                finish();
                                            }
                                        }, Login_Static.hintTime);
                                    } else {
                                        HintText_Dialog mTextDialog = new HintText_Dialog(My_BabyInformation_Activity.this,
                                                Json_Utils.getMessage(response), "fail");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
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
                }
            }
        });
        //更换头像
        mHeaderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(My_BabyInformation_Activity.this, My_BabyHeader_Activity.class);
                if(bean!=null) {
                    intent.putExtra("babyHeader", bean.getPortrait());
                }else {
                    intent.putExtra("babyHeader", "");
                }
                startActivityForResult(intent,3);
            }
        });
        //更换昵称
        mNameLatout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bean!=null) {
                    Intent intent = new Intent(My_BabyInformation_Activity.this, My_BabyInformationChange_Activity.class);
                    intent.putExtra("changeType", "name");
                    intent.putExtra("name", bean.getNickName());
                    startActivityForResult(intent, 1);
                }else {
                    Intent intent = new Intent(My_BabyInformation_Activity.this, My_BabyInformationChange_Activity.class);
                    intent.putExtra("changeType", "name");
                    intent.putExtra("name", mName.getText().toString());
                    startActivityForResult(intent, 1);
                }
            }
        });
        //更换生日
        mBirthdayLatout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowAbortTimeDialog(mBirthday.getText().toString());
            }
        });
        //更换性别
        mSexLatout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bean!=null) {
                Intent intent=new Intent(My_BabyInformation_Activity.this,My_BabyInformationChange_Activity.class);
                intent.putExtra("changeType","sex");
                intent.putExtra("sex",bean.getSex());
                startActivityForResult(intent,1);
                }else {
                    Intent intent=new Intent(My_BabyInformation_Activity.this,My_BabyInformationChange_Activity.class);
                    intent.putExtra("changeType","sex");
                    intent.putExtra("sex",0);
                    startActivityForResult(intent,1);
                }
            }
        });
    }

    private void ShowAbortTimeDialog(String data) {
        ChangeBirthdayDialog mChangeBirthDialog = new ChangeBirthdayDialog(
                My_BabyInformation_Activity.this,1);
        if(!"".equals(data)){
            String[] times=data.split("-");
            mChangeBirthDialog.setDate(Integer.parseInt(times[0]),Integer.parseInt(times[1]),Integer.parseInt(times[2]));
        }else {
            mChangeBirthDialog.setDate(2000,1,1);
        }
        mChangeBirthDialog.show();
        mChangeBirthDialog.setClosingdateListener(new ChangeBirthdayDialog.OnClosingdateListener() {

            @Override
            public void onClick(String year, String month, String day) {
                // TODO Auto-generated method stub
                mBirthday.setText(year + "-" + month + "-" + day);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                if(resultCode==3){
                    name=data.getStringExtra("name");
                    mName.setText(name);
                }
                if(resultCode==2){
                    sex=data.getIntExtra("sex",0);
                    if(sex==0) {
                        mSex.setText("保密");
                    }else if(sex==1){
                        mSex.setText("男");
                    }else if(sex==2){
                        mSex.setText("女");
                    }
                }
                break;
            case 3:
                    if(resultCode==1){
                        picHeader=data.getStringExtra("babyImage");
                        if(picHeader!=null) {
                            mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(picHeader)));
                        }else {
                            mHeader.setImageURI(Uri.parse(""));
                        }
                    }
                break;
        }
    }

    @Override
    public void findViews() {

    }

    @Override
    public void addListeners() {

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
                        HintText_Dialog hint = new HintText_Dialog(mContext, "未知错误", "fail");
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
                                HintText_Dialog hint = new HintText_Dialog(mContext, "添加成功", "success");
                                final String error = Json_Utils.getTemplate(response);
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //得到返回值后，2秒后加载框消失
                                        mDialog.dismiss();
                                        UserPrimaryKey beans = mGson.fromJson(error, UserPrimaryKey.class);

                                        Login_Static.myUserAccount = beans.getAccountId();
                                        Login_Static.myUserID = beans.getUserId();
                                        mSaveSharedPreferences.Save_PREFS(mContext, "token", beans.getToken());
                                        mSaveSharedPreferences.Save_PREFS(mContext, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                                        mSaveSharedPreferences.Save_PREFS(mContext, "Type", "baby");
                                        mSaveSharedPreferences.Save_PREFS(mContext, "Vip", "no");
                                        mSaveSharedPreferences.Save_PREFS(mContext, "Unlogin", "no");
                                        Intent mIntent = new Intent(mContext, MainActivity.class);
                                        startActivity(mIntent);
                                        finish();
                                        BaseAppManager.getInstance().clear();

                                    }
                                }, Login_Static.hintTime);


                            } else {
                                HintText_Dialog hint = new HintText_Dialog(mContext, Json_Utils.getMessage(response), "fail");
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

}
