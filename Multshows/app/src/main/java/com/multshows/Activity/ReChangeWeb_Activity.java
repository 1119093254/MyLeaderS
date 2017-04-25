package com.multshows.Activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.HideInputManager_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Views.MyApplication;

/**
 * 银联支付界面
 */
public class ReChangeWeb_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    WebView mWebView;
    String mUrl;
    String tid="";//交易单id
    MyApplication mMyApplication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_re_change_web);
        MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(ReChangeWeb_Activity.this, R.color.app_topColor);
        Login_Static.mPlaceActivity.clear();
        Login_Static.mPlaceActivity.add(ReChangeWeb_Activity.this);
        mMyApplication= (MyApplication)getApplication();
        initView();
        initDate();
        initListen();
    }
    /**
     * 功能：初始化
     * 参数：无
     * 返回：无
     */
    private void initView() {
        mReturn = (ImageView) findViewById(R.id.Rechage_Web_return);
        mReturnLayout= (RelativeLayout) findViewById(R.id.Rechage_Web_returnLayout);
        mParentLayout= (LinearLayout) findViewById(R.id.Rechage_Web_ParentsLayout);
        mTopTitle= (TextView) findViewById(R.id.Rechage_Web_TopTitle);
        mWebView= (WebView) findViewById(R.id.Rechage_Web_webView);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(ReChangeWeb_Activity.this,mReturnLayout,mParentLayout,mReturn,mTopTitle);
    }

    /**
     * 功能：数据处理
     * 参数：无
     * 返回：无
     */
    private void initDate() {
        Intent intent=getIntent();
        tid=intent.getStringExtra("tid");

        mUrl ="http://www.leadershows.com/Service/QuickPay?tid="+tid+"&uid="+Login_Static.myUserID;
        CLog.e("yinlian",mUrl);
    }

    /**
     * 功能：事件监听
     * 参数：无
     * 返回：无
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏软键盘
                HideInputManager_Utils.hideInputManager(ReChangeWeb_Activity.this);
                finish();
            }
        });
        //WebView加载web资源
        mWebView.loadUrl(mUrl);
        //支持javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
        // 设置可以支持缩放
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        //自适应屏幕
        mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

}
