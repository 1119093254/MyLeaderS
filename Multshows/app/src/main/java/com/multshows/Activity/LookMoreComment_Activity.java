package com.multshows.Activity;

import android.animation.LayoutTransition;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Adapter.MoreCommentList_Adapter;
import com.multshows.Beans.ShowsComment;
import com.multshows.Beans.ShowsCommentAdd;
import com.multshows.Beans.WorkAll_Beans;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Emoji_Change;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.OKHttp;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Utils.SystemBarUtils;
import com.multshows.Utils.System_Utils;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
/**
 * 描述：更多评论界面（注册viewpage之间的跳转）
 * 作者：李萧萧
 * 时间：2016.9.1
 */
public class LookMoreComment_Activity extends AppCompatActivity implements EmojiconsFragment.OnEmojiconBackspaceClickedListener, EmojiconGridFragment.OnEmojiconClickedListener {
    List<ShowsComment> mList;//评论数据集合
    WorkAll_Beans mBeans;
    @Bind(R.id.LookMoreTitle)
    TextView mLookMoreTitle;//更多评论标题
    @Bind(R.id.LookMore_return)
    ImageView mLookMoreReturn;//返回图标
    @Bind(R.id.CommentListView)
    ListView mCommentListView;//评论显示listView

    @Bind(R.id.comment_Layout)
    RelativeLayout Comment_Layout;  //底部表情布局
     @Bind(R.id.My_CommentTopLayout)
    RelativeLayout My_CommentTopLayout;  //顶部布局
    @Bind(R.id.SentComment)
    TextView SentComment;  //发表
    @Bind(R.id.comment_bottom)
    RelativeLayout mRelativeLayout;  //评论布局
    @Bind(R.id.commentEditText)
    EmojiconEditText comment_EditText;  //评论文本框
    @Bind(R.id.Commentface_Image)
    ImageView CommentfaceImage;//表情按钮
    @Bind(R.id.allLayout)
    RelativeLayout allLayout;
    @Bind(R.id.MoreBottomLayout)
    RelativeLayout MoreBottomLayout;
    Dialog mDialog;//消息提示框
    Gson mGson = new Gson();
    MyApplication mApplication ;
    Emoji_Change mEmoji_change = new Emoji_Change();//表情，匹配符替换类
    //表情框高度
    int emotionHeight;
    int firstHeight;
    int screenHeight;
    String TargetUserNickName;//被回复人的名字
    String workId;//作品id
    String userid;//用户id
    String ReplyId;//被回复者id
    String TargetUserId;//用户id
    Dialog_Hint mDialogHint;//确认删除

    InputMethodManager imm;
    SaveSharedPreferences mSave = new SaveSharedPreferences();
    public boolean isShow = false;//记录表情是否正在显示
    //表情框显示标志
    boolean face_Flag = false;
    MoreCommentList_Adapter mAdapter;
    private final LayoutTransition transitioner = new LayoutTransition();//键盘和表情切换

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
        setContentView(R.layout.activity_lookmorecomment);
        ButterKnife.bind(this);
        mApplication= (MyApplication) getApplication();
        //设置状态栏颜色
        MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(LookMoreComment_Activity.this, R.color.green);
        mKeyboardStateListeners = new ArrayList<OnSoftKeyboardStateChangedListener>();
        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        screenHeight = wm.getDefaultDisplay().getHeight();
        mDialog = new HintText_Dialog(LookMoreComment_Activity.this, R.style.MyDialog);
        Intent intent = getIntent();
        mBeans = (WorkAll_Beans) intent.getSerializableExtra("bean");
       // mList = mBeans.getCommentBeansList();
        mLookMoreTitle.setText("更多评论" + "(" + mBeans.getCommentBeansList().size() + ")");
        mList=new ArrayList<>();
        GetShowListComment(mBeans.getId());
        
        
        //注册布局变化监听
        if(!mSave.Get_PREFS(LookMoreComment_Activity.this, "Type").equals("mom")){
             tintManageUtils = new MySystemBarTintManage_Utils();
            //tintManageUtils.setSystemBarTintManage(MainActivity.this, R.color.app_babyColor);
            tintManageUtils.setSystemBarTintManage_noColor(LookMoreComment_Activity.this);
            My_CommentTopLayout.setBackgroundResource(R.drawable.classify_topbg);
            mLookMoreReturn.setImageResource(R.drawable.return_arrow);
            mLookMoreTitle.setTextColor(getResources().getColor(R.color.red_grey));
        }else {
            allLayout.setBackgroundColor(getResources().getColor(R.color.white));
        }
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
                    Comment_Layout.setVisibility(View.VISIBLE);
                    // .setFocusable(true);
                    comment_EditText.requestFocus();//获取焦点
                } else if (!isShow) {
                    Comment_Layout.setVisibility(View.GONE);
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

        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
        CommentfaceImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setEmojiconFragment(false);
                CLog.e("face_Flag", "face_Flag" + face_Flag);

                if (face_Flag && isShow) {
                    //隐藏
                    //点击输入框，打开软键盘，隐藏表情
                    face_Flag = false;
                    hideEmotionView(true);
                    isShow = false;
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                } else {
                    //显示
                    //点击表情图标,如果表情显示，隐藏表情，打开软键盘。反之，显示表情，隐藏键盘
                    face_Flag = true;
                    showEmotionView(System_Utils.isKeyBoardShow(LookMoreComment_Activity.this));
                    isShow = true;
                }
            }


        });
        //评论列表点击判断是否是本人评论，用于删除
        mCommentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                if (!mSave.Get_PREFS(LookMoreComment_Activity.this, "Unlogin").equals("yes")){
                    if (!mList.get(position).getUser().getUserId().equals(Login_Static.myUserID)) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        workId = mBeans.getId();
                        userid = mBeans.getUserid();
                        TargetUserId = mList.get(position).getUser().getUserId();
                        ReplyId = mList.get(position).getReplyId();
                        TargetUserNickName = mList.get(position).getUser().getNickName();
                        CLog.e("mworkId", "" + workId + " " + userid + " " + TargetUserId + " " + ReplyId);
                    }else {
                        mDialogHint = new Dialog_Hint(LookMoreComment_Activity.this, 0,
                                "确认删除吗?", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                DeleteComment(mList,position);
                                mDialogHint.dismiss();
                            }
                        });
                        mDialogHint.show();

                    }
                }else {
                    Intent mIntent = new Intent(LookMoreComment_Activity.this, LoginActivity.class);
                    startActivity(mIntent);
                }

            }
        });
        //点击底部评论布局，弹出键盘进行评论
        MoreBottomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mSave.Get_PREFS(LookMoreComment_Activity.this, "Unlogin").equals("yes")){
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    workId = mBeans.getId();
                    userid = mBeans.getUserid();
                    TargetUserId = mBeans.getUserid();
                    ReplyId = "0";
                    TargetUserNickName = "";
                }else {
                    //未登录，跳转到登录界面
                    Intent mIntent = new Intent(LookMoreComment_Activity.this, LoginActivity.class);
                    startActivity(mIntent);
                }

            }
        });
        //发表评论
        SentComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!comment_EditText.getText().toString().equals(""))
                    releaseComment();
                else {
                    mDialog.show();
                    showError("内容不可为空", 0);
                }
            }
        });
    }

    /**
     * 隐藏emoji
     **/
    private void hideEmotionView(boolean showKeyBoard) {
        if (mRelativeLayout.isShown()) {
            if (showKeyBoard) {
                RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams) Comment_Layout.getLayoutParams();
                localLayoutParams.height = mRelativeLayout.getTop();
                localLayoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
                mRelativeLayout.setVisibility(View.GONE);
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                System_Utils.showKeyBoard(comment_EditText);
                comment_EditText.postDelayed(new Runnable() {

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

    private void lockContainerHeight(int paramInt) {
        //RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams) mLayout.getLayoutParams();
        // localLayoutParams.height = emotionHeight;
        // localLayoutParams.width = RelativeLayout.LayoutParams.MATCH_PARENT;
    }

    public void unlockContainerHeightDelayed() {
        // ((RelativeLayout.LayoutParams) mLayout.getLayoutParams()).height =emotionHeight;
    }
    //显示emoji
    private void showEmotionView(boolean showAnimation) {
        if (showAnimation) {
            transitioner.setDuration(200);
        } else {
            transitioner.setDuration(0);
        }

        int statusBarHeight = SystemBarUtils.getStatusBarHeight(LookMoreComment_Activity.this);
        emotionHeight = System_Utils.getKeyboardHeight(LookMoreComment_Activity.this);

        System_Utils.hideSoftInput(comment_EditText);
        mRelativeLayout.getLayoutParams().height = emotionHeight;
        mRelativeLayout.setVisibility(View.VISIBLE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //在5.0有navigationbar的手机，高度高了一个statusBar
        int lockHeight = System_Utils.getAppContentHeight(this);
//            lockHeight = lockHeight - statusBarHeight;
        lockContainerHeight(lockHeight);
    }


    private void setEmojiconFragment(boolean useSystemDefault) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.comment_fragment, EmojiconsFragment.newInstance(useSystemDefault))
                .commit();
    }
    //返回前一页
    @OnClick(R.id.LookMore_return)
    public void onClick() {
        HideInputManager_Utils.hideInputManager(LookMoreComment_Activity.this);
        finish();
    }

    @Override
    public void onEmojiconBackspaceClicked(View v) {
        EmojiconsFragment.backspace(comment_EditText);
    }

    @Override
    public void onEmojiconClicked(Emojicon emojicon) {
        EmojiconsFragment.input(comment_EditText, emojicon);
    }

    //发表评论接口调用
    private void releaseComment() {
        ShowsCommentAdd mShowsCommentAdd = new ShowsCommentAdd();//添加评论
        mDialog.show();
        HintText_Dialog hint = new HintText_Dialog(LookMoreComment_Activity.this, "正在评论...", "");
        mShowsCommentAdd.setForeignld(workId);
        mShowsCommentAdd.setUserId(Login_Static.myUserID);
        mShowsCommentAdd.setTargetUserId(TargetUserId);
        mShowsCommentAdd.setAuthorId(userid);
        mShowsCommentAdd.setType(1);
        mShowsCommentAdd.setReplyId(ReplyId);
        mShowsCommentAdd.setContent(mEmoji_change.EmojiChange(comment_EditText.getText().toString()));
        String data = mGson.toJson(mShowsCommentAdd);
        CLog.e("testing", data);
        OKHttp.OkHttpPost("/Comment/AddComment", "", data, new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                showError("评论失败", 0);
            }

            @Override
            public void onResponse(String response, int id) {
                try {
                    CLog.e("testing", "成功：" + response);
                    if (Json_Utils.getCode(response) == 0) {
                        hideEmotionView(true);
                        face_Flag = false;
                        isShow = false;
                    /*    UserHead mUserHead = new UserHead();
                        mUserHead.setPortrait(Login_Static.mAccount.getPortrait());
                        mUserHead.setNickName(Login_Static.mAccount.getNickName());
                        mUserHead.setUserId(Login_Static.myUserID);
                        ShowsComment mShowsComment = new ShowsCommentUser("0", mUserHead, mEmoji_change.EmojiChange(comment_EditText.getText().toString()), GetDate(), TargetUserNickName,Login_Static.myUserID);
                        mList.add(mShowsComment);
                        mAdapter.notifyDataSetChanged();*/
                        GetShowListComment(mBeans.getId());
                        comment_EditText.setText("");
                        mSave.Save_PREFS(LookMoreComment_Activity.this, "WorkId", mBeans.getId());
                        mLookMoreTitle.setText("更多评论" + "(" + mBeans.getCommentBeansList().size() + ")");
                        showError("评论成功", 1);
                    } else showError("评论失败", 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //发布结果提示
    public void showError(final String errorString, final int is) {
        String isString = null;
        if (is == 1) {
            isString = "success";
        }
        if (is == 0) {
            isString = "fail";
        }
        HintText_Dialog hint = new HintText_Dialog(LookMoreComment_Activity.this, errorString, isString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //得到返回值后，2秒后加载框消失
                mDialog.dismiss();
            }
        }, 1000);
    }

    public String GetDate() {
        Date now = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");//可以方便地修改日期格式
        String hehe = dateFormat.format(now);
        System.out.println(hehe);
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int date = c.get(Calendar.DATE);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        int second = c.get(Calendar.SECOND);
        return (year + "-" + month+1 + "-" + date + " " + hour + ":" + minute + ":" + second);
    }
    //调取获取评论接口，获取评论列表
    private void GetShowListComment(String showsId){
        CLog.e("showsId","/Comment/ListShowsComment?pageIndex="+0+"&pageSize="+0+"&showsId="+showsId);
        OKHttp.OkHttpPost("/Comment/ListShowsComment?pageIndex="+0+"&pageSize="+0+"&showsId="+showsId, "", "", new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", e.toString());
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                mList.clear();
                                JSONArray jsonArray = new JSONArray(Json_Utils.getTemplate(response));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    ShowsComment comment=mGson.fromJson(jsonArray.getString(i),ShowsComment.class);
                                    mList.add(comment);
                                }
                                Collections.reverse(mList);
                                if(mAdapter==null){
                                    mAdapter = new MoreCommentList_Adapter(mList, LookMoreComment_Activity.this, mBeans.getUserid());
                                    mCommentListView.setAdapter(mAdapter);
                                }else {
                                    mAdapter.notifyDataSetChanged();
                                }

                            } else {

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    //删除评论
    private void DeleteComment(final List<ShowsComment> mMList, final int position){
        OkHttpUtils.get().url(mApplication.getUrl() + "/Comment/DelComment")
                .addParams("commentId", mMList.get(position).getId())
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", e.toString());
                        mDialog.show();
                        showError("删除失败", 0);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                mDialog.show();
                                showError("删除成功", 1);
                                mMList.remove(position);
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mDialog.show();
                                showError("删除失败", 0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}