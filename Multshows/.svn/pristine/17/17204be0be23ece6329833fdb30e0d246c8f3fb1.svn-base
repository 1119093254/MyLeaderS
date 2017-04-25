package com.multshows.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Activity.TaskDetails_Activity;
import com.multshows.Beans.Task;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.HintText_Dialog;
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
public class Task_Nolist_Adapter extends BaseAdapter {
    List<Task> mList;
    private Context mContext;
    MyApplication myApplication;
    Dialog mDialog;
    Dialog_Hint mDialogHint;
    public Task_Nolist_Adapter(Context mContext, List<Task> mList) {
        this.mContext = mContext;
        this.mList = mList;
        myApplication= (MyApplication) mContext.getApplicationContext();
        mDialog=new HintText_Dialog(mContext,R.style.MyDialog);
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapetr_tasklist_item, null);
            viewHolder.mTask_image= (SimpleDraweeView) view.findViewById(R.id.task_image);
            viewHolder.mTask_title = (TextView) view.findViewById(R.id.task_title);
            viewHolder.Task_ListItem = (RelativeLayout) view.findViewById(R.id.Task_ListItem);
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

      //  viewHolder.mTask_Time.setVisibility(View.GONE);
        viewHolder.mTask_Time.setText(beans.getRecordTimeStr());
        viewHolder.BabyName.setText(beans.getExecutor().getNickName());

        viewHolder.mCompleteButton.setVisibility(View.VISIBLE);
        viewHolder.mCompleteButton.setText("删除");
        //删除待完成的任务
        viewHolder.mCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialogHint=new Dialog_Hint(mContext, 0, "确定删除任务?", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.hint_dialog_ok:
                                mDialogHint.dismiss();
                                /**
                                 * 删除任务
                                 */
                                OkHttpUtils.get().url(myApplication.getUrl()+"/Task/DeleteTask")
                                        .addParams("taskId",beans.getId())
                                        .build()
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onError(Call call, Exception e, int id) {
                                                //删除任务失败
                                                CLog.e("testing","删除任务失败");
                                                mDialog.show();
                                                HintText_Dialog mHintTextDialog=new HintText_Dialog(mContext,
                                                        "网络异常","fail");
                                                new Handler().postDelayed(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        mDialog.dismiss();
                                                    }
                                                },1000);
                                            }

                                            @Override
                                            public void onResponse(String response, int id) {
                                                CLog.e("testing","删除任务"+response);
                                                try {
                                                    if(Json_Utils.getCode(response)==0){
                                                        mList.remove(i);
                                                        notifyDataSetChanged();
                                                    }else {
                                                        mDialog.show();
                                                        HintText_Dialog mHintTextDialog=new HintText_Dialog(mContext,
                                                                Json_Utils.getMessage(response),"fail");
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                mDialog.dismiss();
                                                            }
                                                        },1000);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        });
                                break;
                        }
                    }
                });
                mDialogHint.show();


            }
        });
            viewHolder.mCompletenoButton.setVisibility(View.GONE);
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
        viewHolder.Task_ListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //系统任务发布
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
        RelativeLayout Task_ListItem;//任务布局
        TextView mTask_title;//任务标题
        TextView mTask_reward;//任务奖励
        TextView mTask_Time;//完成时间
        Button mCompleteButton;//确认完成按钮
        Button mCompletenoButton;//待完成按钮(不可点击)
        TextView BabyName;//执行宝宝的名字
    }


}
