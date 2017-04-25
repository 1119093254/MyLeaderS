package com.multshows.Adapter;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.TextView;


import com.multshows.Beans.ShowsCommentUser;
import com.multshows.R;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Emoji_Map;


import java.util.List;

/**
 * 描述：所有作品评论列表适配器
 * 作者：李萧萧
 * 时间：2016.9.23
 */
public class WorkAll_Comment_Adapter extends BaseAdapter {
    List<ShowsCommentUser> mList;
    private Context mContext;
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类
    LayoutInflater inflater;
    private SpannableString mContentContent;//评论显示内容
    public WorkAll_Comment_Adapter(Context mContext, List<ShowsCommentUser> mList) {
        this.mContext = mContext;
        this.mList = mList;
        inflater = LayoutInflater.from(mContext);
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
        ViewHolder viewHolder ;
        if (view == null) {
            viewHolder = new ViewHolder();
            view =  inflater.inflate(R.layout.adapter_comment_item, null);
            viewHolder.mContent = (TextView) view.findViewById(R.id.content);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        ShowsCommentUser beans=mList.get(i);
        String mString =mEmoji_change.ChangeEmoji(beans.getContent());
        if(!beans.getReplyId().equals("0")){
            mContentContent = new SpannableString(beans.getUser().getNickName()+" 回复 "+beans.getTargetUserNickName()+"："+mString);
            mContentContent.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text)), 0,
                    beans.getUser().getNickName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            mContentContent.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text)), beans.getUser().getNickName().length() + 4,
                    beans.getUser().getNickName().length() + beans.getTargetUserNickName().length() + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            viewHolder.mContent.setText(Emoji_Map.Emojichange((Activity) mContext, mContentContent));
        }else {
            mContentContent = new SpannableString(beans.getUser().getNickName()+"："+mString);
            if( beans.getUser().getNickName()!=null){
                mContentContent.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.text)), 0,
                        beans.getUser().getNickName().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            viewHolder.mContent.setText(Emoji_Map.Emojichange((Activity) mContext, mContentContent));
        //    viewHolder.mContent.setText(Emoji_Map.Emojichange((Activity) mContext, mEmoji_change.ChangeEmoji(beans.getUser().getNickName()+":"+mString)));
        }

        return view;
    }
    class ViewHolder{
        TextView mContent;//评论内容
    }
}
