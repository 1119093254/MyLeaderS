package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.Idle_ProduceComment_Beans;
import com.multshows.Beans.TaskList_Beans;
import com.multshows.Beans.Task_distributionList_Beans;
import com.multshows.R;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;

import java.util.List;

/**
 * 描述：任务完成情况列表适配器
 * 作者：李萧萧
 * 时间：2016.10.8
 */
public class Task_BabyList_Adapter extends BaseAdapter {
    List<Task_distributionList_Beans> mList;
    private Context mContext;

    public Task_BabyList_Adapter(Context mContext, List<Task_distributionList_Beans> mList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_taskbaby_item, null);
            viewHolder.mtask_Babypic= (SimpleDraweeView) view.findViewById(R.id.task_Babypic);
            viewHolder.mbabyname = (TextView) view.findViewById(R.id.babyname);
            viewHolder.mbabyPhone = (TextView) view.findViewById(R.id.babyPhone);
            viewHolder.mbabyChioce = (ImageView) view.findViewById(R.id.babyChioce);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Task_distributionList_Beans beans=mList.get(i);
        if("null".equals(beans.getUsetPic()) || beans.getUsetPic()==null){
            Uri uri=Uri.parse("");
            viewHolder.mtask_Babypic.setImageURI(uri);
        }else {
            Uri uri=Uri.parse(SubString_Utils.getImageUrl(beans.getUsetPic()));
            viewHolder.mtask_Babypic.setImageURI(uri);
        }
        viewHolder.mbabyname.setText(beans.getUsername());
        viewHolder.mbabyPhone.setText(Time_Now.getYearTime(beans.getBirthday()));
        if(beans.getChioce()){
            viewHolder.mbabyChioce.setVisibility(View.VISIBLE);
        }else  viewHolder.mbabyChioce.setVisibility(View.INVISIBLE);
        return view;
    }
    class ViewHolder{
        SimpleDraweeView mtask_Babypic;//宝宝头像
        TextView mbabyname;//宝宝昵称
        TextView mbabyPhone;//宝宝账号
        ImageView mbabyChioce;//是否选中该宝宝

    }
}
