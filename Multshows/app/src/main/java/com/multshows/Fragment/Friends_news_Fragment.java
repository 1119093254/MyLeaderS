package com.multshows.Fragment;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Activity.Chat_Activity;
import com.multshows.Activity.Friends_Interaction_Activity;
import com.multshows.Activity.Friends_Order_Activity;
import com.multshows.Activity.Friends_RequestList_Activity;
import com.multshows.Activity.MainActivity;
import com.multshows.Adapter.Friends_NewsList_Adapter;
import com.multshows.Beans.Account;
import com.multshows.Beans.Chat_ConversationList_Beans;
import com.multshows.Beans.GroupInfo;
import com.multshows.Beans.MessageData;
import com.multshows.Beans.UserFriendRemark;
import com.multshows.Beans.UserMessageTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.DateDeal;
import com.multshows.Utils.DateDeal_2;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import okhttp3.Call;

/**
 * 好友模块的消息界面
 */
public class Friends_news_Fragment extends Fragment {
    View mView;
    //上下文
    Context mContext;
    ListView mListView;
    TextView mRequestText;//提示
    TextView mMessageText;//提示
    TextView mOrderText;//提示
    TextView mTaskText;//提示
    TextView mRequestTime;//提示
    TextView mMessageTime;//提示
    TextView mOrderTime;//提示
    TextView mTaskTime;//提示
    LinearLayout mGoneView;

    //控件
    MyListView mMyListView;
    RelativeLayout mMessage;//互动消息
    RelativeLayout mOrder;//订单消息
    RelativeLayout mRequest;//好友申请
    RelativeLayout mTask;//任务消息
    //提示点点
    TextView mRequestHint;//
    TextView mMessageHint;//
    TextView mOrderHint;//
    TextView mTaskHint;//
    //适配器
    Friends_NewsList_Adapter mNewsListAdapter;
    //数据
    List<Chat_ConversationList_Beans> mBeansList = new ArrayList<>();
    List<String> mStringList=new ArrayList<>();
    Gson mGson = new Gson();
    boolean isOk = false;
    int flag = 0;//聊天类型
    MyApplication myApplication;
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    int count=0;
    String Message_Flag="";
    int messageFlag=1;//互动消息标志
    boolean isFirst=true;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.friends_news, null);
        myApplication= (MyApplication) getActivity().getApplication();
        initView(mView);
        initData();
        initListen_();
        isOk = true;
        return mView;
    }

    /**
     * 初始化
     */
    private void initView(View view) {
        mMyListView = (MyListView) view.findViewById(R.id.friends_news_MyListView);
        mRequest = (RelativeLayout) view.findViewById(R.id.friends_news_Request_Layout);
        mMessage = (RelativeLayout) view.findViewById(R.id.friends_news_Message_Layout);
        mOrder = (RelativeLayout) view.findViewById(R.id.friends_news_Order_Layout);
        mTask = (RelativeLayout) view.findViewById(R.id.friends_news_Task_Layout);
        mGoneView= (LinearLayout) view.findViewById(R.id.friends_news_View);

        mRequestText = (TextView) view.findViewById(R.id.friends_news_RequestText);
        mMessageText = (TextView) view.findViewById(R.id.friends_news_MessageText);
        mOrderText = (TextView) view.findViewById(R.id.friends_news_OrderText);
        mTaskText = (TextView) view.findViewById(R.id.friends_news_TaskText);
        mRequestTime = (TextView) view.findViewById(R.id.friends_news_RequestTime);
        mMessageTime = (TextView) view.findViewById(R.id.friends_news_MessageTime);
        mOrderTime = (TextView) view.findViewById(R.id.friends_news_OrderTime);
        mTaskTime = (TextView) view.findViewById(R.id.friends_news_TaskTime);

        mRequestHint = (TextView) view.findViewById(R.id.friends_news_Request_NoReader);
        mMessageHint = (TextView) view.findViewById(R.id.friends_news_Message_NoReader);
        mOrderHint = (TextView) view.findViewById(R.id.friends_news_Order_NoReader);
        mTaskHint = (TextView) view.findViewById(R.id.friends_news_Task_NoReader);
       /* String type = mSave.Get_PREFS(getActivity(), "Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        if ("mom".equals(type)) {
            //妈妈端
            mOrder.setVisibility(View.VISIBLE);
        } else if ("baby".equals(type)) {
            //宝宝端
            mOrder.setVisibility(View.GONE);
        }*/
    }

    /**
     * 数据处理
     */
    private void initData() {
        getOutMessage();
        mNewsListAdapter = new Friends_NewsList_Adapter(mContext, mBeansList);
        mMyListView.setAdapter(mNewsListAdapter);
       /* *//**
         *     获取消息刷新界面
         *     message - 收到的消息实体
         *     i - 剩余未拉取消息数目
         *//*
        RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                Log.e("RongYun", "有新版消息");
                setNotify();
                return false;
            }
        });*/

    }

    @Override
    public void onResume() {
        super.onResume();
        count=0;
        if(isFirst) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    isFirst=false;
                    setNotify();
                }
            }, 2000);
        }else {
            setNotify();
        }
      /*  RongIMClient.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageListener() {
            @Override
            public boolean onReceived(Message message, int i) {
                String type=mSave.Get_PREFS(mContext,message.getTargetId()+"Message_Flag");
                setNotify();
                CLog.e("testing","有新版消息:type:"+type);
                if(isBackground(mContext)){
                    //后台
                    if("on".equals(type)){
                        count=0;
                    }else {
                        if(mStringList.size()==0){
                            count=1;
                        }
                        for (int n = 0; n < mStringList.size(); n++) {
                            if(mStringList.get(n).equals(message.getTargetId()+"")){

                            }else {
                                mStringList.add(message.getTargetId()+"");
                                count++;
                            }
                        }
                        showNotification(count);
                    }
                }else {
                    //前台
                    count=0;
                    if("on".equals(type)){

                    }else {
                        Vibrator vib = (Vibrator) getActivity().getSystemService(Service.VIBRATOR_SERVICE);
                        vib.vibrate(100);
                    }
                }

                return false;
            }
        });*/
        getOutMessage();
        CLog.e("testing_news", "onResume");
    }


    public void setNotify() {
        //融云获取本地会话列表数据
        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback<List<Conversation>>() {
            @Override
            public void onSuccess(List<Conversation> conversations) {
                CLog.e("aaa111", mBeansList.size() + "+++");
                mBeansList.clear();
                mNewsListAdapter.notifyDataSetChanged();
                String mes = "";
                MessageTag messageTag = null;
                if (conversations != null) {
                    CLog.e("aaa111", "----------" +conversations.size());
                    for (Conversation conversation : conversations) {
                        flag = 0;
                        try {
                                if (conversation.getLatestMessage() != null) {
                                    String name = "";
                                    String pic = "";
                                    if (conversation.getConversationType() == Conversation.ConversationType.DISCUSSION) {
                                        flag = 1;
                                        name = mSave.Get_PREFS(mContext, Login_Static.myUserID + "chatGroupName" + conversation.getTargetId());
                                    } else {
                                        name = mSave.Get_PREFS(mContext, Login_Static.myUserID + "chatUserName" + conversation.getTargetId());
                                        pic = mSave.Get_PREFS(mContext, Login_Static.myUserID + "chatUserPic" + conversation.getTargetId());
                                    }
                                    String json = new String(conversation.getLatestMessage().encode(), "UTF-8");
                                    messageTag = conversation.getLatestMessage().getClass().getAnnotation(MessageTag.class);
                                    //RC:TxtMsg, RC:VcMsg, RC:ImgMsg

                                    if (messageTag.value().equals("RC:TxtMsg")) {
                                        JSONObject jsonObject = new JSONObject(json);
                                        mes = jsonObject.getString("content");
                                    /*getOtherUserInfo(conversation.getTargetId(),mes,Time_Now.getRongTime(conversation.getSentTime()),
                                            conversation.getUnreadMessageCount());*/
                                        Chat_ConversationList_Beans bean = new Chat_ConversationList_Beans(conversation.getTargetId(),
                                                name, pic,
                                                mes, Time_Now.getRongTime(conversation.getSentTime()), conversation.getUnreadMessageCount(), flag);
                                        mBeansList.add(bean);

                                    } else if (messageTag.value().equals("RC:ImgMsg")) {
                                        //图片
                                        JSONObject jsonObject = new JSONObject(json);
                                        //mes = jsonObject.getString("imageUri");
                                   /* getOtherUserInfo_(conversation.getTargetId(),Time_Now.getRongTime(conversation.getSentTime()),
                                            conversation.getUnreadMessageCount());*/
                                        Chat_ConversationList_Beans bean = new Chat_ConversationList_Beans(conversation.getTargetId(),
                                                name, pic, "【图片】", Time_Now.getRongTime(conversation.getSentTime())
                                                , conversation.getUnreadMessageCount(), flag);
                                        mBeansList.add(bean);

                                    } else {
                                        Chat_ConversationList_Beans bean = new Chat_ConversationList_Beans(conversation.getTargetId(),
                                                "", "", "", Time_Now.getRongTime(conversation.getSentTime()), conversation.getUnreadMessageCount(), flag);
                                        mBeansList.add(bean);
                                    }
                                }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    for (int i = 0; i < mBeansList.size(); i++) {
                        if (mBeansList.get(i).getType() == 0) {
                            getOtherUserInfo(mBeansList.get(i));
                        } else {
                            getGroup(mBeansList.get(i));
                        }
                    }
                    if(mBeansList.size()==0) {
                        mGoneView.setVisibility(View.GONE);
                    }else {
                        mGoneView.setVisibility(View.VISIBLE);
                    }
                    mNewsListAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
            }
        },Conversation.ConversationType.PRIVATE,Conversation.ConversationType.DISCUSSION);//, Conversation.ConversationType.PRIVATE
    }

    /**
     * 事件处理
     */
    private void initListen_() {
        //互动列表
        mMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Friends_Interaction_Activity.class);
                intent.putExtra("messageType",messageFlag);
                startActivity(intent);
            }
        });
        //订单消息
        mOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Friends_Order_Activity.class);
                intent.putExtra("type", "order");
                startActivity(intent);
            }
        });
        //好友申请列表
        mRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Friends_RequestList_Activity.class);
                startActivity(intent);
            }
        });
        //任务消息
        mTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Friends_Order_Activity.class);
                intent.putExtra("type", "task");
                getActivity().startActivityForResult(intent,10);
            }
        });
    }


    /**
     * 获取备注信息
     */
    private void getOtherUserInfo(final Chat_ConversationList_Beans beans){
        OkHttpUtils.get().url(myApplication.getUrl()+"/User/GetUserFriendRemark")
                .addParams("userid",Login_Static.myUserID)
                .addParams("targetuserid",beans.getSendUserId())
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
                                beans.setSendUserName(bean.getName());
                                beans.setSendUserPic(bean.getPortrait());
                                mSave.Save_PREFS(mContext, Login_Static.myUserID+"chatUserName"+beans.getSendUserId(),bean.getName());
                                mSave.Save_PREFS(mContext, Login_Static.myUserID+"chatUserPic"+beans.getSendUserId(),bean.getPortrait());
                                mNewsListAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void getGroup(final Chat_ConversationList_Beans beans) {
        OkHttpUtils.get().url(myApplication.getUrl() + "/GroupChat/GetGroup")
                .addParams("currentUserId", Login_Static.myUserID)
                .addParams("RCGroupId", beans.getSendUserId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "获取群信息失败" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "获取群信息" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                GroupInfo user = mGson.fromJson(Json_Utils.getTemplate(response), GroupInfo.class);
                                beans.setSendUserName(user.getGroupName());
                                beans.setSendUserPic("");
                                mSave.Save_PREFS(mContext, Login_Static.myUserID+"chatGroupName"+beans.getSendUserId(),user.getGroupName());
                                mNewsListAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }


    //获取外部提示消息
    private void getOutMessage() {
        CLog.e("testing", "111:" + Login_Static.myUserID);
        OkHttpUtils.get().url(myApplication.getUrl() + "/User/ListUserMessage")
                .addParams("userId", Login_Static.myUserID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "外部提示信息失败" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "外部提示信息" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    MessageData bean = mGson.fromJson(jsonArray.getString(i), MessageData.class);
                                    int type = bean.getType();
                                    if (type == 1) {
                                        //平台消息

                                    } else if (type == 2) {
                                        //提示信息

                                    } else if (type == 3) {
                                        //私聊消息

                                    } else if (type == 4) {
                                        //订单消息
                                        if(bean.getContent()!=null && !"".equals(bean.getContent())) {
                                            mOrderText.setText(bean.getContent());
                                        }else {
                                            mOrderText.setText("暂无订单消息");
                                        }
                                        String str = "";
                                        if (bean.getRecordTime() != 0)
                                            str = DateDeal.format(Time_Now.getStringTime(bean.getRecordTime()));
                                        mOrderTime.setText(str);
                                        if (bean.getNumber() == 0) {
                                            mOrderHint.setVisibility(View.GONE);
                                            mOrderTime.setText("");
                                            mOrderText.setText("暂无订单消息");
                                        } else {
                                            mOrderHint.setVisibility(View.VISIBLE);
                                            if(bean.getNumber()>99){
                                                mOrderHint.setText("99");
                                            }else {
                                                mOrderHint.setText(bean.getNumber() + "");
                                            }
                                        }
                                    } else if (type == 5) {
                                        //任务消息
                                        if(bean.getContent()!=null && !"".equals(bean.getContent())) {
                                            mTaskText.setText(bean.getContent());
                                        }else {
                                            mTaskText.setText("暂无任务消息");

                                        }
                                        String str = "";
                                        if (bean.getRecordTime() != 0)
                                            str = DateDeal.format(Time_Now.getStringTime(bean.getRecordTime()));
                                        mTaskTime.setText(str);
                                        if (bean.getNumber() == 0) {
                                            mTaskHint.setVisibility(View.GONE);
                                            mTaskText.setText("暂无任务消息");
                                            mTaskTime.setText("");
                                        } else {
                                            mTaskHint.setVisibility(View.VISIBLE);
                                            if(bean.getNumber()>99){
                                                mTaskHint.setText("99");
                                            }else {
                                                mTaskHint.setText(bean.getNumber() + "");
                                            }
                                        }
                                    } else if (type == 6) {
                                        //好友消息
                                        String str = "";
                                        if (bean.getRecordTime() != 0)
                                            str = DateDeal.format(Time_Now.getStringTime(bean.getRecordTime()));
                                        mRequestTime.setText(str);
                                        if (bean.getNumber() == 0) {
                                            mRequestHint.setVisibility(View.GONE);
                                            mRequestText.setText("暂无好友验证消息");
                                            mRequestTime.setText("");
                                        } else {
                                            mRequestHint.setVisibility(View.VISIBLE);
                                            if(bean.getContent()!=null && !"".equals(bean.getContent())) {
                                                mRequestText.setText(bean.getContent());
                                            }else {
                                                mRequestText.setText("暂无好友验证消息");
                                                mRequestTime.setText("");
                                            }
                                            if(bean.getNumber()>99){
                                                mRequestHint.setText("99");
                                            }else {
                                                mRequestHint.setText(bean.getNumber() + "");
                                            }
                                        }
                                    } else if (type == 7) {
                                        //互动消息
                                        messageFlag=bean.getMsgType();
                                        if(bean.getContent()!=null && !"".equals(bean.getContent())) {
                                            mMessageText.setText(bean.getContent());
                                        }else {
                                            messageFlag=1;
                                            mMessageText.setText("暂无互动消息");
                                        }
                                        String str = "";
                                        if (bean.getRecordTime() != 0)
                                            str = DateDeal.format(Time_Now.getStringTime(bean.getRecordTime()));
                                        mMessageTime.setText(str);
                                        if (bean.getNumber() == 0) {
                                            messageFlag=1;
                                            mMessageHint.setVisibility(View.GONE);
                                            mMessageText.setText("暂无互动消息");
                                            mMessageTime.setText("");
                                        } else {
                                            mMessageHint.setVisibility(View.VISIBLE);
                                            if(bean.getNumber()>99){
                                                mMessageHint.setText("99");
                                            }else {
                                                mMessageHint.setText(bean.getNumber() + "");
                                            }
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

    /**
     * 功能：该界面是否为显示状态
     * 参数：isVisibleToUser 是否正在显示
     * 返回：无
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        //界面显示的时候加载数据
        if (isVisibleToUser) {
            if (isOk) {
                getOutMessage();
                CLog.e("testing_news", "setUserVisibleHint");
            }
        }
    }


    public static boolean isBackground(Context context) {

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
            if (appProcess.processName.equals(context.getPackageName())) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_BACKGROUND) {
                    Log.i("后台", appProcess.processName);
                    return true;
                }else{
                    Log.i("前台", appProcess.processName);
                    return false;
                }
            }
        }
        return false;
    }

    /**
     *
     */
    private void showNotification(int count){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getActivity())
                        .setSmallIcon(R.drawable.icon1)
                        .setContentTitle("好友消息")
                        .setContentText(count+"位联系人发来消息")
                        .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)//设置 铃声，振动，闪烁用|分隔，常量在Notification里
                        .setAutoCancel(true);
        Intent resultIntent = new Intent(getActivity(), MainActivity.class);
        resultIntent.putExtra("Notification",6);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }

}
