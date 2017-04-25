package com.multshows.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Activity.Comment_Reply_Activity;
import com.multshows.Beans.Friends_AllList_Beans;
import com.multshows.R;

import java.util.List;

/**
 * 评论，赞我的，公益 公用适配器
 */
public class Friends_AllList_Adapter extends BaseAdapter{
    //上下文
    Context mContext;
    //绑定布局类
    LayoutInflater mInflater;
    //数据集合
    List<Friends_AllList_Beans> mList;
    //传入标志
    int flag;//0：评论；1：赞我的；2：公益

    public Friends_AllList_Adapter(Context context, List<Friends_AllList_Beans> list, int flag) {
        mContext = context;
        mList = list;
        this.flag = flag;
        mInflater=LayoutInflater.from(mContext);
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
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

    class CommentViewHolder{
        SimpleDraweeView mHeader;//用户头像
        TextView mUserName;   //用户头像
        TextView mSendTime;   // 发送时间
        TextView mToUserName;   //对方昵称
        TextView mComment;   // 评论内容
        SimpleDraweeView mToUserPic;// 说说用户头像
        TextView mSSName;   //说说用户昵称
        TextView mSSContent;   //说说内容
        LinearLayout mCommentLayout;//布局
        Button mButton;//回复
    }
    class ZanViewHolder{
        SimpleDraweeView mHeader;//用户头像
        TextView mUserName;   //用户头像
        TextView mSendTime;   // 发送时间
        SimpleDraweeView mToUserPic;// 说说用户头像
        TextView mSSName;   //说说用户昵称
        TextView mSSContent;   //说说内容
        LinearLayout mZanLayout;//布局
        Button mButton;//回复
    }
    class LoveViewHolder{
        SimpleDraweeView mHeader;//用户头像
        TextView mUserName;   //用户头像
        TextView mSendTime;   // 发送时间
        TextView mToUserName;   //对方昵称
        SimpleDraweeView mToUserPic;// 说说用户头像
        TextView mSSName;   //说说用户昵称
        TextView mSSContent;   //说说内容
        LinearLayout mLoveLayout;//布局
        Button mButton;//回复
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(flag==0) {
            CommentViewHolder mViewHolder;
            if (view == null) {
                view = mInflater.inflate(R.layout.friends_all_list_item, null);
                mViewHolder = new CommentViewHolder();
                mViewHolder.mHeader= (SimpleDraweeView) view.findViewById(R.id.friends_allList_userHeader);
                mViewHolder.mUserName= (TextView) view.findViewById(R.id.friends_allList_userName);
                mViewHolder.mSendTime= (TextView) view.findViewById(R.id.friends_allList_sendTime);
                mViewHolder.mToUserName= (TextView) view.findViewById(R.id.friends_allList_myID);
                mViewHolder.mComment= (TextView) view.findViewById(R.id.friends_allList_comment);
                mViewHolder.mToUserPic= (SimpleDraweeView) view.findViewById(R.id.friends_allList_SSuserPic);
                mViewHolder.mSSName= (TextView) view.findViewById(R.id.friends_allList_SSuserName);
                mViewHolder.mSSContent= (TextView) view.findViewById(R.id.friends_allList_SScontent);
                mViewHolder.mCommentLayout= (LinearLayout) view.findViewById(R.id.friends_allList_commentLayout);
                mViewHolder.mButton= (Button) view.findViewById(R.id.friends_allList_button);
                view.setTag(mViewHolder);
            } else {
                mViewHolder= (CommentViewHolder) view.getTag();
            }
            //显示布局
            mViewHolder.mCommentLayout.setVisibility(View.VISIBLE);
            Friends_AllList_Beans beans=mList.get(i);
            mViewHolder.mHeader.setImageURI(Uri.parse(beans.getUserPic()));
            mViewHolder.mUserName.setText(beans.getUserName());
            mViewHolder.mSendTime.setText(beans.getTime());
            mViewHolder.mToUserName.setText(beans.getToUserName());
            mViewHolder.mComment.setText(beans.getComment());
            mViewHolder.mToUserPic.setImageURI(Uri.parse(beans.getSSUserPic()));
            mViewHolder.mSSName.setText(beans.getSSUserName());
            mViewHolder.mSSContent.setText(beans.getSSContent());
            mViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //回复评论
                    Intent intent=new Intent(mContext, Comment_Reply_Activity.class);
                    mContext.startActivity(intent);
                }
            });

        }else if(flag==1){
            ZanViewHolder mViewHolder;
            if (view == null) {
                view = mInflater.inflate(R.layout.friends_all_list_item, null);
                mViewHolder = new ZanViewHolder();
                mViewHolder.mHeader= (SimpleDraweeView) view.findViewById(R.id.friends_allList_userHeader);
                mViewHolder.mUserName= (TextView) view.findViewById(R.id.friends_allList_userName);
                mViewHolder.mSendTime= (TextView) view.findViewById(R.id.friends_allList_sendTime);
                mViewHolder.mToUserPic= (SimpleDraweeView) view.findViewById(R.id.friends_allList_SSuserPic);
                mViewHolder.mSSName= (TextView) view.findViewById(R.id.friends_allList_SSuserName);
                mViewHolder.mSSContent= (TextView) view.findViewById(R.id.friends_allList_SScontent);
                mViewHolder.mZanLayout= (LinearLayout) view.findViewById(R.id.friends_allList_zanLayout);
                mViewHolder.mButton= (Button) view.findViewById(R.id.friends_allList_button);
                view.setTag(mViewHolder);
            } else {
                mViewHolder= (ZanViewHolder) view.getTag();
            }
            //显示布局
            mViewHolder.mZanLayout.setVisibility(View.VISIBLE);
            Friends_AllList_Beans beans=mList.get(i);
            mViewHolder.mHeader.setImageURI(Uri.parse(beans.getUserPic()));
            mViewHolder.mUserName.setText(beans.getUserName());
            mViewHolder.mSendTime.setText(beans.getTime());
            mViewHolder.mToUserPic.setImageURI(Uri.parse(beans.getSSUserPic()));
            mViewHolder.mSSName.setText(beans.getSSUserName());
            mViewHolder.mSSContent.setText(beans.getSSContent());
            mViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //回复赞我的
                }
            });

        }else {
            LoveViewHolder mViewHolder;
            if (view == null) {
                view = mInflater.inflate(R.layout.friends_all_list_item, null);
                mViewHolder = new LoveViewHolder();
                mViewHolder.mHeader= (SimpleDraweeView) view.findViewById(R.id.friends_allList_userHeader);
                mViewHolder.mUserName= (TextView) view.findViewById(R.id.friends_allList_userName);
                mViewHolder.mSendTime= (TextView) view.findViewById(R.id.friends_allList_sendTime);
                mViewHolder.mToUserName= (TextView) view.findViewById(R.id.friends_allList_giveMe);
                mViewHolder.mToUserPic= (SimpleDraweeView) view.findViewById(R.id.friends_allList_SSuserPic);
                mViewHolder.mSSName= (TextView) view.findViewById(R.id.friends_allList_SSuserName);
                mViewHolder.mSSContent= (TextView) view.findViewById(R.id.friends_allList_SScontent);
                mViewHolder.mLoveLayout= (LinearLayout) view.findViewById(R.id.friends_allList_loveLayout);
                mViewHolder.mButton= (Button) view.findViewById(R.id.friends_allList_button);
                view.setTag(mViewHolder);
            } else {
                mViewHolder= (LoveViewHolder) view.getTag();
            }
            //显示布局
            mViewHolder.mLoveLayout.setVisibility(View.VISIBLE);
            Friends_AllList_Beans beans=mList.get(i);
            mViewHolder.mHeader.setImageURI(Uri.parse(beans.getUserPic()));
            mViewHolder.mUserName.setText(beans.getUserName());
            mViewHolder.mSendTime.setText(beans.getTime());
            mViewHolder.mToUserName.setText(beans.getUserName());
            mViewHolder.mToUserPic.setImageURI(Uri.parse(beans.getSSUserPic()));
            mViewHolder.mSSName.setText(beans.getSSUserName());
            mViewHolder.mSSContent.setText(beans.getSSContent());
            mViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //回复公益
                }
            });


        }
        return view;
    }
}
