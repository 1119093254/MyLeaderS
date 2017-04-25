package com.multshows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.multshows.Beans.Task;
import com.multshows.R;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.NineGridview.ImageInfo;
import com.multshows.Views.NineGridview.NineGridView;
import com.multshows.Views.NineGridview.preview.NineGridViewClickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：我的主页 任务列表适配器
 * 作者：李萧萧
 * 时间：2016.9.27
 */
public class MyHome_Task_adapter extends BaseAdapter {
    List<Task> mList;//任务数据集合
    private Context mContext;
    ViewHolder viewHolder;//ViewHolder优化

    public MyHome_Task_adapter(Context mContext, List<Task> mList) {
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
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_task_item, null);
            viewHolder.mtask_month = (TextView) view.findViewById(R.id.task_month);
            viewHolder.mtask_day = (TextView) view.findViewById(R.id.task_day);
            viewHolder.mtaskTitle = (TextView) view.findViewById(R.id.taskTitle);
            viewHolder.mNineGridView = (NineGridView) view.findViewById(R.id.Task_nineGrid);
            viewHolder.mTask_reward = (TextView) view.findViewById(R.id.Task_reward);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        Task beans = mList.get(i);
        viewHolder.mtask_month.setText(Time_Now.getMessageTime(beans.getConfirmTime(), "month") + "月");
        viewHolder.mtask_day.setText(Time_Now.getMessageTime(beans.getConfirmTime(), "day"));
        viewHolder.mtaskTitle.setText("完成了" + beans.getTitle() + "任务");
        viewHolder.mTask_reward.setText("获得了任务奖励金额¥" + beans.getReward());
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();

        ImageInfo info = new ImageInfo();
        info.setThumbnailUrl(SubString_Utils.getImageUrl(beans.getIcon()));
        info.setBigImageUrl(SubString_Utils.getImageUrlclearly(beans.getIcon()));
        imageInfo.add(info);


        viewHolder.mNineGridView.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
        return view;
    }

    class ViewHolder {
        TextView mtask_month;//任务 月
        TextView mtask_day;//任务 日
        TextView mtaskTitle;//任务 标题
        TextView mTask_reward;//任务 奖励
        NineGridView mNineGridView;//任务图片
    }
}
