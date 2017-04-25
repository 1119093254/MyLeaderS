package com.multshows.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.multshows.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * 描述：用户信息补全  //没用到该界面
 * 作者：李萧萧
 * 时间：2016.9.1
 */
public class LoginCompleteDataActivity extends AppCompatActivity {

    @Bind(R.id.Classification_return)
    ImageView mClassificationReturn;
    @Bind(R.id.my_toplayout)
    RelativeLayout mMyToplayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_complete_data);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.Classification_return)
    public void onClick() {
        finish();
    }
}
