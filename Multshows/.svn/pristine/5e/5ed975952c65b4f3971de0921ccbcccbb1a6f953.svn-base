package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Beans.Task;
import com.multshows.Beans.TaskAdd;
import com.multshows.Beans.TaskClass;
import com.multshows.Beans.TaskClassTag;
import com.multshows.Beans.UserHead;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.Dialog_Reward;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 描述：任务详情列表
 * 作者：李萧萧
 * 时间：2016.10/8
 */
public class TaskDetails_Activity extends AppCompatActivity {

    ImageView mReturn;
    RelativeLayout mReturnLayout;//顶部布局
    RelativeLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    SimpleDraweeView mTaskImage;
    TextView mTitle;//标题
    TextView mContent;//内容
    TextView mNumber;//完成任务的所有宝宝数量
    RelativeLayout mAllCompleteTaskUser;//完成任务的所有宝宝头像
    RelativeLayout mAllTaskUserLayout;//完成任务的所有宝宝
    Button mPutTask;//发布任务
    public static TaskDetails_Activity instence = null;
    Intent intent;
    String tagIDs="";
    int tag;
    TaskClassTag mTaskClassTag;//任务内容对象
    Task mTask;//任务内容对象
    List<String> mImages = new ArrayList<>();//用户头像集合
    List<UserHead> mImages2 = new ArrayList<>();//用户头像集合
    Dialog_Reward mDialog_Reward;//支付
    Dialog dialog;
    Gson mGson = new Gson();
    MyApplication mMyApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details);
        MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(TaskDetails_Activity.this,R.color.app_topColor);
        instence=this;
        mMyApplication= (MyApplication) getApplication();
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.Taskdetails_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.TaskDetails_toplayout);
        mParentLayout= (RelativeLayout) findViewById(R.id.Taskdetails_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.Taskdetails_TopTitle);
        mTaskImage = (SimpleDraweeView) findViewById(R.id.Taskdetails_image);
        mTitle = (TextView) findViewById(R.id.Taskdetails_title);
        mPutTask = (Button) findViewById(R.id.put_ReleaseTask);
        mAllTaskUserLayout = (RelativeLayout) findViewById(R.id.AllTaskUserLayout);
        mAllCompleteTaskUser = (RelativeLayout) findViewById(R.id.AllCompleteTask_UserUserImage);
        mNumber = (TextView) findViewById(R.id.Taskdetails_number);
        mContent = (TextView) findViewById(R.id.Taskdetails_content);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(TaskDetails_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        intent = getIntent();
        tagIDs = intent.getStringExtra("TaskClassTag");
        tag = intent.getIntExtra("Tag",0);
        if(tag!=0){
            //详情界面跳转过来隐藏下方按钮
            mPutTask.setVisibility(View.GONE);
            getTaskTag_2(tagIDs);
        }else {
            getTaskTag(tagIDs);
        }

    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //发布任务
        mPutTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(TaskDetails_Activity.this, Release_Task_Activity.class);
                mIntent.putExtra("Type", 1);
                mIntent.putExtra("TaskClassTag", mTaskClassTag);
                startActivity(mIntent);
            }
        });
    }

    //dp 转变为px
    public int Dp2Px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //px转变为dp
    public int Px2Dp(float px) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    //发布任务
    private void sendTask(String babyId, String money) {
        TaskAdd taskAdd = new TaskAdd();
        taskAdd.setTagId(mTaskClassTag.getId());
        taskAdd.setClassId(mTaskClassTag.getClassId());
        taskAdd.setImgs(mTaskClassTag.getIcon());
        taskAdd.setType(1);
        taskAdd.setReward(Double.parseDouble(money));
        taskAdd.setTitle(mTaskClassTag.getTitle());
        taskAdd.setInfos(mTaskClassTag.getContent());
        taskAdd.setPublisherId(Login_Static.myUserID);
        taskAdd.setExecutorId(babyId);
        String data = mGson.toJson(taskAdd);
        CLog.e("testing", data);
        OKHttp.OkHttpPost("/Task/AddTask", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "发布任务失败：" + e.toString());
                HintText_Dialog mDialog = new HintText_Dialog(TaskDetails_Activity.this, "发布失败", "fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //得到返回值后，2秒后加载框消失
                        dialog.dismiss();
                    }
                }, Login_Static.hintTime);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "发布任务：" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        HintText_Dialog mDialog = new HintText_Dialog(TaskDetails_Activity.this, "发布成功", "success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //得到返回值后，2秒后加载框消失
                                finish();
                                dialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //任务详情
    private void getTaskTag(String tagID){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/Task/GetTaskClassTag")
                .addParams("tagid",tagID)//任务标签主键Id--加密
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","查询任务标签信息失败"+e.toString());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","查询任务标签信息"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                mTaskClassTag=mGson.fromJson(Json_Utils.getTemplate(response),TaskClassTag.class);
                                if (mTaskClassTag != null) {
                                    if (mTaskClassTag.getIcon() == null || "null".equals(mTaskClassTag.getIcon())) {
                                        mTaskImage.setImageURI(Uri.parse(""));
                                    } else {
                                        mTaskImage.setImageURI(Uri.parse(SubString_Utils.getImageUrlclearly(mTaskClassTag.getIcon())));
                                    }
                                    mTitle.setText(mTaskClassTag.getName());
                                    mNumber.setText(mTaskClassTag.getCount() + "");
                                    mContent.setText(mTaskClassTag.getContent());
                                    mImages = mTaskClassTag.getUserImgList();
                                    if (mImages != null) {
                                        for (int j = 0; j < mImages.size(); j++) {
                                            SimpleDraweeView imageView = new SimpleDraweeView(TaskDetails_Activity.this);  //创建imageview
                                            GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
                                                    //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
                                                    .setRoundingParams(RoundingParams.asCircle())
                                                   /* //设置淡入淡出动画持续时间(单位：毫秒ms)
                                                    .setFadeDuration(5000)*/
                                                    //构建
                                                    .build();
                                            //设置Hierarchy
                                            Uri urij = Uri.parse(SubString_Utils.getImageUrl(mImages.get(j)));
                                            imageView.setHierarchy(hierarchy);
                                            imageView.setImageURI(urij);
                                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(Dp2Px(25), Dp2Px(25));
                                            layoutParams.leftMargin = 20 + 45 * j;
                                            mAllCompleteTaskUser.addView(imageView, layoutParams);
                                        }
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }
    //任务详情
    private void getTaskTag_2(String tagID){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/Task/GetTaskInfo")
                .addParams("taskId",tagID)//任务标签主键Id--加密
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","查询任务标签信息失败"+e.toString());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","查询任务标签信息"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                mTask=mGson.fromJson(Json_Utils.getTemplate(response),Task.class);
                                if (mTask != null) {
                                    if(mTask.getImgs() == null || "null".equals(mTask.getImgs()) || "".equals(mTask.getImgs())){
                                        if (mTask.getIcon() == null || "null".equals(mTask.getIcon()) || "".equals(mTask.getIcon())) {
                                            mTaskImage.setImageURI(Uri.parse(""));
                                        } else {
                                            mTaskImage.setImageURI(Uri.parse(SubString_Utils.getImageUrlclearly(mTask.getIcon())));
                                        }
                                    }else {
                                        mTaskImage.setImageURI(Uri.parse(SubString_Utils.getImageUrlclearly(mTask.getImgs())));
                                    }
                                    mTitle.setText(mTask.getTitle());
                                    mNumber.setText(mTask.getCompleteCount() + "");
                                    mContent.setText(mTask.getInfos());
                                    mImages2 = mTask.getCompleteTaskUser();
                                    if (mImages2 != null) {
                                        for (int j = 0; j < mImages2.size(); j++) {
                                            SimpleDraweeView imageView = new SimpleDraweeView(TaskDetails_Activity.this);  //创建imageview
                                            GenericDraweeHierarchy hierarchy = GenericDraweeHierarchyBuilder.newInstance(getResources())
                                                    //设置圆形圆角参数；RoundingParams.asCircle()是将图像设置成圆形
                                                    .setRoundingParams(RoundingParams.asCircle())
                                                   /* //设置淡入淡出动画持续时间(单位：毫秒ms)
                                                    .setFadeDuration(5000)*/
                                                    //构建
                                                    .build();
                                            //设置Hierarchy
                                            Uri urij = Uri.parse(SubString_Utils.getImageUrl(mImages2.get(j).getPortrait()));
                                            imageView.setHierarchy(hierarchy);
                                            imageView.setImageURI(urij);
                                            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(Dp2Px(25), Dp2Px(25));
                                            layoutParams.leftMargin = 20 + 45 * j;
                                            mAllCompleteTaskUser.addView(imageView, layoutParams);
                                        }
                                    }
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


}
