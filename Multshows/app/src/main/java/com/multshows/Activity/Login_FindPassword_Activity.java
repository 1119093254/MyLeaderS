package com.multshows.Activity;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.Verification_Utils;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 描述：找回密码
 * 作者：李萧萧
 * 时间：2016.9.1
 */
public class Login_FindPassword_Activity extends AppCompatActivity {

    @Bind(R.id.find_password_account)
    EditText mFindPasswordAccount;//手机号输入处
    @Bind(R.id.register_send)
    Button mRegisterSend;//发送验证码按钮（用来点击，点击后隐藏）
    @Bind(R.id.register_send_two)
    Button mRegisterSendTwo;//发送验证码按钮（用来显示时间）
    @Bind(R.id.find_password_captcha)
    EditText mFindPasswordCaptcha;//验证码输入框
    @Bind(R.id.find_password_button)
    Button mFindPasswordButton;//下一步按钮点击
    TextView mTitle;//标题
    Dialog mDialog;//消息提示框
    int i = 60;//获取验证码间隔时间
    boolean flag=false;//发送验证码是否成功
    int PassWordType=0;//判断是找回什么密码
    MyApplication mApplication ;
    private static final int VERIFICATION_CODE = 1;
    //自动获取短信验证码
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case VERIFICATION_CODE:
                    mFindPasswordCaptcha.setText(msg.obj.toString());
                    mFindPasswordCaptcha.setSelection(msg.obj.toString().trim().length());
                    break;
            }
        }
    };
    //获取验证码倒计时
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == -9) {
                if (i >= 10)
                    mRegisterSendTwo.setText(i + "秒");
                if (i > 0 && i < 10) {
                    mRegisterSendTwo.setText("0" + i + "秒");
                }
            } else if (msg.what == -8) {
                mRegisterSend.setText("获取验证码");
                mRegisterSend.setClickable(true);//恢复获取验证码按钮可点击
                mRegisterSend.setVisibility(View.VISIBLE);
                mRegisterSendTwo.setVisibility(View.INVISIBLE);
                i = 60;
            } else {
                int event = msg.arg1;
          /*      int result = msg.arg2;
                Object data = msg.obj;*/
                CLog.e("event", "event=" + event);
            }
        }
    };
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
  //文本框输入后的监听
            if(mFindPasswordCaptcha.getText().length()>0&&flag){
                mFindPasswordButton.setEnabled(true);
                mFindPasswordButton.setTextColor(Color.parseColor("#ffffff"));
            }else {
                mFindPasswordButton.setEnabled(false);
                mFindPasswordButton.setTextColor(Color.parseColor("#81838E"));
            }
            CLog.e("flag",""+flag);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_findpassword);
        ButterKnife.bind(this);
        mApplication= (MyApplication) getApplication();
        //监听短信
        SmsContentObserver smsContentObserver = new SmsContentObserver(mHandler);
        Uri smsUri = Uri.parse("content://sms");
        getContentResolver().registerContentObserver(smsUri, true, smsContentObserver);
        initView();
        initData();
        mFindPasswordCaptcha.addTextChangedListener(textWatcher);
        mDialog = new HintText_Dialog(Login_FindPassword_Activity.this, R.style.MyDialog);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        mTitle= (TextView) findViewById(R.id.find_password_Title);
    }

    /**
     * 数据处理
     */
    private void initData() {
        MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(Login_FindPassword_Activity.this, R.color.green);
        Intent intent=getIntent();
        PassWordType=intent.getIntExtra("PassWordType",0);
       if(PassWordType==1){
           mTitle.setText("设置切换密码");
           if(Login_Static.mAccount!=null) {
               mFindPasswordAccount.setText(Login_Static.mAccount.getLoginName());
               mFindPasswordAccount.setFocusable(false);
           }
        }else if(PassWordType==2){
           mTitle.setText("设置支付密码");
           if(Login_Static.mAccount!=null) {
               mFindPasswordAccount.setText(Login_Static.mAccount.getLoginName());
               mFindPasswordAccount.setFocusable(false);
           }
       }else if(PassWordType==3){
           mTitle.setText("找回登录密码");
           if(Login_Static.mAccount!=null) {
               mFindPasswordAccount.setText(Login_Static.mAccount.getLoginName());
               mFindPasswordAccount.setFocusable(false);
           }
       }else {
           mTitle.setText("找回登录密码");
       }
    }

    @OnClick({R.id.register_send, R.id.register_send_two, R.id.find_button_two, R.id.find_password_button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_send:
                if(Verification_Utils.checkMobileNumber(mFindPasswordAccount.getText().toString()))
                {
                    mDialog.show();
                    HintText_Dialog hint = new HintText_Dialog(Login_FindPassword_Activity.this,"正在发送...", "");
                    IsExistMobile(mFindPasswordAccount.getText().toString());

                }else {

                }
                break;
            case R.id.register_send_two:
                break;
            case R.id.find_button_two:
                break;
            case R.id.find_password_button:
                mDialog.show();
                HintText_Dialog hint = new HintText_Dialog(Login_FindPassword_Activity.this,"正在验证...", "");
                if(PassWordType==2){
                    //根据类型验证验证码
                    CheckRandomSMS(mFindPasswordAccount.getText().toString(),mFindPasswordCaptcha.getText().toString(),"4");
                }else {
                    //根据类型验证验证码
                    CheckRandomSMS(mFindPasswordAccount.getText().toString(),mFindPasswordCaptcha.getText().toString(),"2");
                }

                break;
        }
    }
    //发送验证码接口调用
    public void findPassWord_back(View view) {
        finish();
    }
    public void SendRandomSMS(String mPhone,String type){
            OkHttpUtils.get().url(mApplication.getUrl() + "/service/SendRandomSMS")
                    .addParams("mobile", mPhone)
                    .addParams("type", type)
                    .build()
                    .execute(new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            showError("发送失败", 0);
                        }

                        @Override
                        public void onResponse(String response, int id) {
                            CLog.e("testing", response);
                            JSONObject jsonObject = null;
                            try {
                                if (Json_Utils.getCode(response) == 0) {
                                    showError("发送成功", 1);
                                    flag = true;
                                    mRegisterSend.setVisibility(View.GONE);//设置Button按钮不可见
                                    mRegisterSendTwo.setVisibility(View.VISIBLE);//设置Button按钮不可见
                                    mRegisterSend.setClickable(false);//设置获取验证码按钮不可点击
                                    mRegisterSendTwo.setText(i + "秒后获取");
                                    //倒计时
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
                                } else {
                                    //后期判断错误原因
                                    showError("发送失败", 0);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
    }
    //调用接口查看手机号是否已注册
    public void IsExistMobile(String mPhone){
        OkHttpUtils.get().url(mApplication.getUrl() + "/User/IsExistMobile")
                .addParams("mobile", mPhone)
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
                                showError("该手机号未注册",0);
                            } else if (Json_Utils.getCode(response) ==1008){
                                if(PassWordType==2){
                                    SendRandomSMS(mFindPasswordAccount.getText().toString(),"4");
                                }else {
                                    SendRandomSMS(mFindPasswordAccount.getText().toString(),"2");
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    //验证验证码接口调用
    public void CheckRandomSMS(String mPhone ,String CheckRandom,String type){
        OkHttpUtils.get().url(mApplication.getUrl() + "/service/CheckRandomSMS")
                .addParams("mobile", mPhone)
                .addParams("code", CheckRandom)
                .addParams("type", type)
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
                                showError("验证成功",2);

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
    //提示显示
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
        HintText_Dialog hint = new HintText_Dialog(Login_FindPassword_Activity.this, errorString, isString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //得到返回值后，2秒后加载框消失
                if(is==2){
                    Intent mIntent = new Intent(Login_FindPassword_Activity.this, Setting_ChangePassword_new_Activity.class);
                    mIntent.putExtra("phone",mFindPasswordAccount.getText().toString());
                    mIntent.putExtra("type","find");
                    mIntent.putExtra("PassWord_type",PassWordType);
                    startActivity(mIntent);
                    finish();
                }
                mDialog.dismiss();
            }
        }, 1000);
    }
    //读取对应短信，并提取验证码
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
                cursor=managedQuery(Uri.parse("content://sms/inbox"),
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
