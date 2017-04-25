package com.multshows.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.multshows.R;
import com.multshows.Utils.MySystemBarTintManage_Utils;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * 设置——联系我们
 */
public class Setting_CallUs_BanQuan_Activity extends AppCompatActivity {
    //控件
    ImageView mReturn;//返回
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_call_us_ban);
        //沉浸式
        MySystemBarTintManage_Utils tintManageUtils = new MySystemBarTintManage_Utils();
        tintManageUtils.setSystemBarTintManage(Setting_CallUs_BanQuan_Activity.this, R.color.app_topColor);
        mReturn= (ImageView) findViewById(R.id.setting_CallUs_return);
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
