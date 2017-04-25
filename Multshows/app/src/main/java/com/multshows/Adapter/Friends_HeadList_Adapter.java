package com.multshows.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Activity.Comment_Reply_Activity;
import com.multshows.Beans.Friends_AllList_Beans;
import com.multshows.Beans.ShowsBase;
import com.multshows.Beans.ShowsFlower;
import com.multshows.Beans.ShowsLike;
import com.multshows.Beans.UserBase;
import com.multshows.R;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;

import java.util.List;

/**
 * 评论，赞我的，公益 公用适配器
 */
public class Friends_HeadList_Adapter extends BaseAdapter{
    //上下文
    Context mContext;
    //绑定布局类
    LayoutInflater mInflater;
    //数据集合
    List<ShowsLike> mList;


    public Friends_HeadList_Adapter(Context context, List<ShowsLike> list) {
        mContext = context;
        mList = list;
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

    class LoveViewHolder{
        SimpleDraweeView mHeader;//用户头像
        TextView mUserName;   //用户头像
        TextView mSendTime;   // 发送时间
        ImageView mImageView;//图标
        TextView mToUserName;   //对方昵称
        TextView mTextView;//提示信息
        SimpleDraweeView mToUserPic;// 说说用户头像
        TextView mSSName;   //说说用户昵称
        TextView mSSContent;   //说说内容
        LinearLayout mLoveLayout;//布局
        Button mButton;//回复
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
            LoveViewHolder mViewHolder;
            if (view == null) {
                view = mInflater.inflate(R.layout.friends_all_list_item, null);
                mViewHolder = new LoveViewHolder();
                mViewHolder.mHeader= (SimpleDraweeView) view.findViewById(R.id.friends_allList_userHeader);
                mViewHolder.mUserName= (TextView) view.findViewById(R.id.friends_allList_userName);
                mViewHolder.mSendTime= (TextView) view.findViewById(R.id.friends_allList_sendTime);
                mViewHolder.mImageView = (ImageView) view.findViewById(R.id.friends_allList_giveImage);
                mViewHolder.mToUserName = (TextView) view.findViewById(R.id.friends_allList_giveMe);
                mViewHolder.mTextView = (TextView) view.findViewById(R.id.friends_allList_giveText);
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
        mViewHolder.mButton.setVisibility(View.GONE);
        mViewHolder.mLoveLayout.setVisibility(View.VISIBLE);
        ShowsLike beans=mList.get(i);
        UserBase user=beans.getUser();//点赞者信息
        UserBase author=beans.getAuthor();//作者信息
        ShowsBase show=beans.getShows();//作品信息
        if(user.getPortrait()!=null && !"null".equals(user.getPortrait()))
            mViewHolder.mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(user.getPortrait())));
        else
            mViewHolder.mHeader.setImageURI(Uri.parse(""));
        mViewHolder.mUserName.setText(user.getNickName());
        //时间
        //String str= DateDeal.format(Time_Now.getStringTime(beans.getRecordTime()));
        //mViewHolder.mSendTime.setText(beans.getTime());
        mViewHolder.mToUserName.setVisibility(View.GONE);
        mViewHolder.mTextView.setText("为你的公益鼓掌");
        if(show.getShowsPhoto()!=null && !"".equals(show.getShowsPhoto())) {
            if(!"".equals(SubString_Utils.getPhotoUrl(show.getShowsPhoto()))){
                mViewHolder.mToUserPic.setImageURI(Uri.parse(SubString_Utils.getImageUrl(SubString_Utils.getPhotoUrl(show.getShowsPhoto()))));
            }else {
                mViewHolder.mToUserPic.setImageURI(Uri.parse(""));
            }
        }else {
            mViewHolder.mToUserPic.setImageURI(Uri.parse(""));
        }
        mViewHolder.mSSName.setText(author.getNickName());
        mViewHolder.mSSContent.setText(show.getTitle());

        return view;
    }
}
