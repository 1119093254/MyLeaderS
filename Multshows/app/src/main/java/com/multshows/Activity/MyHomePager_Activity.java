package com.multshows.Activity;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.multshows.Fragment.MyHome_other_Fragment;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.SaveSharedPreferences;
import com.multshows.Views.PullToRefresh;
import com.rockerhieu.emojicon.EmojiconEditText;
import com.rockerhieu.emojicon.EmojiconGridFragment;
import com.rockerhieu.emojicon.EmojiconsFragment;
import com.rockerhieu.emojicon.emoji.Emojicon;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyHomePager_Activity extends AppCompatActivity implements EmojiconsFragment.OnEmojiconBackspaceClickedListener,
        EmojiconGridFragment.OnEmojiconClickedListener{
    SaveSharedPreferences saveSharedPreferences;
    int screenHeight;
    @Bind(R.id.MyPageComment_Layout)
    RelativeLayout Comment_Layout;  //评论布局
    @Bind(R.id.MyComment_EditText)
    EmojiconEditText comment_EditText;  //评论文本框
    public static boolean isShow = false;//记录表情是否正在显示
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
        /*this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);*/
        saveSharedPreferences = new SaveSharedPreferences();
        saveSharedPreferences.Save_PREFS(MyHomePager_Activity.this, "ismy", "yes");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home_pager);
        ButterKnife.bind(this);
        mKeyboardStateListeners = new ArrayList<OnSoftKeyboardStateChangedListener>();
        WindowManager wm = (WindowManager)
                getSystemService(Context.WINDOW_SERVICE);
        screenHeight = wm.getDefaultDisplay().getHeight();
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
                if (isKeyboardShowing||isShow) {//键盘显示
                    Comment_Layout.setVisibility(View.VISIBLE);
                } else {
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
        //注册布局变化监听
        getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(mLayoutChangeListener);
        showFragment(MyHome_other_Fragment.class);
    }

    public <T extends Fragment> void showFragment(Class<T> clzz) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        try {
            if (fragment == null) {
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, clzz.newInstance(), clzz.getSimpleName()).commit();
            } else {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, clzz.newInstance(), clzz.getSimpleName()).commit();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (!removeFragment()) {
            super.onBackPressed();
        } else {

        }
    }

    public boolean removeFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragmentContainer);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            saveSharedPreferences.Save_PREFS(MyHomePager_Activity.this, "ismy", "no");//是否进入的我的主页，yes表示进入的是我的主页，no表示进入的是发现的闲置页
            finish();
        }

        return false;
    }
}
