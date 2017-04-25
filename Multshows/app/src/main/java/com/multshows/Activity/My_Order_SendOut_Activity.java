package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.Logistics;
import com.multshows.Beans.Logistics_item;
import com.multshows.Beans.Order;
import com.multshows.Beans.OrderEvent_Model;
import com.multshows.Beans.OrderEvent_Model2;
import com.multshows.Beans.OrderLogistics;
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

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

/**
 * 我的订单——发货
 */
public class My_Order_SendOut_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    TextView mGet;//匹配
    //ImageView mSao;//扫一扫
    EditText mEditText;//物流编号
    EditText mNumber;//物流名称
    Button mButton;
    Dialog mDialog;
    Intent mIntent;
    Order mOrder;
    String id="";
    int position;
    int types;
    Gson mGson=new Gson();
    MyApplication mMyApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_sendout);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_Order_SendOut_Activity.this,R.color.app_topColor);
        mMyApplication= (MyApplication) getApplication();
        initView();
        initData();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_Order_SendOut_return);
        mGet= (TextView) findViewById(R.id.My_Order_SendOut_Get);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_Order_SendOut_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.My_Order_SendOut_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.My_Order_SendOut_TopTitle);
        mEditText= (EditText) findViewById(R.id.My_Order_SendOut_EditText);
        mNumber= (EditText) findViewById(R.id.My_Order_SendOut_NumberEditText);
        mButton= (Button) findViewById(R.id.My_Order_SendOut_Button);
       // mSao= (ImageView) findViewById(R.id.My_Order_SendOut_SYS);
        mDialog=new HintText_Dialog(My_Order_SendOut_Activity.this,R.style.MyDialog);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_Order_SendOut_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mIntent=getIntent();
        mOrder= (Order) mIntent.getSerializableExtra("orderBean");
        types=mIntent.getIntExtra("types",0);
        position=mIntent.getIntExtra("position",0);
        if(mOrder!=null){
            mNumber.setText(mOrder.getExpressName());
            mEditText.setText(mOrder.getExpressNumber());
            id=mOrder.getId();
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
        //修改按钮
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=mNumber.getText().toString();
                String number=mEditText.getText().toString();
                mDialog.show();
                if("".equals(name)||"".equals(number)){
                    HintText_Dialog mTextDialog=new HintText_Dialog(My_Order_SendOut_Activity.this,
                            "请把信息填写完整!","fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                        }
                    }, Login_Static.hintTime);
                }else {
                    HintText_Dialog mTextDialog=new HintText_Dialog(My_Order_SendOut_Activity.this,
                            "修改中...","");
                    setOrder(id,name,number);
                }
            }
        });
        //输入框动态监听
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()!=0){
                    mGet.setVisibility(View.VISIBLE);
                }else {
                    mGet.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //匹配物流公司
        mGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLogistics(mEditText.getText().toString());
            }
        });


    }
    //修改物流信息
    private void setOrder(final String orderId, final String name, final String number) {
        OrderLogistics orderLogistics = new OrderLogistics();
        orderLogistics.setOrderId(orderId);
        orderLogistics.setExpressName(name);
        orderLogistics.setExpressNumber(number);
        String data = mGson.toJson(orderLogistics);
        CLog.e("testing", "修改物流信息data" + data);
        OKHttp.OkHttpPost("/Order/UpdateOrderLogistics", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "修改物流信息失败" + e.toString());
                HintText_Dialog mTextDialog = new HintText_Dialog(My_Order_SendOut_Activity.this,
                        "发货失败", "fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                    }
                }, Login_Static.hintTime);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "修改物流信息" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        setOrderState(orderId,4,name,number);
                    } else {
                        HintText_Dialog mTextDialog = new HintText_Dialog(My_Order_SendOut_Activity.this,
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
    //修改订单状态
    private void setOrderState(String id, int state, final String name, final String number){
        CLog.e("testing","修改订单状态"+id+":::"+state);
        OkHttpUtils.get()
                .url(mMyApplication.getUrl()+"/Order/ChangeOrderState")
                .addParams("orderId",id)
                .addParams("state",state+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","修改订单状态失败"+e.toString());
                        HintText_Dialog mTextDialog = new HintText_Dialog(My_Order_SendOut_Activity.this,
                                "发货失败", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","修改订单状态"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                HintText_Dialog mTextDialog = new HintText_Dialog(My_Order_SendOut_Activity.this,
                                        "发货成功", "success");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                        if(types==0) {
                                            EventBus.getDefault().post(new OrderEvent_Model(name, number, position, "Express"));
                                        }else {
                                            EventBus.getDefault().post(new OrderEvent_Model2(name, number, position, "Express2"));
                                        }
                                        finish();
                                    }
                                }, Login_Static.hintTime);
                            }else {
                                HintText_Dialog mTextDialog = new HintText_Dialog(My_Order_SendOut_Activity.this,
                                        "发货失败", "fail");
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
     * 获取物流信息
     */
    private void getLogistics(String number){
        mDialog.show();
        HintText_Dialog mTextDialog=new HintText_Dialog(My_Order_SendOut_Activity.this,
                "匹配中...","");
        OkHttpUtils.get().url("http://ali-deliver.showapi.com/showapi_expInfo?com=auto&nu="+number)
                .addHeader("Authorization","APPCODE 52fad027983f4392a00cd09076b80df4")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取物流信息失败"+e.toString());
                        HintText_Dialog mTextDialog = new HintText_Dialog(My_Order_SendOut_Activity.this,
                                "未知异常,匹配物流公司失败", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取物流信息"+response);
                        JSONObject jsonObject= null;
                        try {
                            jsonObject = new JSONObject(response);
                            int message=jsonObject.getInt("showapi_res_code");
                            if(message==0){
                                Logistics logistics=mGson.fromJson(jsonObject.getString("showapi_res_body"),Logistics.class);
                                mDialog.dismiss();
                                mNumber.setText(logistics.getExpTextName());
                            }else {
                                HintText_Dialog mTextDialog = new HintText_Dialog(My_Order_SendOut_Activity.this,
                                        "匹配物流公司失败,请输入正确的物流编号", "fail");
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
