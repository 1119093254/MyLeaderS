package com.multshows.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.multshows.Beans.ChatEvent_Model;
import com.multshows.R;

import org.greenrobot.eventbus.EventBus;

/**
 * 填写实名认证
 */
public class MyInformation_RealName_No_Fragment extends Fragment{
    Context mContext;
    View mView;
    //控件
    Button mButton;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.my_information_realname_no,null);
        initView();
        initData();
        initListen();
        return mView;
    }

    /**
     * 初始化
     */
    private void initView() {
        mButton= (Button) mView.findViewById(R.id.INformation_realName_btn);
    }

    /**
     * 数据处理
     */
    private void initData() {
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //去修改
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new ChatEvent_Model("1","RenZheng"));
            }
        });
    }
}
