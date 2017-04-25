package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.UserFriend_Beans;
import com.multshows.Beans.User_;
import com.multshows.R;
import com.multshows.Utils.SubString_Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索——查找好友适配器
 */
public class Search_Friends_Adapter extends BaseAdapter{
    //上下文
    Context mContext;
    LayoutInflater mInflater;
    //数据集合
    List<UserFriend_Beans> mList;

    public Search_Friends_Adapter(Context context, List<UserFriend_Beans> list) {
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

    class viewHolder{
        SimpleDraweeView mHeader;//头像
        TextView mNickName;//昵称
        TextView mTag_1,mTag_2,mTag_3;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder mViewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.search_friends_item,null);
            mViewHolder=new viewHolder();
            mViewHolder.mHeader= (SimpleDraweeView) convertView.findViewById(R.id.Search_Friends_items_userHeader);
            mViewHolder.mNickName= (TextView) convertView.findViewById(R.id.Search_Friends_items_userName);
            mViewHolder.mTag_1= (TextView) convertView.findViewById(R.id.Search_Friends_userTag1);
            mViewHolder.mTag_2= (TextView) convertView.findViewById(R.id.Search_Friends_userTag2);
            mViewHolder.mTag_3= (TextView) convertView.findViewById(R.id.Search_Friends_userTag3);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (viewHolder) convertView.getTag();
        }
        UserFriend_Beans beans=mList.get(position);
        User_ target=beans.getTargetUser();
        if(target.getPortrait()!=null && !"null".equals(target.getPortrait())){
            mViewHolder.mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(target.getPortrait())));
        }else {
            mViewHolder.mHeader.setImageURI(Uri.parse(""));
        }
        //显示标签部分
        if(target.getUserTagList()!=null){
            if(target.getUserTagList().size()==0){
                mViewHolder.mTag_1.setVisibility(View.GONE);
                mViewHolder.mTag_2.setVisibility(View.GONE);
                mViewHolder.mTag_3.setVisibility(View.GONE);
            }else if(target.getUserTagList().size()==1){
                mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                mViewHolder.mTag_2.setVisibility(View.GONE);
                mViewHolder.mTag_3.setVisibility(View.GONE);
                mViewHolder.mTag_1.setText(target.getUserTagList().get(0).getTagName());
            }else if(target.getUserTagList().size()==2){
                mViewHolder.mTag_2.setVisibility(View.VISIBLE);
                mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                mViewHolder.mTag_3.setVisibility(View.GONE);
                mViewHolder.mTag_1.setText(target.getUserTagList().get(0).getTagName());
                mViewHolder.mTag_2.setText(target.getUserTagList().get(1).getTagName());
            }else {
                mViewHolder.mTag_3.setVisibility(View.VISIBLE);
                mViewHolder.mTag_2.setVisibility(View.VISIBLE);
                mViewHolder.mTag_1.setVisibility(View.VISIBLE);
                mViewHolder.mTag_1.setText(target.getUserTagList().get(0).getTagName());
                mViewHolder.mTag_2.setText(target.getUserTagList().get(1).getTagName());
                mViewHolder.mTag_3.setText(target.getUserTagList().get(2).getTagName());
            }
        }
        mViewHolder.mNickName.setText(target.getNickName());

        return convertView;
    }
}
