package com.multshows.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Activity.MyHomePager_Activity;
import com.multshows.Beans.ShowsComment;
import com.multshows.Beans.ShowsCommentUser;
import com.multshows.Beans.UserHead;
import com.multshows.R;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Emoji_Map;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;

import java.util.List;

/**
 * 描述:更多评论适配器列表
 * 12/2
 */
public class MoreCommentList_Adapter extends BaseAdapter {
    private SpannableString mContentContent;//评论显示内容
    List<ShowsComment> mList;//送花人姓名
    Context mContext;//上下文
    String mUserId;//发布作品人的id
    LayoutInflater inflater;
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();//偏好设置

    public MoreCommentList_Adapter(List<ShowsComment> list, Context context, String UserId) {
        mContext = context;
        mList = list;
        mUserId = UserId;
        inflater = LayoutInflater.from(context);
    }

    class ViewHolder {
        SimpleDraweeView Person_pic;//列表 头像
        TextView Person_name;//列表 昵称
        TextView commentTime;//列表 昵称
        TextView commentContent;//列表 昵称
        View LineMoreComment;//评论item分割线
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final ShowsComment beans = mList.get(position);
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_morecomment_item, null, false);
            viewHolder.Person_pic = (SimpleDraweeView) convertView.findViewById(R.id.commentuserpic);
            viewHolder.Person_name = (TextView) convertView.findViewById(R.id.commentName);
            viewHolder.commentTime = (TextView) convertView.findViewById(R.id.commentTime);
            viewHolder.commentContent = (TextView) convertView.findViewById(R.id.commentContent);
            viewHolder.LineMoreComment = convertView.findViewById(R.id.LineMoreComment);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Uri mUri = Uri.parse(SubString_Utils.getImageUrl(beans.getUser().getPortrait()));
        viewHolder.Person_pic.setImageURI(mUri);
        viewHolder.Person_name.setText(beans.getUser().getNickName());
        viewHolder.commentTime.setText(beans.getRecordTimeStr());
        if(beans.getTargetUser().getUserId()!=null&&beans.getAuthor().getUserId()!=null)
        if (beans.getTargetUser().getUserId().equals(beans.getAuthor().getUserId())) {
            viewHolder.commentContent.setText(Emoji_Map.Emojichange((Activity) mContext, mEmoji_change.ChangeEmoji(beans.getContent())));
        } else {
            String mString =mEmoji_change.ChangeEmoji(beans.getContent());
            mContentContent = new SpannableString("回复：@" + beans.getTargetUser().getNickName() +" "+mString);
            mContentContent.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.green)), 3,
                    beans.getTargetUser().getNickName().length()+4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
          /*  mContentContent.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.green)), bean.getUserBean().getNickName().toString().length() + 6,
                    mContentContent.getUserBean().getNickName().toString().length() + bean.getUserBeanTwo().getNickName().length() + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);*/
            viewHolder.commentContent.setText(Emoji_Map.Emojichange((Activity) mContext, mContentContent));
        }

        viewHolder.Person_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(mContext, MyHomePager_Activity.class);
                saveSharedPreferences.Save_PREFS(mContext, "otherId", beans.getUser().getUserId());//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                mContext.startActivity(mIntent);
            }
        });
        if(position==mList.size()-1){
            viewHolder.LineMoreComment.setVisibility(View.INVISIBLE);
        }else viewHolder.LineMoreComment.setVisibility(View.VISIBLE);
        return convertView;
    }
}
