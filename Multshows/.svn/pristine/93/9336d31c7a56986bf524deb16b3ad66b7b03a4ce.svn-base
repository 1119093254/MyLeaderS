package com.multshows.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.multshows.Beans.UserAuthentication;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.CountDownTimerUtils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.Verification_Utils;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 填写实名认证
 */
public class MyInformation_RealName_Fragment extends Fragment{
    private static final int VERIFICATION_CODE = 1;
    Context mContext;
    View mView;
    EditText mName;//姓名
    EditText mIDCard;//身份证号
    EditText mBankID;//银行卡号
    EditText mPhone;//手机号
    EditText mCord;//验证码
    Button mButton;//完成按钮
    Button mSendCord;//发送验证码
    ImageView mDelete;//删除验证码

    Dialog mDialog;
    Gson mGson=new Gson();
    MyApplication mMyApplication;
    //标志
    boolean flag_1=false,flag_2=false,flag_3=false,flag_4=false,flag_5=false;
    CountDownTimerUtils cus;//倒计时
    int type=0;//0没认证 6认证失败，修改认证
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case VERIFICATION_CODE:
                    mCord.setText(msg.obj.toString());
                    mCord.setSelection(msg.obj.toString().trim().length());
                    break;
            }
        }
    };
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.my_information_realname,null);
        mMyApplication= (MyApplication) getActivity().getApplication();
        SmsContentObserver smsContentObserver = new SmsContentObserver(mHandler);
        Uri smsUri = Uri.parse("content://sms");
        getActivity().getContentResolver().registerContentObserver(smsUri, true, smsContentObserver);
        initView();
        initData();
        initListen();
        return mView;
    }

    /**
     * 初始化
     */
    private void initView() {
        mName= (EditText) mView.findViewById(R.id.My_Information_RealName_name);
        mIDCard= (EditText) mView.findViewById(R.id.My_Information_RealName_IDCard);
        mBankID= (EditText) mView.findViewById(R.id.My_Information_RealName_bankId);
        mPhone= (EditText) mView.findViewById(R.id.My_Information_RealName_phone);
        mCord= (EditText) mView.findViewById(R.id.My_Information_RealName_Code);
        mButton= (Button) mView.findViewById(R.id.My_Information_RealName_button);
        mSendCord= (Button) mView.findViewById(R.id.My_Information_RealName_sendCord);
        mDelete= (ImageView) mView.findViewById(R.id.My_Information_RealName_delete);
        mDialog = new HintText_Dialog(mContext,R.style.MyDialog);
    }

    /**
     * 数据处理
     */
    private void initData() {
        Bundle bundle=getArguments();
        if(bundle!=null) {
            type = bundle.getInt("type");
        }
        if(type==6){
            getRealName();
        }
        cus=new CountDownTimerUtils(mSendCord,60000,1000);
        //获取验证码
        mSendCord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Verification_Utils.checkMobileNumber(mPhone.getText().toString())) {
                    cus.start();
                    setCode(mPhone.getText().toString());
                }else {
                    mDialog.show();
                    HintText_Dialog mHintTextDialog=new HintText_Dialog(mContext,
                            "请输入正确的手机号","fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                        }
                    },Login_Static.hintTime);
                }
            }
        });
        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                flag_1 = count > 0;
                String con=charSequence.toString().replace(" ","");
                if("".equals(con)){
                    flag_1=false;
                }
                if(flag_1&&flag_2&&flag_3&&flag_4&&flag_5){
                    mButton.setEnabled(true);
                    mButton.setTextColor(getResources().getColor(R.color.white));
                    mButton.setBackgroundResource(R.drawable.shape_green_4);
                }else {
                    mButton.setEnabled(false);
                    mButton.setTextColor(getResources().getColor(R.color.text_white_c));
                    mButton.setBackgroundResource(R.drawable.shape_grey3_4);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mIDCard.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                flag_2 = count > 0;
                if(flag_1&&flag_2&&flag_3&&flag_4&&flag_5){
                    mButton.setEnabled(true);
                    mButton.setTextColor(getResources().getColor(R.color.white));
                    mButton.setBackgroundResource(R.drawable.shape_green_4);
                }else {
                    mButton.setEnabled(false);
                    mButton.setTextColor(getResources().getColor(R.color.text_white_c));
                    mButton.setBackgroundResource(R.drawable.shape_grey3_4);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mBankID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                flag_3 = count > 0;
                if(flag_1&&flag_2&&flag_3&&flag_4&&flag_5){
                    mButton.setEnabled(true);
                    mButton.setTextColor(getResources().getColor(R.color.white));
                    mButton.setBackgroundResource(R.drawable.shape_green_4);
                }else {
                    mButton.setEnabled(false);
                    mButton.setTextColor(getResources().getColor(R.color.text_white_c));
                    mButton.setBackgroundResource(R.drawable.shape_grey3_4);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                flag_4 = count > 0;
                if(flag_1&&flag_2&&flag_3&&flag_4&&flag_5){
                    mButton.setEnabled(true);
                    mButton.setTextColor(getResources().getColor(R.color.white));
                    mButton.setBackgroundResource(R.drawable.shape_green_4);
                }else {
                    mButton.setEnabled(false);
                    mButton.setTextColor(getResources().getColor(R.color.text_white_c));
                    mButton.setBackgroundResource(R.drawable.shape_grey3_4);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mCord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if(count>0){
                    flag_5=true;
                    mDelete.setVisibility(View.VISIBLE);
                }else {
                    flag_5=false;
                    mDelete.setVisibility(View.INVISIBLE);
                }
                if(flag_1&&flag_2&&flag_3&&flag_4&&flag_5){
                    mButton.setEnabled(true);
                    mButton.setTextColor(getResources().getColor(R.color.white));
                    mButton.setBackgroundResource(R.drawable.shape_green_4);
                }else {
                    mButton.setEnabled(false);
                    mButton.setTextColor(getResources().getColor(R.color.text_white_c));
                    mButton.setBackgroundResource(R.drawable.shape_grey3_4);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    /**
     * 事件监听
     */
    private void initListen() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton.setEnabled(false);
                mDialog.show();
                HintText_Dialog mHintTextDialog = new HintText_Dialog(mContext,
                        "正在上传...", "");

                    checkCode(mPhone.getText().toString(), mCord.getText().toString());
            }
        });
        //删除验证码
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCord.setText("");
                mDelete.setVisibility(View.INVISIBLE);
            }
        });
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        cus.cancel();
    }


    //实名认证发送验证码
    private void setCode(String phone){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/service/SendRandomSMS")
                .addParams("mobile",phone)
                .addParams("type","5")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","实名认证发送验证码失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","实名认证发送验证码"+response);
                        try {
                            if (Json_Utils.getCode(response)==0){

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //验证实名认证验证码
    private void checkCode(String phone,String code){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/service/CheckRandomSMS")
                .addParams("mobile",phone)
                .addParams("type","5")
                .addParams("code",code)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","验证实名认证验证码失败"+e.toString());
                        HintText_Dialog mHintTextDialog=new HintText_Dialog(mContext,
                                "网络异常","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                mButton.setEnabled(true);
                            }
                        },Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","验证实名认证验证码成功"+response);
                        try {
                            if (Json_Utils.getCode(response)==0){
                                if(type==6){
                                    setCheckRealName();
                                }else {
                                    putRealName();
                                }
                            }else {
                                HintText_Dialog mHintTextDialog=new HintText_Dialog(mContext,
                                        "验证码验证失败","fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                        mButton.setEnabled(true);
                                    }
                                },Login_Static.hintTime);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //上传实名认证
    private void putRealName(){
        UserAuthentication userAuthentication=new UserAuthentication();
        userAuthentication.setUserId(Login_Static.myUserID);
        userAuthentication.setBankCard(mBankID.getText().toString());
        userAuthentication.setIdCard(mIDCard.getText().toString());
        userAuthentication.setMobile(mPhone.getText().toString());
        userAuthentication.setName(mName.getText().toString());
        String data=mGson.toJson(userAuthentication);
        CLog.e("testing","上传实名认证data:"+data);
        OKHttp.OkHttpPost("/User/AddAuthen", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","网络异常,实名认证失败"+e.toString());
                HintText_Dialog mHintTextDialog=new HintText_Dialog(mContext,
                        "网络异常,实名认证失败","fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                        mButton.setEnabled(true);
                    }
                },Login_Static.hintTime);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","上传实名认证"+response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        HintText_Dialog mHintTextDialog=new HintText_Dialog(mContext,
                                "实名认证成功","success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                mButton.setEnabled(true);
                                getActivity().finish();
                            }
                        },Login_Static.hintTime);
                    }else {
                        HintText_Dialog mHintTextDialog=new HintText_Dialog(mContext,
                                Json_Utils.getMessage(response),"fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                mButton.setEnabled(true);
                            }
                        },Login_Static.hintTime);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //修改实名认证
    private void setCheckRealName(){
        UserAuthentication userAuthentication=new UserAuthentication();
        userAuthentication.setUserId(Login_Static.myUserID);
        userAuthentication.setBankCard(mBankID.getText().toString());
        userAuthentication.setIdCard(mIDCard.getText().toString());
        userAuthentication.setMobile(mPhone.getText().toString());
        userAuthentication.setName(mName.getText().toString());
        String data=mGson.toJson(userAuthentication);
        CLog.e("testing","修改实名认证data:"+data);
        OKHttp.OkHttpPost("/User/UpdateAuthen", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","修改实名认证失败"+e.toString());
                HintText_Dialog mHintTextDialog=new HintText_Dialog(mContext,
                        "网络异常","fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                        mButton.setEnabled(true);
                    }
                },Login_Static.hintTime);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","修改实名认证"+response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        HintText_Dialog mHintTextDialog=new HintText_Dialog(mContext,
                                "修改实名认证成功","success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                mButton.setEnabled(true);
                                getActivity().finish();
                            }
                        },Login_Static.hintTime);
                    }else {
                        HintText_Dialog mHintTextDialog=new HintText_Dialog(mContext,
                                Json_Utils.getMessage(response),"fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                mButton.setEnabled(true);
                            }
                        },Login_Static.hintTime);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    //获取实名认证
    private void getRealName(){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/User/GetAuthen")
                .addParams("userId",Login_Static.myUserID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取实名认证"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取实名认证"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                UserAuthentication bean=mGson.fromJson(Json_Utils.getTemplate(response),UserAuthentication.class);
                                mName.setText(bean.getName());
                                mIDCard.setText(bean.getIdCard());
                                mBankID.setText(bean.getBankCard());
                                mPhone.setText(bean.getMobile());
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
                    CLog.e("smsBody",smsBody);
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
}
