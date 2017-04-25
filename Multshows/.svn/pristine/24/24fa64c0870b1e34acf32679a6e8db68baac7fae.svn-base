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
import com.multshows.R;
import com.multshows.Utils.Time_Now;

import java.text.DecimalFormat;

/**
 * 购买作品 - 交易详情(推广奖励)
 */
public class ZPMessageTg_Fragment extends Fragment{
    View mView;
    Context mContext;

    TextView mHint;//交易类型
    TextView mMoney;//总金额
    TextView mLL;//推广级别
    TextView mBJ;//推广利率
    TextView mType;//交易方式
    TextView mTime;//交易时间
    LinearLayout mLv;


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
        mView=inflater.inflate(R.layout.zp_message_tg_item,null);
        initView();
        initData();
        initListen();
        return mView;
    }

    /**
     * 初始化
     */
    private void initView() {
        mHint= (TextView) mView.findViewById(R.id.ZP_Message_TgBankHint);
        mLL= (TextView) mView.findViewById(R.id.ZP_Message_AllTgLL);
        mBJ= (TextView) mView.findViewById(R.id.ZP_Message_TgJB);
        mMoney= (TextView) mView.findViewById(R.id.ZP_Message_Tgmoney);
        mType= (TextView) mView.findViewById(R.id.ZP_Message_TgText);
        mTime= (TextView) mView.findViewById(R.id.ZP_Message_TgTime);
        mLv= (LinearLayout) mView.findViewById(R.id.ZP_Message_LV);
    }
    /**
     * 数据处理
     */
    private void initData() {
        Bundle mBundle=getArguments();
        if(mBundle!=null) {
            mPayRecord = (PayRecord) mBundle.getSerializable("PayRecords");
                if(mPayRecord!=null){
                    mHint.setText(mPayRecord.getTypeName());
                    mMoney.setText(mPayRecord.getAmount()+"");
                    if(mPayRecord.getExtendLevel()==1) {
                        mLL.setText("一级推广");
                    }else if(mPayRecord.getExtendLevel()==2){
                        mLL.setText("二级推广");
                    }else if(mPayRecord.getExtendLevel()==3){
                        mLL.setText("三级推广");
                    }else if(mPayRecord.getExtendLevel()==4){
                        mLL.setText("四级推广");
                    }else if(mPayRecord.getExtendLevel()==0){
                        mLv.setVisibility(View.GONE);
                    }
                    mBJ.setText(mPayRecord.getExtendRate()+"%");
                   if(mPayRecord.getExtendRewardType() == 1){//--------------------
                        mType.setText("受邀人收到打赏");
                    }else {
                       mType.setText("受邀人出售商品");
                   }
                    mTime.setText(Time_Now.getStringTime(mPayRecord.getRecordTime()));
                }


        }
    }

    /**
     * 事件监听
     */
    private void initListen() {
    }

}
