package com.multshows.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import com.multshows.Activity.Activity_Show_Activity;
import com.multshows.Beans.MyHome_Activity_Beans;
import com.multshows.R;
import com.multshows.Utils.Base64_Utils;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Emoji_Map;
import com.multshows.Views.NineGridview.ImageInfo;
import com.multshows.Views.NineGridview.preview.ImagePreviewActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：我的主页 任务列表适配器
 * 作者：李萧萧
 * 时间：2016.9.27
 */
public class MyHome_Activity_adapter extends BaseAdapter {
    List<MyHome_Activity_Beans> mList;
    private Context mContext;
    ViewHolder viewHolder;
    Emoji_Change mEmoji_change=new Emoji_Change();//表情，匹配符替换类
    public MyHome_Activity_adapter(Context mContext, List<MyHome_Activity_Beans> mList) {
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
    public int getItemViewType(int position) {
        return mList.get(position).getFlag();
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(getItemViewType(i)==1){
            if (view == null) {
                viewHolder = new ViewHolder();
                view = LayoutInflater.from(mContext).inflate(R.layout.adapter_activity_item, null);
                viewHolder.activityImage = (SimpleDraweeView) view.findViewById(R.id.activityImage);
                viewHolder.mActivityTitle = (TextView) view.findViewById(R.id.ActivityTitle);
                viewHolder.mlookNum = (TextView) view.findViewById(R.id.lookNum);
                viewHolder.mstarstopTime = (TextView) view.findViewById(R.id.starstopTime);
                viewHolder.mtime = (TextView) view.findViewById(R.id.time);
                viewHolder.mLayoutalpha= (RelativeLayout) view.findViewById(R.id.Layoutalpha);
                viewHolder.moverImageLayout= (RelativeLayout) view.findViewById(R.id.overImageLayout);
                viewHolder.cancleImageLayout= (RelativeLayout) view.findViewById(R.id.cancelImageLayout);
                view.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) view.getTag();
            }
            MyHome_Activity_Beans beans=mList.get(i);
            Uri uri = Uri.parse(beans.getActivityPic());
            viewHolder.activityImage.setImageURI(uri);
            viewHolder.mActivityTitle.setText(Emoji_Map.Emojichange((Activity) mContext, mEmoji_change.ChangeEmoji(beans.getTitle())));
            viewHolder.mlookNum.setText(beans.getLookNum()+"");
            viewHolder.mstarstopTime.setText("时间："+beans.getStarTime()+"至"+beans.getStopTime());
            viewHolder.mtime.setText(beans.getTime());
            final ArrayList<ImageInfo> imageInfo = new ArrayList<>();
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl(beans.getActivityPic());
            info.setBigImageUrl(beans.getActivityPic());
            imageInfo.add(info);
        /*viewHolder.activityImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //NineGridViewClickAdapter madapter=new NineGridViewClickAdapter(mContext,imageInfo);
                Intent intent = new Intent(mContext, ImagePreviewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
                bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, 0);
                intent.putExtras(bundle);
                mContext.startActivity(intent);
                ((Activity) mContext).overridePendingTransition(0, 0);
            }
        });*/
            if(beans.getState()==-1){
                viewHolder.mLayoutalpha.setVisibility(View.VISIBLE);
                viewHolder.cancleImageLayout.setVisibility(View.VISIBLE);
            }else if(beans.getState()==2){
                viewHolder.mLayoutalpha.setVisibility(View.VISIBLE);
                viewHolder.moverImageLayout.setVisibility(View.VISIBLE);
            }else {
                viewHolder.mLayoutalpha.setVisibility(View.GONE);
                viewHolder.moverImageLayout.setVisibility(View.GONE);
                viewHolder.cancleImageLayout.setVisibility(View.GONE);
            }
        }else {
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_activityitem, null);
            RelativeLayout searchLayout= (RelativeLayout) view.findViewById(R.id.lookmyActivity);
            searchLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent mIntent = new Intent(mContext, Activity_Show_Activity.class);
                    mContext.startActivity(mIntent);
                }
            });
        }

        return view;
    }
    class ViewHolder {
        SimpleDraweeView activityImage;//活动标题
        TextView mActivityTitle;//活动标题
        TextView mlookNum;//浏览量
        TextView mstarstopTime;//开始结束时间
        TextView mtime;//发布时间
        RelativeLayout mLayoutalpha;//半透明布局
        RelativeLayout moverImageLayout;//结束图片布局
        RelativeLayout cancleImageLayout;//结束图片布局
    }
}
