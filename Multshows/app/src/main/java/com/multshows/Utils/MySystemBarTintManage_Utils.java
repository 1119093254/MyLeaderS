package com.multshows.Utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.multshows.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 *  第三方开源库（解决 沉浸式与输入法遮挡问题）
 */
public class MySystemBarTintManage_Utils {
     Activity mActivity;
    public void setSystemBarTintManage(Activity context, int color) {
        mActivity=context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        //第三方开源库（解决 沉浸式与输入法遮挡问题）
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(color);//通知栏所需颜色
    }
    public void setSystemBarTintManage_noColor_2(Activity context){
        mActivity=context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        //第三方开源库（解决 沉浸式与输入法遮挡问题）
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.background_alight);//通知栏所需颜色
    }
   public void setSystemBarTintManage_noColor(Activity context){
       mActivity=context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        //第三方开源库（解决 沉浸式与输入法遮挡问题）
        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.white);//通知栏所需颜色
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    static  public void StatusBar(Activity context, int color){
        context.getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        //StatusBarUtils.StatusBar(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true,context);
        }
        //第三方开源库（解决 沉浸式与输入法遮挡问题）
        com.readystatesoftware.systembartint.SystemBarTintManager tintManager = new com.readystatesoftware.systembartint.SystemBarTintManager(context);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(color);//通知栏所需颜色
    }

    @TargetApi(19)
    private static void setTranslucentStatus(boolean on,Activity context) {
        Window win = context.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

}
