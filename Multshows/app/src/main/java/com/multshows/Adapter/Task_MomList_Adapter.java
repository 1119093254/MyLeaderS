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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Activity.My_BabyInformation_Activity;
import com.multshows.Activity.Release_Task_Activity;
import com.multshows.Activity.TaskDetails_Activity;
import com.multshows.Beans.ActivityDetails_Beans;
import com.multshows.Beans.TaskClassTag;
import com.multshows.Beans.Task_List_Bean;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.Dialog_Hint;

import java.util.List;

/**
 * 描述：妈妈端
 * 作者：李萧萧
 * 时间：2016.10.9
 */
public class Task_MomList_Adapter extends BaseAdapter {
    List<Task_List_Bean> mList;
    private Context mContext;
    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();
    String vip="";
    Dialog_Hint hintText_dialog2;
    public Task_MomList_Adapter(Context mContext, List<Task_List_Bean> mList) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_taskitem, null);
            viewHolder.task_image = (SimpleDraweeView) view.findViewById(R.id.task_image);
            viewHolder.taskTypeText = (TextView) view.findViewById(R.id.taskTypeText);
            viewHolder.task_Text = (TextView) view.findViewById(R.id.task_Text);
            viewHolder.releaseTaskButton = (Button) view.findViewById(R.id.releaseTaskButton);
            viewHolder.taskTypeLayout = (RelativeLayout) view.findViewById(R.id.taskTypeLayout);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        final Task_List_Bean beans=mList.get(i);
        final TaskClassTag tag=beans.getClassTag();
        CLog.e("sssssss123",SubString_Utils.getImageUrl(tag.getIcon()));
        if(tag.getIcon()==null||"null".equals(tag.getIcon()) || "".equals(tag.getIcon())) {
            viewHolder.task_image.setImageURI(Uri.parse(""));
        }else {
            Uri mUri = Uri.parse(SubString_Utils.getImageUrl(tag.getIcon()));
            viewHolder.task_image.setImageURI(mUri);
        }
        viewHolder.task_Text.setText(tag.getName());
        if(beans.getFlag()==1){
            viewHolder.taskTypeLayout.setVisibility(View.GONE);
        }else {
            viewHolder.taskTypeLayout.setVisibility(View.VISIBLE);
            viewHolder.taskTypeText.setText(beans.getClassName());
        }
        viewHolder.releaseTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                vip=mSharedPreferences.Get_PREFS(mContext,"Vip");
                if("yes".equals(vip)) {
                    hintText_dialog2 = new Dialog_Hint(mContext, 0,
                            "您还没有添加宝宝,是否前往添加?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            hintText_dialog2.dismiss();
                            Intent intent = new Intent(mContext, My_BabyInformation_Activity.class);
                            intent.putExtra("Type", 4);
                            mContext.startActivity(intent);
                        }
                    });
                    hintText_dialog2.show();
                }else {
                    if ("自定义".equals(beans.getClassName())) {
                        //自定义发布
                        Intent mIntent = new Intent(mContext, Release_Task_Activity.class);
                        mIntent.putExtra("TaskClassTag", tag);
                        mContext.startActivity(mIntent);
                    } else {
                        //系统任务发布
                        Intent mIntent = new Intent(mContext, TaskDetails_Activity.class);
                        mIntent.putExtra("TaskClassTag", tag.getId());
                        mContext.startActivity(mIntent);
                    }
                }
            }
        });


        return view;
    }
    class ViewHolder{
        SimpleDraweeView task_image;//任务图片
        TextView taskTypeText;//任务类型
        TextView task_Text;//任务名称
        Button releaseTaskButton;//发布按钮
        RelativeLayout taskTypeLayout;//任务类型布局，用来控制其显示
    }
}
