package com.multshows.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.multshows.R;

/**
 * 和宝贝的关系（没有用到这个部分，请忽略）
 */
public class My_Information_BabyShip_Fragment extends Fragment{
    View mView;
    //控件
    RelativeLayout mFatherLayout;
    RelativeLayout mMotherLayout;
    ImageView mFather;
    ImageView mMother;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.my_information_badyship,null);
        mFatherLayout= (RelativeLayout) mView.findViewById(R.id.my_BabyShip_fragment_fatherLayout);
        mMotherLayout= (RelativeLayout) mView.findViewById(R.id.my_BabyShip_fragment_motherLayout);
        mFather= (ImageView) mView.findViewById(R.id.my_BabyShip_fragment_father);
        mMother= (ImageView) mView.findViewById(R.id.my_BabyShip_fragment_mother);
        initData();
        initListen();
        return mView;
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
        mFatherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFather.setVisibility(View.VISIBLE);
                mMother.setVisibility(View.INVISIBLE);
            }
        });
        mMotherLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMother.setVisibility(View.VISIBLE);
                mFather.setVisibility(View.INVISIBLE);
            }
        });
    }
}
