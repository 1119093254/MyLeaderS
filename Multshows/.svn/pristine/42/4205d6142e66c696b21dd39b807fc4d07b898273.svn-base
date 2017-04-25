package com.multshows.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Activity.Chat_Activity;
import com.multshows.Activity.Group_Chat_Activity;
import com.multshows.Beans.Chat_ConversationList_Beans;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.DateDeal;
import com.multshows.Utils.DateDeal_2;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.SwipeLayout_Views;

import java.util.List;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * 即时消息列表  适配器
 */
public class Friends_NewsList_Adapter extends BaseAdapter{
    //上下文
    Context mContext;
    //绑定布局类
    LayoutInflater mInflater;
    //数据集合
    List<Chat_ConversationList_Beans> mList;

    public Friends_NewsList_Adapter(Context context, List<Chat_ConversationList_Beans> list) {
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

    class NewListViewHolder{
        SimpleDraweeView mHeader;
        TextView mUserName;
        TextView mLastMessage;
        TextView mSendTime;
        RelativeLayout mLayout;//点击聊天
        SwipeLayout_Views mSwipeLayoutViews;
        TextView mNoRead;//未读条数
        TextView mDelete;//删除
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        final NewListViewHolder mViewHolder;
        if(view==null) {
            view = mInflater.inflate(R.layout.friends_news_list_item, null);
            mViewHolder=new NewListViewHolder();
            mViewHolder.mHeader= (SimpleDraweeView) view.findViewById(R.id.friends_newsList_userHeader);
            mViewHolder.mUserName= (TextView) view.findViewById(R.id.friends_newsList_userName);
            mViewHolder.mLastMessage= (TextView) view.findViewById(R.id.friends_newsList_lastMessage);
            mViewHolder.mSendTime= (TextView) view.findViewById(R.id.friends_newsList_sendTime);
            mViewHolder.mLayout= (RelativeLayout) view.findViewById(R.id.friends_newsList_LeftLayout);
            mViewHolder.mSwipeLayoutViews= (SwipeLayout_Views) view.findViewById(R.id.friends_newsList_swipeLayout);
            mViewHolder.mDelete= (TextView) view.findViewById(R.id.friends_newsList_delete);
            mViewHolder.mNoRead= (TextView) view.findViewById(R.id.friends_newsList_NoReader);
            SwipeLayout_Views.addSwipeView(mViewHolder.mSwipeLayoutViews);
            view.setTag(mViewHolder);
        }else {
            mViewHolder= (NewListViewHolder) view.getTag();
        }
        final Chat_ConversationList_Beans beans=mList.get(position);

        if(beans.getType()==0) {
            if (beans.getSendUserPic() == null || "null".equals(beans.getSendUserPic()) || "".equals(beans.getSendUserPic())) {
                mViewHolder.mHeader.setImageURI(Uri.parse(""));
            } else {
                mViewHolder.mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(beans.getSendUserPic())));
            }
        }else {
            Uri uri = Uri.parse("res://"+mContext.getPackageName()+"/"+R.drawable.moretouchicon);
            mViewHolder.mHeader.setImageURI(uri);
        }
        mViewHolder.mUserName.setText(beans.getSendUserName());
        mViewHolder.mLastMessage.setText(beans.getSendLastMessage());
        String str;
        if(beans.getSendTime()!=null && !"".equals(beans.getSendTime())) {
            str = DateDeal.format(beans.getSendTime());
        }else {
            str="";
        }
        mViewHolder.mSendTime.setText(str);
        if(beans.getUnReadNumber()==0){
            mViewHolder.mNoRead.setVisibility(View.GONE);
        }else if(beans.getUnReadNumber()>99){
            mViewHolder.mNoRead.setVisibility(View.VISIBLE);
            mViewHolder.mNoRead.setText("99");
        }else {
            mViewHolder.mNoRead.setVisibility(View.VISIBLE);
            mViewHolder.mNoRead.setText(beans.getUnReadNumber()+"");
        }
        if(beans.getType()==0) {
            //删除操作
            mViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RongIMClient.getInstance().removeConversation(Conversation.ConversationType.PRIVATE,
                            beans.getSendUserId(), new RongIMClient.ResultCallback<Boolean>() {
                                @Override
                                public void onSuccess(Boolean aBoolean) {
                                    //删除成功
                                    SwipeLayout_Views.removeSwipeView(mViewHolder.mSwipeLayoutViews);
                                    mList.remove(position);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    //删除失败

                                }
                            });
                }
            });
            //点击聊天
            mViewHolder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, Chat_Activity.class);
                    intent.putExtra("userId", beans.getSendUserId());
                    mContext.startActivity(intent);
                }
            });
        }else {
            //删除操作
            mViewHolder.mDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RongIMClient.getInstance().removeConversation(Conversation.ConversationType.DISCUSSION,
                            beans.getSendUserId(), new RongIMClient.ResultCallback<Boolean>() {
                                @Override
                                public void onSuccess(Boolean aBoolean) {
                                    //删除成功
                                    SwipeLayout_Views.removeSwipeView(mViewHolder.mSwipeLayoutViews);
                                    mList.remove(position);
                                    notifyDataSetChanged();
                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    //删除失败

                                }
                            });
                }
            });
            //点击群聊天
            mViewHolder.mLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CLog.e("testing","RcID"+beans.getSendUserId());
                    Intent intent = new Intent(mContext,Group_Chat_Activity.class);
                    intent.putExtra("groupId", beans.getSendUserId());
                    mContext.startActivity(intent);
                }
            });
        }

        return view;
    }

}
