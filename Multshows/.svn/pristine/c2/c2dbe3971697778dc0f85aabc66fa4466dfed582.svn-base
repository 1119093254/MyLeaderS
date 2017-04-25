package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.Message;
import com.multshows.R;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 消息——订单消息适配器
 */
public class Friends_Order_Adapter extends BaseAdapter{
    Context mContext;
    LayoutInflater mInflater;
    List<Message> mList;
    String types;

    public Friends_Order_Adapter(Context context, List<Message> list,String type) {
        mContext = context;
        mList = list;
        types=type;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class Friends_OrderViewHolder{
        TextView mTime;//时间
        TextView mTitle;//标题
        SimpleDraweeView mPhoto;//商品图片
        TextView mContent;//商品简介
        TextView mNumber;//运送编号
        TextView mTextView;
        LinearLayout mNumberLayout;//显示或隐藏布局
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Friends_OrderViewHolder mViewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.friends_order_item,null);
            mViewHolder=new Friends_OrderViewHolder();
            mViewHolder.mTime= (TextView) convertView.findViewById(R.id.friends_order_item_Time);
            mViewHolder.mTitle= (TextView) convertView.findViewById(R.id.friends_order_item_Title);
            mViewHolder.mPhoto= (SimpleDraweeView) convertView.findViewById(R.id.friends_order_item_Photo);
            mViewHolder.mContent= (TextView) convertView.findViewById(R.id.friends_order_item_Content);
            mViewHolder.mNumber= (TextView) convertView.findViewById(R.id.friends_order_item_Number);
            mViewHolder.mTextView= (TextView) convertView.findViewById(R.id.friends_order_item_NumberText);
            mViewHolder.mNumberLayout= (LinearLayout) convertView.findViewById(R.id.friends_order_item_NumberLayout);

            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (Friends_OrderViewHolder) convertView.getTag();
        }
        Message bean=mList.get(position);
        mViewHolder.mTime.setText(Time_Now.getStringTime(bean.getRecordTime()));
        if("task".equals(types)){
            mViewHolder.mTextView.setText("奖励 ¥");
            //任务消息
            try {
                JSONObject jsonObject=new JSONObject(bean.getContent());
                mViewHolder.mTitle.setText(jsonObject.getString("Desc"));
                mViewHolder.mContent.setText(jsonObject.getString("Title"));
                if(!"null".equals(jsonObject.getString("Imgs")) && jsonObject.getString("Imgs")!=null){
                    mViewHolder.mPhoto.setImageURI(Uri.parse(SubString_Utils.getImageUrl(jsonObject.getString("Imgs"))));
                }else {
                    mViewHolder.mPhoto.setImageURI(Uri.parse(""));
                }

                mViewHolder.mNumberLayout.setVisibility(View.VISIBLE);
                mViewHolder.mNumber.setText(jsonObject.getString("Reward"));


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {
           //订单消息
            mViewHolder.mTextView.setText("运单编号：");
            try {
                JSONObject jsonObject=new JSONObject(bean.getContent());
                mViewHolder.mTitle.setText(jsonObject.getString("Desc"));
                mViewHolder.mContent.setText(jsonObject.getString("Title"));
                if(!"null".equals(jsonObject.getString("ShowsPhoto")) && jsonObject.getString("ShowsPhoto")!=null){
                    mViewHolder.mPhoto.setImageURI(Uri.parse(SubString_Utils.getImageUrl(jsonObject.getString("ShowsPhoto"))));
                }else {
                    mViewHolder.mPhoto.setImageURI(Uri.parse(""));
                }
                if("null".equals(jsonObject.getString("Number"))||"".equals(jsonObject.getString("Number"))){
                    mViewHolder.mNumberLayout.setVisibility(View.GONE);
                }else {
                    mViewHolder.mNumberLayout.setVisibility(View.VISIBLE);
                    mViewHolder.mNumber.setText(jsonObject.getString("Number"));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return convertView;
    }
}
