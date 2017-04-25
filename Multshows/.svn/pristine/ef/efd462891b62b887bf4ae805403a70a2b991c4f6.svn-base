package com.multshows.Adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.ActivityDetails_Beans;
import com.multshows.Beans.WorkComment_Beans;
import com.multshows.R;
import com.multshows.Utils.Base64_Utils;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Emoji_Map;

import java.util.List;

/**
 * 描述：活动详情图文详情适配器
 * 作者：李萧萧
 * 时间：2016.10.9
 */
public class ActivityDetails_ImageText_Adapter extends BaseAdapter {
    List<ActivityDetails_Beans> mList;
    private Context mContext;
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类

    public ActivityDetails_ImageText_Adapter(Context mContext, List<ActivityDetails_Beans> mList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_activitydetalis_imagetext, null);
            viewHolder.mActivityDetails_image = (SimpleDraweeView) view.findViewById(R.id.ActivityDetails_image);
            viewHolder.mActivityDetails_Text = (TextView) view.findViewById(R.id.ActivityDetails_Text);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ActivityDetails_Beans beans = mList.get(i);
        Uri mUri = Uri.parse(beans.getImage());
        viewHolder.mActivityDetails_image.setImageURI(mUri);
        viewHolder.mActivityDetails_Text.setText(Emoji_Map.Emojichange((Activity) mContext, mEmoji_change.ChangeEmoji(beans.getDescribe())));
        viewHolder.mActivityDetails_Text.setVisibility(View.GONE);
        return view;
    }

    class ViewHolder {
        SimpleDraweeView mActivityDetails_image;//图片详情
        TextView mActivityDetails_Text;//文字详情
    }
}
