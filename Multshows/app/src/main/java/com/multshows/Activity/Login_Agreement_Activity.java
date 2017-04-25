package com.multshows.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.multshows.R;
import com.multshows.Utils.MySystemBarTintManage_Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 描述：网络协议界面
 * 作者：李萧萧
 * 时间：2016.9.1
 */
public class Login_Agreement_Activity extends AppCompatActivity {

    @Bind(R.id.Classification_return)
    ImageView mClassificationReturn;//返回图标
    @Bind(R.id.contine)
    TextView mcontine;//继续注册
    @Bind(R.id.UserAgreement)
    WebView mUserAgreement;//本地html协议显示webView
    String contine="";//接收上一页传过来的值，判断是否从注册页面跳过来的
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_agreement);
        ButterKnife.bind(this);
        contine=getIntent().getStringExtra("contine");
        if(contine!=null&&contine.equals("yes")){
            mcontine.setVisibility(View.VISIBLE);
        }else {
            mcontine.setVisibility(View.GONE);
        }
        WebSettings settings = mUserAgreement.getSettings();
        //支持JS
        settings.setJavaScriptEnabled(true);
        //html中的按钮点击监听
        mUserAgreement.addJavascriptInterface(new JSInterface (),"Android");
        mUserAgreement.loadUrl("file:///android_asset/agreement.html");
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        mUserAgreement.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return false;
            }
        });


        MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(Login_Agreement_Activity.this, R.color.green);//导航栏颜色
    }

    @OnClick({R.id.Classification_return, R.id.UserAgreement,R.id.contine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Classification_return://返回图标点击事件
                finish();
                break;
            case R.id.UserAgreement:
                break;
            case R.id.contine://继续注册，返回到注册页面继续注册
                finish();
                break;
        }
    }
    class JSInterface {
        @JavascriptInterface
        public void showToast(String arg){
            finish();
        }
    }
}