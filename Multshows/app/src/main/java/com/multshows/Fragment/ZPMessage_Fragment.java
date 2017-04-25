package com.multshows.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.multshows.Beans.PayRecord;
import com.multshows.Beans.UserBase;
import com.multshows.R;
import com.multshows.Utils.Time_Now;

import java.text.DecimalFormat;

/**
 * 购买作品 - 交易详情
 */
public class ZPMessage_Fragment extends Fragment{
    View mView;
    Context mContext;

    TextView mHint;//交易类型
    TextView mMoney;//总金额
    TextView mMoneyText;//总金额提示
    TextView mMoney2;//总金额
    TextView mYunFei;//手续费
    TextView mRealMoney1;//实际金额
    TextView mRealMoney2;//实际金额
    TextView mType;//交易方式
    TextView mNickName;//交易对象
    TextView mTime;//交易时间
    LinearLayout mLayout;//
    RelativeLayout mNickNameLayout;//
    TextView mTypeHint;


    PayRecord mPayRecord;
    int types_=0;//进入标志
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.zp_message_item,null);
        initView();
        initData();
        initListen();
        return mView;
    }

    /**
     * 初始化
     */
    private void initView() {
        mHint= (TextView) mView.findViewById(R.id.ZP_Message_BankHint);
        mMoney= (TextView) mView.findViewById(R.id.ZP_Message_money);
        mMoneyText= (TextView) mView.findViewById(R.id.ZP_Message_AllMoneyText);
        mMoney2= (TextView) mView.findViewById(R.id.ZP_Message_AllMoney);
        mYunFei= (TextView) mView.findViewById(R.id.ZP_Message_Fei);
        mRealMoney1= (TextView) mView.findViewById(R.id.ZP_Message_RealMoney1);
        mRealMoney2= (TextView) mView.findViewById(R.id.ZP_Message_RealMoney2);
        mType= (TextView) mView.findViewById(R.id.ZP_Message_TypeText);
        mNickName= (TextView) mView.findViewById(R.id.ZP_Message_OtherUser);
        mTime= (TextView) mView.findViewById(R.id.ZP_Message_Time);
        mLayout= (LinearLayout) mView.findViewById(R.id.ZP_Message_MoneyLayout);
        mNickNameLayout= (RelativeLayout) mView.findViewById(R.id.ZP_Message_OtherUserLayout);
        mTypeHint= (TextView) mView.findViewById(R.id.ZP_Message_TypeTextL);
    }
    /**
     * 数据处理
     */
    private void initData() {
        Bundle mBundle=getArguments();
        if(mBundle!=null) {
            mPayRecord = (PayRecord) mBundle.getSerializable("PayRecords");
            types_=mBundle.getInt("types");
            if(types_==1){
                //收到打赏
                mLayout.setVisibility(View.VISIBLE);
                if(mPayRecord!=null){
                    double real = mPayRecord.getAmount() + mPayRecord.getTransFee();
                    mHint.setText(mPayRecord.getTypeName());
                    mMoney.setText(mPayRecord.getAmount()+"");
                    mMoney2.setText("¥"+real);
                    DecimalFormat df=new DecimalFormat("0.##");
                    String[] sMoney = df.format(mPayRecord.getAmount()).split("\\.");
                    if (sMoney.length == 2) {
                        mRealMoney1.setText("¥" + sMoney[0]);
                        mRealMoney2.setText("." + sMoney[1]);
                    } else if (sMoney.length != 0) {
                        mRealMoney1.setText("¥" + sMoney[0]);
                    }
                    mYunFei.setText("-¥"+mPayRecord.getTransFee());
                    if (mPayRecord.getPayType() == 1) {
                        mType.setText("账户余额");
                    }else if(mPayRecord.getPayType() == 2){
                        mType.setText("一麻袋");
                    }else if(mPayRecord.getPayType() == 3){
                        mType.setText("银联");
                    }else if(mPayRecord.getPayType() == 4){
                        mType.setText("微信");
                    }else if(mPayRecord.getPayType() == 5){
                        mType.setText("IOS支付");
                    }
                    mMoneyText.setText("打赏金额：");

                    mNickName.setText(mPayRecord.getOtherNickName());
                    mTime.setText(Time_Now.getStringTime(mPayRecord.getRecordTime()));
                }
            }else if(types_==7){
                mLayout.setVisibility(View.GONE);
                mNickNameLayout.setVisibility(View.GONE);
                if(mPayRecord!=null){
                    mHint.setText(mPayRecord.getTypeName());
                    mMoney.setText(mPayRecord.getAmount()+"");

                    if (mPayRecord.getPayType() == 1) {
                        mType.setText("账户余额");
                    }else if(mPayRecord.getPayType() == 2){
                        mType.setText("一麻袋");
                    }else if(mPayRecord.getPayType() == 3){
                        mType.setText("银联");
                    }else if(mPayRecord.getPayType() == 4){
                        mType.setText("微信");
                    }else if(mPayRecord.getPayType() == 5){
                        mType.setText("IOS支付");
                    }
                    mTime.setText(Time_Now.getStringTime(mPayRecord.getRecordTime()));
                }
            }else if(types_==8){
                //充值
                mLayout.setVisibility(View.GONE);
                mNickNameLayout.setVisibility(View.GONE);
                if(mPayRecord!=null){
                    mHint.setText(mPayRecord.getTypeName());
                    mMoney.setText(mPayRecord.getAmount()+"");
                    mTypeHint.setText("充值方式");
                     if (mPayRecord.getPayType() == 1) {
                        mType.setText("账户余额");
                    }else if(mPayRecord.getPayType() == 2){
                        mType.setText("一麻袋");
                    }else if(mPayRecord.getPayType() == 3){
                        mType.setText("银联");
                    }else if(mPayRecord.getPayType() == 4){
                        mType.setText("微信");
                    }else if(mPayRecord.getPayType() == 5){
                         mType.setText("IOS支付");
                     }
                    mTime.setText(Time_Now.getStringTime(mPayRecord.getRecordTime()));
                }
            }else if(types_==9 || types_==10){
                //打赏退回、任务撤回
                mLayout.setVisibility(View.GONE);
                mNickNameLayout.setVisibility(View.GONE);
                if(mPayRecord!=null){
                    mHint.setText(mPayRecord.getTypeName());
                    mMoney.setText(mPayRecord.getAmount()+"");
                    mTypeHint.setText("付款方式");
                    mType.setText("账户余额");

                    mTime.setText(Time_Now.getStringTime(mPayRecord.getRecordTime()));
                }
            }else {
                mLayout.setVisibility(View.VISIBLE);
                if(mPayRecord!=null){
                    double real = mPayRecord.getAmount() + mPayRecord.getTransFee();
                    mHint.setText(mPayRecord.getTypeName());
                    mMoney.setText(mPayRecord.getAmount()+"");
                    mMoney2.setText("¥"+real+"(包含运费"+mPayRecord.getExpressFee()+")");
                    DecimalFormat df=new DecimalFormat("0.##");
                    String[] sMoney = df.format(mPayRecord.getAmount()).split("\\.");
                    if (sMoney.length == 2) {
                        mRealMoney1.setText("¥" + sMoney[0]);
                        mRealMoney2.setText("." + sMoney[1]);
                    } else if (sMoney.length != 0) {
                        mRealMoney1.setText("¥" + sMoney[0]);
                    }
                    mYunFei.setText("-¥"+mPayRecord.getTransFee());
                    mType.setText("账户余额");
                    mMoneyText.setText("商品金额：");
                    mNickName.setText(mPayRecord.getOtherNickName());
                    mTime.setText(Time_Now.getStringTime(mPayRecord.getRecordTime()));
                }
            }


        }
    }

    /**
     * 事件监听
     */
    private void initListen() {
    }

}
