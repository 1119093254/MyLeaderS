package com.multshows.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.multshows.R;
import com.multshows.Utils.MomORBaby_Utils;

/**
 * 我的宝贝——性别修改
 */
public class My_BabySexChange_Fragment extends Fragment{
    View mView;
    Context mContext;
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    RelativeLayout mBoyLayout;
    RelativeLayout mGirlLayout;
    RelativeLayout mBaoMiLayout;
    ImageView mBoy;
    ImageView mGirl;
    ImageView mBaoMi;
    SendBabyMessage mBabyMessage;
    int sex=0;
    public interface SendBabyMessage{
        void sendBabyMessageSex(int sex);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
        mBabyMessage= (SendBabyMessage) activity;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.my_baby_sexchange,null);
        mReturn= (ImageView) mView.findViewById(R.id.My_BabyChangeSex_return);
        mReturnLayout= (RelativeLayout) mView.findViewById(R.id.My_BabyChangeSex_returnLayout);
        mParentLayout= (LinearLayout) mView.findViewById(R.id.My_BabyChangeSex_ParentsLayout);
        mTopTitle= (TextView) mView.findViewById(R.id.My_BabyChangeSex_TopTitle);
        mBoyLayout= (RelativeLayout) mView.findViewById(R.id.My_BabyChangeSex_boyLayout);
        mGirlLayout= (RelativeLayout) mView.findViewById(R.id.My_BabyChangeSex_girlLayout);
        mBaoMiLayout= (RelativeLayout) mView.findViewById(R.id.My_BabyChangeSex_baomiLayout);
        mBoy= (ImageView) mView.findViewById(R.id.My_BabyChangeSex_boy);
        mGirl= (ImageView) mView.findViewById(R.id.My_BabyChangeSex_girl);
        mBaoMi= (ImageView) mView.findViewById(R.id.My_BabyChangeSex_baomi);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(getActivity(),mReturnLayout,mParentLayout,mReturn,mTopTitle);

        initData();
        initListen();
        return mView;
    }

    /**
     * 数据处理
     */
    private void initData() {
        Bundle bundle=getArguments();
        sex=bundle.getInt("sex",0);
        if(sex==0){
            //保密
            mBaoMi.setVisibility(View.VISIBLE);
            mBoy.setVisibility(View.INVISIBLE);
            mGirl.setVisibility(View.INVISIBLE);
        }else if(sex==1){
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
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBabyMessage.sendBabyMessageSex(sex);
                getActivity().finish();
            }
        });
        mBoyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBoy.setVisibility(View.VISIBLE);
                mGirl.setVisibility(View.INVISIBLE);
                mBaoMi.setVisibility(View.INVISIBLE);
                sex=1;
            }
        });
        mGirlLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGirl.setVisibility(View.VISIBLE);
                mBoy.setVisibility(View.INVISIBLE);
                mBaoMi.setVisibility(View.INVISIBLE);
                sex=2;
            }
        });
        mBaoMiLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBaoMi.setVisibility(View.VISIBLE);
                mBoy.setVisibility(View.INVISIBLE);
                mGirl.setVisibility(View.INVISIBLE);
                sex=0;
            }
        });

    }
}
