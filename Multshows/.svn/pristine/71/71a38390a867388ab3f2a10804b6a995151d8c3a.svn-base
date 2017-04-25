package com.multshows.Adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.Idle_ProduceComment_Beans;
import com.multshows.Beans.WorkComment_Beans;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.Base64_Utils;
import com.multshows.Utils.DateDeal;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Emoji_Map;
import com.multshows.Utils.Time_Now;

import java.util.List;

/**
 * 描述：闲置详情评论列表适配器
 * 作者：李萧萧
 * 时间：2016.10.8
 */
public class Idle_Comment_Adapter extends BaseAdapter {
    List<Idle_ProduceComment_Beans> mList;
    private Context mContext;
    Emoji_Change mEmoji_change=new Emoji_Change();//表情，匹配符替换类
    String userbaseId;
    public Idle_Comment_Adapter(Context mContext, List<Idle_ProduceComment_Beans> mList,String userbaseId) {
        this.mContext = mContext;
        this.mList = mList;
        this.userbaseId=userbaseId;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_idleproducecomment, null);
            viewHolder.mUsername = (TextView) view.findViewById(R.id.Idlecomment_name);
            viewHolder.mSimpleDraweeView= (SimpleDraweeView) view.findViewById(R.id.idle_commentuserpic);
            viewHolder.mTime = (TextView) view.findViewById(R.id.Idlecomment_time);
            viewHolder.content = (TextView) view.findViewById(R.id.idle_commentContent);
            viewHolder.Interest_personLabel = (RelativeLayout) view.findViewById(R.id.Interest_personLabel);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Idle_ProduceComment_Beans beans=mList.get(i);
    //    if(beans.getUsername().equals(Login_Static.myUserAccount))
        viewHolder.mTime.setText(DateDeal.format(Time_Now.getStringTime(beans.getTime())));
        viewHolder.mUsername.setText(beans.getReplyusername());
        //beans.getReplyusername().equals("")if( beans.getUser().getNickName()!=null){
        if("". equals(beans.getReplyusername())){
            viewHolder.content.setText(Emoji_Map.Emojichange((Activity) mContext, mEmoji_change.ChangeEmoji(beans.getContent())));
        }else {
            viewHolder.content.setText(Emoji_Map.Emojichange((Activity) mContext, mEmoji_change.ChangeEmoji("回复@"+beans.getUsername()+": "+beans.getContent())));
        }
        if(userbaseId.equals(beans.getReplyUserId())){
            viewHolder.Interest_personLabel.setVisibility(View.INVISIBLE);
        }else {
            viewHolder.Interest_personLabel.setVisibility(View.INVISIBLE);
        }
        Uri uri=Uri.parse(beans.getUsetPic());
        viewHolder.mSimpleDraweeView.setImageURI(uri);
        return view;
    }
    class ViewHolder{
        SimpleDraweeView mSimpleDraweeView;
        TextView mUsername;//评论人姓名
        TextView mTime;//评论时间
        RelativeLayout Interest_personLabel;//评论时间
        TextView content;//评论内容
    }
}
