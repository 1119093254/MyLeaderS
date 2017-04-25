package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multshows.Beans.FindPassWord;
import com.multshows.Beans.UpdatePwdAPI;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 设置——设置新密码
 */
public class Setting_ChangePassword_new_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    EditText mEditText;//新密码输入框
    EditText mEditText_2;//重复新密码输入框
    Button mButton;//完成
    ImageView mDelete;//删除
    ImageView mDelete_2;//重复 删除
    TextView mTitle;//标题、
    Gson mGson = new Gson();
    String OldPwd = "";
    String mPhone;//手机号
    String type="";//手机号
    int mPassWordType = 0;//判断是找回什么密码
    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_change_password_new);
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Setting_ChangePassword_new_Activity.this, R.color.app_topColor);
        initView();
        initData();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.Setting_ChangePassword_new_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Setting_ChangePassword_new_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Setting_ChangePassword_new_ParentsLayout);
        mEditText = (EditText) findViewById(R.id.Setting_ChangePassword_new_editText);
        mEditText_2 = (EditText) findViewById(R.id.Setting_ChangePassword_new_editText_2);
        mButton = (Button) findViewById(R.id.Setting_ChangePassword_new_button);
        mDelete = (ImageView) findViewById(R.id.Setting_ChangePassword_new_delete);
        mDelete_2 = (ImageView) findViewById(R.id.Setting_ChangePassword_new_delete_2);
        mTitle = (TextView) findViewById(R.id.Setting_ChangePassword_new_Title);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Setting_ChangePassword_new_Activity.this,mReturnLayout,mParentLayout,mReturn,mTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        Intent intent = getIntent();
        OldPwd = intent.getStringExtra("oldPwd");
        mPhone = intent.getStringExtra("phone");
        type = intent.getStringExtra("type");
        mPassWordType = intent.getIntExtra("PassWord_type", 0);
        if (mPassWordType == 1) {
            //找回切换密码
            mTitle.setText("设置切换密码");
            mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            mEditText_2.setInputType(InputType.TYPE_CLASS_NUMBER);
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
            mEditText_2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
        } else if (mPassWordType == 2) {
            //设置支付密码
            mTitle.setText("设置支付密码");
            mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
            mEditText_2.setInputType(InputType.TYPE_CLASS_NUMBER);
            mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
            mEditText_2.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
        } else {
            //找回账号密码
            mTitle.setText("设置账号密码");
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
        //监听EditText内容变化(新密码)
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (mEditText.getText().toString().length() > 0) {
                    mButton.setBackgroundResource(R.drawable.shape_green_4);
                    mButton.setTextColor(getResources().getColor(R.color.white));
                    mButton.setEnabled(true);
                    mDelete.setVisibility(View.VISIBLE);
                } else {
                    mButton.setBackgroundResource(R.drawable.shape_grey_4);
                    mButton.setTextColor(getResources().getColor(R.color.text_white_c));
                    mButton.setEnabled(false);
                    mDelete.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //监听EditText内容变化(重复新密码)
        mEditText_2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (mEditText_2.getText().toString().length() > 0) {
                    mDelete_2.setVisibility(View.VISIBLE);
                } else {
                    mDelete_2.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        if (mButton.isClickable()) {
            //完成
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDialog = new HintText_Dialog(Setting_ChangePassword_new_Activity.this, R.style.MyDialog);
                    mDialog.show();
                    if (!mEditText.getText().toString().equals(mEditText_2.getText().toString())) {
                        //两次密码输入不一致
                        HintText_Dialog hintTextDialog = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
                                "两次密码输入不一致", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    }else {
                        HintText_Dialog hintTextDialog = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
                                "修改中...", "");
                        if (!"find".equals(type)) {
                            UpdatePwdAPI updatePwdAPI = new UpdatePwdAPI();
                            updatePwdAPI.setOldPwd(OldPwd);
                            if(Login_Static.mAccount!=null)
                            updatePwdAPI.setAccountId(Login_Static.mAccount.getAccountId());
                            updatePwdAPI.setNewPwd(mEditText.getText().toString());
                            updatePwdAPI.setType(1);
                            String data = mGson.toJson(updatePwdAPI);
                            OKHttp.OkHttpPost("/User/UpdatePwd", "", data, new StringCallback() {
                                @Override
                                public void onError(Call call, Exception e, int id) {
                                    HintText_Dialog hintTextDialog = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
                                            "网络异常", "fail");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDialog.dismiss();
                                        }
                                    }, Login_Static.hintTime);
                                }

                                @Override
                                public void onResponse(String response, int id) {
                                    try {
                                        if (Json_Utils.getCode(response) == 0) {
                                            HintText_Dialog hintTextDialog = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
                                                    "设置登录密码成功", "success");
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    mDialog.dismiss();
                                                    finish();
                                                }
                                            }, Login_Static.hintTime);
                                        } else {
                                            HintText_Dialog hintTextDialog = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
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
                        } else {
                            String text = mEditText_2.getText().toString();
                            if (mPassWordType == 1) {
                                if (text.length() != 4) {
                                    HintText_Dialog hintTextDialogss = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
                                            "切换密码必须为4位", "fail");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDialog.dismiss();
                                        }
                                    }, Login_Static.hintTime);
                                } else {
                                    //找回切换密码
                                    FindPassWord(2);
                                }
                            } else if (mPassWordType == 2) {
                                if(mEditText.getText().toString().length()<6){
                                    //密码小于6位数
                                    HintText_Dialog hintTextDialogss = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
                                            "密码至少为6位数", "fail");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDialog.dismiss();
                                        }
                                    }, Login_Static.hintTime);
                                }
                                    //设置支付密码
                                    setRecharge(mEditText_2.getText().toString());
                            } else {
                                if(mEditText.getText().toString().length()<6){
                                    //密码小于6位数
                                    HintText_Dialog hintTextDialogss = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
                                            "密码至少为6位数", "fail");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDialog.dismiss();
                                        }
                                    }, Login_Static.hintTime);
                                }
                                //找回账号密码
                                FindPassWord(1);
                            }
                        }

                    }
                }
            });
        }
        //删除
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
                mDelete.setVisibility(View.INVISIBLE);
            }
        });
        //删除(重复)
        mDelete_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText_2.setText("");
                mDelete_2.setVisibility(View.INVISIBLE);
            }
        });

    }

    public void FindPassWord(int Ptype) {
        FindPassWord mFindPassWord = new FindPassWord(mPhone, mEditText_2.getText().toString(), Ptype);
        String data = mGson.toJson(mFindPassWord);
        CLog.e("testing","修改密码data"+data);
        OKHttp.OkHttpPost("/User/FindPassword", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","修改密码失败"+e.toString());
                HintText_Dialog hintTextDialog = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
                        "网络异常", "fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                    }
                }, Login_Static.hintTime);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","修改密码"+response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        HintText_Dialog hintTextDialog = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
                                "设置密码成功", "success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                finish();
                            }
                        }, Login_Static.hintTime);
                    } else {
                        HintText_Dialog hintTextDialog = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
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

    /**
     * 设置支付密码
     */
    private void setRecharge(String pwd) {
        MyApplication myApplication = (MyApplication) getApplication();
        OkHttpUtils.get().url(myApplication.getUrl() + "/User/SetPayPassword")
                .addParams("userId", Login_Static.myUserID)
                .addParams("pwd", pwd)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "设置支付密码失败" + e.toString());
                        HintText_Dialog hintTextDialog = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
                                "网络异常", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "设置支付密码" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                HintText_Dialog hintTextDialog = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
                                        "设置支付密码成功", "success");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                        finish();
                                    }
                                }, Login_Static.hintTime);
                            } else {
                                HintText_Dialog hintTextDialog = new HintText_Dialog(Setting_ChangePassword_new_Activity.this,
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
