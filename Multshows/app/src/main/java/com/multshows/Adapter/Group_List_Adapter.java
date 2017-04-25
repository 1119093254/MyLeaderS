package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.GroupInfo;
import com.multshows.R;

import java.util.List;

/**
 * 群聊列表适配器
 */
public class Group_List_Adapter extends BaseAdapter{
    //上下文
    Context mContext;
    LayoutInflater mInflater;
    List<GroupInfo> mList;

    public Group_List_Adapter(Context context, List<GroupInfo> list) {
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

    class GroupViewHolder{
        SimpleDraweeView mHeader;
        TextView mName;//群聊昵称+群聊成员数量
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GroupViewHolder mViewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.group_list_item,null);
            mViewHolder=new GroupViewHolder();
            mViewHolder.mName= (TextView) convertView.findViewById(R.id.GroupList_item_GroupName);
            mViewHolder.mHeader= (SimpleDraweeView) convertView.findViewById(R.id.GroupList_item_GroupHeader);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (GroupViewHolder) convertView.getTag();
        }
        GroupInfo bean=mList.get(position);
        mViewHolder.mName.setText(bean.getGroupName()+"("+bean.getGroupMembers().size()+")");
        Uri uri = Uri.parse("res://"+mContext.getPackageName()+"/"+R.drawable.moretouchicon);
        mViewHolder.mHeader.setImageURI(uri);
        return convertView;
    }
}
