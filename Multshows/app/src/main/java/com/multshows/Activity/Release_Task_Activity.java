package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.Task_BabyList_Adapter;
import com.multshows.Beans.ImageInfo;
import com.multshows.Beans.TaskAdd;
import com.multshows.Beans.TaskClassTag;
import com.multshows.Beans.Task_distributionList_Beans;
import com.multshows.Beans.UserBase;
import com.multshows.Beans.UserTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.NetUtil;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.Photo_Bimp;
import com.multshows.Utils.SavePicture_toLocal_Utils;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.Dialog_Reward;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyPicPopupWindow;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 描述：发布作品页
 * 作者：李萧萧
 * 时间：2016.10/3
 */
public class Release_Task_Activity extends AppCompatActivity {
    public static final int REQUEST_CODE = 1;
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局\
    TextView mTopTitle;//大标题
    ImageView mDelete;//返回
    ImageView mTaskpaly;//播放图片
    TextView mSend;//发布
    ListView mBabyList;//宝宝列表
    EditText mTitle;//任务标题
    EditText mContent;//任务内容
    RelativeLayout mCenter;//
    RelativeLayout mBottom;//
    //MyGridView mGridView;//上传图片
    ImageView mAddIcon;//添加任务图标
    ImageView mIcon;//任务图标
    Uri photoUri;
    String name;
    String token;//七牛云token
    int IsVideo = 2;//是否是视频  1 视频 2图片
    String VideoName;//上传七牛云的视频名称
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    //适配器
    Task_BabyList_Adapter mBabyAdapter;//宝宝列表适配器
    List<Task_distributionList_Beans> mList = new ArrayList<>();//宝宝列表bean
    Dialog dialog;
    Dialog_Reward mDialog_Reward;
    Dialog mDialog;//消息提示框
    String babyId = "";//选中的孩子id
    Intent intent;
    int flags = 0;// 1为系统任务 0为自定义任务
    TaskClassTag mTaskClassTag;//任务内容对象
    MyPicPopupWindow mPopWindow;
    Dialog_Hint mDialogHint;
    Gson mGson = new Gson();
    MyApplication mApplication = new MyApplication();
    Emoji_Change mEmojiChange = new Emoji_Change();
    MyApplication myApplication ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_activity);
        myApplication= (MyApplication) getApplication();
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.Release_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Release_ReturnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Release_ParrentLayout);
        mTopTitle= (TextView) findViewById(R.id.Release_TopTitle);
        mTaskpaly = (ImageView) findViewById(R.id.Taskpaly);
        mDelete = (ImageView) findViewById(R.id.mDelete);
        mBabyList = (ListView) findViewById(R.id.Release_babyList);
        mTitle = (EditText) findViewById(R.id.Release_TaskTitle);
        mContent = (EditText) findViewById(R.id.Release_TaskContent);
        //mGridView= (MyGridView) findViewById(R.id.Release_GridView);
        mAddIcon = (ImageView) findViewById(R.id.Release_addIcon);
        mIcon = (ImageView) findViewById(R.id.Release_icon);
        mSend = (TextView) findViewById(R.id.Release_send);
        mCenter = (RelativeLayout) findViewById(R.id.Release_TaskTitle_center);
        mBottom = (RelativeLayout) findViewById(R.id.Release_TaskTitle_bottom);
        mDialog = new HintText_Dialog(Release_Task_Activity.this, R.style.MyDialog);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Release_Task_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle,mSend);
    }
    //初始化数据
    private void initData() {
        MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(Release_Task_Activity.this, R.color.green);
        intent = getIntent();
        flags = intent.getIntExtra("Type", 0);
        mTaskClassTag = (TaskClassTag) intent.getSerializableExtra("TaskClassTag");
        if (flags == 1) {
            mCenter.setVisibility(View.GONE);
            mContent.setVisibility(View.GONE);
        }

        mBabyAdapter = new Task_BabyList_Adapter(Release_Task_Activity.this, mList);
        mBabyList.setAdapter(mBabyAdapter);
        getBabyList(0);
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回

        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo_Bimp.albumSelectBitmap.clear();
                Photo_Bimp.taskSelectBitmap.clear();
                Photo_Bimp.tempSelectBitmap.clear();
                finish();
            }
        });
        mBabyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (mList.get(position).getChioce()) {
                    mList.get(position).setChioce(false);
                } else {
                    mList.get(position).setChioce(true);
                }
                mBabyAdapter.notifyDataSetChanged();
            }
        });
        //去除显示图片
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Photo_Bimp.taskSelectBitmap.clear();
                Photo_Bimp.tempSelectBitmap.clear();
                mDelete.setVisibility(View.GONE);
                mIcon.setVisibility(View.GONE);
                mTaskpaly.setVisibility(View.GONE);
            }
        });
        //选择图片
        mIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Release_Task_Activity.this,
                        Photo_Preview_Activity.class);
                intent.putExtra("photoPosition", 1);
                intent.putExtra("ID", 0);
                startActivity(intent);
            }
        });
        //发布
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean flag=true;
                if (flags != 1) {
                 /*   mCenter.setVisibility(View.GONE);
                    mContent.setVisibility(View.GONE);*/
                    if(mTitle.getText().toString().length()>6){
                        mDialog.show();
                        flag=false;
                        showError("标题不能超过6个字",0);
                    }
                    if(mTitle.getText().toString().equals("")){
                        mDialog.show();
                        flag=false;
                        showError("标题不能为空",0);
                    }
                    if(mContent.getText().toString().equals("")){
                        mDialog.show();
                        flag=false;
                        showError("内容不能为空",0);
                    }
                    if(mContent.getText().toString().length()>140){
                        mDialog.show();
                        flag=false;
                        showError("标题不能超过140个字",0);
                    }
                    String[] res = mTitle.getText().toString().split("\n");
                    String[] res2 = mTitle.getText().toString().split(" ");
                    String[] res1 = mContent.getText().toString().split("\n");
                    String[] res3 = mContent.getText().toString().split(" ");
                    CLog.e("res",res.length+"");
                    CLog.e("res1",res1.length+"");
                    CLog.e("res2",res2.length+"");
                    CLog.e("res3",res3.length+"");
                    if((res.length>3&&res2.length>=5)||(res1.length>4&&res3.length>5)){
                        mDialog.show();
                        showError("输入文字内容不符合要求",0);
                        flag = false;
                        mContent.setText("");
                        mTitle.setText("");
                    }
                }
                if(flag){
                    mSend.setEnabled(false);
                    babyId = "";
                    List<String> mList_new = new ArrayList<>();
                    for (int i = 0; i < mList.size(); i++) {
                        if (mList.get(i).getChioce()) {
                            mList_new.add(mList.get(i).getUserId());
                        }
                    }
                    for (int n = 0; n < mList_new.size(); n++) {
                        if (n == 0) {
                            babyId = mList_new.get(n);
                        } else {
                            babyId = babyId + "," + mList_new.get(n);
                        }
                    }
                    if (!"".equals(babyId)) {
                        if (flags == 0) {
                            //自定义
                            putTask();
                        } else {
                            isRepetition();
                        }
                    } else {
                        dialog = new HintText_Dialog(Release_Task_Activity.this, R.style.MyDialog);
                        dialog.show();
                        HintText_Dialog mDialog1 = new HintText_Dialog(Release_Task_Activity.this, "请选择孩子", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //得到返回值后，2秒后加载框消失
                                dialog.dismiss();
                                mSend.setEnabled(true);
                            }
                        }, 2000);
                    }
                }
            }
        });
        //添加任务图标
        mAddIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //底部弹出框
                //实例化MyPicPopupWindow
                mPopWindow = new MyPicPopupWindow(Release_Task_Activity.this, itemsOnClick,
                        "相册", "视频", "", "");
                //设置弹出动画效果
                // mPopWindow.setAnimationStyle(R.style.PopupAnimation);
                //显示窗口  //设置layout在PopupWindow中显示的位置
                mPopWindow.showAtLocation(findViewById(R.id.Release_ParrentLayout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    //为弹出窗口实现监听类
    public View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.lookPhoto_dialog_Top:
                    //打开相册
                    Intent intent = new Intent(Release_Task_Activity.this, Photo_Album_Activity.class);
                    intent.putExtra("album_type", "putTask");
                    startActivityForResult(intent, REQUEST_CODE);
                    break;
                case R.id.lookPhoto_dialog_Center:
                    //打开视频
                    intent = new Intent(Release_Task_Activity.this, VideoRecording_Activity.class);
                    if (Photo_Bimp.tempSelectBitmap.size() != 0) {
                        mDialog.show();
                        showError("不能再次选择视频", 0);
                    } else {
                        startActivityForResult(intent, 200);
                    }
                    break;
                default:
                    break;
            }
            mPopWindow.dismiss();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (Photo_Bimp.taskSelectBitmap.size() != 0) {
            if(Photo_Bimp.taskSelectBitmap.get(0).isVideo()){
                mTaskpaly.setVisibility(View.VISIBLE);
            }
            mIcon.setVisibility(View.VISIBLE);
            mDelete.setVisibility(View.VISIBLE);
            mIcon.setImageBitmap(Photo_Bimp.taskSelectBitmap.get(0).getBitmap());
        } else if (Photo_Bimp.albumSelectBitmap.size() != 0) {
            if(Photo_Bimp.albumSelectBitmap.get(0).isVideo()){
                mTaskpaly.setVisibility(View.VISIBLE);
            }
            mDelete.setVisibility(View.VISIBLE);
            mIcon.setVisibility(View.VISIBLE);
            mIcon.setImageBitmap(Photo_Bimp.albumSelectBitmap.get(0).getBitmap());
        } else {
            mIcon.setVisibility(View.GONE);
            mDelete.setVisibility(View.GONE);
            mTaskpaly.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Photo_Bimp.taskSelectBitmap.clear();
        Photo_Bimp.tempSelectBitmap.clear();
        Photo_Bimp.albumSelectBitmap.clear();
    }

    //获取我的宝贝列表
    private void getBabyList(final int pageIndex) {
        UserTerm userTerm = new UserTerm();
        userTerm.setUserId(Login_Static.myUserID);
        userTerm.setPageIndex(pageIndex);
        userTerm.setPageSize(0);
        String data = mGson.toJson(userTerm);
        CLog.e("testing", "获取宝贝列表data：" + data);
        OKHttp.OkHttpPost("/User/GetUserList", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "获取宝贝列表失败：" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "获取宝贝列表：" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        mList.clear();
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            //Log.e("baby",array.toString());
                            UserBase baby = mGson.fromJson(array.toString(), UserBase.class);
                            boolean isChioce = false;
                            isChioce = i == 0;
                            Task_distributionList_Beans bean = new Task_distributionList_Beans(baby.getUserId(),
                                    baby.getNickName(), baby.getMobile(), baby.getPortrait(), baby.getBirthday(), baby.getSex(), isChioce);
                            mList.add(bean);
                        }
                        mBabyAdapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //发布任务
    private void sendTask(String babyId, String path, String text, int flag) {
        TaskAdd taskAdd = new TaskAdd();
        taskAdd.setTagId(mTaskClassTag.getId());
        taskAdd.setClassId(mTaskClassTag.getClassId());
        taskAdd.setAtchType(flag);//附件类型 1图片 2视频
        if (flag == 2) {
            taskAdd.setVideoName(name);
        }
        taskAdd.setImgs(path);
        taskAdd.setType(1);
        taskAdd.setReward(Double.parseDouble(text));
        taskAdd.setTitle(mEmojiChange.EmojiChange(mTitle.getText().toString()));
        taskAdd.setInfos(mEmojiChange.EmojiChange(mContent.getText().toString()));
        taskAdd.setPublisherId(Login_Static.myUserID);
        taskAdd.setExecutorId(babyId);
        taskAdd.setIsDefined(1);
        String data = mGson.toJson(taskAdd);
        CLog.e("testing", data);
        OKHttp.OkHttpPost("/Task/AddTask", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "发布任务失败：" + e.toString());
                HintText_Dialog mDialog = new HintText_Dialog(Release_Task_Activity.this, "发布失败", "fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //得到返回值后，2秒后加载框消失
                        dialog.dismiss();
                    }
                }, 2000);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "发布任务：" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        HintText_Dialog mDialog = new HintText_Dialog(Release_Task_Activity.this, "发布成功", "success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //得到返回值后，2秒后加载框消失
                                dialog.dismiss();
                                mSave.Save_PREFS(Release_Task_Activity.this,"intent","task");
                                finish();

                            }
                        }, 2000);
                    } else {
                        HintText_Dialog mDialog = new HintText_Dialog(Release_Task_Activity.this,
                                Json_Utils.getMessage(response), "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //得到返回值后，2秒后加载框消失
                                dialog.dismiss();

                            }
                        }, 2000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //系统发布任务
    private void sendTask2(String babyId, String path, String text, int flag) {
        TaskAdd taskAdd = new TaskAdd();
        taskAdd.setTagId(mTaskClassTag.getId());
        taskAdd.setClassId(mTaskClassTag.getClassId());
        taskAdd.setAtchType(flag);//附件类型 1图片 2视频
        if (flag == 2) {
            taskAdd.setVideoName(name);
        }
        if(!path.equals("")){
            taskAdd.setImgs(path);
            CLog.e("Release_Task_Activity","上传的是有图的系统任务");
        }else {
            taskAdd.setImgs("");
            CLog.e("Release_Task_Activity","上传的是无图的系统任务");
        }

        taskAdd.setType(1);
        taskAdd.setReward(Double.parseDouble(text));
        taskAdd.setTitle(mTaskClassTag.getTitle());
        taskAdd.setInfos(mTaskClassTag.getContent());
        taskAdd.setPublisherId(Login_Static.myUserID);
        taskAdd.setExecutorId(babyId);
        taskAdd.setIsDefined(0);
        String data = mGson.toJson(taskAdd);
        CLog.e("Task", data);
        OKHttp.OkHttpPost("/Task/AddTask", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "发布任务失败：" + e.toString());
                HintText_Dialog mDialog = new HintText_Dialog(Release_Task_Activity.this, "发布失败", "fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //得到返回值后，2秒后加载框消失
                        dialog.dismiss();
                    }
                }, 2000);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "发布任务：" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                       dialog.show();
                        HintText_Dialog mDialog = new HintText_Dialog(Release_Task_Activity.this, "发布成功", "success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //得到返回值后，2秒后加载框消失
                                dialog.dismiss();
                                finish();
                                mSave.Save_PREFS(Release_Task_Activity.this,"intent","task");
                                TaskDetails_Activity.instence.finish();
                                Photo_Bimp.albumSelectBitmap.clear();
                            }
                        }, 2000);
                    } else {
                        HintText_Dialog mDialog = new HintText_Dialog(Release_Task_Activity.this,
                                Json_Utils.getMessage(response), "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //得到返回值后，2秒后加载框消失
                                dialog.dismiss();
                            }
                        }, 2000);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    //发布结果提示
    public void showError(String errorString, final int is) {
        String isString = null;
        if (is == 1) {
            isString = "success";
        }
        if (is == 0) {
            isString = "fail";
        }
        HintText_Dialog hint = new HintText_Dialog(Release_Task_Activity.this, errorString, isString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //得到返回值后，2秒后加载框消失
                if (is == 1) {
                    Photo_Bimp.albumSelectBitmap.clear();
                    Photo_Bimp.taskSelectBitmap.clear();
                    Photo_Bimp.tempSelectBitmap.clear();
                    finish();
                }
                mDialog.dismiss();
            }
        }, 1000);
    }

    public void getQiNiuToken(final String path, final String url) {
        OkHttpUtils.get().url(mApplication.getUrl() + "/service/GetQiniuUploadToken")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mSend.setEnabled(true);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        mSend.setEnabled(true);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                JSONObject jsonArray = new JSONObject(response);
                                token = jsonArray.getString("token");
                                //上传视频至七牛云
                                UploadManager uploadManager = new UploadManager();
                                uploadManager.put(path, url, token,
                                        new UpCompletionHandler() {
                                            @Override
                                            public void complete(String key, ResponseInfo info,
                                                                 JSONObject response) {
                                                //Log.e("ppp", "000"+imageName+"");
                                                if (info.statusCode == 200) {
                                                    CLog.e("path", "" + info.isOK());
                                                    CLog.e("path", "成功");
                                                    CLog.e("path", url);
                                                    VideoName = url;
                                                    IsVideo = 1;
                                                    /*babyId = "";
                                                    List<String> mList_new = new ArrayList<>();
                                                    for (int i = 0; i < mList.size(); i++) {
                                                        if (mList.get(i).getChioce()) {
                                                            mList_new.add(mList.get(i).getUserId());
                                                        }
                                                    }
                                                    for (int n = 0; n < mList_new.size(); n++) {
                                                        if (n == 0) {
                                                            babyId = mList_new.get(n);
                                                        } else {
                                                            babyId = babyId + "," + mList_new.get(n);
                                                        }
                                                    }
                                                    if (!"".equals(babyId)) {*/
                                                    mDialog_Reward = new Dialog_Reward(Release_Task_Activity.this,
                                                            Release_Task_Activity.this, 0, Login_Static.mAccount.getPortrait()
                                                            , Login_Static.mAccount.getNickName(), new Dialog_Reward.EditTextContent() {
                                                        @Override
                                                        public void getEditText(final String text) {
                                                            //通过验证
                                                            dialog = new HintText_Dialog(Release_Task_Activity.this, R.style.MyDialog);
                                                            dialog.show();
                                                            HintText_Dialog mDialog = new HintText_Dialog(Release_Task_Activity.this, getString(R.string.release), "");

                                                            try {
                                                                File file = SavePicture_toLocal_Utils.saveCacheFile(Photo_Bimp.albumSelectBitmap.get(0).getBitmap(),
                                                                        Login_Static.myUserID + System.currentTimeMillis() + ".png",
                                                                        Release_Task_Activity.this);
                                                                byte[] bytes = NetUtil.getByte(file);
                                                                int[] pic = new int[bytes.length];
                                                                //加128，去掉符号位
                                                                for (int a = 0; a < pic.length; a++) {
                                                                    pic[a] = bytes[a] & 0xff;
                                                                }


                                                                ImageInfo imageInfo = new ImageInfo();
                                                                imageInfo.setExt(".png");
                                                                imageInfo.setFileData(pic);
                                                                imageInfo.setTemp(false);
                                                                imageInfo.setMaxWidth(480);
                                                                imageInfo.setMaxHeight(800);
                                                                String data = mGson.toJson(imageInfo);
                                                                CLog.e("testing", "上传图片" + data);
                                                                //上传头像图片
                                                                OkHttpUtils.postString()
                                                                        .url(myApplication.getFileUrl())
                                                                        .mediaType(okhttp3.MediaType.parse("application/json"))
                                                                        .content(data)
                                                                        //.addParams("", request)
                                                                        .build()
                                                                        .execute(new StringCallback() {
                                                                            @Override
                                                                            public void onError(Call call, Exception e, int id) {
                                                                                CLog.e("testing", "上传图片失败：" + e.toString());
                                                                                HintText_Dialog mDialog = new HintText_Dialog(Release_Task_Activity.this, "发布失败", "fail");
                                                                                new Handler().postDelayed(new Runnable() {
                                                                                    @Override
                                                                                    public void run() {
                                                                                        //得到返回值后，2秒后加载框消失
                                                                                        dialog.dismiss();
                                                                                    }
                                                                                }, 2000);
                                                                            }

                                                                            @Override
                                                                            public void onResponse(String response, int id) {
                                                                                CLog.e("testing", "上传图片：" + response);
                                                                                try {
                                                                                    if (flags == 0) {
                                                                                        sendTask(babyId, Json_Utils.getMessage(response), text, 2);
                                                                                    } else {
                                                                                        sendTask2(babyId, Json_Utils.getMessage(response), text, 2);
                                                                                    }
                                                                                } catch (JSONException e) {
                                                                                    e.printStackTrace();
                                                                                }
                                                                            }
                                                                        });

                                                            } catch (IOException e) {
                                                                e.printStackTrace();
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }
                                                        }
                                                    }, "输入奖励金额(最低1元)", "确认奖励");
                                                    mDialog_Reward.show();
                                                    /*} else {
                                                        dialog = new HintText_Dialog(Release_Task_Activity.this, R.style.MyDialog);
                                                        dialog.show();
                                                        HintText_Dialog mDialog1 = new HintText_Dialog(Release_Task_Activity.this, "请选择孩子", "fail");
                                                        new Handler().postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                //得到返回值后，2秒后加载框消失
                                                                dialog.dismiss();
                                                            }
                                                        }, 2000);
                                                    }*/
                                                } else {

                                                    CLog.e("path", "上传失败");
                                                }
                                            }
                                        }, null);
                                CLog.e("token", "" + token);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }

    /**
     * 判断任务是否重复（已确认的除外）
     */
    private void isRepetition() {
        TaskAdd taskAdd = new TaskAdd();
        taskAdd.setTagId(mTaskClassTag.getId());
        taskAdd.setClassId(mTaskClassTag.getClassId());
        taskAdd.setType(1);
        taskAdd.setTitle(mTaskClassTag.getTitle());
        taskAdd.setInfos(mTaskClassTag.getContent());
        taskAdd.setPublisherId(Login_Static.myUserID);
        taskAdd.setExecutorId(babyId);
        taskAdd.setIsDefined(0);
        String data = mGson.toJson(taskAdd);
        CLog.e("testing", data);
        OKHttp.OkHttpPost("/Task/IsTaskDuplicate", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "判断任务是否重复失败：" + e.toString());
                mSend.setEnabled(true);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "判断任务是否重复：" + response);
                mSend.setEnabled(true);
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        mDialogHint = new Dialog_Hint(Release_Task_Activity.this, 0, "已存在该任务,是否删除后重新发表?", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                switch (view.getId()) {
                                    case R.id.hint_dialog_ok:
                                        CLog.e("testing", "删除重复任务");
                                        mDialogHint.dismiss();
                                        putTask();
                                        break;
                                }
                            }
                        });
                        mDialogHint.show();
                    } else if (Json_Utils.getCode(response) == -1) {
                        putTask();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void putTask() {
        if (Photo_Bimp.albumSelectBitmap.size() > 0 && Photo_Bimp.albumSelectBitmap.get(0).isVideo()) {
            name = Login_Static.myUserID + System.currentTimeMillis() + ".mp4";
            getQiNiuToken(Photo_Bimp.albumSelectBitmap.get(0).imagePath, name);//获取taken,及上传视频
        } else {
            mSend.setEnabled(true);
            /*babyId = "";
            List<String> mList_new = new ArrayList<>();
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getChioce()) {
                    mList_new.add(mList.get(i).getUserId());
                }
            }
            for (int n = 0; n < mList_new.size(); n++) {
                if (n == 0) {
                    babyId = mList_new.get(n);
                } else {
                    babyId = babyId + "," + mList_new.get(n);
                }
            }*/
            /*if (!"".equals(babyId)) {*/
            if (Photo_Bimp.taskSelectBitmap.size() != 0) {
                mDialog_Reward = new Dialog_Reward(Release_Task_Activity.this,
                        Release_Task_Activity.this, 0, Login_Static.mAccount.getPortrait()
                        , Login_Static.mAccount.getNickName(), new Dialog_Reward.EditTextContent() {
                    @Override
                    public void getEditText(final String text) {
                        //通过验证
                        dialog = new HintText_Dialog(Release_Task_Activity.this, R.style.MyDialog);
                        dialog.show();
                        HintText_Dialog mDialog = new HintText_Dialog(Release_Task_Activity.this, getString(R.string.release), "");

                        try {
                            File file = SavePicture_toLocal_Utils.saveCacheFile(Photo_Bimp.taskSelectBitmap.get(0).getBitmap(),
                                    Login_Static.myUserID + System.currentTimeMillis() + ".png",
                                    Release_Task_Activity.this);
                            byte[] bytes = NetUtil.getByte(file);
                            int[] pic = new int[bytes.length];
                            //加128，去掉符号位
                            for (int a = 0; a < pic.length; a++) {
                                pic[a] = bytes[a] & 0xff;
                            }


                            ImageInfo imageInfo = new ImageInfo();
                            imageInfo.setExt(".png");
                            imageInfo.setFileData(pic);
                            imageInfo.setTemp(false);
                            imageInfo.setMaxWidth(480);
                            imageInfo.setMaxHeight(800);
                            String data = mGson.toJson(imageInfo);
                            CLog.e("testing", "上传图片" + data);
                            //上传头像图片
                            OkHttpUtils.postString()
                                    .url(myApplication.getFileUrl())
                                    .mediaType(okhttp3.MediaType.parse("application/json"))
                                    .content(data)
                                    //.addParams("", request)
                                    .build()
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onError(Call call, Exception e, int id) {
                                            CLog.e("testing", "上传图片失败：" + e.toString());
                                            HintText_Dialog mDialog = new HintText_Dialog(Release_Task_Activity.this, "发布失败", "fail");
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    //得到返回值后，2秒后加载框消失
                                                    dialog.dismiss();
                                                }
                                            }, 2000);
                                        }

                                        @Override
                                        public void onResponse(String response, int id) {
                                            CLog.e("testing", "上传图片：" + response);
                                            try {
                                                if (flags == 0) {
                                                    sendTask(babyId, Json_Utils.getMessage(response), text, 1);
                                                } else {
                                                    sendTask2(babyId, Json_Utils.getMessage(response), text, 1);
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, "输入奖励金额(最低1元)", "确认奖励");
                mDialog_Reward.show();
            } else {
                    dialog = new HintText_Dialog(Release_Task_Activity.this, R.style.MyDialog);

                    mDialog_Reward = new Dialog_Reward(Release_Task_Activity.this,
                            Release_Task_Activity.this, 0, Login_Static.mAccount.getPortrait()
                            , Login_Static.mAccount.getNickName(), new Dialog_Reward.EditTextContent() {
                        @Override
                        public void getEditText(String text) {
                            dialog.show();
                            if (flags == 1) {
                                sendTask2(babyId, "", text, 1);
                            }else {
                                sendTask(babyId,"", text, 1);
                            }
                        }
                    },"输入奖励金额(最低1元)", "确认奖励");
                    mDialog_Reward.show();

            }
            /*} else {
                dialog = new HintText_Dialog(Release_Task_Activity.this, R.style.MyDialog);
                dialog.show();
                HintText_Dialog mDialog1 = new HintText_Dialog(Release_Task_Activity.this, "请选择孩子", "fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //得到返回值后，2秒后加载框消失
                        dialog.dismiss();
                    }
                }, 2000);
            }*/
        }
    }

}