package com.multshows.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.multshows.R;
import com.multshows.Utils.Json_Utils;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 描述：注册activity,加载不同的viewpage(身份选择页面)
 * 作者：李萧萧
 * 时间：2016.9/3
 */
public class Login_identityChioce extends Fragment {

    Login_InvitationCodeFragment mLogin_InvitationCodeFragment;
    FragmentTransaction mFragmentTransaction;
    FragmentManager mFragmentManager;
    @Bind(R.id.ChioceVipLayout)
    RelativeLayout mChioceVipLayout;
    @Bind(R.id.ChioceParentLayout)
    RelativeLayout mChioceParentLayout;
    private boolean init;//记录界面是否为第一次加载
    MyApplication mApplication;
    Dialog mDialog;//消息提示框
    public String IsThree;//是否为第三方登录
    String Logintoken;//第三方token登录

    public Login_identityChioce() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chioce_identity, container, false);
        init = true;//第一次加载
        mApplication = (MyApplication) getActivity().getApplication();
        mDialog = new HintText_Dialog(getActivity(), R.style.MyDialog);
        IsThree = (String) getActivity().getIntent().getSerializableExtra("IsThree");
        Logintoken = (String) getActivity().getIntent().getSerializableExtra("Logintoken");
        mLogin_InvitationCodeFragment = new Login_InvitationCodeFragment();
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        ButterKnife.bind(this, view);
        return view;
    }

    /**
     * 功能：该frament第一次显示的时候，加载网络数据
     * * 参数：无
     * 返回：无
     */
    private void getservciesData() {

    }

    @Override
    public void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (getUserVisibleHint()) {
            getdata();
        }
    }

    /**
     * 功能：加载网络数据
     * 参数：无
     * 返回：无
     */
    private void getdata() {
        if (init) {
            init = false;//第一次加载数据后，设置为false,不在加载数据
            getservciesData();
        }
    }

    /**
     * 功能：该界面是否为显示状态
     * 参数：isVisibleToUser 是否正在显示
     * 返回：无
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        // TODO Auto-generated method stub
        super.setUserVisibleHint(isVisibleToUser);
        //界面显示的时候加载数据
        if (isVisibleToUser) {
            getdata();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.ChioceVipLayout, R.id.ChioceParentLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ChioceVipLayout:
                Bundle bundle = new Bundle();
                bundle.putString("IsThree", IsThree);
                bundle.putString("Logintoken", Logintoken);
                bundle.putString("Identity", "mom");
                mLogin_InvitationCodeFragment.setArguments(bundle);
                mFragmentTransaction.replace(R.id.RegisterFrameLayout, mLogin_InvitationCodeFragment);
                mFragmentTransaction.addToBackStack(null);
                //提交事务
                mFragmentTransaction.commit();
                break;
            case R.id.ChioceParentLayout:
                Bundle bundle1 = new Bundle();
                bundle1.putString("IsThree", IsThree);
                bundle1.putString("Logintoken", Logintoken);
                bundle1.putString("Identity", "vip");
                mLogin_InvitationCodeFragment.setArguments(bundle1);
                mFragmentTransaction.replace(R.id.RegisterFrameLayout, mLogin_InvitationCodeFragment);
                mFragmentTransaction.addToBackStack(null);
                //提交事务
                mFragmentTransaction.commit();
                break;
        }
    }
}
