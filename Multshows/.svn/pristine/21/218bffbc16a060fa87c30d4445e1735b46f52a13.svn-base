package com.multshows.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.multshows.R;
/**
 * 重复登录启动
 */
public class Again_Login_Activity extends Activity {
//控件
    TextView mCancel;//取消
    TextView mOK;//重新登录
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_again_login);

        mCancel= (TextView) findViewById(R.id.Again_Login_cancel);
        mOK= (TextView) findViewById(R.id.Again_Login_ok);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
