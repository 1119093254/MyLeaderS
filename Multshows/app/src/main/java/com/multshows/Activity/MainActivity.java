package com.multshows.Activity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.multshows.Adapter.Change_popupwindow_adapter;
import com.multshows.Adapter.MyFragmentPagerAdapter;
import com.multshows.Beans.EventBus_Model;
import com.multshows.Beans.SystemVersion;
import com.multshows.Beans.User;
import com.multshows.Beans.UserBase;
import com.multshows.Beans.UserPrimaryKey;
import com.multshows.Beans.UserTerm;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.BaseAppManager;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.ChangeUser_Pop;
import com.multshows.Views.CustomViewPager;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyReleaseWindow;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;
import io.rong.imlib.RongIMClient;
import okhttp3.Call;


/**
 * 描述：主页activity,加载不同的viewpage
 * 作者：李萧萧
 * 时间：2016.8/30
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        ViewPager.OnPageChangeListener, EmojiconsFragment.OnEmojiconBackspaceClickedListener, EmojiconGridFragment.OnEmojiconClickedListener {
    //妈妈端按钮下的文字
    @Bind(R.id.home_Text)
    TextView mHomeText;//主页文本
    @Bind(R.id.find_Text)
    TextView mFindText;//发现文本
    @Bind(R.id.news_Text)
    TextView mNewsText;//消息文本
    @Bind(R.id.mys_Text)
    TextView mMysText;//我的文本
    //宝宝端按钮下的文字
    @Bind(R.id.babyTask)
    TextView mBabyTask;//作品文本
    @Bind(R.id.BabyWork_Text)
    TextView mBabyWork_Text;//任务文本
    @Bind(R.id.BabyNews_Text)
    TextView mBabyNews_Text;//消息文本
    @Bind(R.id.BabyMy_Text)
    TextView mBabyMy_Text;//我的文本
    @Bind(R.id.identity_switchimg)
    SimpleDraweeView identity_switchimg;//身份切换图标
    @Bind(R.id.comment_BottomLayout)
    RelativeLayout Comment_Layout;  //底部表情布局
    @Bind(R.id.comment_bottom_bottom)
    RelativeLayout mComment_BottomLayout;  //评论布局
    @Bind(R.id.bootom)
    RelativeLayout mBootom;  //妈妈底部导航 布局
    @Bind(R.id.BabyBootom)
    RelativeLayout mBabyBootom;  //宝宝底部导航 布局
    @Bind(R.id.comment_EditText)
    EmojiconEditText comment_EditText;  //评论文本框
    List<UserBase> mUserBase;//存放身份切换获取到的用户信息
    Dialog_Hint mDialogHint;//前去登录
    Change_popupwindow_adapter groupAdapter;//切换身份listview适配器
    public static boolean flag = false;//记录当前fragment是圈子还是作品，false代表圈子，true代表作品
    public static boolean isShow = false;//记录表情是否正在显示,打赏支付，
    public static boolean isShow2 = true;//记录表情是否正在显示
    //public static final String TOKEN = "jnx8dnGsiRA/v0V5+QZLGrBWr4MuOHOgf5CpKbjqV1hxdCIWbwiPIBXdB6k4K23qqx+dmDUz8ROMdu8PIOZ8Vw\u003d\u003d";//0002
    MyReleaseWindow mMyReleaseWindow;
    Dialog_Hint hintText_dialog;
    Dialog_Hint hintText_dialog2;
    MyApplication mApplication;
    //UI Objects
    private boolean isOnKeyBacking;
    private Handler mHandler;
    private Toast mBackToast;

    private PopupWindow popupWindow;//切换身份popwindow
    private View view;
    private ListView lv_group;//切换身份listview
    //宝宝端 radioButton
    private RadioGroup rg_babyGroup;
    private RadioButton rb_baby1;
    private RadioButton rb_baby2;
    private RadioButton rb_baby3;
    private RadioButton rb_baby4;

    //妈妈端端 radioButton
    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;
    private RadioButton rb_message;
    private RadioButton rb_better;
    private RadioButton rb_setting;
    private RelativeLayout mainactivityTop;
    private CustomViewPager vpager;
    ImageView mAddImage;//控制发布布局显示
    ImageView mAddBabyImage;//控制发布布局显示
    private MyFragmentPagerAdapter mAdapter;
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;
    public static Bitmap bitmap_;//勿动
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    ChangeUser_Pop mChangeUser_Pop;
    Gson mGson = new Gson();
    int screenHeight;
    Dialog mDialog;
    InputMethodManager imm;
    int Noti_type = 0;
    String mom_Type = "";
    boolean mflag=true;//点击作品是否刷新置顶，(只有当前显示作品页，点击作品才会置顶刷新)
    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(comment_EditText);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(comment_EditText, emojicon);
    }


    public interface OnSoftKeyboardStateChangedListener {
        void OnSoftKeyboardStateChanged(boolean isKeyBoardShow, int keyboardHeight);
    }

    //注册软键盘状态变化监听
    public void addSoftKeyboardChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (listener != null) {
            mKeyboardStateListeners.add(listener);
        }
    }

    //取消软键盘状态变化监听
    public void removeSoftKeyboardChangedListener(OnSoftKeyboardStateChangedListener listener) {
        if (listener != null) {
            mKeyboardStateListeners.remove(listener);
        }
    }

    private ArrayList<OnSoftKeyboardStateChangedListener> mKeyboardStateListeners;      //软键盘状态监听列表
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener;
    private boolean mIsSoftKeyboardShowing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Login_Static.ShowsClassId="";
        mApplication = (MyApplication) this.getApplicationContext();


        mHandler = new Handler(Looper.getMainLooper());
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mKeyboardStateListeners = new ArrayList<OnSoftKeyboardStateChangedListener>();
        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        screenHeight = wm.getDefaultDisplay().getHeight();
        bitmap_ = BitmapFactory.decodeResource(
                getResources(),
                R.drawable.upimg_logo);
        mom_Type = mSave.Get_PREFS(MainActivity.this, "Type");
        if (mom_Type.equals("mom")) {
            //妈妈端
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            tintManageUtils.setSystemBarTintManage(MainActivity.this, R.color.green);
        } else {
            //宝宝端
            MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
            //tintManageUtils.setSystemBarTintManage(MainActivity.this, R.color.app_babyColor);
            identity_switchimg.setImageResource(R.drawable.icon_returnimg);
            tintManageUtils.setSystemBarTintManage_noColor(MainActivity.this);
        }


        Login_Static.mActivityList.clear();
        Login_Static.mActivityList.add(MainActivity.this);
        CLog.e("testingst","nd:"+Login_Static.myUserID+"的token: "+mSave.Get_PREFS(MainActivity.this, Login_Static.myUserID+"token"));
        getToken(1000);
        mDialog = new HintText_Dialog(MainActivity.this, R.style.MyDialog);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this);
        bindViews();
        mIsSoftKeyboardShowing = false;
        mLayoutChangeListener = new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //判断窗口可见区域大小
                Rect r = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
                //如果屏幕高度和Window可见区域高度差值大于整个屏幕高度的1/3，则表示软键盘显示中，否则软键盘为隐藏状态。
                //键盘显示的时候，显示评论输入框布局
                int heightDifference = screenHeight - (r.bottom - r.top);
                CLog.e("heightDifference", "" + heightDifference);
                CLog.e("screenHeight", "" + screenHeight);
                boolean isKeyboardShowing = heightDifference > screenHeight / 3;
                if (isKeyboardShowing || isShow) {//键盘显示
                    if (isShow2) {
                        Comment_Layout.setVisibility(View.VISIBLE);
                    }

                    mBabyBootom.setVisibility(View.GONE);
                    mBootom.setVisibility(View.GONE);

                } else if (!isShow) {
                    Comment_Layout.setVisibility(View.INVISIBLE);
                    if (mSave.Get_PREFS(MainActivity.this, "Type").equals("baby")) {
                        mBabyBootom.setVisibility(View.VISIBLE);
                        mBootom.setVisibility(View.GONE);
                    } else {
                        mBootom.setVisibility(View.VISIBLE);
                        mBabyBootom.setVisibility(View.GONE);
                    }


                }
                //如果之前软键盘状态为显示，现在为关闭，或者之前为关闭，现在为显示，则表示软键盘的状态发生了改变
                if ((mIsSoftKeyboardShowing && !isKeyboardShowing) || (!mIsSoftKeyboardShowing && isKeyboardShowing)) {
                    mIsSoftKeyboardShowing = isKeyboardShowing;
                    for (int i = 0; i < mKeyboardStateListeners.size(); i++) {
                        OnSoftKeyboardStateChangedListener listener = mKeyboardStateListeners.get(i);
                        listener.OnSoftKeyboardStateChanged(mIsSoftKeyboardShowing, heightDifference);
                    }
                }
            }
        };
        //注册布局变化监听
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
        initLister();
        if (mSave.Get_PREFS(MainActivity.this, "Type").equals("mom")) {
            mainactivityTop.setBackgroundColor(getResources().getColor(R.color.white));
        } else if (mSave.Get_PREFS(MainActivity.this, "Type").equals("baby")) {
            //mainactivityTop.setBackgroundResource(R.drawable.zhuti);
        }
        rb_channel.setChecked(true);


    }

    @Override
    public void findViews() {

    }

    @Override
    public void addListeners() {

    }


    /**
     * 获取个人信息数据
     */
    private void initData() {
        if (mSave.Get_PREFS(MainActivity.this, "Unlogin").equals("yes")) {
            identity_switchimg.setVisibility(View.INVISIBLE);
        }
        mSave.Save_PREFS(MainActivity.this, "idleFresh", "no");
        mUserBase = new ArrayList<>();
        Intent intent_ = getIntent();
        Noti_type = intent_.getIntExtra("Notification", 0);
        GetSystemVersion();
        OkHttpUtils.get().url(mApplication.getmUrl()+"?type=1")
                .addParams("userid", Login_Static.myUserID)
                .addParams("targetuserid", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "个人信息 失败" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            CLog.e("testing", "个人信息：" + Json_Utils.getTemplate(response));
                            if (Json_Utils.getCode(response) == 0) {
                                User mAccount = mGson.fromJson(Json_Utils.getTemplate(response), User.class);
                                Login_Static.mAccount = mGson.fromJson(Json_Utils.getTemplate(response), User.class);
                                if (mSave.Get_PREFS(MainActivity.this, "Unlogin").equals("yes")) {
                                    JPushInterface.setAlias(MainActivity.this, "领秀S", new TagAliasCallback() {
                                        @Override
                                        public void gotResult(int i, String s, Set<String> set) {
                                            Log.e("JPush", "Jpush status: " + i + s);//状态
                                          // String regId = JPushInterface.getRegistrationID(MainActivity.this);
                                          //  CLog.e("regId",""+regId);
                                        }
                                    });//极光推送设置设备名称
                                }else {
                                    if(Login_Static.mAccount!=null){
                                        JPushInterface.setAlias(MainActivity.this, "领秀S" + Login_Static.mAccount.getAccountId(), new TagAliasCallback() {
                                            @Override
                                            public void gotResult(int i, String s, Set<String> set) {

                                                Log.i("JPush", "Jpush status: " + i + s);//状态
                                                String regId = JPushInterface.getRegistrationID(MainActivity.this);
                                                changeAppKey( Login_Static.mAccount.getAccountId(),regId);
                                                CLog.e("regId",""+regId);
                                            }
                                        });//极光推送设置设备名称
                                    }

                                }
                                identity_switchimg.setImageURI(Uri.parse(SubString_Utils.getImageUrl(Login_Static.mAccount.getPortrait())));
                                getBabyList(1, 0);
                                if (mAccount.getUserType() == 0 && "mom".equals(mom_Type)) {
                                    //妈妈信息
                                    mSave.Save_PREFS(MainActivity.this, "momUserId", mAccount.getUserId());
                                    mSave.Save_PREFS(MainActivity.this, "momNickName", mAccount.getNickName());
                                    mSave.Save_PREFS(MainActivity.this, "momPic", mAccount.getPortrait());
                                    mSave.Save_PREFS(MainActivity.this, "momPhone", mAccount.getMobile());
                                } else if (mAccount.getUserType() == 1 && "baby".equals(mom_Type)) {
                                    OkHttpUtils.get().url(mApplication.getmUrl()+"?type=1")
                                            .addParams("userid", mAccount.getParentId())
                                            .addParams("targetuserid", "0")
                                            .build()
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onError(Call call, Exception e, int id) {

                                                }

                                                @Override
                                                public void onResponse(String response, int id) {
                                                    try {
                                                        if (Json_Utils.getCode(response) == 0) {
                                                            User mAccount = mGson.fromJson(Json_Utils.getTemplate(response), User.class);
                                                            if (mAccount.getUserType() == 0) {
                                                                //妈妈信息
                                                                mSave.Save_PREFS(MainActivity.this, "momUserId", mAccount.getUserId());
                                                                mSave.Save_PREFS(MainActivity.this, "momNickName", mAccount.getNickName());
                                                                mSave.Save_PREFS(MainActivity.this, "momPic", mAccount.getPortrait());
                                                                mSave.Save_PREFS(MainActivity.this, "momPhone", mAccount.getMobile());
                                                            }
                                                        }
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            });
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mSave.Get_PREFS(MainActivity.this, "Unlogin").equals("yes")){
            if(Login_Static.myUserID==null||Login_Static.myUserID.equals("")){
                //判断登录状态下，数据是否被回收，重启应用
                Intent intent = getBaseContext().getPackageManager()
                        .getLaunchIntentForPackage(getBaseContext().getPackageName());
                PendingIntent restartIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
                AlarmManager mgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent); // 1秒钟后重启应用
                System.exit(0);
            }
        }
        initData();
        if (Noti_type == 6) {
            vpager.setCurrentItem(2);
            Noti_type = 0;
        }
        CLog.e("Get_PREFS", mSave.Get_PREFS(MainActivity.this, "intent"));
        if (mSave.Get_PREFS(MainActivity.this, "intent").equals("activity")) {
            rg_tab_bar.check(R.id.rb_message);
            if (mSave.Get_PREFS(MainActivity.this, "Type").equals("mom")) {
                vpager.setCurrentItem(1);
                mAdapter.mFindfragment.mFindViewpage.setCurrentItem(2);
            }
          /*  if(mSave.Get_PREFS(MainActivity.this, "Type").equals("baby")){
                vpager.setCurrentItem(1);
                mAdapter.mFind_BabyFragment.mFindViewpage.setCurrentItem(2);
            }*/

        } else if (mSave.Get_PREFS(MainActivity.this, "intent").equals("work")) {
            rg_tab_bar.check(R.id.rb_channel);
            rg_babyGroup.check(R.id.rb_baby1);
            vpager.setCurrentItem(0);
            if (mSave.Get_PREFS(MainActivity.this, "Type").equals("mom")) {
                mAdapter.mHomefragment.mHomeViewpage.setCurrentItem(0);
            } else if (mSave.Get_PREFS(MainActivity.this, "Type").equals("baby")) {
                mAdapter.mHomefragment.mHomeViewpage.setCurrentItem(0);
            }
        } else if (mSave.Get_PREFS(MainActivity.this, "intent").equals("cricle")) {
            if (mSave.Get_PREFS(MainActivity.this, "Type").equals("mom")) {
                vpager.setCurrentItem(0);
                mAdapter.mHomefragment.mHomeViewpage.setCurrentItem(1);
            }
        } else if (mSave.Get_PREFS(MainActivity.this, "intent").equals("idle")) {
            rg_tab_bar.check(R.id.rb_message);
            vpager.setCurrentItem(PAGE_TWO);
            if (mSave.Get_PREFS(MainActivity.this, "Type").equals("mom")) {
                mAdapter.mFindfragment.mFindViewpage.setCurrentItem(1);
            }/*else  if(mSave.Get_PREFS(MainActivity.this, "Type").equals("baby")){
                mAdapter.mFind_BabyFragment.mFindViewpage.setCurrentItem(1);
            }*/
        } else if (mSave.Get_PREFS(MainActivity.this, "intent").equals("task")) {
            vpager.setCurrentItem(PAGE_TWO);
            mAdapter.mFindfragment.mFindViewpage.setCurrentItem(0);
            rg_babyGroup.check(R.id.rb_baby2);
            rg_tab_bar.check(R.id.rb_message);
            mSave.Save_PREFS(MainActivity.this, "intent", "no");
            /*else  if(mSave.Get_PREFS(MainActivity.this, "Type").equals("baby")){
                mAdapter.mFind_BabyFragment.mFindViewpage.setCurrentItem(1);
            }*/
        }
        /*else if(mSave.Get_PREFS(MainActivity.this,"intent").equals("publicwelfare")){
            rg_tab_bar.check(R.id.rb_message);
            vpager.setCurrentItem(1);
            mAdapter.mFind_BabyFragment.mFindViewpage.setCurrentItem(3);
        }*/
    }

    private void initLister() {
        identity_switchimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSave.Get_PREFS(MainActivity.this, "Unlogin").equals("yes")) {
                    identity_switchimg.setEnabled(false);
                    if (mUserBase.size() > 1&&Login_Static.mAccount!=null) {
                        showWindow();
                    } else
                        getBabyList(1, 1);
                } else {

                    mDialogHint = new Dialog_Hint(MainActivity.this, 0,
                            "前去登录?", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(MainActivity.this, LoginActivity.class);
                            startActivity(intent);
                            mDialogHint.dismiss();
                        }
                    });
                    mDialogHint.show();
                }


            }
        });
        mAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyReleaseWindow = new MyReleaseWindow(MainActivity.this, itemsOnClick);
                //设置弹出动画效果
                //    mMyReleaseWindow.setAnimationStyle(R.style.PopupAnimation);
                //显示窗口  //设置layout在PopupWindow中显示的位置
                mMyReleaseWindow.showAtLocation(findViewById(R.id.mainactivityTop),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
        mAddBabyImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMyReleaseWindow = new MyReleaseWindow(MainActivity.this, itemsOnClick);
                //设置弹出动画效果
                //    mMyReleaseWindow.setAnimationStyle(R.style.PopupAnimation);
                //显示窗口  //设置layout在PopupWindow中显示的位置
                mMyReleaseWindow.showAtLocation(findViewById(R.id.mainactivityTop),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });
    }

    /**
     * 功能：初始化控件
     * 参数：无
     * 返回：无
     */
    private void bindViews() {
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rb_channel = (RadioButton) findViewById(R.id.rb_channel);
        rb_message = (RadioButton) findViewById(R.id.rb_message);
        rb_better = (RadioButton) findViewById(R.id.rb_better);
        rb_setting = (RadioButton) findViewById(R.id.rb_setting);

        rg_babyGroup = (RadioGroup) findViewById(R.id.rg_babyGroup);
        rb_baby1 = (RadioButton) findViewById(R.id.rb_baby1);
        rb_baby2 = (RadioButton) findViewById(R.id.rb_baby2);
        rb_baby3 = (RadioButton) findViewById(R.id.rb_baby3);
        rb_baby4 = (RadioButton) findViewById(R.id.rb_baby4);
        mainactivityTop = (RelativeLayout) findViewById(R.id.mainactivityTop);
        mAddImage = (ImageView) findViewById(R.id.add);
        mAddBabyImage = (ImageView) findViewById(R.id.addBaby);
        rg_babyGroup.setOnCheckedChangeListener(this);
        rg_tab_bar.setOnCheckedChangeListener(this);

        vpager = (CustomViewPager) findViewById(R.id.vpager);
        vpager.setOffscreenPageLimit(4);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        rb_channel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLog.e("EventBus","setOnClickListener");
                if(mflag){
                    EventBus.getDefault().post(new EventBus_Model("update"));
                }
                mflag=true;
            }
        });
        rb_baby1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLog.e("EventBus","setOnClickListener");
                if(mflag){
                    EventBus.getDefault().post(new EventBus_Model("update"));
                }
                mflag=true;

            }
        });
    }

    /**
     * 功能：监听单选按钮组
     * 参数：group 单选按钮组 checkedId 单选按钮id
     * 返回：无
     */
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_channel:
                mflag=false;
                Log.e("EventBus","onCheckedChanged");
            //    EventBus.getDefault().post(new EventBus_Model("宝宝作品"));
                vpager.setCurrentItem(PAGE_ONE, false);
                mHomeText.setTextColor(this.getResources().getColor(R.color.green));
                mFindText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                mNewsText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                mMysText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                break;
            case R.id.rb_message:
                vpager.setCurrentItem(PAGE_TWO, false);
                mHomeText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                mFindText.setTextColor(this.getResources().getColor(R.color.green));
                mNewsText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                mMysText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                break;
            case R.id.rb_better:
                vpager.setCurrentItem(PAGE_THREE, false);
                mHomeText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                mFindText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                mNewsText.setTextColor(this.getResources().getColor(R.color.green));
                mMysText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                break;
            case R.id.rb_setting:
                vpager.setCurrentItem(PAGE_FOUR, false);
                mHomeText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                mFindText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                mNewsText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                mMysText.setTextColor(this.getResources().getColor(R.color.green));
                break;
            case R.id.rb_baby1:
                mflag=false;
                Log.e("EventBus","onCheckedChanged");
                vpager.setCurrentItem(PAGE_ONE, false);
                mBabyTask.setTextColor(this.getResources().getColor(R.color.green));
                mBabyWork_Text.setTextColor(this.getResources().getColor(R.color.white));
                mBabyNews_Text.setTextColor(this.getResources().getColor(R.color.white));
                mBabyMy_Text.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.rb_baby2:
                vpager.setCurrentItem(PAGE_TWO, false);
                mBabyTask.setTextColor(this.getResources().getColor(R.color.white));
                mBabyWork_Text.setTextColor(this.getResources().getColor(R.color.green));
                mBabyNews_Text.setTextColor(this.getResources().getColor(R.color.white));
                mBabyMy_Text.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.rb_baby3:
                vpager.setCurrentItem(PAGE_THREE, false);
                mBabyTask.setTextColor(this.getResources().getColor(R.color.white));
                mBabyWork_Text.setTextColor(this.getResources().getColor(R.color.white));
                mBabyNews_Text.setTextColor(this.getResources().getColor(R.color.green));
                mBabyMy_Text.setTextColor(this.getResources().getColor(R.color.white));
                break;
            case R.id.rb_baby4:
                vpager.setCurrentItem(PAGE_FOUR, false);
                mBabyTask.setTextColor(this.getResources().getColor(R.color.white));
                mBabyWork_Text.setTextColor(this.getResources().getColor(R.color.white));
                mBabyNews_Text.setTextColor(this.getResources().getColor(R.color.white));
                mBabyMy_Text.setTextColor(this.getResources().getColor(R.color.green));
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    /**
     * 功能：viewpage滑动改变单选按钮状态
     * 参数：state  viewpage状态
     * 返回：无
     */
    @Override
    public void onPageScrollStateChanged(int state) {
        //viewpage滑动
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    rb_channel.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_message.setChecked(true);
                    break;
                case PAGE_THREE:
                    rb_better.setChecked(true);
                    break;
                case PAGE_FOUR:
                    rb_setting.setChecked(true);
                    break;
            }
        }
    }
   // 在大学里学习过机械加工课程，对数控加工中心有所了解，对刀库和换刀机构有一定的理解，同时，同时所学的课程例如：机械设计、机械原理、材料力学、理论力学等又给予我设计计算一定的思路和启发，在通过去图书馆所借有关数控机床、加工中心的书更加完善和丰富了该课题、印证了我一些设计思路并且提供了理论依据。
    private void getBabyList(final int pageIndex, final int flag) {
        UserTerm userTerm = new UserTerm();
        userTerm.setMobile(Login_Static.mAccount.getMobile());
        userTerm.setPageIndex(pageIndex);
        userTerm.setPageSize(20);
        String data = mGson.toJson(userTerm);
        CLog.e("testing", "获取宝贝列表data：" + data);
        OKHttp.OkHttpPost("?type=4","data", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "获取宝贝列表失败：" + e.toString());
                identity_switchimg.setEnabled(true);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testing", "获取宝贝列表：" + response);
                try {
                    identity_switchimg.setEnabled(true);
                    if (Json_Utils.getCode(response) == 0) {

                        mUserBase.clear();
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            UserBase baby = mGson.fromJson(array.toString(), UserBase.class);
                            mUserBase.add(baby);
                        }
                        if (mUserBase.size() > 1) {
                            if (flag == 1) {
                                showWindow();
                            }
                        } else if (mUserBase.size() == 1) {
                            if (flag == 1) {
                                if (mSave.Get_PREFS(MainActivity.this, "Vip").equals("yes")) {
                                    hintText_dialog2 = new Dialog_Hint(mContext, 0,
                                            "是否前往添加宝宝，升级为家长?", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            hintText_dialog2.dismiss();
                                            Intent intent = new Intent(MainActivity.this, My_BabyInformation_Activity.class);
                                            intent.putExtra("Type", 4);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                    hintText_dialog2.show();
                                } else {
                                    hintText_dialog2 = new Dialog_Hint(mContext, 0,
                                            "您还没有添加宝宝,是否前往添加?", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            hintText_dialog2.dismiss();
                                            Intent intent = new Intent(MainActivity.this, My_BabyInformation_Activity.class);
                                            intent.putExtra("Type", 4);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                    hintText_dialog2.show();
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

    private void showWindow() {

        if (popupWindow == null) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.change_popupwindow_list, null);
            lv_group = (ListView) view.findViewById(R.id.lvGroup);

            // 加载数据
        }
        for (int i = 0; i < mUserBase.size(); i++) {
            if (mUserBase.get(i).getUserId().equals(Login_Static.myUserID)) {
                mUserBase.remove(i);
                break;
            }

        }
        groupAdapter = new Change_popupwindow_adapter(MainActivity.this, mUserBase);
        lv_group.setAdapter(groupAdapter);
        // 创建一个PopuWidow对象
        if (mUserBase.size() == 1) {
            popupWindow = new PopupWindow(view, Dp2Px(153), Dp2Px(49));
        } else if (mUserBase.size() == 2) {
            popupWindow = new PopupWindow(view, Dp2Px(153), Dp2Px(110));
        } else {
            popupWindow = new PopupWindow(view, Dp2Px(153), Dp2Px(110));
        }

        // 使其聚集
        popupWindow.setFocusable(true);
        // 设置允许在外点击消失
        popupWindow.setOutsideTouchable(true);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        // 这个是为了点击“返回Back”也能使其消失，并且并不会影响你的背景
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        // 显示的位置为:屏幕的宽度的一半-PopupWindow的高度的一半
        //int xPos = windowManager.getDefaultDisplay().getWidth() /2- popupWindow.getWidth() / 2;
        // int xPos = windowManager.getDefaultDisplay().getWidth()/2 - popupWindow.getWidth()/2;
        CLog.e("coder", "windowManager.getDefaultDisplay().getWidth()/2:"
                + windowManager.getDefaultDisplay().getWidth() / 2);
        //
        CLog.e("coder", "windowManager.getDefaultDisplay().getWidth()/2" + windowManager.getDefaultDisplay().getWidth() / 2);
        CLog.e("coder", "popupWindow.getWidth()/2:" + popupWindow.getWidth() / 2);
        //  Log.e("coder", "xPos:" + xPos);
        // popupWindow.showAsDropDown(parent,xPos, 25);
        popupWindow.showAsDropDown(findViewById(R.id.identity_switchimg), 0, 5);//
        identity_switchimg.setEnabled(true);
        lv_group.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,
                                    int position, long id) {
                CLog.e("position", "position");
                if (mUserBase.get(position).getUserType() == 1) {//切换至宝宝
                    changeUser(mUserBase.get(position).getUserId());
                } else if (mUserBase.get(position).getUserType() == 0) {//切换至妈妈
                    getUser(mUserBase.get(position).getUserId());
                }
                if (popupWindow != null) {
                    popupWindow.dismiss();

                }

            }
        });
    }

    /**
     * 获取个人信息数据
     */
    private void getUser(final String userid) {
        OkHttpUtils.get().url(mApplication.getmUrl()+"?type=1")
                .addParams("userid", Login_Static.myUserID)
                .addParams("targetuserid", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "个人信息 失败" + e.toString());

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "个人信息：" + response);
                        try {
                            CLog.e("testing", "个人信息：" + Json_Utils.getTemplate(response));
                            if (Json_Utils.getCode(response) == 0) {
                                User mUser = mGson.fromJson(Json_Utils.getTemplate(response), User.class);
                                if (mUser.getTogglePassword()==null||"".equals(mUser.getTogglePassword())) {
                                    //没设置
                                    changeUserMom(userid, "0");
                                  /*  hintText_dialog = new Dialog_Hint(mContext, 0,
                                            "您还没有设置切换密码,是否前往设置?", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            hintText_dialog.dismiss();
                                            Intent intent = new Intent(mContext, Login_FindPassword_Activity.class);
                                            intent.putExtra("PassWordType", 1);
                                            mContext.startActivity(intent);
                                        }
                                    });
                                    hintText_dialog.show();*/
                                } else {
                                    mSave.Save_PREFS(getApplicationContext(), "Type", "unLogin");
                                    mSave.Save_PREFS(MainActivity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                                    Intent mIntent = new Intent(MainActivity.this, Setting_Change_Activity.class);
                                    startActivity(mIntent);
                                    /*mChangeUser_Pop = new ChangeUser_Pop(MainActivity.this);
                                    //设置弹出动画效果
                                    mChangeUser_Pop.setAnimationStyle(R.style.PopupAnimation);
                                    //显示窗口  //设置layout在PopupWindow中显示的位置
                                    mChangeUser_Pop.showAtLocation(findViewById(R.id.layout),
                                            Gravity.CENTER , 0, 0);
                                    mChangeUser_Pop.setNum(new ChangeUser_Pop.Num() {
                                        @Override
                                        public void num(String text) {
                                            CLog.e("testing", text);
                                            //判断密码是否正确
                                            changeUser(userid, text);
                                        }
                                    });*/
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //有密码切换至妈妈端
    private void changeUser(String userId, String password) {
        mDialog.show();
        CLog.e("testing", "切换用户:" + userId);
        HintText_Dialog hint = new HintText_Dialog(mContext, "切换中...", "");
        OkHttpUtils.get().url(mApplication.getUrl() + "/User/SwitchUserStatus")
                .addParams("userid", userId)
                .addParams("pwd", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "切换用户登录失败" + e.toString());
                        HintText_Dialog hint = new HintText_Dialog(mContext, "未知错误", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //得到返回值后，2秒后加载框消失
                                mDialog.dismiss();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "切换用户登录" + response);
                        try {
                            if (Json_Utils.getCode(response) == 1003) {
                                HintText_Dialog hint = new HintText_Dialog(mContext, "密码错误", "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //得到返回值后，2秒后加载框消失
                                        mDialog.dismiss();
                                    }
                                }, 2000);

                            } else if (Json_Utils.getCode(response) == 0) {
                                mDialog.dismiss();
                                UserPrimaryKey beans = mGson.fromJson(Json_Utils.getTemplate(response), UserPrimaryKey.class);
                                Login_Static.myUserAccount = beans.getAccountId();
                                Login_Static.myUserID = beans.getUserId();
                                mSave.Save_PREFS(mContext, beans.getUserId()+"token", beans.getToken());
                                mSave.Save_PREFS(mContext, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                                mSave.Save_PREFS(mContext, "Type", "mom");
                                Intent mIntent = new Intent(mContext, MainActivity.class);
                                startActivity(mIntent);
                                BaseAppManager.getInstance().clear();
                            } else {
                                HintText_Dialog hint = new HintText_Dialog(mContext, Json_Utils.getMessage(response), "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //得到返回值后，2秒后加载框消失
                                        mDialog.dismiss();
                                    }
                                }, 2000);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    //无密码切换至宝宝端
    private void changeUser(String userId) {
        CLog.e("testing", "切换用户:" + userId);
        mDialog.show();
        HintText_Dialog hint = new HintText_Dialog(MainActivity.this, "正在切换...", "");
        OkHttpUtils.get().url(mApplication.getmUrl()+"?type=6")
                .addParams("userid", userId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "切换用户登录失败" + e.toString());
                        HintText_Dialog hint = new HintText_Dialog(MainActivity.this, "未知错误", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //得到返回值后，2秒后加载框消失
                                mDialog.dismiss();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "切换用户登录" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {

                                HintText_Dialog hint = new HintText_Dialog(MainActivity.this, "切换成功", "success");
                                final String error = Json_Utils.getTemplate(response);
                                //得到返回值后，2秒后加载框消失
                                mDialog.dismiss();

                                UserPrimaryKey beans = mGson.fromJson(error, UserPrimaryKey.class);

                                Login_Static.myUserAccount = beans.getAccountId();
                                Login_Static.myUserID = beans.getUserId();
                                CLog.e("testingst","ndcd:"+beans.getUserId()+"的token: "+beans.getToken());
                                mSave.Save_PREFS(MainActivity.this, beans.getUserId()+"token", beans.getToken());
                                mSave.Save_PREFS(MainActivity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                                mSave.Save_PREFS(MainActivity.this, "Type", "baby");

                                Intent mIntent = new Intent(MainActivity.this, MainActivity.class);
                                startActivity(mIntent);
                                finish();
                                if (Login_Static.mActivityList.size() > 0) {
                                    Login_Static.mActivityList.get(0).finish();
                                }
                            } else {
                                HintText_Dialog hint = new HintText_Dialog(MainActivity.this, Json_Utils.getMessage(response), "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //得到返回值后，2秒后加载框消失
                                        mDialog.dismiss();
                                    }
                                }, 2000);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    public void connect(String token) {

        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {
                    CLog.e("onMessage","新用户已登录过期===：");
                    //getConnectionStatus();
                    Log.d("LoginActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {
                    Log.d("LoginActivity", "--onSuccess---" + userid);
                    //getConnectionStatus();
                    CLog.e("onMessage","新用户已登录成功+："+userid);
                    mAdapter.notifyDataSetChanged();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    Log.d("LoginActivity", "--onError" + errorCode);
                    CLog.e("onMessage","新用户已登录失败+："+errorCode);
                    getConnectionStatus();
                }
            });
        }
    }


    //为弹出窗口实现监听类
    public View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.work_btn:
                    Intent mIntent = new Intent(MainActivity.this, Release_Work_Activity.class);
                    startActivity(mIntent);
                    break;
                case R.id.Idle_btn:
                    if (mSave.Get_PREFS(MainActivity.this, "Type").equals("mom")) {
                        vpager.setCurrentItem(PAGE_TWO);
                        mAdapter.mFindfragment.mFindViewpage.setCurrentItem(0);
                        rg_tab_bar.check(R.id.rb_message);
                    }
                    if (mSave.Get_PREFS(MainActivity.this, "Type").equals("baby")) {
                        if (!mSave.Get_PREFS(MainActivity.this, "Unlogin").equals("yes")) {
                            mIntent = new Intent(MainActivity.this, Release_Work_Activity.class);
                            startActivity(mIntent);
                        } else {
                            mIntent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(mIntent);
                        }

                    }
                    break;
                case R.id.activity_btn:
                    mIntent = new Intent(MainActivity.this, Release_Cricle_Activity.class);
                    startActivity(mIntent);
                    break;
                case R.id.PublicWelfare_btn:
                    if (mSave.Get_PREFS(MainActivity.this, "Type").equals("baby")) {

                    } else if (mSave.Get_PREFS(MainActivity.this, "Type").equals("mom")) {
                        mIntent = new Intent(MainActivity.this, Release_Activity_Activity.class);
                        startActivity(mIntent);
                    }

                    break;
                case R.id.Photo_btn:
                    if (mSave.Get_PREFS(MainActivity.this, "Type").equals("baby")) {

                    } else if (mSave.Get_PREFS(MainActivity.this, "Type").equals("mom")) {
                        mIntent = new Intent(MainActivity.this, Release_Idle_Activity.class);
                        startActivity(mIntent);
                    }

                    break;
                default:
                    break;
            }
            mMyReleaseWindow.dismiss();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongIMClient.getInstance().disconnect();
    }

    private void getConnectionStatus() {
        getToken(4000);

       /* RongIMClient.setConnectionStatusListener(new RongIMClient.ConnectionStatusListener() {
            @Override
            public void onChanged(ConnectionStatus connectionStatus) {
                if (connectionStatus == ConnectionStatus.CONNECTED || connectionStatus == ConnectionStatus.TOKEN_INCORRECT) {
                    //断开连接或token无效
                    getToken(5000);
                }*//*else if(connectionStatus==ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT){
                    Intent intent=new Intent(MainActivity.this,Again_Login_Activity.class);
                    startActivity(intent);
                }*//*
            }
        });*/
    }

    //重新获取token
    private void getToken(long time) {
        CLog.e("onMessage","重连："+Login_Static.myUserID+"===token==="+mSave.Get_PREFS(MainActivity.this, Login_Static.myUserID+"token"));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                connect(mSave.Get_PREFS(MainActivity.this, Login_Static.myUserID+"token"));
            }
        },time);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 7:
                if (resultCode == 8) {
                    if ("guang".equals(data.getStringExtra("result"))) {
                        //我的订单——去逛逛返回值
                        /*vpager.setCurrentItem(PAGE_ONE);
                        mHomeText.setTextColor(this.getResources().getColor(R.color.green));
                        mFindText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                        mNewsText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                        mMysText.setTextColor(this.getResources().getColor(R.color.text_grey_2));*/
                        rb_channel.setChecked(true);
                        rb_baby1.setChecked(true);
                    }
                }
                break;
            case 10:
                if (resultCode == 9) {
                    if ("task".equals(data.getStringExtra("results"))) {
                        //宝宝端任务
                        /*vpager.setCurrentItem(PAGE_TWO);
                        mHomeText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                        mFindText.setTextColor(this.getResources().getColor(R.color.green));
                        mNewsText.setTextColor(this.getResources().getColor(R.color.text_grey_2));
                        mMysText.setTextColor(this.getResources().getColor(R.color.text_grey_2));*/
                        rb_message.setChecked(true);
                        rb_baby2.setChecked(true);
                    }
                }
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (isShow) {
                isShow = false;
                Comment_Layout.setVisibility(View.GONE);
                if (mSave.Get_PREFS(MainActivity.this, "Type").equals("baby")) {
                    mBabyBootom.setVisibility(View.VISIBLE);
                    mBootom.setVisibility(View.GONE);
                } else {
                    mBootom.setVisibility(View.VISIBLE);
                    mBabyBootom.setVisibility(View.GONE);
                }
                mComment_BottomLayout.setVisibility(View.GONE);
            } else {
                //  finish();
            }
            if (isOnKeyBacking) {
                mHandler.removeCallbacks(onBackTimeRunnable);
                if (mBackToast != null) {
                    mBackToast.cancel();
                }
                finish();
                return true;
            } else {
                isOnKeyBacking = true;
                if (mBackToast == null) {
                    mBackToast = Toast.makeText(MainActivity.this, "再按一次退出", 2000);
                }
                mBackToast.show();
                //延迟两秒的时间，把Runable发出去
                mHandler.postDelayed(onBackTimeRunnable, 2000);
                return true;
            }
        }

        return false;

    }

    private Runnable onBackTimeRunnable = new Runnable() {

        @Override
        public void run() {
            isOnKeyBacking = false;
            if (mBackToast != null) {
                mBackToast.cancel();
            }
        }
    };

    //点击
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                Comment_Layout.setVisibility(View.GONE);
                if (mSave.Get_PREFS(MainActivity.this, "Type").equals("baby")) {
                    mBabyBootom.setVisibility(View.VISIBLE);
                    mBootom.setVisibility(View.GONE);
                } else {
                    mBootom.setVisibility(View.VISIBLE);
                    mBabyBootom.setVisibility(View.GONE);
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


  /*  @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        if (event.getAction() == MotionEvent.ACTION_DOWN) {  if (this.getCurrentFocus() != null) {
            if (this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        }
        return super.onTouchEvent(event);
    }*/

    //获取版本信息
    public void GetSystemVersion() {
        OkHttpUtils.get().url(mApplication.getUrl() + "/User/GetSystemVersion")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                JSONObject jsonArray = new JSONObject(Json_Utils.getTemplate(response));
                                CLog.e("baby", jsonArray.length() + "");
                                SystemVersion mSystemVersion = mGson.fromJson(jsonArray.toString(), SystemVersion.class);
                                if ((mSystemVersion.getVersionNnumber().equals(getVersionName()))) {

                                } else {
                                    CLog.e("version", getVersionName());
                                    String[] aa = getVersionName().toString().split("\\.");
                                    String[] bb = mSystemVersion.getVersionNnumber().split("\\.");
                                    boolean isNew = false;
                                    for (int i = 0; i < aa.length; i++) {
                                        if (Integer.parseInt(aa[i]) < Integer.parseInt(bb[i])) {
                                            isNew = true;
                                            break;
                                        } else if (Integer.parseInt(aa[i]) != Integer.parseInt(bb[i])) {
                                            break;
                                        }
                                    }
                                    if (!mSystemVersion.getVersionNnumber().equals("") && Login_Static.userfirst == 0 && isNew) {
                                        Login_Static.userfirst = 1;
                                        final Intent intent = new Intent();
                                        intent.setAction("android.intent.action.VIEW");
                                        Uri content_url = Uri.parse(mSystemVersion.getUpdateAddress());
                                        intent.setData(content_url);
                                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                        builder.setMessage("发现新版本"+mSystemVersion.getVersionNnumber()+"，是否立即更新!");
                                        builder.setTitle("提示");
                                        builder.setPositiveButton("立即下载", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(final DialogInterface dialog, int which) {
                                                startActivity(intent);
                                            }

                                        });

                                        builder.setNegativeButton("以后再说", new DialogInterface.OnClickListener() {

                                            @Override

                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }

                                        });
                                        builder.create().show();

                                    }
                                }
                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                });
    }

    /*
* 获取当前程序的版本号
*/
    private String getVersionName() throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(getPackageName(), 0);
        return packInfo.versionName;
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

    //切换至家长端
    private void changeUserMom(String userId, String password) {
        mDialog.show();
        CLog.e("testing", "切换用户:" + userId);
        HintText_Dialog hint = new HintText_Dialog(mContext, "切换中...", "");
        OkHttpUtils.get().url(mApplication.getmUrl()+"?type=6")
                .addParams("userid", userId)
                .addParams("pwd", password)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "切换用户登录失败" + e.toString());
                        HintText_Dialog hint = new HintText_Dialog(mContext, "未知错误", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //得到返回值后，2秒后加载框消失
                                mDialog.dismiss();
                            }
                        }, 2000);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "切换用户登录" + response);
                        try {
                            if (Json_Utils.getCode(response) == 1003) {
                                HintText_Dialog hint = new HintText_Dialog(mContext, "密码错误", "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //得到返回值后，2秒后加载框消失
                                        mDialog.dismiss();
                                    }
                                }, 2000);
                            } else if (Json_Utils.getCode(response) == 0) {
                                mDialog.dismiss();
                                UserPrimaryKey beans = mGson.fromJson(Json_Utils.getTemplate(response), UserPrimaryKey.class);
                                Login_Static.myUserAccount = beans.getAccountId();
                                Login_Static.myUserID = beans.getUserId();
                                CLog.e("onMessage","新用户："+beans.getUserId()+"的token="+beans.getToken());
                                mSave.Save_PREFS(mContext, beans.getUserId()+"token", beans.getToken());
                                mSave.Save_PREFS(mContext, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                                mSave.Save_PREFS(mContext, "Type", "mom");
                                mSave.Save_PREFS(mContext, "Vip", "no");
                                Intent mIntent = new Intent(mContext, MainActivity.class);
                                startActivity(mIntent);
                                if (Login_Static.mActivityList.size() > 0) {
                                    Login_Static.mActivityList.get(0).finish();
                                }
                                /*HintText_Dialog hint = new HintText_Dialog(mContext, "切换成功", "success");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //得到返回值后，2秒后加载框消失
                                        //  mDialog.dismiss();
                                    }
                                }, 2000);*/
                            } else {
                                HintText_Dialog hint = new HintText_Dialog(mContext, Json_Utils.getMessage(response), "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        //得到返回值后，2秒后加载框消失
                                        mDialog.dismiss();
                                    }
                                }, 2000);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
    //保存极光 设备id到后台
    public void changeAppKey(String accountId,String appkey){
        OkHttpUtils.get().url(mApplication.getUrl() + "/user/ChangeAccountAppKey")
                .addParams("accountId", accountId)
                .addParams("appkey", appkey)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("changeAppKey", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("changeAppKey", response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                CLog.e("changeAppKey","保存极光id成功");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
