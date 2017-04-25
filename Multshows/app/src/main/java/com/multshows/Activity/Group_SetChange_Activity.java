package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Beans.GroupBase;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 群聊修改群信息 界面
 */
public class Group_SetChange_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    EditText mEditText;
    TextView mSave;//保存
    TextView mTitle;//标题
    TextView mTopLayout;
    TextView mBottomLayout;
    ImageView mDelete;//删除

    Intent intent;
    Gson mGson=new Gson();
    Dialog mDialog;
    String type="";
    String groupID="";
    String RcGroupID="";
    String names="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_set_change);
        //沉浸式
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Group_SetChange_Activity.this, R.color.app_topColor);
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.Group_SetChange_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Group_SetChange_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Group_SetChange_ParentsLayout);
        mEditText= (EditText) findViewById(R.id.Group_SetChange_EditText);
        mSave= (TextView) findViewById(R.id.Group_SetChange_Save);
        mTitle= (TextView) findViewById(R.id.Group_SetChange_Title);
        mTopLayout= (TextView) findViewById(R.id.Group_SetChange_NameLayout);
        mBottomLayout= (TextView) findViewById(R.id.Group_SetChange_Text);
        mDelete= (ImageView) findViewById(R.id.Group_SetChange_Delete);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Group_SetChange_Activity.this,mReturnLayout,mParentLayout,mReturn,mTitle,mSave);
    }

    /**
     * 数据处理
     */
    private void initData() {
        intent=getIntent();
        names=intent.getStringExtra("name");
        type=intent.getStringExtra("type");
        groupID=intent.getStringExtra("GroupID");
        RcGroupID=intent.getStringExtra("RCGroupID");
        mEditText.setText(names);
        if("GroupName".equals(type)){
            //修改群昵称
            mTitle.setText("群聊名称");

        }else {
            mTitle.setText("我在本群的昵称");
            mTopLayout.setVisibility(View.INVISIBLE);
            mBottomLayout.setVisibility(View.VISIBLE);
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
                HideInputManager_Utils.hideInputManager(Group_SetChange_Activity.this);
                finish();
            }
        });
        //保存
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text=mEditText.getText().toString();
                mDialog=new HintText_Dialog(Group_SetChange_Activity.this,R.style.MyDialog);
                mDialog.show();
                if(!"".equals(text)){
                    if("GroupName".equals(type)){
                        //修改群昵称
                        setGroupName(text);
                    }else {
                        setMyGroupName(text);
                    }

                }else {
                    HintText_Dialog mHintText_dialog=new HintText_Dialog(Group_SetChange_Activity.this,"名称不能为空","fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                          mDialog.dismiss();
                        }
                    },Login_Static.hintTime);
                }
            }
        });
        //清空
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.setText("");
            }
        });
    }


    //更新群昵称
    private void setGroupName(final String name){
        HintText_Dialog mHintText_dialog=new HintText_Dialog(Group_SetChange_Activity.this,"修改中...","");
        GroupBase groupBase=new GroupBase();
        groupBase.setId(groupID);
        groupBase.setRCGroupId(RcGroupID);
        groupBase.setGroupName(name);
        String data=mGson.toJson(groupBase);
        CLog.e("testing","更新群昵称data"+data);
        OKHttp.OkHttpPost("/GroupChat/UpdateGroup", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing","更新群昵称失败"+e.toString());
                HintText_Dialog mHintText_dialog=new HintText_Dialog(Group_SetChange_Activity.this,"修改失败","fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                    }
                },Login_Static.hintTime);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing","更新群昵称"+response);
                try {
                    if(Json_Utils.getCode(response)==0){
                        HintText_Dialog mHintText_dialog=new HintText_Dialog(Group_SetChange_Activity.this,
                                "修改成功","success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                intent.putExtra("GNresult",name);
                                setResult(6,intent);
                                finish();
                            }
                        },Login_Static.hintTime);
                    }else {
                        HintText_Dialog mHintText_dialog=new HintText_Dialog(Group_SetChange_Activity.this,
                                Json_Utils.getMessage(response),"fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //修改我的群名片
    private void setMyGroupName(final String name){
        HintText_Dialog mHintText_dialog=new HintText_Dialog(Group_SetChange_Activity.this,"修改中...","");
        MyApplication myApplication= (MyApplication) getApplication();
        OkHttpUtils.get().url(myApplication.getUrl()+"/GroupChat/SetAliasInGroup")
                .addParams("userId", Login_Static.myUserID)
                .addParams("RCGroupId",RcGroupID)
                .addParams("alias",name)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","修改群名片失败"+e.toString());
                        HintText_Dialog mHintText_dialog=new HintText_Dialog(Group_SetChange_Activity.this,
                                "修改失败","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","修改群名片"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                HintText_Dialog mHintText_dialog=new HintText_Dialog(Group_SetChange_Activity.this,
                                        "修改成功","success");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                        intent.putExtra("MNresult",name);
                                        setResult(6,intent);
                                        finish();
                                    }
                                },Login_Static.hintTime);
                            }else {
                                HintText_Dialog mHintText_dialog=new HintText_Dialog(Group_SetChange_Activity.this,
                                        Json_Utils.getMessage(response),"fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                },Login_Static.hintTime);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }
}
