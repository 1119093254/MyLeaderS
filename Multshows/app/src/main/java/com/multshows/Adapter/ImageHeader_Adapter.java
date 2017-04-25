package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.Friends_friend_Beans;
import com.multshows.R;
import com.multshows.Utils.SubString_Utils;

import java.util.List;

/**
 * 头像显示适配器
 */
public class ImageHeader_Adapter extends BaseAdapter{
    Context mContext;
    List<Friends_friend_Beans> mList;
    LayoutInflater mInflater;

    public ImageHeader_Adapter(Context context, List<Friends_friend_Beans> list) {
        mContext = context;
        mList = list;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.image_show_item,null);
            mViewHolder=new ViewHolder();
            mViewHolder.mHeader= (SimpleDraweeView)convertView.findViewById(R.id.image_Show_Header);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (ViewHolder) convertView.getTag();
        }
        Friends_friend_Beans beans=mList.get(position);
        if (!"null".equals(beans.getUserPic()) && beans.getUserPic() != null) {
            Uri uri = Uri.parse(SubString_Utils.getImageUrl(beans.getUserPic()));
            mViewHolder.mHeader.setImageURI(uri);
        } else {
            Uri uri = Uri.parse("");
            mViewHolder.mHeader.setImageURI(uri);
        }
        return convertView;
    }

    class ViewHolder {
        SimpleDraweeView mHeader;//头像
    }

 /*   *//**
     * 创建ViewHolder
     *//*
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.image_show_item,
                parent, false);
        ViewHolder mViewHolder = new ViewHolder(view);

        mViewHolder.mHeader= (SimpleDraweeView) view.findViewById(R.id.image_Show_Header);
        return mViewHolder;
    }
    *//**
     * 设置值
     *//*
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        Friends_friend_Beans beans=mList.get(position);
        if (!"null".equals(beans.getUserPic()) && beans.getUserPic() != null) {
            Uri uri = Uri.parse(SubString_Utils.getImageUrl(beans.getUserPic()));
            holder.mHeader.setImageURI(uri);
        } else {
            Uri uri = Uri.parse("");
            holder.mHeader.setImageURI(uri);
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }*/

}
