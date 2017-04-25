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
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Activity.Comment_Reply_Activity;
import com.multshows.Activity.Idle_ProduceDetails_Activity;
import com.multshows.Activity.MyHomePager_Activity;
import com.multshows.Beans.Activity;
import com.multshows.Beans.Friends_AllList_Beans;
import com.multshows.Beans.ShowsBase;
import com.multshows.Beans.ShowsComment;
import com.multshows.Beans.UserBase;
import com.multshows.R;
import com.multshows.Utils.DateDeal;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Emoji_Map;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;

import java.util.List;

/**
 * 评论，赞我的，公益 公用适配器
 */
public class Friends_CommentList_Adapter extends BaseAdapter {
    //上下文
    Context mContext;
    //绑定布局类
    LayoutInflater mInflater;
    //数据集合
    List<ShowsComment> mList;
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();

    public Friends_CommentList_Adapter(Context context, List<ShowsComment> list) {
        mContext = context;
        mList = list;

        mInflater = LayoutInflater.from(mContext);
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

    class CommentViewHolder {
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
        LinearLayout mShowLayout;//作品布局
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        CommentViewHolder mViewHolder;
        if (view == null) {
            view = mInflater.inflate(R.layout.friends_all_list_item, null);
            mViewHolder = new CommentViewHolder();
            mViewHolder.mHeader = (SimpleDraweeView) view.findViewById(R.id.friends_allList_userHeader);
            mViewHolder.mUserName = (TextView) view.findViewById(R.id.friends_allList_userName);
            mViewHolder.mSendTime = (TextView) view.findViewById(R.id.friends_allList_sendTime);
            mViewHolder.mToUserName = (TextView) view.findViewById(R.id.friends_allList_myID);
            mViewHolder.mComment = (TextView) view.findViewById(R.id.friends_allList_comment);
            mViewHolder.mToUserPic = (SimpleDraweeView) view.findViewById(R.id.friends_allList_SSuserPic);
            mViewHolder.mSSName = (TextView) view.findViewById(R.id.friends_allList_SSuserName);
            mViewHolder.mSSContent = (TextView) view.findViewById(R.id.friends_allList_SScontent);
            mViewHolder.mCommentLayout = (LinearLayout) view.findViewById(R.id.friends_allList_commentLayout);
            mViewHolder.mButton = (Button) view.findViewById(R.id.friends_allList_button);
            mViewHolder.mShowLayout= (LinearLayout) view.findViewById(R.id.friends_allList_ShowsLayout);
            view.setTag(mViewHolder);
        } else {
            mViewHolder = (CommentViewHolder) view.getTag();
        }
        //显示布局
        mViewHolder.mCommentLayout.setVisibility(View.VISIBLE);
        final ShowsComment beans = mList.get(i);
        final UserBase user = beans.getUser();//评论人
        final UserBase author = beans.getAuthor();//说说作者
        UserBase targetUser = beans.getTargetUser();//被评论人信息（我）
        final ShowsBase show = beans.getShows();//作品信息
        Activity activity_ = beans.getActivity();//活动信息
        if (user.getPortrait() != null && !"null".equals(user.getPortrait()))
            mViewHolder.mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(user.getPortrait())));
        else
            mViewHolder.mHeader.setImageURI(Uri.parse(""));
        mViewHolder.mUserName.setText(user.getNickName());
        //时间
        String str = DateDeal.format(Time_Now.getStringTime(beans.getRecordTime()));
        mViewHolder.mSendTime.setText(str);
        mViewHolder.mToUserName.setText(targetUser.getNickName());
        //解密
        Emoji_Change emoji_change = new Emoji_Change();
        mViewHolder.mComment.setText(Emoji_Map.Emojichange((android.app.Activity) mContext, emoji_change.ChangeEmoji(beans.getContent())));
        if (show.getShowsPhoto() != null && !"".equals(show.getShowsPhoto())) {
            if (!"".equals(SubString_Utils.getPhotoUrl(show.getShowsPhoto()))) {
                mViewHolder.mToUserPic.setImageURI(Uri.parse(SubString_Utils.getImageUrl(SubString_Utils.getPhotoUrl(show.getShowsPhoto()))));
            } else {
                mViewHolder.mToUserPic.setImageURI(Uri.parse(""));
            }
        } else {
            mViewHolder.mToUserPic.setImageURI(Uri.parse(""));
        }
        mViewHolder.mSSName.setText(author.getNickName());
        if (show != null) {
            mViewHolder.mSSContent.setText(show.getInfos());
        } else {
            mViewHolder.mSSContent.setText(activity_.getTitle());
        }
        mViewHolder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //回复评论
                Intent intent = new Intent(mContext, Comment_Reply_Activity.class);
                intent.putExtra("otherID", user.getUserId());
                intent.putExtra("AuthorId", author.getUserId());
                intent.putExtra("CommentID", beans.getId());
                intent.putExtra("WorkID", show.getId());
                ((android.app.Activity) mContext).startActivityForResult(intent, 2);
            }
        });
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
                if(show.getTitle()!=null && !"".equals(show.getTitle())) {
                    Intent mIntent = new Intent(mContext, Idle_ProduceDetails_Activity.class);
                    mIntent.putExtra("IdleID", show.getId());
                    mContext.startActivity(mIntent);
                }
            }
        });
        return view;
    }
}
