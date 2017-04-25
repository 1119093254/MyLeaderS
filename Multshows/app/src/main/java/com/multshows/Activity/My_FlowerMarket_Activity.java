package com.multshows.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Beans.Account;
import com.multshows.Beans.SystemFlowers;
import com.multshows.Beans.User;
import com.multshows.Beans.UserAmount;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.RechangeNumber_Pop;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.DecimalFormat;

import okhttp3.Call;

/**
 * 鲜花市场
 */
public class My_FlowerMarket_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    TextView mMyFlower;//我的鲜花
    TextView mMyBalance;//我的余额
    SimpleDraweeView mHeader;//用户头像
    Button mButton;//购买按钮
    EditText mNumber;//购买数量
    TextView mBuyMoney;//购买总金额
    TextView mTitle;//标题

    RelativeLayout mFlower_1;//1束鲜花
    RelativeLayout mFlower_10;//10束鲜花
    RelativeLayout mFlower_20;//20束鲜花
    RelativeLayout mFlower_50;//50束鲜花
    RelativeLayout mFlower_100;//100束鲜花
    RelativeLayout mFlower_200;//200束鲜花
    ImageView mFlowerIamge_1;//1束鲜花
    ImageView mFlowerIamge_10;//10束鲜花
    ImageView mFlowerIamge_20;//20束鲜花
    ImageView mFlowerIamge_50;//50束鲜花
    ImageView mFlowerIamge_100;//100束鲜花
    ImageView mFlowerIamge_200;//200束鲜花

    InputMethodManager manager;
    Gson mGson=new Gson();
    Dialog mDialog;
    RechangeNumber_Pop mNumberPop;
    Dialog_Hint hintText_dialog;
    Dialog_Hint mDialogHint;
    double flowermoney=0;//金额
    double price=0.1;//单价
    MyApplication myApplication;
    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_flower_market);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage_noColor_2(My_FlowerMarket_Activity.this);
        myApplication= (MyApplication) getApplication();
        manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        type=mSharedPreferences.Get_PREFS(My_FlowerMarket_Activity.this,"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        initView();
        if("mom".equals(type)) {
            //妈妈端
            mReturn.setImageResource(R.drawable.index_return_arrow);
            mTitle.setTextColor(getResources().getColor(R.color.text_grey_2));
        }else if("baby".equals(type)){
            //宝宝端
            mReturn.setImageResource(R.drawable.return_arrow);
            mTitle.setTextColor(getResources().getColor(R.color.red_grey));
        }
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_FlowerMarket_return);
        mHeader= (SimpleDraweeView) findViewById(R.id.My_FlowerMarket_userHeader);
        mMyFlower= (TextView) findViewById(R.id.My_FlowerMarket_myFlower);
        mMyBalance= (TextView) findViewById(R.id.My_FlowerMarket_myBalance);
        mButton= (Button) findViewById(R.id.my_recharge_fragment_Button);
        mNumber= (EditText) findViewById(R.id.My_FlowerMarket_EditText);
        mTitle= (TextView) findViewById(R.id.My_FlowerMarket_Title);
        mBuyMoney= (TextView) findViewById(R.id.My_FlowerMarket_BuyMoneyEditText);
        mFlower_1= (RelativeLayout) findViewById(R.id.My_FlowerMarket_flower1);
        mFlower_10= (RelativeLayout) findViewById(R.id.My_FlowerMarket_flower10);
        mFlower_20= (RelativeLayout) findViewById(R.id.My_FlowerMarket_flower20);
        mFlower_50= (RelativeLayout) findViewById(R.id.My_FlowerMarket_flower50);
        mFlower_100= (RelativeLayout) findViewById(R.id.My_FlowerMarket_flower100);
        mFlower_200= (RelativeLayout) findViewById(R.id.My_FlowerMarket_flower200);
        mFlowerIamge_1= (ImageView) findViewById(R.id.My_FlowerMarket_flowerImage1);
        mFlowerIamge_10= (ImageView) findViewById(R.id.My_FlowerMarket_flowerImage10);
        mFlowerIamge_20= (ImageView) findViewById(R.id.My_FlowerMarket_flowerImage20);
        mFlowerIamge_50= (ImageView) findViewById(R.id.My_FlowerMarket_flowerImage50);
        mFlowerIamge_100= (ImageView) findViewById(R.id.My_FlowerMarket_flowerImage100);
        mFlowerIamge_200= (ImageView) findViewById(R.id.My_FlowerMarket_flowerImage200);

    }

    /**
     * 数据处理
     */
    private void initData() {
        getInformation();
        getFlowerPrice();
        if(!"null".equals(Login_Static.mAccount.getPortrait()) && Login_Static.mAccount.getPortrait()!=null) {
            mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(Login_Static.mAccount.getPortrait())));
        }else {
            mHeader.setImageURI(Uri.parse(""));
        }
        //mMyFlower.setText(""+Login_Static.mAccount.getFlowers());
        //mMyBalance.setText(""+Login_Static.mAccount.getBalance());

    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HideInputManager_Utils.hideInputManager(My_FlowerMarket_Activity.this);
                finish();
            }
        });
        //购买
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nums=mNumber.getText().toString();
                if("".equals(mNumber.getText().toString()) || Integer.parseInt(mNumber.getText().toString())==0){
                    mDialog=new HintText_Dialog(My_FlowerMarket_Activity.this,R.style.MyDialog);
                    mDialog.show();
                    HintText_Dialog mTextDialog=new HintText_Dialog(My_FlowerMarket_Activity.this,
                            "请输入购买数量","fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                        }
                    },Login_Static.hintTime);

                }else{
                    mButton.setEnabled(false);
                    double m = Integer.parseInt(nums.toString()) * price;
                    DecimalFormat df = new DecimalFormat("0.##");
                    flowermoney = Double.parseDouble(df.format(m));
                    mNumberPop=new RechangeNumber_Pop(My_FlowerMarket_Activity.this ,df.format(m));
                    getUser();
                }
            }
        });


        mFlower_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumber.setText("1");
                mFlower_1.setBackgroundResource(R.drawable.shape_alight_green_2);
                mFlower_10.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_20.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_50.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_100.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_200.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlowerIamge_1.setImageResource(R.drawable.slelected_flower);
                mFlowerIamge_10.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_20.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_50.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_100.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_200.setImageResource(R.drawable.money_flower_hua);
            }
        });
        mFlower_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumber.setText("10");
                mFlower_1.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_10.setBackgroundResource(R.drawable.shape_alight_green_2);
                mFlower_20.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_50.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_100.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_200.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlowerIamge_1.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_10.setImageResource(R.drawable.slelected_flower);
                mFlowerIamge_20.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_50.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_100.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_200.setImageResource(R.drawable.money_flower_hua);
            }
        });
        mFlower_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumber.setText("20");
                mFlower_1.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_10.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_20.setBackgroundResource(R.drawable.shape_alight_green_2);
                mFlower_50.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_100.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_200.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlowerIamge_1.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_10.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_20.setImageResource(R.drawable.slelected_flower);
                mFlowerIamge_50.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_100.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_200.setImageResource(R.drawable.money_flower_hua);
            }
        });
        mFlower_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumber.setText("50");
                mFlower_1.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_10.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_20.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_50.setBackgroundResource(R.drawable.shape_alight_green_2);
                mFlower_100.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_200.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlowerIamge_1.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_10.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_20.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_50.setImageResource(R.drawable.slelected_flower);
                mFlowerIamge_100.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_200.setImageResource(R.drawable.money_flower_hua);
            }
        });
        mFlower_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumber.setText("100");
                mFlower_1.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_10.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_20.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_50.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_100.setBackgroundResource(R.drawable.shape_alight_green_2);
                mFlower_200.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlowerIamge_1.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_10.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_20.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_50.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_100.setImageResource(R.drawable.slelected_flower);
                mFlowerIamge_200.setImageResource(R.drawable.money_flower_hua);
            }
        });
        mFlower_200.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNumber.setText("200");
                mFlower_1.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_10.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_20.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_50.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_100.setBackgroundResource(R.drawable.shape_alight_grey_2);
                mFlower_200.setBackgroundResource(R.drawable.shape_alight_green_2);
                mFlowerIamge_1.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_10.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_20.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_50.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_100.setImageResource(R.drawable.money_flower_hua);
                mFlowerIamge_200.setImageResource(R.drawable.slelected_flower);
            }
        });
        mNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0) {
                    double m = Integer.parseInt(s.toString()) * 0.1;
                    DecimalFormat df = new DecimalFormat("0.##");
                    String sMoney = df.format(m);
                    mBuyMoney.setText("¥"+sMoney);
                }else {
                    mBuyMoney.setText("¥0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            if(getCurrentFocus()!=null && getCurrentFocus().getWindowToken()!=null){
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
    private void getFlowerLsit(){
        OKHttp.OkHttpPost("/SystemSet/GetFlowersList", "", "", new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","获取鲜花市场失败"+e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","获取鲜花市场"+response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        JSONArray jsonArray=new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            SystemFlowers flowers=mGson.fromJson(jsonArray.getString(i),SystemFlowers.class);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void buyFlower(String count){
        double m = Integer.parseInt(count) * 0.1;
        DecimalFormat df = new DecimalFormat("0.##");
        String flowermoney = df.format(m);
        //String flowermoney=Integer.parseInt(count)*0.1+"";
        OkHttpUtils.get().url(myApplication.getUrl()+"/User/BuyFlower")
                .addParams("userid",Login_Static.myUserID)    //用户id加密
                .addParams("count",count)     //鲜花数量
                .addParams("flowermoney",flowermoney)    //购买鲜花的金额
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","购买鲜花失败"+e.toString());
                        HintText_Dialog mTextDialog=new HintText_Dialog(My_FlowerMarket_Activity.this,
                                "网络异常","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","购买鲜花"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                mNumber.setText("");
                                getInformation();
                                mNumberPop.setNumText();
                                mNumberPop.dismiss();
                                HintText_Dialog mTextDialog=new HintText_Dialog(My_FlowerMarket_Activity.this,
                                        "支付成功","success");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                },Login_Static.hintTime);
                            }else {
                                HintText_Dialog mTextDialog=new HintText_Dialog(My_FlowerMarket_Activity.this,
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

    /**
     * 获取数据
     */
    private void getInformation() {
      OkHttpUtils.get().url(myApplication.getUrl()+"/User/GetUserAmount")
              .addParams("userid",Login_Static.myUserID)
              .build()
              .execute(new StringCallback() {
                  @Override
                  public void onError(Call call, Exception e, int id) {

                  }

                  @Override
                  public void onResponse(String response, int id) {
                      try {
                          if(Json_Utils.getCode(response)==0){
                              UserAmount bean=mGson.fromJson(Json_Utils.getTemplate(response),UserAmount.class);
                              mMyFlower.setText(""+bean.getFlowers());
                              mMyBalance.setText(""+bean.getBalance());
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                  }
              });
    }
    /**
     * 验证支付密码
     */
    private void isRecharge(String pwd){
        OkHttpUtils.get().url(myApplication.getUrl()+"/User/IsCheckPayPassword")
                .addParams("userId", Login_Static.myUserID)
                .addParams("pwd",pwd)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","验证支付密码失败"+e.toString());
                        HintText_Dialog mTextDialog=new HintText_Dialog(My_FlowerMarket_Activity.this,
                                "网络异常","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","验证支付密码"+response);
                        try {
                            if(Integer.parseInt(Json_Utils.getTemplate(response))==1){
                                //正确时调用——
                                buyFlower(mNumber.getText().toString());
                            }else {
                                //错误时执行——
                                mDialog.dismiss();
                                hintText_dialog = new Dialog_Hint(My_FlowerMarket_Activity.this
                                        , 0, "您的输入密码不正确,是否重新输入?", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        mNumberPop.setNumText();
                                        hintText_dialog.dismiss();
                                    }
                                });
                                hintText_dialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
    /**
     * 获取个人信息数据
     */
    private void getUser() {
        OkHttpUtils.get().url(myApplication.getUrl() + "/User/GetUser")
                .addParams("userid", Login_Static.myUserID)
                .addParams("targetuserid", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "个人信息 失败" + e.toString());
                        mButton.setEnabled(true);
                        mDialog=new HintText_Dialog(My_FlowerMarket_Activity.this,R.style.MyDialog);
                        mDialog.show();
                        HintText_Dialog mTextDialog=new HintText_Dialog(My_FlowerMarket_Activity.this,
                                "网络异常","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, final int id) {
                        mButton.setEnabled(true);
                        try {
                            CLog.e("testing", "个人信息：" + Json_Utils.getTemplate(response));
                            if (Json_Utils.getCode(response) == 0) {
                                User mUser = mGson.fromJson(Json_Utils.getTemplate(response), User.class);
                                if("".equals(mUser.getPayPassword())){
                                    mDialogHint = new Dialog_Hint(My_FlowerMarket_Activity.this, 0,
                                            "您还没有设置支付密码,是否前往设置?", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mDialogHint.dismiss();
                                            Intent intent = new Intent(My_FlowerMarket_Activity.this, Login_FindPassword_Activity.class);
                                            intent.putExtra("PassWordType", 2);
                                            startActivity(intent);
                                        }
                                    });
                                    mDialogHint.show();
                                }else if(mUser.getBalance()<flowermoney){
                                    //没设置
                                    mDialogHint = new Dialog_Hint(My_FlowerMarket_Activity.this, 0,
                                            "您的余额已不足,是否前往充值?", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mDialogHint.dismiss();
                                            Intent intent = new Intent(My_FlowerMarket_Activity.this, My_MyWallet_Activity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    mDialogHint.show();
                                }else {
                                    //设置弹出动画效果
                                    mNumberPop.setAnimationStyle(R.style.PopupAnimation);
                                    //显示窗口  //设置layout在PopupWindow中显示的位置
                                    mNumberPop.showAtLocation(findViewById(R.id.My_FlowerMarket_ParentsLayout),
                                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                                    mNumberPop.setNum(new RechangeNumber_Pop.Num() {
                                        @Override
                                        public void num(String text) {
                                            CLog.e("testing", text);
                                            mDialog=new HintText_Dialog(My_FlowerMarket_Activity.this,R.style.MyDialog);
                                            mDialog.show();
                                            HintText_Dialog mTextDialog=new HintText_Dialog(My_FlowerMarket_Activity.this,
                                                    "支付中...","");
                                            //判断密码是否正确
                                            isRecharge(text);

                                        }
                                    });
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    /**
     * 获取鲜花价格
     */
    private void getFlowerPrice(){
        OkHttpUtils.get().url(myApplication.getUrl() + "/User/FlowerPrice")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取鲜花价格失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取鲜花价格"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                price=Double.parseDouble(Json_Utils.getTemplate(response));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
