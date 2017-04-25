package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.UserFriendRemark;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.Photo_Bimp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyPicpupWindow;
import com.multshows.Views.PicpupWindow_2;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import okhttp3.Call;

/**
 * 聊天设置界面
 */
public class Chat_Setting_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    SimpleDraweeView mHeader;//头像
    ImageView mMakeTop;//置顶
    ImageView mMessage;//消息免打扰
    RelativeLayout mClear;//清空聊天记录
    ImageView mAddGroup;//建群
    TextView mName;//昵称

    Intent mIntent;
    String userId="";
    String Message_Flag="off";
    String MakeTop_Flag="off";
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    PicpupWindow_2 mPopWindow;
    Dialog mDialog;
    MyApplication mMyApplication;
    Gson mGson=new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_setting);
        Login_Static.mPlaceActivity.add(Chat_Setting_Activity.this);
        mMyApplication= (MyApplication) getApplication();
        //沉浸式
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Chat_Setting_Activity.this, R.color.app_topColor);
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn= (ImageView) findViewById(R.id.chat_Setting_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.chat_Setting_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.chat_Setting_Parents);
        mTopTitle= (TextView) findViewById(R.id.chat_Setting_TopTitle);
        mMakeTop= (ImageView) findViewById(R.id.chat_Setting_MakeTop);
        mMessage= (ImageView) findViewById(R.id.chat_Setting_Message);
        mClear= (RelativeLayout) findViewById(R.id.chat_Setting_clear);
        mHeader= (SimpleDraweeView) findViewById(R.id.chat_Setting_Header);
        mName= (TextView) findViewById(R.id.chat_Setting_Name);
        mAddGroup= (ImageView) findViewById(R.id.chat_Setting_AddGroup);
        mDialog=new HintText_Dialog(Chat_Setting_Activity.this,R.style.MyDialog);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Chat_Setting_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mIntent=getIntent();
        userId=mIntent.getStringExtra("UserId");
        Message_Flag=mSave.Get_PREFS(Chat_Setting_Activity.this,userId+"Message_Flag");
        if("on".equals(Message_Flag)){
            Message_Flag="on";
            mMessage.setImageResource(R.drawable.switch_on);
        }else {
            Message_Flag="off";
            mMessage.setImageResource(R.drawable.switch_off);
        }
        MakeTop_Flag=mSave.Get_PREFS(Chat_Setting_Activity.this,userId+"MakeTop_Flag");
        if("on".equals(MakeTop_Flag)){
            MakeTop_Flag="on";
            mMakeTop.setImageResource(R.drawable.switch_on);
        }else {
            MakeTop_Flag="off";
            mMakeTop.setImageResource(R.drawable.switch_off);
        }
        getFriendRemark();
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
        //清空聊天记录
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow = new PicpupWindow_2(Chat_Setting_Activity.this, itemsOnClick);
                //设置弹出动画效果
                mPopWindow.setAnimationStyle(R.style.PopupAnimation);
                //显示窗口  //设置layout在PopupWindow中显示的位置
                mPopWindow.showAtLocation(findViewById(R.id.chat_Setting_Parents),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        //消息通知按钮
        mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("on".equals(Message_Flag)) {
                    CLog.e("Rongyun","关闭便打扰");
                    setNofic(false);
                } else {
                    CLog.e("Rongyun","开启便打扰");
                    setNofic(true);

                }
            }
        });
        //消息置顶按钮
        mMakeTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("on".equals(MakeTop_Flag)) {
                    setToTop(false);
                } else {
                    setToTop(true);
                }
            }
        });
        //建群
        mAddGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Chat_Setting_Activity.this,Group_AddPerson_Activity.class);
                intent.putExtra("ID",userId);
                startActivityForResult(intent,1);
            }
        });
    }
    //为弹出窗口实现监听类
    public View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.Chat_Clear:
                    mDialog.show();
                    HintText_Dialog mHintText_dialog=new HintText_Dialog(Chat_Setting_Activity.this,
                            "清除...","");
                    RongIMClient.getInstance().clearMessages(Conversation.ConversationType.PRIVATE,
                            userId, new RongIMClient.ResultCallback<Boolean>() {
                                @Override
                                public void onSuccess(Boolean aBoolean) {
                                    if(aBoolean){
                                        HintText_Dialog mHintText_dialog=new HintText_Dialog(Chat_Setting_Activity.this,
                                                "清除成功","success");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                                EventBus.getDefault().post(new ChatEvent_Model("1", "clear"));
                                            }
                                        },Login_Static.hintTime);
                                    }else {
                                        HintText_Dialog mHintText_dialog=new HintText_Dialog(Chat_Setting_Activity.this,
                                                "清除失败","fail");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                            }
                                        },Login_Static.hintTime);
                                    }
                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    HintText_Dialog mHintText_dialog=new HintText_Dialog(Chat_Setting_Activity.this,
                                            "清除失败","fail");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDialog.dismiss();
                                        }
                                    },Login_Static.hintTime);
                                }
                            });
                    break;
                default:
                    break;
            }
            mPopWindow.dismiss();
        }
    };
    /**
     * 获取备注信息
     */
    private void getFriendRemark(){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/User/GetUserFriendRemark")
                .addParams("userid",Login_Static.myUserID)
                .addParams("targetuserid",userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "获取备注信息失败" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "获取备注信息" + response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                UserFriendRemark bean=mGson.fromJson(Json_Utils.getTemplate(response),UserFriendRemark.class);
                                //mTitle.setText(bean.getName());
                                if(!"null".equals(bean.getPortrait()) && bean.getPortrait()!=null){
                                    mHeader.setImageURI(Uri.parse(SubString_Utils.getImageUrl(bean.getPortrait())));
                                }else {
                                    mHeader.setImageURI(Uri.parse(""));
                                }
                                mName.setText(bean.getName());
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //设置置顶
    private void setToTop(final boolean flag){
        RongIMClient.getInstance().setConversationToTop(Conversation.ConversationType.PRIVATE, userId, flag,
                new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        CLog.e("RongYun","置顶成功");
                        if(aBoolean) {
                            if (!flag) {
                                MakeTop_Flag = "off";
                                mMakeTop.setImageResource(R.drawable.switch_off);
                                mSave.Save_PREFS(Chat_Setting_Activity.this, userId + "MakeTop_Flag", MakeTop_Flag);
                            } else {
                                MakeTop_Flag = "on";
                                mMakeTop.setImageResource(R.drawable.switch_on);
                                mSave.Save_PREFS(Chat_Setting_Activity.this, userId + "MakeTop_Flag", MakeTop_Flag);
                            }
                        }
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        CLog.e("RongYun","置顶失败");
                    }
                });
    }

    //设置消息提醒状态
    private void setNofic(boolean flag){
        if(flag) {
            RongIMClient.getInstance().setConversationNotificationStatus(Conversation.ConversationType.PRIVATE, userId,
                    Conversation.ConversationNotificationStatus.DO_NOT_DISTURB, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                        @Override
                        public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                            Message_Flag="on";
                            mMessage.setImageResource(R.drawable.switch_on);
                            mSave.Save_PREFS(Chat_Setting_Activity.this, userId+"Message_Flag", Message_Flag);
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            mDialog.show();
                            HintText_Dialog mHintTextDialog=new HintText_Dialog(Chat_Setting_Activity.this,
                                    "网络异常","fail");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mDialog.dismiss();
                                }
                            },Login_Static.hintTime);
                        }
                    });
        }else {
            RongIMClient.getInstance().setConversationNotificationStatus(Conversation.ConversationType.PRIVATE, userId,
                    Conversation.ConversationNotificationStatus.NOTIFY, new RongIMClient.ResultCallback<Conversation.ConversationNotificationStatus>() {
                        @Override
                        public void onSuccess(Conversation.ConversationNotificationStatus conversationNotificationStatus) {
                            Message_Flag="off";
                            mMessage.setImageResource(R.drawable.switch_off);
                            mSave.Save_PREFS(Chat_Setting_Activity.this, userId+"Message_Flag", Message_Flag);
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            mDialog.show();
                            HintText_Dialog mHintTextDialog=new HintText_Dialog(Chat_Setting_Activity.this,
                                    "网络异常","fail");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mDialog.dismiss();
                                }
                            },Login_Static.hintTime);
                        }
                    });
        }
    }
}
