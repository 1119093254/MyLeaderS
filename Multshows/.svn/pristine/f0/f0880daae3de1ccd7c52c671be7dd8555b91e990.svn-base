package com.multshows.Activity;

import android.animation.LayoutTransition;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.MyHome_Idle_adapter;
import com.multshows.Adapter.Search_Friends_Adapter;
import com.multshows.Adapter.WorkAll_Adapter;
import com.multshows.Beans.MyHome_Idle_Beans;
import com.multshows.Beans.Shows;
import com.multshows.Beans.ShowsCommentAdd;
import com.multshows.Beans.ShowsTerm;
import com.multshows.Beans.UserFriend_Beans;
import com.multshows.Beans.WorkAll_Beans;
import com.multshows.Interfaces.CommentInterface;
import com.multshows.Login_Static;
import com.multshows.R;
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
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.PullToRefresh;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 作品搜索页（附带评论输入框）
 */
public class Search_2_Activity extends AppCompatActivity implements EmojiconsFragment.OnEmojiconBackspaceClickedListener,
        EmojiconGridFragment.OnEmojiconClickedListener {
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    RelativeLayout mParentLayout;//父布局
    TextView mSearch;//搜索
    EditText mEditText;//输入框
    EditText mEditText2;
    RelativeLayout mSearchLayout2;

    ListView mBeansListView;//搜索数据显示
    TextView mNoView;//空视图
    //数据集合
    List<UserFriend_Beans> mFriendsList = new ArrayList<>();
    List<MyHome_Idle_Beans> mIdleList = new ArrayList<>();
    List<WorkAll_Beans> mWorkList = new ArrayList<>();//作品列表bean对象
    //适配器
    Search_Friends_Adapter mFriendsAdapter;//查找好友适配器
    MyHome_Idle_adapter mIdleAdapter;//闲置适配器
    WorkAll_Adapter mWorkAllAdapter;//作品，圈子等适配器
    //输入框-------------------------------------
    EmojiconEditText mContext;//发送内容
    TextView mSend;//发送
    ImageView mFace;//表情按钮

    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类
    boolean isShows = false;//是否显示输入框布局
    //下部布局
    RelativeLayout mParent;
    //输入框所在布局
    RelativeLayout mLayout;
    //自定义表情框
    RelativeLayout mRelativeLayout;
    //表情框高度
    int emotionHeight;
    int firstHeight;
    //表情框显示标志
    boolean face_Flag = false;
    int screenHeight;
    private final LayoutTransition transitioner = new LayoutTransition();//键盘和表情切换
    private ArrayList<OnSoftKeyboardStateChangedListener> mKeyboardStateListeners;      //软键盘状态监听列表
    private ViewTreeObserver.OnGlobalLayoutListener mLayoutChangeListener;
    private boolean mIsSoftKeyboardShowing;
    //-----------------------------------------
    String workId;//作品id
    String userid;//用户id
    String TargetUserId;//用户id
    String ReplyId;//被回复者id
    //-----------------------------------------
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();//偏好设置
    RelativeLayout mImageLayout;
    int pageIndex;//页数
    String mSearchType;
    Intent mIntent;
    Gson mGson = new Gson();
    Dialog mDialog;
    ShowsCommentAdd mShowsCommentAdd;//添加评论
    private PullToRefresh mPullToRefresh;
    int pageIndex_ = 1;
    String texts_ = "";
    String type;
    String unlogin="";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_2);
        //沉浸式
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Search_2_Activity.this, R.color.app_topColor);
        mKeyboardStateListeners = new ArrayList<OnSoftKeyboardStateChangedListener>();
        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        screenHeight = wm.getDefaultDisplay().getHeight();
        initView();
        type=saveSharedPreferences.Get_PREFS(Search_2_Activity.this,"Type");//代表此页妈妈界面还是宝宝页面unlogin没登录
        unlogin=saveSharedPreferences.Get_PREFS(Search_2_Activity.this,"Unlogin");
        if("mom".equals(type)) {
            mImageLayout.setVisibility(View.VISIBLE);
            mSearchLayout2.setVisibility(View.GONE);
        }else {
            mImageLayout.setVisibility(View.GONE);
            mSearchLayout2.setVisibility(View.VISIBLE);
        }
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.Search_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Search_ReturnLayout);
        mParentLayout= (RelativeLayout) findViewById(R.id.Search2_ParentsLayout);
        mImageLayout= (RelativeLayout) findViewById(R.id.Search2_imageLayout);
        mSearch = (TextView) findViewById(R.id.Search_Search);
        mEditText = (EditText) findViewById(R.id.Search_editText);
        mEditText2= (EditText) findViewById(R.id.Search2_editText2);
        mSearchLayout2= (RelativeLayout) findViewById(R.id.Search2_imageLayout2);
        // mMyListView= (MyListView) findViewById(R.id.Search_ListView);
        // mTopLayout= (LinearLayout) findViewById(R.id.Search_TopLayout);
        mBeansListView = (ListView) findViewById(R.id.Search_ListView_Beans);
        //mBeanLayout= (RelativeLayout) findViewById(R.id.Search_BeanListLayout);
        mNoView = (TextView) findViewById(R.id.Search_NoView);


        mPullToRefresh = (PullToRefresh) findViewById(R.id.Search2_Refresh);
        mContext = (EmojiconEditText) findViewById(R.id.Search_EditText);
        mSend = (TextView) findViewById(R.id.Search_Send);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.Search_bottom_bottom);
        mParent = (RelativeLayout) findViewById(R.id.Search_BottomLayout);
        mLayout = (RelativeLayout) findViewById(R.id.Search_editTextLayout);
        mFace = (ImageView) findViewById(R.id.Search_Face);
        firstHeight = mLayout.getHeight();
        mDialog = new HintText_Dialog(Search_2_Activity.this, R.style.MyDialog);
        mShowsCommentAdd = new ShowsCommentAdd();

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
                if (isKeyboardShowing || face_Flag) {//键盘显示
                    if (isShows) {
                        mParent.setVisibility(View.VISIBLE);
                    } else {
                        isShows = false;
                        mParent.setVisibility(View.INVISIBLE);
                    }
                } else if (!face_Flag) {
                    isShows = false;
                    mParent.setVisibility(View.INVISIBLE);

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
        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(Search_2_Activity.this,mReturnLayout,mParentLayout,mReturn,mSearch);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mIntent = getIntent();
        mSearchType = mIntent.getStringExtra("SearchType");
        if ("cricle".equals(mSearchType)) {
            //圈子搜索
            mWorkAllAdapter = new WorkAll_Adapter(Search_2_Activity.this, Search_2_Activity.this, mWorkList);
            mBeansListView.setAdapter(mWorkAllAdapter);
        } else if ("show".equals(mSearchType)) {
            //作品搜索
            mWorkAllAdapter = new WorkAll_Adapter(Search_2_Activity.this, Search_2_Activity.this, mWorkList);
            mBeansListView.setAdapter(mWorkAllAdapter);
        }
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //隐藏软键盘
                HideInputManager_Utils.hideInputManager(Search_2_Activity.this);
                finish();
            }
        });
        mPullToRefresh.setOnHeaderRefreshListener(new PullToRefresh.OnHeaderRefreshListener() {
            @Override
            public void onHeaderRefresh(PullToRefresh view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pageIndex_ = 1;
                        getListShows2(1, texts_);
                    }
                }, 1000);
            }
        });
        mPullToRefresh.setOnFooterRefreshListener(new PullToRefresh.OnFooterRefreshListener() {
            @Override
            public void onFooterRefresh(PullToRefresh view) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getListShows2(pageIndex_, texts_);
                    }
                }, 1000);
            }
        });
        //表情按钮点击事件
        mFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmojiconFragment(false);
                if (face_Flag) {
                    //隐藏
                    //点击输入框，打开软键盘，隐藏表情
                    face_Flag = false;
                    hideEmotionView(true);
                } else {
                    //显示
                    //点击表情图标,如果表情显示，隐藏表情，打开软键盘。反之，显示表情，隐藏键盘
                    face_Flag = true;
                    showEmotionView(System_Utils.isKeyBoardShow(Search_2_Activity.this));
                }
            }
        });
        //输入框点击事件
        mContext.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //点击输入框，打开软键盘，隐藏表情
                face_Flag = false;
                hideEmotionView(true);
                return false;
            }
        });
        //发送评论
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mContext.getText().toString();
                if ("".equals(text)) {
                    mDialog.show();
                    HintText_Dialog hint = new HintText_Dialog(Search_2_Activity.this, "评论不能为空!", "fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mDialog.dismiss();
                        }
                    }, Login_Static.hintTime);
                } else {
                    releaseComment(mEmoji_change.EmojiChange(text));
                }

            }
        });
        //搜索
        mSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBeansListView.setEmptyView(mNoView);
                if("mom".equals(type)) {
                    texts_=mEditText.getText().toString();
                }else {
                    texts_=mEditText2.getText().toString();
                }
                if ("".equals(texts_)) {

                } else {
                    mDialog = new HintText_Dialog(Search_2_Activity.this, R.style.MyDialog);
                    mDialog.show();
                    HintText_Dialog mTextDialog = new HintText_Dialog(Search_2_Activity.this,
                            "努力搜索中...", "");
                    if ("show".equals(mSearchType)) {
                        //作品搜索
                        getListShows(1, texts_);

                    }
                }
            }
        });
        //listview点击事件
        mBeansListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if ("friend".equals(mSearchType)) {
                    Intent intent = new Intent(Search_2_Activity.this, MyHomePager_Activity.class);
                    //是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
                    saveSharedPreferences.Save_PREFS(Search_2_Activity.this, "otherId",
                            mFriendsList.get(position).getTargetUser().getUserId());
                    startActivity(intent);
                } else if ("idle".equals(mSearchType)) {
                    //闲置搜索
                    Intent mIntent = new Intent(Search_2_Activity.this, Idle_ProduceDetails_Activity.class);
                    mIntent.putExtra("IdleID", mIdleList.get(position).getId());
                    startActivity(mIntent);
                }
            }
        });
        if ("cricle".equals(mSearchType) || "show".equals(mSearchType)) {
            mWorkAllAdapter.getInterface(new CommentInterface() {
                @Override
                public void execute(String mworkId, String mTargetUserId, String muserid, String mReplyId) {
                    CLog.e("mworkId", "" + mworkId + " " + muserid + " " + mTargetUserId + " " + mReplyId);
                    isShows = true;
                    workId = mworkId;
                    TargetUserId = mTargetUserId;
                    userid = muserid;
                    ReplyId = mReplyId;
                    mContext.setSelection(mContext.getText().length());
                    mContext.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                }
            });
        }

    }

    private void releaseComment(String text) {
        mDialog.show();
        HintText_Dialog hint = new HintText_Dialog(Search_2_Activity.this, "正在评论...", "");
        mShowsCommentAdd.setForeignld(workId);
        mShowsCommentAdd.setUserId(Login_Static.myUserID);
        mShowsCommentAdd.setTargetUserId(TargetUserId);
        mShowsCommentAdd.setAuthorId(userid);
        mShowsCommentAdd.setType(1);
        mShowsCommentAdd.setReplyId(ReplyId);
        mShowsCommentAdd.setContent(text);
        String data = mGson.toJson(mShowsCommentAdd);
        CLog.e("testing", data);
        OKHttp.OkHttpPost("/Comment/AddComment", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                HintText_Dialog hint = new HintText_Dialog(Search_2_Activity.this, "网络异常", "fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                    }
                }, Login_Static.hintTime);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    CLog.e("testing", "成功：" + response);
                    if (Json_Utils.getCode(response) == 0) {
                        HintText_Dialog hint = new HintText_Dialog(Search_2_Activity.this, "评论成功", "success");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                                getListShows2(1, texts_);
                            }
                        }, Login_Static.hintTime);
                    } else {
                        HintText_Dialog hint = new HintText_Dialog(Search_2_Activity.this,
                                Json_Utils.getMessage(response), "fail");
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



    // 获取作品数据集合
    private void getListShows(final int pageIndex, String searchStr) {
        ShowsTerm mShowsTerm = new ShowsTerm();
        saveSharedPreferences = new SaveSharedPreferences();
        //mShowsTerm.setUserid(Login_Static.myUserID);
        mShowsTerm.setPageIndex(pageIndex);
        mShowsTerm.setPageSize(20);
        mShowsTerm.setTitile(searchStr);
        mShowsTerm.setType(1);
        if("yes".equals(unlogin)) {
            //未登录
            mShowsTerm.setIsTour(1);
        }else {
            mShowsTerm.setIsTour(0);
        }
        mShowsTerm.setIsOver(-1);
        mShowsTerm.setIsShow(-1);
        mShowsTerm.setSoType(-1);
        mShowsTerm.setLocalId(Login_Static.myUserID);
        mShowsTerm.setUserId(Login_Static.myUserID);
        String data = mGson.toJson(mShowsTerm);
        CLog.e("datasss", data);
        OKHttp.OkHttpPost("/Shows/GetListShows", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "错误");
                HintText_Dialog mTextDialog = new HintText_Dialog(Search_2_Activity.this,
                        "搜索失败", "fail");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDialog.dismiss();
                    }
                }, Login_Static.hintTime);
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testinggg", "changeNickName:" + response);
                try {
                    if (Json_Utils.getCode(response) == 0) {

                        mDialog.dismiss();

                        if (pageIndex == 1) {
                            mWorkList.clear();
                        }
                        pageIndex_++;
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            Shows Shows = mGson.fromJson(array.toString(), Shows.class);
                            List<String> imageStrinig3 = new ArrayList<>();
                            List<String> likeUsername = new ArrayList<>();

                            if (Shows.getLikeUser() != null) {
                                for (int m = 0; m < Shows.getLikeUser().size(); m++) {
                                    likeUsername.add(Shows.getLikeUser().get(m).getNickName());
                                }
                            }

                            //对获取到的图片进行处理包装
                            if (Shows.getShowsPhoto() != null) {
                                String[] imag = Shows.getShowsPhoto().split(",");
                                for (int m = 0; m < imag.length; m++) {
                                    imageStrinig3.add(imag[m]);
                                }
                            }
                            WorkAll_Beans beans = new WorkAll_Beans(Shows.getId(), Shows.getUser().getNickName(), Shows.getUser().getUserType(), Shows.getPrice() + "", Shows.getUser().getUserId(), SubString_Utils.getImageUrl(Shows.getUser().getPortrait()), Shows.getViews(), Shows.getPublishTime() + "", Shows
                                    .getSerialNumber(), Shows.getTitle(), Shows.getInfos(), imageStrinig3, Shows.getRewardUse(), Shows.getLikeUser(), Shows.getFlowerUser(), Shows.getCommentUser(), Shows.getFavUser(),1);
                            beans.setIsTrade(Shows.getIsTrade());
                            if (Shows.getIsVideo() == 1) {
                                beans.setIsVideo(Shows.getIsVideo());
                                beans.setVideoName(Shows.getVideoName());
                                // Toast.makeText(getActivity(), ""+beans.getVideoName(), Toast.LENGTH_SHORT).show();
                            }
                            mWorkList.add(beans);
                        }

                        mWorkAllAdapter.notifyDataSetChanged();

                    } else {
                        HintText_Dialog mTextDialog = new HintText_Dialog(Search_2_Activity.this,
                                "搜索失败", "fail");
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

    // 获取作品数据集合
    private void getListShows2(final int pageIndex, String searchStr) {
        ShowsTerm mShowsTerm = new ShowsTerm();
        saveSharedPreferences = new SaveSharedPreferences();
        //mShowsTerm.setUserid(Login_Static.myUserID);
        mShowsTerm.setPageIndex(pageIndex);
        mShowsTerm.setPageSize(20);
        mShowsTerm.setTitile(searchStr);
        mShowsTerm.setType(1);

        if("yes".equals(unlogin)) {
            //未登录
            mShowsTerm.setIsTour(1);
        }else {
            mShowsTerm.setIsTour(0);

        }

        mShowsTerm.setIsOver(-1);
        mShowsTerm.setIsShow(-1);
        mShowsTerm.setSoType(-1);
        mShowsTerm.setLocalId(Login_Static.myUserID);
        mShowsTerm.setUserId(Login_Static.myUserID);
        String data = mGson.toJson(mShowsTerm);
        CLog.e("data", data);
        OKHttp.OkHttpPost("/Shows/GetListShows", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                CLog.e("testing", "错误");
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
            }

            @Override
            public void onResponse(String response, int id) {
                CLog.e("testinggg", "changeNickName:" + response);
                mPullToRefresh.onHeaderRefreshComplete();
                mPullToRefresh.onFooterRefreshComplete();
                try {
                    if (Json_Utils.getCode(response) == 0) {
                        if (pageIndex == 1) {
                            mWorkList.clear();
                        }
                        pageIndex_++;
                        JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject array = new JSONObject(jsonArray.getString(i));
                            Shows Shows = mGson.fromJson(array.toString(), Shows.class);
                            List<String> imageStrinig3 = new ArrayList<>();
                            List<String> likeUsername = new ArrayList<>();

                            if (Shows.getLikeUser() != null) {
                                for (int m = 0; m < Shows.getLikeUser().size(); m++) {
                                    likeUsername.add(Shows.getLikeUser().get(m).getNickName());
                                }
                            }

                            //对获取到的图片进行处理包装
                            if (Shows.getShowsPhoto() != null) {
                                String[] imag = Shows.getShowsPhoto().split(",");
                                for (int m = 0; m < imag.length; m++) {
                                    imageStrinig3.add(imag[m]);
                                }
                            }
                            WorkAll_Beans beans = new WorkAll_Beans(Shows.getId(), Shows.getUser().getNickName(), Shows.getUser().getUserType(), Shows.getPrice() + "", Shows.getUser().getUserId(), SubString_Utils.getImageUrl(Shows.getUser().getPortrait()),  Shows.getViews(),Shows.getPublishTime() + "", Shows
                                    .getSerialNumber(), Shows.getTitle(), Shows.getInfos(), imageStrinig3, Shows.getRewardUse(), Shows.getLikeUser(), Shows.getFlowerUser(), Shows.getCommentUser(), Shows.getFavUser(),1);
                            beans.setIsTrade(Shows.getIsTrade());
                            if (Shows.getIsVideo() == 1) {
                                beans.setIsVideo(Shows.getIsVideo());
                                beans.setVideoName(Shows.getVideoName());
                                // Toast.makeText(getActivity(), ""+beans.getVideoName(), Toast.LENGTH_SHORT).show();
                            }
                            mWorkList.add(beans);
                        }

                        mWorkAllAdapter.notifyDataSetChanged();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(mContext);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(mContext, emojicon);
    }

    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.Search_bottom_fragment, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
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
                    }

                }, 200L);
            } else {
                mRelativeLayout.setVisibility(View.GONE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
            }
        }
    }

    private void showEmotionView(boolean showAnimation) {
        if (showAnimation) {
            transitioner.setDuration(200);
        } else {
            transitioner.setDuration(0);
        }

        int statusBarHeight = SystemBarUtils.getStatusBarHeight(Search_2_Activity.this);
        emotionHeight = System_Utils.getKeyboardHeight(Search_2_Activity.this);

        System_Utils.hideSoftInput(mContext);
        mRelativeLayout.getLayoutParams().height = emotionHeight;
        mRelativeLayout.setVisibility(View.VISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //在5.0有navigationbar的手机，高度高了一个statusBar
        int lockHeight = System_Utils.getAppContentHeight(Search_2_Activity.this);

    }

    //按键事件
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mRelativeLayout.getVisibility() == View.VISIBLE) {
                mRelativeLayout.setVisibility(View.GONE);
            } else {
                finish();
            }
        }
        return true;
    }
}
