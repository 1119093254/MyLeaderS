package com.multshows.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Activity.BoundBankList_Activity;
import com.multshows.Activity.My_MyWalletFragment_Activity;
import com.multshows.Beans.BankEvent_Model;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.Kitting_Model;
import com.multshows.Beans.PayRecord;
import com.multshows.Beans.UserBank;
import com.multshows.Beans.UserBankBase;
import com.multshows.Beans.UserCashOutAgain;
import com.multshows.Beans.UserCashOutRecord;
import com.multshows.Beans.UserCashOutRecordAddBank;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.text.DecimalFormat;

import okhttp3.Call;

/**
 * 提现详情
 */
public class My_Fragment_ok extends Fragment{
    View mView;
    Context mContext;
    ImageView mImageView;//图标
    TextView mMoney;//提现金额
    TextView mType;//提现方式
    TextView mBank;//提现银行卡信息
    TextView mTime;//提现时间
    TextView mHint;//提示
    TextView mAllMoney;//提现金额
    TextView mFei;//手续费
    TextView mRealMoney1;//真实金额
    TextView mRealMoney2;//真实金额
    LinearLayout mMoneyLayout;//金额部分布局
    TextView hintLayout;//失败时提示信息
    TextView erroeMsg;//失败信息

    RelativeLayout mBankLayout;//
    RelativeLayout mChoseBankLayout;//

    Button mButton;//完成
    MyApplication mMyApplication;
    Gson mGson=new Gson();
    PayRecord mPayRecord;
    int types_=0;
    String bankID="";//银行卡id
    Dialog mDialog;
    boolean isFlag=false;
    String TradeId;//交易单id
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.my_fragment_ok,null);
        mMyApplication= (MyApplication) getActivity().getApplication();
        initView();
        initData();
        initListen();
        return mView;
    }

    /**
     * 初始化
     */
    private void initView() {
        mImageView= (ImageView) mView.findViewById(R.id.my_fragment_ok_timeIcon);
        mMoney= (TextView) mView.findViewById(R.id.My_Fragment_OK_money);
        mBank= (TextView) mView.findViewById(R.id.My_Fragment_OK_Bank);
        mTime= (TextView) mView.findViewById(R.id.My_Fragment_OK_Time);
        mButton= (Button) mView.findViewById(R.id.My_Fragment_OK_OKBtn);
        mHint=(TextView) mView.findViewById(R.id.My_Fragment_OK_BankHint);
        mType= (TextView) mView.findViewById(R.id.My_Fragment_OK_BankType);
        mAllMoney= (TextView) mView.findViewById(R.id.My_Fragment_OK_AllMoney);
        mFei= (TextView) mView.findViewById(R.id.My_Fragment_OK_Fei);
        mRealMoney1= (TextView) mView.findViewById(R.id.My_Fragment_OK_RealMoney1);
        mRealMoney2= (TextView) mView.findViewById(R.id.My_Fragment_OK_RealMoney2);
        mMoneyLayout= (LinearLayout) mView.findViewById(R.id.My_Fragment_OK_MoneyLayout);
        mBankLayout= (RelativeLayout) mView.findViewById(R.id.My_Fragment_OK_BankLayout);
        mChoseBankLayout= (RelativeLayout) mView.findViewById(R.id.My_Fragment_OK_ChoseBankLayout);
        hintLayout= (TextView) mView.findViewById(R.id.My_Fragment_OK_HintLayout);
        erroeMsg= (TextView) mView.findViewById(R.id.My_Fragment_OK_ChoseBankType);
    }

    /**
     * 数据处理
     */
    private void initData() {
        Bundle mBundle=getArguments();
        if(mBundle!=null){
            String ID=mBundle.getString("message");
            mPayRecord= (PayRecord) mBundle.getSerializable("PayRecords");
            types_=mBundle.getInt("types");
            if(types_==9){
                //提现成功
                mButton.setVisibility(View.GONE);
            }else if(types_==10){
                TradeId=mBundle.getString("TradeId");
                mButton.setVisibility(View.GONE);
            }
            getKittingMessage(ID);
        }
    }

    /**
     * 事件监听
     */
    private void initListen() {
        mChoseBankLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, BoundBankList_Activity.class);
                startActivity(intent);
            }
        });
        //完成按钮点击
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(types_==10){
                    mDialog=new HintText_Dialog(mContext,R.style.MyDialog);
                    mDialog.show();
                    if(isFlag) {
                        HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"正在提现中...","");
                        mDialog.dismiss();
                        //getKittingBank(TradeId,bankID);
                    }else {
                        HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"请重新选择正确的银行卡","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }
                }else {
                    Intent intent = new Intent(mContext, My_MyWalletFragment_Activity.class);
                    intent.putExtra("MyWallet_type", "TradingList");
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
    }
    @Subscribe
    public void onEvent(BankEvent_Model model) {
        if("bank".equals(model.getFlag())){
            UserBank bean=model.getContent();
            if(bean!=null){
                isFlag=true;
                mChoseBankLayout.setVisibility(View.GONE);
                mBankLayout.setVisibility(View.VISIBLE);
                String number="";
                mType.setText(bean.getBank().getName());
                if(bean.getCard().length()>=4){
                    number=bean.getCard().substring(bean.getCard().length()-4,bean.getCard().length());
                }
                mBank.setText("尾号"+number);
                bankID=bean.getId();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * 提现详情
     */
    private void getKittingMessage(String ID){
        CLog.e("testing",ID);
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/User/GetCashRecord")
                .addParams("cashId",ID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取提现详情失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取提现详情"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                UserCashOutRecord bean=mGson.fromJson(Json_Utils.getTemplate(response),UserCashOutRecord.class);
                                if(bean!=null) {
                                    UserBankBase bank = bean.getUserBank();
                                    double money = bean.getTradeCash();
                                    double fei=bean.getTradeFee();
                                    mMoney.setText(money+"");
                                    if (bean.getTradeType() == 1) {
                                        //1提现一麻袋
                                        mType.setText("一麻袋");
                                        mBank.setText(bean.getUserYmdNumber());
                                        if (bean.getVerifyStatus()==-1 || bean.getCashStatus()==-1) {
                                            mButton.setVisibility(View.GONE);
                                            mHint.setText("审核未通过,提现失败");
                                            mImageView.setImageResource(R.drawable.icon_cashno);
                                        }else if (bean.getVerifyStatus()==2 || bean.getCashStatus()==1){
                                            mHint.setText("已成功提现至一麻袋");
                                            mImageView.setImageResource(R.drawable.icon_cashyes);
                                            if(types_==10) {
                                                mButton.setVisibility(View.GONE);
                                            }
                                        }else {
                                            mHint.setText("提现申请已提交,请等待一麻袋处理");
                                        }
                                    } else {
                                        //2提现银行卡
                                        mMoneyLayout.setVisibility(View.VISIBLE);
                                        if(bank!=null) {
                                            String number="";
                                            mType.setText(bank.getBank());
                                            if(bank.getCard().length()>=4){
                                                number=bank.getCard().substring(bank.getCard().length()-4,bank.getCard().length());
                                            }
                                            mBank.setText("尾号"+number);
                                        }
                                        if (bean.getVerifyStatus()==-1 || bean.getCashStatus()==-1) {
                                            mHint.setText("提现至银行卡失败");
                                            mImageView.setImageResource(R.drawable.icon_cashno);
                                            mChoseBankLayout.setVisibility(View.VISIBLE);
                                            hintLayout.setVisibility(View.VISIBLE);
                                            erroeMsg.setText(bean.getErrorMsg());
                                            //mBankLayout.setVisibility(View.GONE);
                                            mButton.setVisibility(View.GONE);
                                        }else if (bean.getVerifyStatus()==2 || bean.getCashStatus()==1){
                                            mHint.setText("已成功提现至银行卡");
                                            mImageView.setImageResource(R.drawable.icon_cashyes);
                                            if(types_==10) {
                                                mButton.setVisibility(View.GONE);
                                            }
                                        }else {
                                            mHint.setText("提现申请已提交,请等待银行卡处理");
                                        }
                                        mAllMoney.setText("¥" + money);
                                        mFei.setText("-¥" + fei);

                                        double real = money - fei;
                                        DecimalFormat df=new DecimalFormat("0.##");
                                        CLog.e("testing","保留："+df.format(real));
                                        String[] sMoney = df.format(real).split("\\.");
                                        if (sMoney.length == 2) {
                                            mRealMoney1.setText("¥" + sMoney[0]);
                                            mRealMoney2.setText("." + sMoney[1]);
                                        } else if (sMoney.length != 0) {
                                            mRealMoney1.setText("¥" + sMoney[0]);
                                        }

                                    }
                                    mTime.setText(bean.getRecordTimeStr());

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 提现(再次提现到银行卡)
     */
    private void getKittingBank(String id, String bankID){
        UserCashOutAgain cashOutRecordAdd=new UserCashOutAgain();
        cashOutRecordAdd.setUserId(Login_Static.myUserID);
        cashOutRecordAdd.setTradeId(id);
        cashOutRecordAdd.setBankId(bankID);
        String data=mGson.toJson(cashOutRecordAdd);
        CLog.e("testing","再次提现至银行卡data"+data);
        OKHttp.OkHttpPost("/User/WithdrawalsBankAgain", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","再次提现至银行卡失败"+e.toString());
                HintText_Dialog mTextDialog=new HintText_Dialog(mContext,"提现失败","fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                    }
                },Login_Static.hintTime);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","再次提现至银行卡"+response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        HintText_Dialog mTextDialog=new HintText_Dialog(mContext,"提现成功","success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                getActivity().finish();
                            }
                        },Login_Static.hintTime);
                    }else {
                        HintText_Dialog mTextDialog=new HintText_Dialog(mContext,Json_Utils.getMessage(response),"fail");
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
