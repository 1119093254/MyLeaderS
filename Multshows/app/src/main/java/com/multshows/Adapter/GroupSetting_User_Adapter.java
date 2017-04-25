package com.multshows.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Activity.Group_AddPerson_Activity;
import com.multshows.Beans.GroupMember;
import com.multshows.R;
import com.multshows.Utils.SubString_Utils;

import java.io.Serializable;
import java.util.List;

/**
 * 童虎头像信息展示 适配器
 */
public class GroupSetting_User_Adapter extends BaseAdapter{
    Context mContext;
    LayoutInflater mInflater;
    List<GroupMember> mList;
    String RCGroupID="";

    public GroupSetting_User_Adapter(Context context, List<GroupMember> list,String RCGroupID) {
        mContext = context;
        mList = list;
        this.RCGroupID=RCGroupID;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getFlag();
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

    class ItemViewHolder{
        SimpleDraweeView mHeader;//头像
        TextView mName;//昵称
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(getItemViewType(position)!=1) {
            ItemViewHolder mViewHolder;
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.group_setting_user, null);
                mViewHolder = new ItemViewHolder();
                mViewHolder.mHeader = (SimpleDraweeView) convertView.findViewById(R.id.Group_Setting_UserHeader);
                mViewHolder.mName = (TextView) convertView.findViewById(R.id.Group_Setting_UserName);
                convertView.setTag(mViewHolder);
            } else {
                mViewHolder = (ItemViewHolder) convertView.getTag();
            }
            GroupMember bean = mList.get(position);
            if (!"null".equals(bean.getUserHead().getPortrait()) && bean.getUserHead().getPortrait() != null) {
                mViewHolder.mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(bean.getUserHead().getPortrait())));
            } else {
                mViewHolder.mHeader.setImageURI(Uri.parse(""));
            }
            mViewHolder.mName.setText(bean.getAlias());
            return convertView;
        }else {
            convertView = mInflater.inflate(R.layout.group_setting_user2, null);
            ImageView mImageView= (ImageView) convertView.findViewById(R.id.Group_Setting_UserHeader2);
            //添加成员
            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(mContext,Group_AddPerson_Activity.class);
                    intent.putExtra("Group",2);
                    intent.putExtra("RCGroupId",RCGroupID);
                    intent.putExtra("GroupUser", (Serializable) mList);
                    mContext.startActivity(intent);
                }
            });
            return convertView;
        }
    }
}
