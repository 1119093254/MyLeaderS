package com.multshows.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multshows.Activity.ReChangeWeb_Activity;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.OrderPayment;
import com.multshows.Beans.WxPrePay;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MD5;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyPicPopupWindow;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import okhttp3.Call;

/**
 * 充值
 */
public class My_Recharge_Fragment extends Fragment{
    //上下文
    Context mContext;
    View mView;
    //控件
    EditText mEditText;
    Button mButton;
    String money="";
    MyPicPopupWindow mPopWindow;
    Dialog mDialog;
    String Tid="";//交易单id
    private IWXAPI api; // IWXAPI 是第三方app和微信通信的openapi接口
    MyApplication mMyApplication;
    Gson mGson=new Gson();
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int what=msg.what;
            switch (what){
                case 1:
                    getState(Tid);
                    break;
                case 2:
                    //支付成功
                    EventBus.getDefault().post(new ChatEvent_Model("", "ReChange"));
                    if(Login_Static.mPlaceActivity!=null && Login_Static.mPlaceActivity.size()!=0) {
                        for (int i=0 ;i<Login_Static.mPlaceActivity.size();i++){
                            Login_Static.mPlaceActivity.get(i).finish();
                        }
                    }
                    break;
            }
        }
    };
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
        mMyApplication= (MyApplication) mContext.getApplicationContext();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.my_recharge_fragment,null);
        mEditText = (EditText) mView.findViewById(R.id.my_recharge_fragment_editText);
        mButton= (Button) mView.findViewById(R.id.my_recharge_fragment_Button);
        mDialog=new HintText_Dialog(mContext,R.style.MyDialog);
        api = WXAPIFactory.createWXAPI(mContext,null);//appId
        api.registerApp("wxad057bc461c628e2");//APP_ID
        initListen();
        return mView;
    }

    /**
     * 事件处理
     */
    private void initListen() {
        //确定充值
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                money=mEditText.getText().toString();
                if("".equals(money) || Double.parseDouble(money)==0){
                    mDialog.show();
                    HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"请输入充值金额","fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                        }
                    },Login_Static.hintTime);
                }else {
                    HideInputManager_Utils.hideInputManager(mContext);
                    //底部弹出框
                    //实例化MyPicPopupWindow
                    mPopWindow = new MyPicPopupWindow(getActivity(), itemsOnClick,
                            "微信", "银联", "", "");
                    //设置弹出动画效果
                    mPopWindow.setAnimationStyle(R.style.PopupAnimation);
                    //显示窗口  //设置layout在PopupWindow中显示的位置
                    mPopWindow.showAtLocation(getActivity().findViewById(R.id.my_recharge_fragment_parents),
                            Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                }
            }
        });
    }
    //为弹出窗口实现监听类
    public View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lookPhoto_dialog_Top:
                    setWeiXin(money);
                    break;
                case R.id.lookPhoto_dialog_Center:
                    getTid(money);
                    break;
                default:
                    break;
            }
            mPopWindow.dismiss();
        }
    };

    private void getTid(String m){
        mDialog.show();
        HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"跳转中...","");
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/Order/RechargeMore")
                .addParams("userId", Login_Static.myUserID)//用户id
                .addParams("amount",m)//金额
                .addParams("type","3")//银联充值
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取充值交易单失败"+e.toString());
                        HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"未知错误","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取充值交易单"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                mDialog.dismiss();
                                Tid=Json_Utils.getTemplate(response);
                                CLog.e("testing","获取充值交易单ID:"+Tid);
                                getState(Tid);
                                Intent intent=new Intent(mContext, ReChangeWeb_Activity.class);
                                intent.putExtra("tid",Tid);
                                mContext.startActivity(intent);
                            }else {
                                HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"未知错误","fail");
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
     * 持续监听交易单状态
     */
    private void getState(String tid){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/Order/GetOrderPaymentByPayId")
                .addParams("payId",tid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","监听交易单状态失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","监听交易单状态"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                OrderPayment orderPayment=mGson.fromJson(Json_Utils.getTemplate(response),OrderPayment.class);
                                if(orderPayment.getState()==1){
                                    mHandler.sendEmptyMessageDelayed(2,2000);
                                }else {
                                    mHandler.sendEmptyMessageDelayed(1,2000);
                               }
                            }else {
                                mHandler.sendEmptyMessageDelayed(1,2000);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(1);
    }


    /**
     * 微信支付
     */
    private void setWeiXin(String m){
        mDialog.show();
        HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"跳转中...","");
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/Order/RechargeMore")
                .addParams("userId", Login_Static.myUserID)//用户id
                .addParams("amount",m)//金额
                .addParams("type","4")//微信支付
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取充值交易单失败"+e.toString());
                        HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"未知错误","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取充值交易单"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                Tid=Json_Utils.getTemplate(response);
                                CLog.e("testing","获取充值交易单ID:"+Tid);
                                getWeiXinMessage(Tid);
                            }else {
                                HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"未知错误","fail");
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
     * 获取微信需要的信息
     * @param Tid
     */
    private void getWeiXinMessage(String Tid){
        //CLog.e("testing","URL:"+"/Service/GetMobileWxPay?uid="+Login_Static.myUserID+"&tid="+Tid);
        OkHttpUtils.post().url(mMyApplication.getUrl()+"/Service/GetMobileWxPay?uid="+Login_Static.myUserID+"&tid="+Tid)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取微信需要的信息失败111"+e.toString());
                        HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"未知错误","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取微信需要的信息"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                mDialog.dismiss();
                                WxPrePay wxPrePay=mGson.fromJson(Json_Utils.getTemplate(response),WxPrePay.class);
                                //String time=""+System.currentTimeMillis() / 1000;
                                if (api != null && wxPrePay!=null) {
                                    //微信支付
                                    PayReq req = new PayReq();
                                    req.appId = wxPrePay.getAppid();
                                    req.partnerId = wxPrePay.getMch_id();
                                    req.prepayId = wxPrePay.getPrepay_id();
                                    req.nonceStr = wxPrePay.getNonce_str();
                                    req.timeStamp = wxPrePay.getTimestamp_str();
                                    req.packageValue =wxPrePay.getPackage_str();
                                    req.sign = wxPrePay.getSign_str();
                                    //req.extData = "app data"; // optional
                                    // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信\
                                    api.sendReq(req);
                                }
                            }else {
                                HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"未知错误","fail");
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
     * 获得签名
     */
    /*private String getSign(WxPrePay wxPrePay,String time){
        String stringA="appid="+wxPrePay.getAppid()+"&noncestr="+wxPrePay.getNonce_str()+
                "&package="+"Sign=WXPay"+"&partnerid="+wxPrePay.getMch_id()+
                "&prepayid="+wxPrePay.getPrepay_id()+"&timestamp="+time;
        String stringSignTemp=stringA+"&key=e7a1ca5bab8b7b754426431ef60f11e7";
        String sign= MD5.encode(stringSignTemp);
        Log.e("testing","签名:"+sign+"++"+wxPrePay.toString());
        return sign;
    }*/


}