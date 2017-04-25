package com.multshows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multshows.Beans.My_TradingList_Beans;
import com.multshows.Beans.PayRecord;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Time_Now;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 交易
 */
public class My_TradingList_Adapter extends BaseAdapter{
    //上下文
    Context mContext;
    LayoutInflater mInflater;
    //数据集合
    List<PayRecord> mList;

    public My_TradingList_Adapter(Context context, List<PayRecord> list) {
        mContext = context;
        mList = list;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    class TradingListViewHolder{
        TextView mTitle;//标题
        TextView mTime;//时间
        TextView mMoney;//金额
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        TradingListViewHolder mViewHolder;
        if(view==null){
            view=mInflater.inflate(R.layout.my_tradinglist_items,null);
            mViewHolder=new TradingListViewHolder();
            mViewHolder.mTitle= (TextView) view.findViewById(R.id.my_TradingList_items_Title);
            mViewHolder.mTime= (TextView) view.findViewById(R.id.my_TradingList_items_Time);
            mViewHolder.mMoney= (TextView) view.findViewById(R.id.my_TradingList_items_money);
            view.setTag(mViewHolder);
        }else {
            mViewHolder= (TradingListViewHolder) view.getTag();
        }
        PayRecord beans=mList.get(i);
        mViewHolder.mTitle.setText(beans.getTypeName());
        mViewHolder.mTime.setText(Time_Now.getStringTime(beans.getRecordTime()));
        double allMoney;
        allMoney=beans.getAmount();
        CLog.e("testing","金额："+allMoney);
        DecimalFormat df=new DecimalFormat("0.##");
        String sMoney = df.format(allMoney);
        if(beans.getInType()==1) {
            //收入
            mViewHolder.mMoney.setText("+¥" + sMoney);
        }else {
            //支出
            mViewHolder.mMoney.setText("-¥" + sMoney);
        }
        return view;
    }
}
