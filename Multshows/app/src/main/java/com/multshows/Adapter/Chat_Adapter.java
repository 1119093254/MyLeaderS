package com.multshows.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Activity.MyHomePager_Activity;
import com.multshows.Beans.ChatContext_Beans;
import com.multshows.Beans.GroupMember;
import com.multshows.R;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.NineGridview.ImageInfo;
import com.multshows.Views.NineGridview.preview.ImagePreviewActivity;
import com.rockerhieu.emojicon.EmojiconTextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：聊天通讯界面适配器
 */
public class Chat_Adapter extends BaseAdapter {
    //上下文
    Context mContext;
    //绑定布局类
    LayoutInflater mInflater;
    //数据集合
    List<ChatContext_Beans> mList;
    List<ImageInfo> imageInfo;
    int position_c=0;
    int type=0;//0私聊 1群聊
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();//偏好设置

    public Chat_Adapter(Context context, List<ChatContext_Beans> list, List<ImageInfo> imageInfo,int type) {
        mContext = context;
        mList = list;
        this.imageInfo = imageInfo;
        this.type=type;
        mInflater = LayoutInflater.from(mContext);
    }

    /**
     * 不可点击
     *
     * @param position
     * @return
     */
    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    /**
     * 获取布局标志
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        return mList.get(position).getFlag();
    }

    /**
     * 获取不同布局的个数
     * @return
     */
    @Override
    public int getViewTypeCount() {
        return 4;
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

    //我发出的信息布局（右边）
    class viewHolder_me {
        //TextView mID;
        RelativeLayout mTopLayout;
        SimpleDraweeView mHead;
        EmojiconTextView mContext;//文字
        SimpleDraweeView mImageView;//图片
        LinearLayout mContentLayout;
        TextView mTime;
    }
    //他人发出的信息布局（左边）
    class viewHolder_other {
        TextView mNickName;//昵称
        RelativeLayout mTopLayout;
        SimpleDraweeView mHead;
        EmojiconTextView mContext;//文字
        SimpleDraweeView mImageView;//图片
        LinearLayout mContentLayout;
        TextView mTime;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (getItemViewType(position) == 0) {
            //自己本人
            final viewHolder_me mViewHolder;
            if (view == null) {
                view = mInflater.inflate(R.layout.chat_item_me, null);
                mViewHolder = new viewHolder_me();
                mViewHolder.mHead = (SimpleDraweeView) view.findViewById(R.id.chat_item_me_header);
                mViewHolder.mContext = (EmojiconTextView) view.findViewById(R.id.chat_item_me_context);
                mViewHolder.mTime = (TextView) view.findViewById(R.id.chat_item_me_sendTime);
                mViewHolder.mTopLayout = (RelativeLayout) view.findViewById(R.id.chat_item_me_topTimeLayout);
                mViewHolder.mImageView = (SimpleDraweeView) view.findViewById(R.id.chat_item_me_imageContent);
                mViewHolder.mContentLayout= (LinearLayout) view.findViewById(R.id.chat_item_me_contextLyout);
                view.setTag(mViewHolder);
            } else {
                mViewHolder = (viewHolder_me) view.getTag();
            }
            final ChatContext_Beans beans = mList.get(position);
            if (position == 0) {
                //第一个显示
            } else {
                ChatContext_Beans beans_0 = mList.get(position - 1);
                //判断时间是否显示
                if (Time_Now.getLongTime(beans.getTime()) - Time_Now.getLongTime(beans_0.getTime()) <= 30000) {
                    //当时间间隔小于3分钟时不显示时间
                    mViewHolder.mTopLayout.setVisibility(View.GONE);
                }
            }

            //Picasso图片加载框架
            if (!beans.getUserPicUrl().equals("null") && beans.getUserPicUrl() != null) {
                //Picasso.with(mContext).load(beans.getUserPicUrl()).into(mViewHolder.mHead);
                mViewHolder.mHead.setImageURI(Uri.parse(beans.getUserPicUrl()));
            } else {
                mViewHolder.mHead.setImageURI(Uri.parse(""));
            }
            mViewHolder.mContentLayout.setVisibility(View.VISIBLE);
            mViewHolder.mImageView.setVisibility(View.GONE);
            mViewHolder.mContext.setText(beans.getContext());
            //头像点击事件
            mViewHolder.mHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MyHomePager_Activity.class);
                    saveSharedPreferences.Save_PREFS(mContext, "otherId", beans.getUid());//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                    mContext.startActivity(intent);
                }
            });

            mViewHolder.mTime.setText(beans.getTime());
            return view;
        } else if (getItemViewType(position) == 2) {
            //自己本人
            final viewHolder_me mViewHolder;
            if (view == null) {
                view = mInflater.inflate(R.layout.chat_item_me, null);
                mViewHolder = new viewHolder_me();
                mViewHolder.mHead = (SimpleDraweeView) view.findViewById(R.id.chat_item_me_header);
                mViewHolder.mContext = (EmojiconTextView) view.findViewById(R.id.chat_item_me_context);
                mViewHolder.mTime = (TextView) view.findViewById(R.id.chat_item_me_sendTime);
                mViewHolder.mTopLayout = (RelativeLayout) view.findViewById(R.id.chat_item_me_topTimeLayout);
                mViewHolder.mImageView = (SimpleDraweeView) view.findViewById(R.id.chat_item_me_imageContent);
                mViewHolder.mContentLayout= (LinearLayout) view.findViewById(R.id.chat_item_me_contextLyout);
                view.setTag(mViewHolder);
            } else {
                mViewHolder = (viewHolder_me) view.getTag();
            }
            final ChatContext_Beans beans = mList.get(position);
            if (position == 0) {
                //第一个显示
            } else {
                ChatContext_Beans beans_0 = mList.get(position - 1);
                //判断时间是否显示
                if (Time_Now.getLongTime(beans.getTime()) - Time_Now.getLongTime(beans_0.getTime()) <= 30000) {
                    //当时间间隔小于3分钟时不显示时间
                    mViewHolder.mTopLayout.setVisibility(View.GONE);
                }
            }
            //头像点击事件
            mViewHolder.mHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MyHomePager_Activity.class);
                    saveSharedPreferences.Save_PREFS(mContext, "otherId", beans.getUid());//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                    mContext.startActivity(intent);
                }
            });
            //Picasso图片加载框架
            if (!beans.getUserPicUrl().equals("null") && beans.getUserPicUrl() != null) {
                //Picasso.with(mContext).load(beans.getUserPicUrl()).into(mViewHolder.mHead);
                mViewHolder.mHead.setImageURI(Uri.parse(beans.getUserPicUrl()));
            } else {
                mViewHolder.mHead.setImageURI(Uri.parse(""));
            }
            mViewHolder.mContentLayout.setVisibility(View.GONE);
            mViewHolder.mContext.setVisibility(View.GONE);
            mViewHolder.mImageView.setAspectRatio(0.75f);
            if (beans.getContext() != null && !beans.getContext().equals("null"))
                mViewHolder.mImageView.setImageURI(Uri.parse(beans.getContext()));


            //查看大图
            mViewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < imageInfo.size(); i++) {
                        if(imageInfo.get(i).getThumbnailUrl().equals(beans.getContext())){
                            position_c=i;
                        }
                    }
                    Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
                    bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, position_c);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(0, 0);
                }
            });

            mViewHolder.mTime.setText(beans.getTime());
            return view;
        } else if (getItemViewType(position) == 3) {
            //他人
            final viewHolder_other mViewHolder;
            if (view == null) {
                view = mInflater.inflate(R.layout.chat_item_other, null);
                mViewHolder = new viewHolder_other();
                mViewHolder.mHead = (SimpleDraweeView) view.findViewById(R.id.chat_item_other_header);
                mViewHolder.mContext = (EmojiconTextView) view.findViewById(R.id.chat_item_other_context);
                mViewHolder.mNickName= (TextView) view.findViewById(R.id.chat_item_other_NickName);
                mViewHolder.mTime = (TextView) view.findViewById(R.id.chat_item_other_sendTime);
                mViewHolder.mTopLayout = (RelativeLayout) view.findViewById(R.id.chat_item_other_topTimeLayout);
                mViewHolder.mImageView = (SimpleDraweeView) view.findViewById(R.id.chat_item_other_imageContent);
                mViewHolder.mContentLayout= (LinearLayout) view.findViewById(R.id.chat_item_other_contextLyout);
                view.setTag(mViewHolder);
            } else {
                mViewHolder = (viewHolder_other) view.getTag();
            }
            final ChatContext_Beans beans = mList.get(position);
            if(type==0){
                mViewHolder.mNickName.setVisibility(View.GONE);
            }else {
                mViewHolder.mNickName.setVisibility(View.VISIBLE);
                mViewHolder.mNickName.setText(beans.getNickName());
            }
            if (position == 0) {
                //第一个显示
            } else {
                ChatContext_Beans beans_0 = mList.get(position - 1);
                //判断时间是否显示
                if (Time_Now.getLongTime(beans.getTime()) - Time_Now.getLongTime(beans_0.getTime()) <= 30000) {
                    //当时间间隔小于3分钟时不显示时间
                    mViewHolder.mTopLayout.setVisibility(View.GONE);
                }
            }
            //头像点击事件
            mViewHolder.mHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MyHomePager_Activity.class);
                    saveSharedPreferences.Save_PREFS(mContext, "otherId", beans.getUid());//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                    mContext.startActivity(intent);
                }
            });
            //Picasso图片加载框架
            if (!beans.getUserPicUrl().equals("null") && beans.getUserPicUrl() != null) {
                //Picasso.with(mContext).load(beans.getUserPicUrl()).into(mViewHolder.mHead);
                mViewHolder.mHead.setImageURI(Uri.parse(beans.getUserPicUrl()));
            } else {
                mViewHolder.mHead.setImageURI(Uri.parse(""));
            }
            mViewHolder.mContentLayout.setVisibility(View.GONE);
            mViewHolder.mContext.setVisibility(View.GONE);
            mViewHolder.mImageView.setAspectRatio(0.75f);
            if (beans.getContext() != null && !beans.getContext().equals("null"))
                mViewHolder.mImageView.setImageURI(Uri.parse(beans.getContext()));

            //查看大图
            mViewHolder.mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < imageInfo.size(); i++) {
                        if(imageInfo.get(i).getThumbnailUrl().equals(beans.getContext())){
                            position_c=i;
                        }
                    }
                    Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
                    bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, position_c);
                    intent.putExtras(bundle);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(0, 0);
                }
            });

            mViewHolder.mTime.setText(beans.getTime());
            return view;
        } else {
            //他人
            final viewHolder_other mViewHolder;
            if (view == null) {
                view = mInflater.inflate(R.layout.chat_item_other, null);
                mViewHolder = new viewHolder_other();
                mViewHolder.mHead = (SimpleDraweeView) view.findViewById(R.id.chat_item_other_header);
                mViewHolder.mContext = (EmojiconTextView) view.findViewById(R.id.chat_item_other_context);
                mViewHolder.mTime = (TextView) view.findViewById(R.id.chat_item_other_sendTime);
                mViewHolder.mTopLayout = (RelativeLayout) view.findViewById(R.id.chat_item_other_topTimeLayout);
                mViewHolder.mImageView = (SimpleDraweeView) view.findViewById(R.id.chat_item_other_imageContent);
                mViewHolder.mContentLayout= (LinearLayout) view.findViewById(R.id.chat_item_other_contextLyout);
                mViewHolder.mNickName= (TextView) view.findViewById(R.id.chat_item_other_NickName);
                view.setTag(mViewHolder);
            } else {
                mViewHolder = (viewHolder_other) view.getTag();
            }
            final ChatContext_Beans beans = mList.get(position);
            if(type==0){
                mViewHolder.mNickName.setVisibility(View.GONE);
            }else {
                mViewHolder.mNickName.setVisibility(View.VISIBLE);
                mViewHolder.mNickName.setText(beans.getNickName());
            }
            if (position == 0) {
                //第一个显示
            } else {
                ChatContext_Beans beans_0 = mList.get(position - 1);
                //判断时间是否显示
                if (Time_Now.getLongTime(beans.getTime()) - Time_Now.getLongTime(beans_0.getTime()) <= 30000) {
                    //当时间间隔小于3分钟时不显示时间
                    mViewHolder.mTopLayout.setVisibility(View.GONE);
                }
            }
            //头像点击事件
            mViewHolder.mHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MyHomePager_Activity.class);
                    saveSharedPreferences.Save_PREFS(mContext, "otherId", beans.getUid());//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                    mContext.startActivity(intent);
                }
            });
            //Picasso图片加载框架
            if (!beans.getUserPicUrl().equals("null") && beans.getUserPicUrl() != null) {
                //Picasso.with(mContext).load(beans.getUserPicUrl()).into(mViewHolder.mHead);
                mViewHolder.mHead.setImageURI(Uri.parse(beans.getUserPicUrl()));
            } else {
                mViewHolder.mHead.setImageURI(Uri.parse(""));
            }

            mViewHolder.mContentLayout.setVisibility(View.VISIBLE);
            mViewHolder.mImageView.setVisibility(View.GONE);
            mViewHolder.mContext.setText(beans.getContext());

            mViewHolder.mTime.setText(beans.getTime());
            return view;
        }
    }

}
