package com.multshows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.multshows.Beans.Logistics;
import com.multshows.Beans.Logistics_item;
import com.multshows.R;

import java.util.List;

/**
 * 物流信息适配器
 */
public class My_Order_Logistics_Adapter extends BaseAdapter{
    Context mContext;
    LayoutInflater mInflater;
    List<Logistics_item> mList;

    public My_Order_Logistics_Adapter(Context context, List<Logistics_item> list) {
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
    class LogisticsViewHolder{
        ImageView mImageView;//标志
        TextView mContent;//内容
        TextView mTime;//时间
        TextView mTextView;//线
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LogisticsViewHolder mViewHolder;
        if(view==null){
            view=mInflater.inflate(R.layout.my_order_logistics_item,null);
            mViewHolder=new LogisticsViewHolder();
            mViewHolder.mImageView= (ImageView) view.findViewById(R.id.my_OrderLogistics_item_image);
            mViewHolder.mContent= (TextView) view.findViewById(R.id.my_OrderLogistics_item_content);
            mViewHolder.mTime= (TextView) view.findViewById(R.id.my_OrderLogistics_item_time);
            mViewHolder.mTextView= (TextView) view.findViewById(R.id.my_OrderLogistics_item_text);
            view.setTag(mViewHolder);
        }else {
            mViewHolder= (LogisticsViewHolder) view.getTag();
        }
        Logistics_item beans=mList.get(i);

        if(i==0){
            mViewHolder.mImageView.setImageResource(R.drawable.item_logistics_chosen);
            mViewHolder.mTextView.setVisibility(View.VISIBLE);
        }else {
            mViewHolder.mImageView.setImageResource(R.drawable.item_logistics_unchosen);
            mViewHolder.mTextView.setVisibility(View.VISIBLE);
        }
        if(i==mList.size()-1){
            mViewHolder.mTextView.setVisibility(View.GONE);
        }
        mViewHolder.mContent.setText(beans.getContext());
        mViewHolder.mTime.setText(beans.getTime());
        return view;
    }
}
