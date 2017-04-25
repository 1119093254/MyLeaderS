package com.multshows.Views;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.multshows.Utils.CrashHandler;
import com.multshows.Utils.SharedPreferencesUtils;
import com.multshows.Views.NineGridview.NineGridView;
import com.squareup.picasso.Picasso;

import cn.jpush.android.api.JPushInterface;
import io.rong.imlib.RongIMClient;

/**
 * Created by Administrator on 2016/9/10.
 */
public class MyApplication extends Application{
    //虚拟键盘
  private static MyApplication context;
      //服务器路径
    private String url="http://api.mshow.test.viigoo.com:3311/api";//www.api.leadershows.com/api--///--http://api.mshow.test.viigoo.com:3311/api
    //上传图片服务器路径
    private String fileUrl="http://file.test.viigoo.com:3322/api/leadershows/upload";
    //图片服务器路径
    private String imageUrl="http://file.test.viigoo.com:3322/image/leadershows/";//http://api.test.viigoo.com:3311/api/home/GetStaticImgUrl

    private String mUrl="http://192.168.189.2:8080/MyLeaderS/servlet/LeaderServlet";//www.api.leadershows.com/api--///--http://api.mshow.test.viigoo.com:3311/api

    public String getmUrl() {
        return mUrl;
    }

    public void setmUrl(String mUrl) {
        this.mUrl = mUrl;
    }
//上线地址
  /*private String url="http://api.leadershows.com/api";//www.api.leadershows.com/api--///--http://api.mshow.test.viigoo.com:3311/api
    //上传图片服务器路径
    private String fileUrl="http://file.viigoo.com/api/leadershows/upload";
    //图片服务器路径
    private String imageUrl="http://file.viigoo.com/image/leadershows/";//http://api.test.viigoo.com:3311/api/home/GetStaticImgUrl*/
    /** Picasso 加载 */
    private class PicassoImageLoader implements NineGridView.ImageLoader {
        @Override
        public void onDisplayImage(Context context, ImageView imageView, String url) {
            Picasso.with(context).load(url)//
                    .config(Bitmap.Config.ALPHA_8)
            /*        .resize(Dp2Px(250),Dp2Px(250))
                    .centerCrop()*/
                   // .placeholder(R.drawable.defult)//
                   // .error(R.drawable.defult)//
                    .into(imageView);

        }
        @Override
        public Bitmap getCacheImage(String url) {
            return null;
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        //提交崩溃日志初始化
        CrashHandler catchHandler = CrashHandler.getInstance();
        catchHandler.init(getApplicationContext());
        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIMClient 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            RongIMClient.init(this);
        }
        NineGridView.setImageLoader(new PicassoImageLoader());
        //初始化  Fresco加载图片框架
        Fresco.initialize(getApplicationContext());
        //虚拟键盘与表情流畅切换
        context = this;
        SharedPreferencesUtils.config(this);

        // 将“5858a445”替换成您申请的APPID，申请地址：http://open.voicecloud.cn
        SpeechUtility.createUtility(context, SpeechConstant.APPID +"=5858da1c");

    }
    public static MyApplication getInstance_2() {
        return context;
    }
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

   public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    //dp 转变为px
    public int Dp2Px(float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    //px转变为dp
    public int Px2Dp(float px) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }
}
