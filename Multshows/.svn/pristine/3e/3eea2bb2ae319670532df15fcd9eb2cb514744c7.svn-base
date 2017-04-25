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
import com.multshows.Activity.MyHomePager_Activity;
import com.multshows.Beans.ShowsFlower;
import com.multshows.Beans.ShowsReward;
import com.multshows.Beans.UserHead;
import com.multshows.R;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;

import java.util.List;

/**
 * 描述：送花打赏人显示列表适配器
 * 12/2
 */
public class FlowerRewordList_Adapter extends BaseAdapter{
    List<ShowsFlower> mList;//送花人姓名
    List<ShowsReward> mRewardList;//打赏人信息
    List<UserHead> mLikeList;//打赏人信息
    Context mContext;//上下文
    int flag=0;
    LayoutInflater inflater;
    SaveSharedPreferences saveSharedPreferences=new SaveSharedPreferences();//偏好设置
    public FlowerRewordList_Adapter(List<ShowsFlower> list, Context context) {
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(context);
    }
    public FlowerRewordList_Adapter(List<ShowsReward> list, Context context,int mFlag) {
        mContext = context;
        mRewardList = list;
        flag=mFlag;
        inflater = LayoutInflater.from(context);
    }
    public FlowerRewordList_Adapter(Context context,List<UserHead> list,int mFlag) {
        mContext = context;
        mLikeList = list;
        flag=mFlag;
        inflater = LayoutInflater.from(context);
    }
    class  ViewHolder{
        SimpleDraweeView Person_pic;//列表 头像
        TextView Person_name;//列表 昵称
        TextView mNum;//送花朵数 打赏金额
        ImageView flowerImage;//花图片
    }
    @Override
    public int getCount() {
        if(flag==1){
            return mRewardList.size();
        }if(flag==2){
            return mLikeList.size();
        }else return mList.size();

    }

    @Override
    public Object getItem(int position) {
        if(flag==1){
            return mRewardList.get(position);
        }if(flag==2){
            return mLikeList.get(position);
        }else return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_personitem, null, false);
            viewHolder.Person_pic= (SimpleDraweeView) convertView.findViewById(R.id.Person_pic);
            viewHolder.Person_name= (TextView) convertView.findViewById(R.id.Person_name);
            viewHolder.mNum= (TextView) convertView.findViewById(R.id.flowernum);
            viewHolder.flowerImage= (ImageView) convertView.findViewById(R.id.flowerImage);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if(flag==0){
            final ShowsFlower beans=mList.get(position);
            Uri urij=Uri.parse(SubString_Utils.getImageUrl(beans.getUser().getPortrait()));
            viewHolder.Person_pic.setImageURI(urij);
            viewHolder.Person_name.setText(beans.getUser().getNickName());
            viewHolder.mNum.setText(beans.getAmount()+"");
            viewHolder.Person_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, MyHomePager_Activity.class);
                    saveSharedPreferences.Save_PREFS(mContext, "otherId", beans.getUser().getUserId());//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                    mContext.startActivity(mIntent);
                }
            });
        }else if(flag==1){
            viewHolder.flowerImage.setVisibility(View.GONE);
            final ShowsReward beans=mRewardList.get(position);
            Uri urij=Uri.parse(SubString_Utils.getImageUrl(beans.getUser().getPortrait()));
            viewHolder.Person_pic.setImageURI(urij);
            viewHolder.Person_name.setText(beans.getUser().getNickName());
            viewHolder.mNum.setText("¥"+beans.getAmount());
            viewHolder.Person_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, MyHomePager_Activity.class);
                    saveSharedPreferences.Save_PREFS(mContext, "otherId", beans.getUser().getUserId());//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                    mContext.startActivity(mIntent);
                }
            });
        }else if(flag==2){
            final UserHead beans=mLikeList.get(position);
            Uri urij=Uri.parse(SubString_Utils.getImageUrl(beans.getPortrait()));
            viewHolder.Person_pic.setImageURI(urij);
            viewHolder.Person_name.setText(beans.getNickName());
            viewHolder.mNum.setVisibility(View.INVISIBLE);
            viewHolder.flowerImage.setVisibility(View.GONE);
            viewHolder.Person_pic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent mIntent = new Intent(mContext, MyHomePager_Activity.class);
                    saveSharedPreferences.Save_PREFS(mContext, "otherId", beans.getUserId());//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                    mContext.startActivity(mIntent);
                }
            });
        }


        return convertView;
    }
}
