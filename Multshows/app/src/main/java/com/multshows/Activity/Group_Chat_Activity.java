package com.multshows.Activity;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multshows.Adapter.Chat_Adapter;
import com.multshows.Beans.ChatContext_Beans;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Beans.ChatLogBase;
import com.multshows.Beans.GroupChatLogBase;
import com.multshows.Beans.GroupInfo;
import com.multshows.Beans.GroupMember;
import com.multshows.Beans.PersonalSetting;
import com.multshows.Fragment.Chat_Add_Fragment;
import com.multshows.Interfaces.NoticConList;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.Base64_Utils;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Utils.SystemBarUtils;
import com.multshows.Utils.System_Utils;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.MyApplication;
import com.multshows.Views.NineGridview.ImageInfo;
import com.multshows.Views.PullToRefresh;
import com.multshows.Views.PullToRefresh2;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.rong.imlib.IRongCallback;
import io.rong.imlib.MessageTag;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;
import okhttp3.Call;

/**
 * 描述：聊天界面activity
 * 作者：贾强胜
 * 时间：2016.9.18
 */
public class Group_Chat_Activity extends AppCompatActivity implements EmojiconsFragment.OnEmojiconBackspaceClickedListener,
        EmojiconGridFragment.OnEmojiconClickedListener {
    RelativeLayout mParentsLayout;
    RelativeLayout mReturnLayout;
    EmojiconEditText mContext;//发送内容
    ListView mListView;//聊天内容展示
    ImageView mReturn;//返回
    TextView mSend;//发送
    TextView mTitle;//标题
    ImageView mFace;//表情按钮
    ImageView mAdd;//特殊功能实现
    ImageView mVoice;//屏蔽提示
    ImageView mPerson;//聊天设置
    //下部布局
    RelativeLayout mParent;
    //输入框所在布局
    RelativeLayout mLayout;
    //自定义表情框
    RelativeLayout mRelativeLayout;
    Chat_Add_Fragment mAddFragment;
    //表情框高度
    int emotionHeight;
    int firstHeight;
    //表情框显示标志
    boolean face_Flag = false;
    boolean add_Flag = false;
    int Flag = 0;
    private final LayoutTransition transitioner = new LayoutTransition();//键盘和表情切换
    //groupID
    String groupID = "";
    String groupName="";

    MyApplication mMyApplication;
    NoticConList mNoticConList;
    Gson mGson = new Gson();
    SaveSharedPreferences mSharedPreferences=new SaveSharedPreferences();
    //数据集合
    List<ChatContext_Beans> mList = new ArrayList<>();
    private Handler mHandler = new Handler();
    //适配器
    Chat_Adapter mChatAdapter;
    private PullToRefresh2 mPullToRefresh;
    int pageIndexs=10;
    List<ImageInfo> imageInfo = new ArrayList<>();

    //群信息数据
    GroupInfo groupInfo;//群信息
    String GroupName="";//群名
    List<GroupMember> mUserInfo=new ArrayList<>();//群成员信息集合
    PersonalSetting mPersonalSetting;//群设置
    Emoji_Change mEmojiChange=new Emoji_Change();
    String Message_Flag="off";
    List<String> mStringList=new ArrayList<>();
    int count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);
        EventBus.getDefault().register(Group_Chat_Activity.this);
        mMyApplication= (MyApplication) getApplication();
        //沉浸式
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Group_Chat_Activity.this, R.color.background_grey);
        Login_Static.mPlaceActivity.clear();
        Login_Static.mPlaceActivity.add(Group_Chat_Activity.this);
        Login_Static.otherPic = "";
        initView();
        String type=mSharedPreferences.Get_PREFS(Group_Chat_Activity.this,"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        if("mom".equals(type)) {
            mVoice.setImageResource(R.drawable.item_talking_quiet_w);
            mPerson.setImageResource(R.drawable.item_talking_person_w);
        }else {
            mVoice.setImageResource(R.drawable.item_talking_quiet);
            mPerson.setImageResource(R.drawable.item_talking_person);
        }
        initData();
        inintListen();
        getHistory(10);

    }

    /**
     * 初始化控件
     */
    private void initView() {
        mContext = (EmojiconEditText) findViewById(R.id.chat_EditText);
        mReturn = (ImageView) findViewById(R.id.chat_return);
        mParentsLayout= (RelativeLayout) findViewById(R.id.chat_ParentsLayouts);
        mReturnLayout= (RelativeLayout) findViewById(R.id.chat_TopLayout);
        mVoice= (ImageView) findViewById(R.id.chat_Voice);
        mPerson= (ImageView) findViewById(R.id.chat_person);
        mListView = (ListView) findViewById(R.id.chat_ListView);
        mPullToRefresh= (PullToRefresh2) findViewById(R.id.chat_Refresh);
        mSend = (TextView) findViewById(R.id.chat_Send);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.chat_bottom_bottom);
        mParent = (RelativeLayout) findViewById(R.id.chat_BottomLayout);
        mLayout = (RelativeLayout) findViewById(R.id.chat_editTextLayout);
        mFace = (ImageView) findViewById(R.id.chat_Face);
        mAdd = (ImageView) findViewById(R.id.chat_Add);
        mTitle = (TextView) findViewById(R.id.chat_title);
        firstHeight = mLayout.getHeight();

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Group_Chat_Activity.this,mReturnLayout,mParentsLayout,mReturn,mTitle);
    }

    /**
     * 数据处理
     */
    private void initData() {
        Intent mIntent = getIntent();
        groupID = mIntent.getStringExtra("groupId");
        groupName=mIntent.getStringExtra("groupName");
        mTitle.setText(groupName);
        mChatAdapter = new Chat_Adapter(Group_Chat_Activity.this, mList,imageInfo,1);
        mListView.setAdapter(mChatAdapter);

    }

    /**
     * 事件处理
     */
    private void inintListen() {

        //返回事件
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                HideInputManager_Utils.hideInputManager(Group_Chat_Activity.this);
               if (mRelativeLayout.getVisibility() == View.VISIBLE) {
                    mRelativeLayout.setVisibility(View.GONE);
                } else {
                    finish();
                }
            }
        });
        //群聊设置
        mPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Group_Chat_Activity.this,Group_Setting_Activity.class);
                intent.putExtra("groupID",groupID);
                intent.putExtra("groupInfo",groupInfo);
                startActivity(intent);
            }
        });
        //下拉加载
        mPullToRefresh.setOnHeaderRefreshListener(new PullToRefresh2.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefresh2 view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageIndexs=pageIndexs+10;
                        getHistory(pageIndexs);
                    }
                },2000);
            }
        });


        //发送
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String text=mContext.getText().toString();
                if ("".equals(text)) {

                } else {
                    /**
                     * 发送普通消息
                     *
                     * @param conversationType      会话类型
                     * @param targetId              会话ID
                     * @param content               消息的内容，一般是MessageContent的子类对象
                     * @param pushContent           接收方离线时需要显示的push消息内容
                     * @param pushData              接收方离线时需要在push消息中携带的非显示内容
                     * @param SendMessageCallback   发送消息的回调
                     * @param ResultCallback        消息存库的回调，可用于获取消息实体
                     *
                     */
                    TextMessage textMessage=TextMessage.obtain(text);
                    textMessage.setExtra(Message_Flag);
                    RongIMClient.getInstance().sendMessage(Conversation.ConversationType.DISCUSSION, groupID,
                            textMessage, null,null, new IRongCallback.ISendMediaMessageCallback() {
                                @Override
                                public void onProgress(Message message, int i) {

                                }

                                @Override
                                public void onAttached(Message message) {

                                }

                                @Override
                                public void onSuccess(Message message) {
                                    CLog.e("onMessage", "发送成功");
                                    //添加历史记录
                                    putHistoryMessage(Login_Static.myUserID, groupID, mContext.getText().toString(), Time_Now.getNowTime());
                                    ChatContext_Beans beans;
                                    if(Login_Static.mAccount!=null) {
                                        beans = new ChatContext_Beans(Login_Static.myUserID,Login_Static.mAccount.getNickName(),
                                                SubString_Utils.getImageUrl(Login_Static.mAccount.getPortrait())
                                                , text, true, Time_Now.getRongTime(), 0);
                                    }else {
                                        beans = new ChatContext_Beans(Login_Static.myUserID,"",
                                                "", text, true, Time_Now.getRongTime(), 0);
                                    }
                                    mList.add(beans);
                                    mContext.setText("");
                                    mChatAdapter.notifyDataSetChanged();
                                    if(mList.size()!=0)
                                        mListView.setSelection(mList.size()-1);
                                }

                                @Override
                                public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                                    CLog.e("onMessage", "发送失败");
                                }
                            });

                }
            }
        });
        //表情按钮点击事件
        mFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmojiconFragment(false);
                if (add_Flag && Flag == 2) {
                    face_Flag = true;
                    add_Flag=false;
                } else if (face_Flag) {
                    //隐藏
                    //点击输入框，打开软键盘，隐藏表情
                    face_Flag = false;
                    hideEmotionView(true);
                } else {
                    //显示
                    //点击表情图标,如果表情显示，隐藏表情，打开软键盘。反之，显示表情，隐藏键盘
                    face_Flag = true;
                    showEmotionView(System_Utils.isKeyBoardShow(Group_Chat_Activity.this));
                }
                Flag = 1;
            }
        });
        //特殊功能实现
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAddFragment();
                if (face_Flag && Flag == 1) {
                    add_Flag = true;
                    face_Flag=false;
                    showEmotionView(System_Utils.isKeyBoardShow(Group_Chat_Activity.this));
                } else if (add_Flag) {
                    //隐藏
                    add_Flag = false;
                    hideEmotionView(true);
                } else {
                    //显示
                    add_Flag = true;
                    showEmotionView(System_Utils.isKeyBoardShow(Group_Chat_Activity.this));
                }
                Flag = 2;
            }
        });

        //输入框点击事件
        mContext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //点击输入框，打开软键盘，隐藏表情
                face_Flag = false;
                add_Flag = false;
                hideEmotionView(true);
                if(mList.size()!=0)
                    mListView.setSelection(mList.size()-1);
                return false;
            }
        });
        //失去焦点时输入法消失
        mParent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //Toast.makeText(PersonalLetter_Activity.this, "消失", Toast.LENGTH_SHORT).show();
                    mParent.clearFocus();
                    HideInputManager_Utils.hideInputManager(Group_Chat_Activity.this);
                    if (mRelativeLayout.getVisibility() == View.VISIBLE) {
                        mRelativeLayout.setVisibility(View.GONE);
                    }
                }

            }
        });
        RongIMClient.setOnReceiveMessageListener(new MyReceiveMessageListener());

    }

    @Override
    protected void onResume() {
        super.onResume();
        getGroupInformation();
    }
    //点击
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if (mRelativeLayout.isShown()) {
                    mRelativeLayout.setVisibility(View.GONE);
                }else {
                    HideInputManager_Utils.hideInputManager(Group_Chat_Activity.this);
                }
                if (hideInputMethod(this, v)) {
                    return true; //隐藏键盘时，其他控件不响应点击事件==》注释则不拦截点击事件
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }
    public static Boolean hideInputMethod(Context context, View v) {
        InputMethodManager imm = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            return imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
        return false;
    }
    public static boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            v.getLocationInWindow(leftTop);
            int left = leftTop[0] - 200, top = leftTop[1], bottom = top + v.getHeight() + 2500, right = left
                    + v.getWidth() + 400;//可正常点击的区域
            return !(event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom);
        }
        return false;
    }

    private void getHistory(int number) {
        //获取本地历史消息
        RongIMClient.getInstance().getLatestMessages(Conversation.ConversationType.DISCUSSION,
                groupID, number, new RongIMClient.ResultCallback<List<Message>>() {
                    @Override
                    public void onSuccess(List<Message> messages) {
                        mPullToRefresh.onHeaderRefreshComplete();
                        if (messages != null) {
                            mList.clear();
                            imageInfo.clear();
                            for (int i = messages.size() - 1; i >= 0; i--) {
                                String mes = null;
                                MessageTag messageTag = null;
                                try {
                                    //获得json字符串
                                    String json = new String(messages.get(i).getContent().encode(), "UTF-8");
                                    CLog.e("json_message", json);
                                    messageTag = messages.get(i).getContent().getClass().getAnnotation(MessageTag.class);
                                    //RC:TxtMsg, RC:VcMsg, RC:ImgMsg
                                    if (messageTag.value().equals("RC:TxtMsg")) {
                                        JSONObject jsonObject = new JSONObject(json);
                                        mes = jsonObject.getString("content");
                                    } else if(messageTag.value().equals("RC:ImgMsg")){
                                        JSONObject jsonObject = new JSONObject(json);
                                        mes = jsonObject.getString("imageUri");
                                    }else {
                                        mes="";
                                    }

                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ChatContext_Beans beans;
                                if (messageTag.value().equals("RC:TxtMsg")) {
                                    //文字信息
                                    if (messages.get(i).getSenderUserId().equals(Login_Static.myUserID)) {
                                        beans = new ChatContext_Beans(messages.get(i).getSenderUserId(),Login_Static.mAccount.getNickName(),
                                                SubString_Utils.getImageUrl(Login_Static.mAccount.getPortrait()), mes, true,
                                                Time_Now.getRongTime(messages.get(i).getSentTime()), 0);
                                        mList.add(beans);
                                    } else {
                                        beans = new ChatContext_Beans(messages.get(i).getSenderUserId(),getUserNickName(messages.get(i).getSenderUserId()),
                                                getUserHeader(messages.get(i).getSenderUserId()), mes, true,
                                                Time_Now.getRongTime(messages.get(i).getSentTime()), 1);
                                        mList.add(beans);
                                    }
                                } else if(messageTag.value().equals("RC:ImgMsg")){
                                    //图片信息
                                    if (messages.get(i).getSenderUserId().equals(Login_Static.myUserID)) {
                                        beans = new ChatContext_Beans(messages.get(i).getSenderUserId(),Login_Static.mAccount.getNickName(),
                                                SubString_Utils.getImageUrl(Login_Static.mAccount.getPortrait()), mes, false,
                                                Time_Now.getRongTime(messages.get(i).getSentTime()), 2);
                                        mList.add(beans);
                                    } else {
                                        beans = new ChatContext_Beans(messages.get(i).getSenderUserId(),getUserNickName(messages.get(i).getSenderUserId()),
                                                getUserHeader(messages.get(i).getSenderUserId()), mes, false,
                                                Time_Now.getRongTime(messages.get(i).getSentTime()), 3);
                                        mList.add(beans);
                                    }
                                    ImageInfo info = new ImageInfo();
                                    info.setThumbnailUrl(mes);
                                    info.setBigImageUrl(mes);
                                    imageInfo.add(info);
                                }

                            }
                        }
                        mChatAdapter = new Chat_Adapter(Group_Chat_Activity.this, mList,imageInfo,1);
                        mListView.setAdapter(mChatAdapter);
                        mChatAdapter.notifyDataSetChanged();
                        if(mList.size()!=0)
                            mListView.setSelection(mList.size()-1);
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        mPullToRefresh.onHeaderRefreshComplete();
                    }
                });
    }


    // 隐藏软键盘
    public void hideInputManager(Context ct) {
        try {
            ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(((Activity) ct)
                    .getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            CLog.e("error", "异常");
        }
    }

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.chat_bottom_fragment, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }

    private void setAddFragment() {
        mAddFragment = new Chat_Add_Fragment();
        Bundle bundle = new Bundle();
        bundle.putString("otherID", groupID);
        bundle.putInt("IDType", 2);
        bundle.putString("Message",Message_Flag);
        mAddFragment.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.chat_bottom_fragment, mAddFragment)
                .commit();
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mContext);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mContext, emojicon);
    }


    /**
     * 隐藏emoji
     **/
    private void hideEmotionView(boolean showKeyBoard) {
        if (mRelativeLayout.isShown()) {
            if (showKeyBoard) {
                RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams) mLayout.getLayoutParams();
                localLayoutParams.height = mRelativeLayout.getTop();
                localLayoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                mRelativeLayout.setVisibility(View.GONE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                System_Utils.showKeyBoard(mContext);
                mContext.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        unlockContainerHeightDelayed();
                    }

                }, 200L);
            } else {
                mRelativeLayout.setVisibility(View.GONE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                unlockContainerHeightDelayed();
            }
        }
    }

    private void showEmotionView(boolean showAnimation) {
        if (showAnimation) {
            transitioner.setDuration(200);
        } else {
            transitioner.setDuration(0);
        }

        int statusBarHeight = SystemBarUtils.getStatusBarHeight(Group_Chat_Activity.this);
        emotionHeight = System_Utils.getKeyboardHeight(Group_Chat_Activity.this);

        System_Utils.hideSoftInput(mContext);
        mRelativeLayout.getLayoutParams().height = emotionHeight;
        mRelativeLayout.setVisibility(View.VISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //在5.0有navigationbar的手机，高度高了一个statusBar
        int lockHeight = System_Utils.getAppContentHeight(Group_Chat_Activity.this);
//            lockHeight = lockHeight - statusBarHeight;
        lockContainerHeight(lockHeight);
    }

    private void lockContainerHeight(int paramInt) {
        //RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams) mLayout.getLayoutParams();
        // localLayoutParams.height = emotionHeight;
        // localLayoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
    }

    public void unlockContainerHeightDelayed() {
        // ((RelativeLayout.LayoutParams) mLayout.getLayoutParams()).height =emotionHeight;
    }

    //发送图片
    @Subscribe
    public void onEvent(ChatEvent_Model model) {
        if("Group".equals(model.getFlag())) {
            String url = model.getUrl();
            //添加历史记录
            putHistoryMessage(Login_Static.myUserID, groupID,url, Time_Now.getNowTime());
            ChatContext_Beans beans;
            if(Login_Static.mAccount!=null) {
                beans = new ChatContext_Beans(Login_Static.myUserID,Login_Static.mAccount.getNickName(),
                        SubString_Utils.getImageUrl(Login_Static.mAccount.getPortrait())
                        , url, false, Time_Now.getRongTime(), 2);
            }else {
                beans = new ChatContext_Beans(Login_Static.myUserID,"",
                        "",url, false, Time_Now.getRongTime(), 2);
            }
            mList.add(beans);
            ImageInfo info = new ImageInfo();
            info.setThumbnailUrl(url);
            info.setBigImageUrl(url);
            imageInfo.add(info);
            mChatAdapter.notifyDataSetChanged();
            if(mList.size()!=0)
                mListView.setSelection(mList.size()-1);
        }else if("AddUser".equals(model.getFlag())){
            getGroupInformation();
        }else if("Groupclear".equals(model.getFlag())){
            mList.clear();
            mChatAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //消除未读条数
        RongIMClient.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.DISCUSSION, groupID,
                new RongIMClient.ResultCallback<Boolean>() {
                    @Override
                    public void onSuccess(Boolean aBoolean) {
                        CLog.e("RongYun", "清除未读消息成功");
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        CLog.e("RongYun", "清除未读消息失败");
                    }
                });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(Group_Chat_Activity.this);
    }

    private class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

        /**
         * 收到消息的处理。
         *
         * @param message 收到的消息实体。
         * @param left    剩余未拉取消息数目。
         * @return
         */
        @Override
        public boolean onReceived(Message message, int left) {
            //开发者根据自己需求自行处理
            if(isBackground(Group_Chat_Activity.this)){
                //后台
                if("on".equals(Message_Flag)){
                    count=0;
                }else {
                    if(mStringList.size()==0){
                        count=1;
                    }
                    for (int n = 0; n < mStringList.size(); n++) {
                        if(mStringList.get(n).equals(message.getMessageId()+"")){

                        }else {
                            mStringList.add(message.getMessageId()+"");
                            count++;
                        }
                    }
                    showNotification(count);
                }
            }else {
                //前台
                count=0;
            }
            String mes = null;
            if (message.getTargetId().equals(groupID)) {
                MessageTag messageTag = null;
                try {
                    //获得json字符串
                    String json = new String(message.getContent().encode(), "UTF-8");
                    CLog.e("json_message", json);
                    messageTag = message.getContent().getClass().getAnnotation(MessageTag.class);
                    //RC:TxtMsg, RC:VcMsg, RC:ImgMsg
                    if (messageTag.value().equals("RC:TxtMsg")) {
                        JSONObject jsonObject = new JSONObject(json);
                        mes = jsonObject.getString("content");
                    }else if (messageTag.value().equals("RC:ImgMsg")){
                        JSONObject jsonObject = new JSONObject(json);
                        mes = jsonObject.getString("imageUri");
                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ChatContext_Beans beans;
                if (messageTag.value().equals("RC:TxtMsg")) {
                    //文字信息
                    if (message.getSenderUserId().equals(Login_Static.myUserID)) {
                        beans = new ChatContext_Beans(message.getSenderUserId(),Login_Static.mAccount.getNickName(),
                                SubString_Utils.getImageUrl(Login_Static.mAccount.getPortrait()), mes, true,
                                Time_Now.getRongTime(message.getSentTime()), 0);
                        mList.add(beans);
                    } else {
                        beans = new ChatContext_Beans(message.getSenderUserId(),getUserNickName(message.getSenderUserId()),
                                getUserHeader(message.getSenderUserId()), mes, true,
                                Time_Now.getRongTime(message.getSentTime()), 1);
                        mList.add(beans);
                    }
                } else {
                    //图片信息
                    if (message.getSenderUserId().equals(Login_Static.myUserID)) {
                        beans = new ChatContext_Beans(message.getSenderUserId(),Login_Static.mAccount.getNickName(),
                                SubString_Utils.getImageUrl(Login_Static.mAccount.getPortrait()), mes, false,
                                Time_Now.getRongTime(message.getSentTime()), 2);
                        mList.add(beans);
                    } else {
                        beans = new ChatContext_Beans(message.getSenderUserId(),getUserNickName(message.getSenderUserId()),
                                getUserHeader(message.getSenderUserId()), mes, false,
                                Time_Now.getRongTime(message.getSentTime()), 3);
                        mList.add(beans);
                    }
                    ImageInfo info = new ImageInfo();
                    info.setThumbnailUrl(mes);
                    info.setBigImageUrl(mes);
                    imageInfo.add(info);
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        /*mChatAdapter = new Chat_Adapter(Chat_Activity.this, mList);
                        mListView.setAdapter(mChatAdapter);*/
                        mChatAdapter.notifyDataSetChanged();
                        if(mList.size()!=0)
                            mListView.setSelection(mList.size()-1);
                    }
                });
            }
            return false;
        }
    }


    //按键事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // ((RelativeLayout.LayoutParams) mLayout.getLayoutParams()).height =100;
            if (mRelativeLayout.getVisibility() == View.VISIBLE) {
                mRelativeLayout.setVisibility(View.GONE);
            } else {
                finish();
            }
        }
        return true;
    }

    //添加历史消息记录
    private void putHistoryMessage(String sendId, String RCId, String content, int time) {
        GroupChatLogBase chatLogBase = new GroupChatLogBase();
        chatLogBase.setUserId(sendId);
        chatLogBase.setRCGroupId(RCId);
        chatLogBase.setContent(mEmojiChange.EmojiChange(content));
        chatLogBase.setRecordTime(time);
        String data = mGson.toJson(chatLogBase);
        CLog.e("testing", "添加群聊历史消息记录data:" + data);
        OKHttp.OkHttpPost("/GroupChat/AddGroupChatLog", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "添加群聊历史消息记录失败" + e.toString());
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "添加群聊历史消息记录" + response);
            }
        });
    }

    /**
     * 获取群基本信息
     *
     *
     */
    private void getGroupInformation(){
        OkHttpUtils.get().url(mMyApplication.getUrl()+"/GroupChat/GetGroup")
                .addParams("currentUserId",Login_Static.myUserID)//加密当前用户id
                .addParams("RCGroupId",groupID)//融云提供的群id
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing","获取群信息失败"+e.toString());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing","获取群信息"+response);
                        try {
                            if(Json_Utils.getCode(response)==0){
                                groupInfo=mGson.fromJson(Json_Utils.getTemplate(response),GroupInfo.class);
                                mTitle.setText(groupInfo.getGroupName());
                                mUserInfo=groupInfo.getGroupMembers();
                                mPersonalSetting=groupInfo.getPersonalSetting();
                                if(mPersonalSetting.getNotifyType()==1){
                                    Message_Flag="off";
                                    mVoice.setVisibility(View.GONE);
                                }else {
                                    Message_Flag="on";
                                    mVoice.setVisibility(View.VISIBLE);
                                }
                                for (int i = 0; i < mList.size(); i++) {
                                    mList.get(i).setUserPicUrl(getUserHeader(mList.get(i).getUid()));
                                    mList.get(i).setNickName(getUserNickName(mList.get(i).getUid()));
                                }
                                mChatAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    private String getUserHeader(String userID){
        for (int i = 0; i < mUserInfo.size(); i++) {
            if(userID.equals(mUserInfo.get(i).getUserId())){
                String pic=mUserInfo.get(i).getUserHead().getPortrait();
                if(!"null".equals(pic))
                return SubString_Utils.getImageUrl(pic);
            }
        }
        return "";
    }
    private String getUserNickName(String userID){
        for (int i = 0; i < mUserInfo.size(); i++) {
            if(userID.equals(mUserInfo.get(i).getUserId())){
                String pic=mUserInfo.get(i).getUserHead().getNickName();
                return pic;
            }
        }
        return "";
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
                new NotificationCompat.Builder(Group_Chat_Activity.this)
                        .setSmallIcon(R.drawable.icon1)
                        .setContentTitle("好友消息")
                        .setContentText(count+"位联系人发来消息")
                        .setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE)//设置 铃声，振动，闪烁用|分隔，常量在Notification里
                        .setAutoCancel(true);
        Intent resultIntent = new Intent(Group_Chat_Activity.this, MainActivity.class);
        resultIntent.putExtra("Notification",6);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(Group_Chat_Activity.this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
