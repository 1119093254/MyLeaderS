package com.multshows.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.multshows.Fragment.Login_identityChioce;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.MySystemBarTintManage_Utils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 描述：注册页面（注册viewpage之间的跳转）
 * 作者：李萧萧
 * 时间：2016.9.1
 */
public class LoginRegisterActivity extends BaseActivity {

    @Bind(R.id.Classification_return)
    ImageView mClassificationReturn;//返回按钮
    @Bind(R.id.my_toplayout)
    RelativeLayout mMyToplayout;//顶部布局
    FragmentManager mFragmentManager;
    private FragmentTransaction transaction;
    FragmentTransaction mFragmentTransaction;
    Login_identityChioce mLogin_identityChioce;//注册身份选择界面
    public String IsThree;//是否为第三方注册(接收上一页传过来的)
    public String Logintoken;//第三方注册token(接收上一页传过来的)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_register);
        MySystemBarTintManage_Utils.StatusBar(LoginRegisterActivity.this,R.color.green);
        ButterKnife.bind(this);

        Intent mIntent=getIntent();
        IsThree=mIntent.getStringExtra("IsThree");
        Logintoken=mIntent.getStringExtra("Logintoken");
        CLog.e("IsThree",""+IsThree);
        mLogin_identityChioce=new Login_identityChioce();
        Bundle bundle2 = new Bundle();
        bundle2.putString("IsThree", IsThree);//传至下一页fragment
        mLogin_identityChioce.setArguments(bundle2);
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        //默认显示身份切换fragment
        mFragmentTransaction.add(R.id.RegisterFrameLayout, mLogin_identityChioce);
        mFragmentTransaction.commit();
    }

    @OnClick({R.id.Classification_return})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Classification_return:
                //返回到前一个fragment，不存在fragment，则关闭该activity
                if(getSupportFragmentManager().getBackStackEntryCount()>0){
                    getSupportFragmentManager().popBackStack();
                }else {
                    finish();
                }

                break;
          /*  case R.id.register:
                Intent mIntent=new Intent(LoginRegisterActivity.this, LoginRegisterSucessActivity.class);
                startActivity(mIntent);
                break;*/
        }
    }

    @Override
    public void findViews() {

    }

    @Override
    public void addListeners() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
