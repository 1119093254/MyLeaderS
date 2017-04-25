package com.multshows.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.multshows.Login_Static;
import com.multshows.R;

/**
 * 和宝贝的关系
 */
public class My_Information_SexChange_Fragment extends Fragment{
    View mView;
    Context mContext;
    //控件
    RelativeLayout mBoyLayout;
    RelativeLayout mGirlLayout;
    RelativeLayout mBaoMiLayout;
    ImageView mBoy;
    ImageView mGirl;
    ImageView mBaoMi;
    SendSexMessage mSexMessage;
    public interface SendSexMessage{
        void setSexMessage(int sex);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
        mSexMessage= (SendSexMessage) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.my_information_sexchange,null);
        mBoyLayout= (RelativeLayout) mView.findViewById(R.id.my_SexChange_fragment_boyLayout);
        mGirlLayout= (RelativeLayout) mView.findViewById(R.id.my_SexChange_fragment_girlLayout);
        mBaoMiLayout= (RelativeLayout) mView.findViewById(R.id.my_SexChange_fragment_baomiLayout);
        mBoy= (ImageView) mView.findViewById(R.id.my_SexChange_fragment_boy);
        mGirl= (ImageView) mView.findViewById(R.id.my_SexChange_fragment_girl);
        mBaoMi= (ImageView) mView.findViewById(R.id.my_SexChange_fragment_baomi);
        initData();
        initListen();
        return mView;
    }

    /**
     * 数据处理
     */
    private void initData() {
        if(Login_Static.mAccount.getSex()==0){
            //保密
            mBaoMi.setVisibility(View.VISIBLE);
            mBoy.setVisibility(View.INVISIBLE);
            mGirl.setVisibility(View.INVISIBLE);
        }else if(Login_Static.mAccount.getSex()==1){
            //男
            mBoy.setVisibility(View.VISIBLE);
            mGirl.setVisibility(View.INVISIBLE);
            mBaoMi.setVisibility(View.INVISIBLE);
        }else {
            //女
            mGirl.setVisibility(View.VISIBLE);
            mBoy.setVisibility(View.INVISIBLE);
            mBaoMi.setVisibility(View.INVISIBLE);
        }


    }

    /**
     * 事件监听
     */
    private void initListen() {
        mBoyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBoy.setVisibility(View.VISIBLE);
                mGirl.setVisibility(View.INVISIBLE);
                mBaoMi.setVisibility(View.INVISIBLE);
                mSexMessage.setSexMessage(1);
            }
        });
        mGirlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGirl.setVisibility(View.VISIBLE);
                mBoy.setVisibility(View.INVISIBLE);
                mBaoMi.setVisibility(View.INVISIBLE);
                mSexMessage.setSexMessage(2);
            }
        });
        mBaoMiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaoMi.setVisibility(View.VISIBLE);
                mBoy.setVisibility(View.INVISIBLE);
                mGirl.setVisibility(View.INVISIBLE);
                mSexMessage.setSexMessage(0);
            }
        });
    }
}
