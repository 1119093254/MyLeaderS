package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Adapter.My_Order_Logistics_Adapter;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.Order;
import com.multshows.Beans.OrderEvent_Model;
import com.multshows.Beans.OrderEvent_Model2;
import com.multshows.Beans.ShowsBase;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import okhttp3.Call;

/**
 * 我的订单——编辑
 */
public class My_Order_Editor_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    SimpleDraweeView mPhoto;//商品展示
    TextView mName;//商品名称
    TextView mMoney;//商品价格
    TextView mPlace;//地区
    TextView mYunFei;//运费
    EditText mEditText_1;//宝贝价格
    EditText mEditText_2;//运费
    Button mButton;

    Intent intent;
    Order mOrder;
    Dialog mDialog;
    int position;
    int types;
    MyApplication mMyApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_editor);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(My_Order_Editor_Activity.this,R.color.app_topColor);
        mMyApplication= (MyApplication) getApplication();
        initView();
        initData();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.My_Order_Editor_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.My_Order_Editor_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.My_Order_Editor_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.My_Order_Editor_TopTitle);
        mPhoto= (SimpleDraweeView) findViewById(R.id.My_Order_Editor_photo);
        mName= (TextView) findViewById(R.id.My_Order_Editor_name);
        mMoney= (TextView) findViewById(R.id.My_Order_Editor_money);
        mPlace= (TextView) findViewById(R.id.My_Order_Editor_place);
        mYunFei= (TextView) findViewById(R.id.My_Order_Editor_yunfei);
        mEditText_1= (EditText) findViewById(R.id.My_Order_Editor_EditText1);
        mEditText_2= (EditText) findViewById(R.id.My_Order_Editor_EditText2);
        mButton= (Button) findViewById(R.id.My_Order_Editor_Button);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(My_Order_Editor_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        intent=getIntent();
        mOrder= (Order) intent.getSerializableExtra("orderBean");
        position=intent.getIntExtra("position",0);
        types=intent.getIntExtra("types",0);
        if(mOrder!=null){
            ShowsBase show=mOrder.getShows();
            if(show.getShowsPhoto()!=null && !"null".equals(show.getShowsPhoto())){
                if(!"".equals(SubString_Utils.getPhotoUrl(show.getShowsPhoto()))){
                    mPhoto.setImageURI(Uri.parse(SubString_Utils.getImageUrl(SubString_Utils.getPhotoUrl(show.getShowsPhoto()))));
                }else {
                    mPhoto.setImageURI(Uri.parse(""));
                }
            }else {
                mPhoto.setImageURI(Uri.parse(""));
            }
            mName.setText(show.getTitle());
            mYunFei.setText("(含"+mOrder.getExpressFee()+"元运费)");
            mMoney.setText("¥"+mOrder.getRealPrice());
            mPlace.setText("来自"+show.getLocationAddress());//地点
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
                mDialog=new HintText_Dialog(My_Order_Editor_Activity.this,R.style.MyDialog);
                mDialog.show();
                if("".equals(mEditText_1.getText().toString()) || "".equals(mEditText_2.getText().toString())){
                    HintText_Dialog mHintTextDialog=new HintText_Dialog(My_Order_Editor_Activity.this,"请把价格和运费填写完整","fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                        }
                    }, Login_Static.hintTime);
                }else{
                    HintText_Dialog mHintTextDialog=new HintText_Dialog(My_Order_Editor_Activity.this,"修改中...","");
                    //修改
                    setChangePrice(Double.parseDouble(mEditText_1.getText().toString()),
                            Double.parseDouble(mEditText_2.getText().toString()));
                }
            }
        });
    }

    //修改价格
    private void setChangePrice(final double price, final double yunfei){
        CLog.e("testing","修改订单价格price:"+price);
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/Order/ChangeOrderPrice")
                .addParams("orderId",mOrder.getId())
                .addParams("price",price+"")
                .addParams("fare",yunfei+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","修改订单价格失败"+e.toString());
                        HintText_Dialog mHintTextDialog=new HintText_Dialog(My_Order_Editor_Activity.this,"网络异常","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","修改订单价格"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                HintText_Dialog mHintTextDialog=new HintText_Dialog(My_Order_Editor_Activity.this,
                                        "修改成功","success");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                        if(types==0) {
                                            EventBus.getDefault().post(new OrderEvent_Model("" + price, "" + yunfei, position, "Price"));
                                        }else {
                                            EventBus.getDefault().post(new OrderEvent_Model2("" + price, "" + yunfei, position, "Price2"));
                                        }
                                        finish();
                                    }
                                },Login_Static.hintTime);
                            }else {
                                HintText_Dialog mHintTextDialog=new HintText_Dialog(My_Order_Editor_Activity.this,
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
}
