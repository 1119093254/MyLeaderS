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

/**
 * 购买作品 - 交易详情
 */
public class ZPMessage_buy_Fragment extends Fragment{
    View mView;
    Context mContext;

    TextView mHint;//交易类型
    TextView mMoney;//总金额
    TextView mMoney2;//总金额
    TextView mYunFei;//运费
    TextView mRealMoney1;//实际金额
    TextView mRealMoney2;//实际金额
    TextView mType;//交易方式
    TextView mNickName;//交易对象
    TextView mTime;//交易时间
    LinearLayout mLayout;//
    RelativeLayout mNickNameLayout;//


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
        mView=inflater.inflate(R.layout.zp_message_buy_item,null);
        initView();
        initData();
        initListen();
        return mView;
    }

    /**
     * 初始化
     */
    private void initView() {
        mHint= (TextView) mView.findViewById(R.id.ZP_Message_buy_BankHint);
        mMoney= (TextView) mView.findViewById(R.id.ZP_Message_buy_money);
        mMoney2= (TextView) mView.findViewById(R.id.ZP_Message_buy_AllMoney);
        mYunFei= (TextView) mView.findViewById(R.id.ZP_Message_buy_Fei);
        mRealMoney1= (TextView) mView.findViewById(R.id.ZP_Message_buy_RealMoney1);
        mRealMoney2= (TextView) mView.findViewById(R.id.ZP_Message_buy_RealMoney2);
        mType= (TextView) mView.findViewById(R.id.ZP_Message_buy_TypeText);
        mNickName= (TextView) mView.findViewById(R.id.ZP_Message_buy_OtherUser);
        mTime= (TextView) mView.findViewById(R.id.ZP_Message_buy_Time);
        mLayout= (LinearLayout) mView.findViewById(R.id.ZP_Message_buy_MoneyLayout);
        mNickNameLayout= (RelativeLayout) mView.findViewById(R.id.ZP_Message_buy_OtherUserLayout);

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
                //打赏
                mLayout.setVisibility(View.GONE);
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
                    mNickName.setText(mPayRecord.getOtherNickName());
                    mTime.setText(Time_Now.getStringTime(mPayRecord.getRecordTime()));
                }
            }else if(types_==2 || types_==6){
                //购买鲜花/任务奖励
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

            }else {
                mLayout.setVisibility(View.VISIBLE);
                if(mPayRecord!=null){
                    double real = mPayRecord.getAmount() - mPayRecord.getExpressFee();
                    mHint.setText(mPayRecord.getTypeName());
                    mMoney.setText(mPayRecord.getAmount()+"");
                    mMoney2.setText("¥"+real);
                    String[] sMoney = (mPayRecord.getAmount()+"").split("\\.");
                    if (sMoney.length == 2) {
                        mRealMoney1.setText("¥" + sMoney[0]);
                        mRealMoney2.setText("." + sMoney[1]);
                    } else if (sMoney.length != 0) {
                        mRealMoney1.setText("¥" + sMoney[0]);
                    }
                    mYunFei.setText("+¥"+mPayRecord.getExpressFee());
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