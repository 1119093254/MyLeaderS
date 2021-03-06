package com.multshows.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Adapter.GroupSetting_User_Adapter;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.GroupInfo;
import com.multshows.Beans.GroupMember;
import com.multshows.Beans.PersonalSetting;
import com.multshows.Beans.UserFriendRemark;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyGridView;
import com.multshows.Views.PicpupWindow_2;
import com.multshows.Views.PicpupWindow_Out;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Discussion;
import okhttp3.Call;

/**
 * 聊天设置界面
 */
public class Group_Setting_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTitle;//标题信息
    MyGridView mHeader;//头像
    CheckBox mMakeTop;//置顶
    CheckBox mMessage;//消息免打扰
    //CheckBox mSaveMessage;//保存至通讯录
    // CheckBox mShowName;//显示成员昵称
    RelativeLayout mClear;//清空聊天记录
    RelativeLayout mGroupName;//修改群聊名称
    // RelativeLayout mMyName;//修改我的群聊名称
    // RelativeLayout mER;//群聊二维码
    // SimpleDraweeView mERImage;//二维码展示
    TextView mGroupNameText;//群聊名称
    // TextView mMyNameText;//我的群聊名称
    Button mOut;//退出群组
    //适配器
    GroupSetting_User_Adapter mUserAdapter;


    Intent mIntent;
    String userId = "";
    //群信息数据
    GroupInfo mGroupInfo;//群信息
    String groupName = "";//群名
    List<GroupMember> mUserInfo = new ArrayList<>();//群成员信息集合
    PersonalSetting mPersonalSetting;//群设置

    String MakeTop_Flag = "off";
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    PicpupWindow_2 mPopWindow;
    PicpupWindow_Out mPicpupWindow_out;
    Dialog mDialog;
    MyApplication mMyApplication;
    String pathUrl = "";
    Gson mGson = new Gson();
    Dialog_Hint mDialogHints;
    Boolean isCreator = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_setting);
        EventBus.getDefault().register(Group_Setting_Activity.this);
        //沉浸式
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Group_Setting_Activity.this, R.color.app_topColor);
        mMyApplication= (MyApplication) getApplication();
        pathUrl = mMyApplication.getUrl();
        initView();
        initData();
        initListen();
        //获取群的创建者
        RongIMClient.getInstance().getDiscussion(userId, new RongIMClient.ResultCallback<Discussion>() {
            @Override
            public void onSuccess(Discussion discussion) {
                isCreator = discussion.getCreatorId().equals(Login_Static.myUserID);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.Group_Setting_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Group_Setting_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.chat_Setting_Parents);
        mMakeTop = (CheckBox) findViewById(R.id.Group_Setting_MakeTop);
        mMessage = (CheckBox) findViewById(R.id.Group_Setting_Message);
       /* mSaveMessage= (CheckBox) findViewById(R.id.Group_Setting_Save);
        mShowName= (CheckBox) findViewById(R.id.Group_Setting_ShowFlag);*/
        mClear = (RelativeLayout) findViewById(R.id.Group_Setting_clear);
        mHeader = (MyGridView) findViewById(R.id.Group_Setting_GridView);
        mTitle = (TextView) findViewById(R.id.Group_Setting_Title);
        mGroupName = (RelativeLayout) findViewById(R.id.Group_Setting_GroupName);
       /* mMyName= (RelativeLayout) findViewById(R.id.Group_Setting_Name);
        mER= (RelativeLayout) findViewById(R.id.Group_Setting_ER);*/
        mGroupNameText = (TextView) findViewById(R.id.Group_Setting_GroupNameText);
        /*mMyNameText= (TextView) findViewById(R.id.Group_Setting_MyNameText);
        mERImage= (SimpleDraweeView) findViewById(R.id.Group_Setting_ERImage);*/
        mOut = (Button) findViewById(R.id.Group_Setting_Out);
        mDialog = new HintText_Dialog(Group_Setting_Activity.this, R.style.MyDialog);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Group_Setting_Activity.this,mReturnLayout,mParentLayout,mReturn,mTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mIntent = getIntent();
        userId = mIntent.getStringExtra("groupID");
        mGroupInfo = (GroupInfo) mIntent.getSerializableExtra("groupInfo");
        if (mGroupInfo != null) {
            mUserInfo = mGroupInfo.getGroupMembers();
            groupName = mGroupInfo.getGroupName();
            mPersonalSetting = mGroupInfo.getPersonalSetting();
        }
        mTitle.setText("聊天信息(" + mUserInfo.size() + ")");
        mGroupNameText.setText(mGroupInfo.getGroupName());
        /*for (int i = 0; i < mUserInfo.size(); i++) {
            if(mUserInfo.get(i).getUserId().equals(Login_Static.myUserID)){
                mMyNameText.setText(mUserInfo.get(i).getAlias());
            }
        }*/

        MakeTop_Flag = mSave.Get_PREFS(Group_Setting_Activity.this, userId + "MakeTop_Flag");
        //消息提醒类型 1正常提醒 2免打扰
        if (mPersonalSetting.getNotifyType() == 2) {
            mMessage.setChecked(true);
        } else {
            mMessage.setChecked(false);
        }
        if ("on".equals(MakeTop_Flag)) {
            mMakeTop.setChecked(true);
        } else {
            mMakeTop.setChecked(false);
        }
      /*  if("on".equals(SaveMessage_Flag)){
            mSaveMessage.setChecked(true);
        }else {
            mSaveMessage.setChecked(false);
        }
        if(mPersonalSetting.getIsShowAlias()==1){
            mShowName.setChecked(true);
        }else {
            mShowName.setChecked(false);
        }*/
        GroupMember member = new GroupMember();
        member.setFlag(1);
        mUserInfo.add(member);
        mUserAdapter = new GroupSetting_User_Adapter(Group_Setting_Activity.this, mUserInfo, userId);
        mHeader.setAdapter(mUserAdapter);
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
        //头像点击事件
        mHeader.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                if (position != mUserInfo.size() - 1) {
                    if (isCreator) {
                        if (!mUserInfo.get(position).getUserId().equals(Login_Static.myUserID)) {
                            mDialogHints = new Dialog_Hint(Group_Setting_Activity.this, 0, "确定将该成员移出群组?", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    switch (view.getId()) {
                                        case R.id.hint_dialog_ok:
                                            RongIMClient.getInstance().removeMemberFromDiscussion(userId, mUserInfo.get(position).getUserId(),
                                                    new RongIMClient.OperationCallback() {
                                                        @Override
                                                        public void onSuccess() {
                                                            CLog.e("RongYun", "移出群成员onSuccess");
                                                            removePerson(mUserInfo.get(position).getUserId(), position);
                                                        }

                                                        @Override
                                                        public void onError(RongIMClient.ErrorCode errorCode) {
                                                            CLog.e("RongYun", "移出群成员onError");
                                                        }
                                                    });
                                            break;
                                    }

                                }
                            });
                            mDialogHints.show();
                        }else{
                            mDialog = new HintText_Dialog(Group_Setting_Activity.this, R.style.MyDialog);
                            mDialog.show();
                            HintText_Dialog mHintText_dialog = new HintText_Dialog(Group_Setting_Activity.this,
                                    "不能移出自己", "fail");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mDialog.dismiss();
                                }
                            }, Login_Static.hintTime);
                        }
                    }else {
                        mDialog = new HintText_Dialog(Group_Setting_Activity.this, R.style.MyDialog);
                        mDialog.show();
                        HintText_Dialog mHintText_dialog = new HintText_Dialog(Group_Setting_Activity.this,
                                "只有群主有权限移出成员", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    }
                }
                return false;
            }
        });
        //清空聊天记录
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPopWindow = new PicpupWindow_2(Group_Setting_Activity.this, itemsOnClick);
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
                if (mPersonalSetting.getNotifyType() == 2) {
                    setNofic(false);

                } else {
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
       /* //保存至通讯录按钮
        mSaveMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("on".equals(SaveMessage_Flag)) {
                    SaveMessage_Flag="off";
                    mSaveMessage.setChecked(false);
                    mSave.Save_PREFS(Group_Setting_Activity.this, userId+"MakeTop_Flag", SaveMessage_Flag);
                } else {
                    SaveMessage_Flag="on";
                    mSaveMessage.setChecked(true);
                    mSave.Save_PREFS(Group_Setting_Activity.this, userId+"MakeTop_Flag", SaveMessage_Flag);
                }
            }
        });
        //显示群成员昵称按钮
        mShowName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mPersonalSetting.getIsShowAlias()==1){
                    setShowUserName("0");
                } else {
                    setShowUserName("1");
                }
            }
        });*/
        //修改群名称
        mGroupName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Group_Setting_Activity.this, Group_SetChange_Activity.class);
                intent.putExtra("type", "GroupName");
                intent.putExtra("name", mGroupNameText.getText().toString());
                intent.putExtra("RCGroupID", userId);
                intent.putExtra("GroupID", mGroupInfo.getId());
                startActivityForResult(intent, 1);
            }
        });
      /*  //修改我的群名片
        mMyName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Group_Setting_Activity.this,Group_SetChange_Activity.class);
                intent.putExtra("type","GroupMyName");
                intent.putExtra("name",mMyNameText.getText().toString());
                intent.putExtra("RCGroupID",userId);
                intent.putExtra("GroupID",mGroupInfo.getId());
                startActivityForResult(intent,2);
            }
        });*/
        //退出群组
        mOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPicpupWindow_out = new PicpupWindow_Out(Group_Setting_Activity.this, itemsOnClick);
                //设置弹出动画效果
                mPicpupWindow_out.setAnimationStyle(R.style.PopupAnimation);
                //显示窗口  //设置layout在PopupWindow中显示的位置
                mPicpupWindow_out.showAtLocation(findViewById(R.id.chat_Setting_Parents),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
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
                    HintText_Dialog mHintText_dialog = new HintText_Dialog(Group_Setting_Activity.this,
                            "清除...", "");
                    RongIMClient.getInstance().clearMessages(Conversation.ConversationType.DISCUSSION,
                            userId, new RongIMClient.ResultCallback<Boolean>() {
                                @Override
                                public void onSuccess(Boolean aBoolean) {
                                    if (aBoolean) {
                                        HintText_Dialog mHintText_dialog = new HintText_Dialog(Group_Setting_Activity.this,
                                                "清除成功", "success");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                                EventBus.getDefault().post(new ChatEvent_Model("1", "Groupclear"));
                                            }
                                        }, Login_Static.hintTime);
                                    } else {
                                        HintText_Dialog mHintText_dialog = new HintText_Dialog(Group_Setting_Activity.this,
                                                "清除失败", "fail");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                            }
                                        }, Login_Static.hintTime);
                                    }
                                }

                                @Override
                                public void onError(RongIMClient.ErrorCode errorCode) {
                                    HintText_Dialog mHintText_dialog = new HintText_Dialog(Group_Setting_Activity.this,
                                            "清除失败", "fail");
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            mDialog.dismiss();
                                        }
                                    }, Login_Static.hintTime);
                                }
                            });
                    mPopWindow.dismiss();
                    break;
                case R.id.Group_Clear:
                    //退出群聊

                    mDialog.show();
                    HintText_Dialog mHintText_dialogs = new HintText_Dialog(Group_Setting_Activity.this,
                            "退出...", "");
                    RongIMClient.getInstance().quitDiscussion(userId, new RongIMClient.OperationCallback() {
                        @Override
                        public void onSuccess() {
                            CLog.e("RongYun", "退出群聊成功");
                            setOutGroup();
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            CLog.e("RongYun", "退出群聊失败");
                            HintText_Dialog mHintText_dialogs = new HintText_Dialog(Group_Setting_Activity.this,
                                    "退出群聊失败", "fail");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mDialog.dismiss();
                                }
                            }, Login_Static.hintTime);

                        }
                    });
                    mPicpupWindow_out.dismiss();
                    break;
                default:
                    break;
            }

        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                //修改群名称
                if (resultCode == 6) {
                    String name = data.getStringExtra("GNresult");
                    mGroupNameText.setText(name);
                }
                break;
            case 2:
                //修改我的群名片
                if (resultCode == 6) {
                    String name = data.getStringExtra("MNresult");
                    // mMyNameText.setText(name);
                }
                break;
        }
    }

    @Subscribe
    public void getEvent(ChatEvent_Model model) {
        if ("AddUser".equals(model.getFlag())) {
            for (int i = 0; i < model.getMembers().size(); i++) {
                CLog.e("testing", "Size:" + (mUserInfo.size() - 2));
                mUserInfo.add(mUserInfo.size() - 2, model.getMembers().get(i));
            }
            mUserAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(Group_Setting_Activity.this);
    }

    //退出群聊
    private void setOutGroup() {
        OkHttpUtils.get().url(pathUrl + "/GroupChat/QuitGroup")
                .addParams("userId", Login_Static.myUserID)//加密当前用户id
                .addParams("RCGroupId", userId)//融云提供的群id
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "退出群聊失败" + e.toString());
                        HintText_Dialog mHintText_dialog = new HintText_Dialog(Group_Setting_Activity.this,
                                "退出群聊失败", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "退出群聊" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                HintText_Dialog mHintText_dialog = new HintText_Dialog(Group_Setting_Activity.this,
                                        "退出群聊成功", "success");
                                if (Login_Static.mPlaceActivity != null) {
                                    for (int i = 0; i < Login_Static.mPlaceActivity.size(); i++) {
                                        Login_Static.mPlaceActivity.get(i).finish();
                                    }
                                }
                                finish();
                            } else {
                                HintText_Dialog mHintText_dialog = new HintText_Dialog(Group_Setting_Activity.this,
                                        "退出群聊失败", "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                }, Login_Static.hintTime);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //设置置顶
    private void setToTop(final boolean flag) {
        RongIMClient.getInstance().setConversationToTop(Conversation.ConversationType.DISCUSSION, userId, flag,
                new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        CLog.e("RongYun", "置顶成功");
                        if(aBoolean) {
                            if (!flag) {
                                MakeTop_Flag = "off";
                                mMakeTop.setChecked(false);
                                mSave.Save_PREFS(Group_Setting_Activity.this, userId + "MakeTop_Flag", MakeTop_Flag);
                            } else {
                                MakeTop_Flag = "on";
                                mMakeTop.setChecked(true);
                                mSave.Save_PREFS(Group_Setting_Activity.this, userId + "MakeTop_Flag", MakeTop_Flag);
                            }
                        }
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        CLog.e("RongYun", "置顶失败");
                    }
                });
    }

    /* //修改群成员昵称显示状态
     private void setShowUserName(final String flag){
         OkHttpUtils.get().url(pathUrl+"/GroupChat/ChangeAliasDisplay")
                 .addParams("userId",Login_Static.myUserID)//加密当前用户id
                 .addParams("RCGroupId",userId)//融云提供的群id
                 .addParams("isDisplay",flag)//0不显示 1显示
                 .build()
                 .execute(new StringCallback() {
                     @Override
                     public void onError(Call call, Exception e, int id) {
                         CLog.e("testing","修改群成员昵称显示状态失败"+e.toString());

                     }

                     @Override
                     public void onResponse(String response, int id) {
                         CLog.e("testing","修改群成员昵称显示状态"+response);
                         try {
                             if(Json_Utils.getCode(response)==0){
                                 if("0".equals(flag)){
                                     mShowName.setChecked(false);
                                     mPersonalSetting.setIsShowAlias(0);
                                 }else {
                                     mShowName.setChecked(true);
                                     mPersonalSetting.setIsShowAlias(1);
                                 }
                             }
                         } catch (JSONException e) {
                             e.printStackTrace();
                         }

                     }
                 });
     }*/
    //	更改消息提醒类型
    private void setMessageType(final String flag) {
        OkHttpUtils.get().url(pathUrl + "/GroupChat/ChangeGroupNotifyType")
                .addParams("userId", Login_Static.myUserID)//加密当前用户id
                .addParams("RCGroupId", userId)//融云提供的群id
                .addParams("type", flag)//1正常提醒 2免打扰
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "更改消息提醒类型失败" + e.toString());
                        mDialog.show();
                        HintText_Dialog mHintTextDialog=new HintText_Dialog(Group_Setting_Activity.this,
                                "网络异常","fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        },Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "更改消息提醒类型" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                if ("1".equals(flag)) {
                                    mMessage.setChecked(false);
                                    mPersonalSetting.setNotifyType(1);
                                    mSave.Save_PREFS(Group_Setting_Activity.this, userId+"Message_Flag", "off");
                                } else {
                                    mMessage.setChecked(true);
                                    mPersonalSetting.setNotifyType(2);
                                    mSave.Save_PREFS(Group_Setting_Activity.this, userId+"Message_Flag", "on");
                                }
                            }else {
                                mDialog.show();
                                HintText_Dialog mHintTextDialog=new HintText_Dialog(Group_Setting_Activity.this,
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

    /**
     * 移出群成员
     */
    private void removePerson(String use, final int position) {
        CLog.e("00000000",pathUrl+"/GroupChat/DelGroupMembers?"+"currentUserId="+Login_Static.myUserID+"&targetUserIds="+use+
         "&RCGroupId="+userId);
        OkHttpUtils.get().url(pathUrl + "/GroupChat/DelGroupMembers")
                .addParams("currentUserId", Login_Static.myUserID)//加密当前用户id
                .addParams("targetUserIds", use)//逗号拼接的加密的用户id
                .addParams("RCGroupId", userId)//融云提供的群id
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "移出群成员失败" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "移出群成员" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                mDialogHints.dismiss();
                                mUserInfo.remove(position);
                                mUserAdapter.notifyDataSetChanged();
                            } else {
                                mDialogHints.dismiss();
                                mDialog = new HintText_Dialog(Group_Setting_Activity.this, R.style.MyDialog);
                                mDialog.show();
                                HintText_Dialog mHintText_dialog = new HintText_Dialog(Group_Setting_Activity.this,
                                        "移出失败", "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                }, Login_Static.hintTime);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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
                            setMessageType("2");
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            mDialog.show();
                            HintText_Dialog mHintTextDialog=new HintText_Dialog(Group_Setting_Activity.this,
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
                            setMessageType("1");
                        }

                        @Override
                        public void onError(RongIMClient.ErrorCode errorCode) {
                            mDialog.show();
                            HintText_Dialog mHintTextDialog=new HintText_Dialog(Group_Setting_Activity.this,
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
