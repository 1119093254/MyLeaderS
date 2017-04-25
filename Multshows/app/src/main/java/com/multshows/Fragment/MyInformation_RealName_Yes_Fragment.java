package com.multshows.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.multshows.R;

/**
 * 填写实名认证
 */
public class MyInformation_RealName_Yes_Fragment extends Fragment{
    Context mContext;
    View mView;
    //控件
    TextView mTextView;
    TextView mHint;

    int type=1;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.my_information_realname_yes,null);
        initView();
        initData();
        initListen();
        return mView;
    }

    /**
     * 初始化
     */
    private void initView() {
        mTextView= (TextView) mView.findViewById(R.id.INformation_realName_yes);
        mHint= (TextView) mView.findViewById(R.id.INformation_realName_yesHint);
    }

    /**
     * 数据处理
     */
    private void initData() {
        Bundle bundle=getArguments();
        if(bundle!=null){
            type=bundle.getInt("type");
        }
        if(type==1){
            //1认证通过
            mTextView.setText("认证通过");
            mHint.setText("认证已通过,谢谢您的合作!");
        }else {
            ////2认证中
            mTextView.setText("认证中");
            mHint.setText("24小时内,工作人员将会审核,请耐心等待。");
        }
    }

    /**
     * 事件监听
     */
    private void initListen() {
    }

}
