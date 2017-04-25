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
import com.multshows.Activity.Idle_ProduceDetails_Activity;
import com.multshows.Activity.MyHomePager_Activity;
import com.multshows.Activity.My_ReWordList_Activity;
import com.multshows.Beans.Friends_AllList_Beans;
import com.multshows.Beans.ShowsBase;
import com.multshows.Beans.ShowsReward;
import com.multshows.Beans.UserBase;
import com.multshows.R;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;

import java.util.List;

/**
 * 打赏我的适配器
 */
public class Friends_ReWordList_Adapter extends BaseAdapter{
    //上下文
    Context mContext;
    //绑定布局类
    LayoutInflater mInflater;
    //数据集合
    List<ShowsReward> mList;
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();

    public Friends_ReWordList_Adapter(Context context, List<ShowsReward> list) {
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
        LinearLayout mShowLayout;//作品布局
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

                mViewHolder.mImageView= (ImageView) view.findViewById(R.id.friends_allList_giveImage);
                mViewHolder.mToUserName= (TextView) view.findViewById(R.id.friends_allList_giveMe);
                mViewHolder.mTextView= (TextView) view.findViewById(R.id.friends_allList_giveText);

                mViewHolder.mToUserPic= (SimpleDraweeView) view.findViewById(R.id.friends_allList_SSuserPic);
                mViewHolder.mSSName= (TextView) view.findViewById(R.id.friends_allList_SSuserName);
                mViewHolder.mSSContent= (TextView) view.findViewById(R.id.friends_allList_SScontent);
                mViewHolder.mLoveLayout= (LinearLayout) view.findViewById(R.id.friends_allList_loveLayout);
                mViewHolder.mButton= (Button) view.findViewById(R.id.friends_allList_button);
                mViewHolder.mShowLayout= (LinearLayout) view.findViewById(R.id.friends_allList_ShowsLayout);
                view.setTag(mViewHolder);
            } else {
                mViewHolder= (LoveViewHolder) view.getTag();
            }
            //显示布局
            mViewHolder.mButton.setText("查看");
             mViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, My_ReWordList_Activity.class);
                mContext.startActivity(intent);
            }
        });
            mViewHolder.mLoveLayout.setVisibility(View.VISIBLE);

             ShowsReward beans=mList.get(i);
             final UserBase user=beans.getUser();
             UserBase author=beans.getAuthor();
             final ShowsBase shows=beans.getShows();
        if(user.getPortrait()!=null && !"null".equals(user.getPortrait())) {
            mViewHolder.mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(user.getPortrait())));
        }else {
            mViewHolder.mHeader.setImageURI(Uri.parse(""));
        }
            mViewHolder.mUserName.setText(user.getNickName());
            mViewHolder.mSendTime.setText(Time_Now.getStringTime(beans.getRewardTime()));
        //mViewHolder.mImageView.setImageResource(R.drawable.);
        mViewHolder.mToUserName.setVisibility(View.GONE);
        mViewHolder.mTextView.setText("给你打赏了¥"+beans.getAmount());
        if(shows.getShowsPhoto()!=null && !"".equals(shows.getShowsPhoto())) {
            if(!"".equals(SubString_Utils.getPhotoUrl(shows.getShowsPhoto()))){
                mViewHolder.mToUserPic.setImageURI(Uri.parse(SubString_Utils.getImageUrl(SubString_Utils.getPhotoUrl(shows.getShowsPhoto()))));
            }else {
                mViewHolder.mToUserPic.setImageURI(Uri.parse(""));
            }
        }else {
            mViewHolder.mToUserPic.setImageURI(Uri.parse(""));
        }
            mViewHolder.mSSName.setText(author.getNickName());
            mViewHolder.mSSContent.setText(shows.getInfos());
        mViewHolder.mHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MyHomePager_Activity.class);
                saveSharedPreferences.Save_PREFS(mContext, "otherId", user.getUserId());
                mContext.startActivity(intent);
            }
        });
        //作品详情
        mViewHolder.mShowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shows.getTitle()!=null && !"".equals(shows.getTitle())) {
                    Intent mIntent = new Intent(mContext, Idle_ProduceDetails_Activity.class);
                    mIntent.putExtra("IdleID", shows.getId());
                    mContext.startActivity(mIntent);
                }
            }
        });
        return view;
    }
}
