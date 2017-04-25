package com.multshows.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Activity.TaskDetails_Activity;
import com.multshows.Beans.Idle_ProduceComment_Beans;
import com.multshows.Beans.Task;
import com.multshows.Beans.TaskList_Beans;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.List;

import okhttp3.Call;

/**
 * 描述：任务完成情况列表适配器
 * 作者：李萧萧
 * 时间：2016.10.8
 */
public class Task_list_Adapter extends BaseAdapter {
    List<Task> mList;//数据集合
    private Context mContext;//上下文
    MyApplication mMyApplication;

    public Task_list_Adapter(Context mContext, List<Task> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mMyApplication= (MyApplication) mContext.getApplicationContext();
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
            view = LayoutInflater.from(mContext).inflate(R.layout.adapetr_oktasklist_item, null);
            viewHolder.mTask_image= (SimpleDraweeView) view.findViewById(R.id.task_image);
            viewHolder.mTask_title = (TextView) view.findViewById(R.id.task_title);
            viewHolder.TaskListLayout = (RelativeLayout) view.findViewById(R.id.TaskListLayout);
            viewHolder.BabyName = (TextView) view.findViewById(R.id.BabyName);
            viewHolder.mTask_reward = (TextView) view.findViewById(R.id.task_reward);
            viewHolder.mTask_Time = (TextView) view.findViewById(R.id.task_Time);
            viewHolder.mCompleteButton= (Button) view.findViewById(R.id.completeButton);
            viewHolder.mCompletenoButton= (Button) view.findViewById(R.id.completenoButton);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Task beans=mList.get(i);
        viewHolder.mTask_title.setText(beans.getTitle());
        viewHolder.mTask_reward.setText(beans.getReward()+"");
       /* if(beans.getState()==2){
            viewHolder.mTask_Time.setVisibility(View.GONE);
        }else {*/
            viewHolder.mTask_Time.setVisibility(View.VISIBLE);
            viewHolder.mTask_Time.setText(Time_Now.getStringTime(beans.getRecordTime()));
        //}
        //判断任务状态
        if(beans.getState()==2){
            viewHolder.mCompleteButton.setVisibility(View.VISIBLE);
            viewHolder.mCompletenoButton.setVisibility(View.GONE);
        }else {
            viewHolder.mCompleteButton.setVisibility(View.GONE);
            viewHolder.mCompletenoButton.setVisibility(View.VISIBLE);
        }
        if(beans.getImgs()==null || "null".equals(beans.getImgs()) || "".equals(beans.getImgs())) {
            if (beans.getIcon()== null || "null".equals(beans.getIcon()) || "".equals(beans.getIcon())) {
                Uri uri = Uri.parse("");
                viewHolder.mTask_image.setImageURI(uri);
            }{
                Uri uri = Uri.parse(SubString_Utils.getImageUrl4(beans.getIcon()));
                viewHolder.mTask_image.setImageURI(uri);
            }
        }else {
            Uri uri = Uri.parse(SubString_Utils.getImageUrl4(beans.getImgs()));
            viewHolder.mTask_image.setImageURI(uri);
        }


        viewHolder.BabyName.setText(beans.getExecutor().getNickName());
        final ViewHolder finalViewHolder = viewHolder;
        //妈妈端 确认任务完成 操作
        viewHolder.mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setChangeState(beans.getId(), finalViewHolder.mCompletenoButton, finalViewHolder.mCompleteButton);
            }
        });
        //点击进入任务详情介绍
        viewHolder.TaskListLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 Intent mIntent = new Intent(mContext, TaskDetails_Activity.class);
                mIntent.putExtra("TaskClassTag", beans.getId());
                mIntent.putExtra("Tag", 1);
                mContext.startActivity(mIntent);
            }
        });
        return view;
    }
    class ViewHolder{
        SimpleDraweeView mTask_image;
        RelativeLayout TaskListLayout;
        TextView mTask_title;//任务标题
        TextView mTask_reward;//任务奖励
        TextView mTask_Time;//完成时间
        Button mCompleteButton;//确认完成按钮
        Button mCompletenoButton;//已确认按钮
        TextView BabyName;//宝宝名字
    }

    /**
     * 妈妈端 确认任务完成 操作
     * @param taskId
     * @param button
     * @param button_ok
     */
    private void setChangeState(String taskId, final Button button,final Button button_ok){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/Task/ChangeTaskState")
                .addParams("taskId",taskId)
                .addParams("state","3")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","待确认——已确认失败"+e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","待确认——已确认"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                button_ok.setEnabled(false);
                                button_ok.setBackgroundResource(R.drawable.resend_verification_code);
                                button_ok.setText("已确认");
                                button_ok.setTextColor(mContext.getResources().getColor(R.color.text_white_c));
                                notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
