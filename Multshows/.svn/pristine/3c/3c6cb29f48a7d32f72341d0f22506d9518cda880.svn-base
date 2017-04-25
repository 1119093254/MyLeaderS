package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.ActivityDetailsUser_Beans;
import com.multshows.Beans.ActivityDetails_Beans;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Views.MyGridView;

import java.util.List;

/**
 * 描述：活动详情图文详情适配器
 * 作者：李萧萧
 * 时间：2016.10.9
 */
public class ActivityDetails_User_Adapter extends BaseAdapter {
    List<ActivityDetailsUser_Beans> mList;
    private Context mContext;

    public ActivityDetails_User_Adapter(Context mContext, List<ActivityDetailsUser_Beans> mList) {
        this.mContext = mContext;
        this.mList = mList;
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

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_activitydetails_user, null);
            viewHolder.user_image = (SimpleDraweeView) view.findViewById(R.id.user_image);
            viewHolder.username = (TextView) view.findViewById(R.id.username);
            viewHolder.userPhone = (TextView) view.findViewById(R.id.userPhone);
            viewHolder.userGridView = (MyGridView) view.findViewById(R.id.userGridView);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ActivityDetailsUser_Beans beans=mList.get(i);
        Uri mUri=Uri.parse(beans.getUserImage());
        viewHolder.user_image.setImageURI(mUri);
        viewHolder.username.setText(beans.getUserName());
        if(Login_Static.myUserID.equals(beans.getId())){
            viewHolder.userPhone.setText(beans.getPhone());
        }else
        if(beans.getPhone().length()>7)
        viewHolder.userPhone.setText(beans.getPhone().substring(0,3)+"****"+beans.getPhone().substring(7));
        else {
            viewHolder.userPhone.setText(beans.getPhone());
        }
        return view;
    }
    class ViewHolder{
        SimpleDraweeView user_image;//图片详情
        TextView username;//文字详情
        TextView userPhone;//文字详情
        MyGridView userGridView;//用户名片
    }
}
