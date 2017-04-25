package com.multshows.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.multshows.Activity.Login_Agreement_Activity;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.Verification_Utils;
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
 * 描述：注册activity,加载不同的viewpage
 * 作者：李萧萧
 * 时间：2016.9/3
 */
public class Login_RegisterPhoneFragment extends Fragment {
    @Bind(R.id.phoneEditext)
    EditText mPhoneEditext;
    @Bind(R.id.next)
    Button mNext;
    @Bind(R.id.Agreement)
    TextView mAgreement;
    @Bind(R.id.ClearImage)
    ImageView mClearImage;
    Login_RegisterVerificationFragment mVerificationFragment;
    FragmentTransaction mFragmentTransaction;
    FragmentManager mFragmentManager;
    private boolean init;//记录界面是否为第一次加载
    MyApplication mApplication = new MyApplication();
    Dialog mDialog;//消息提示框
    public String IsThree;//是否为第三方登录
    String ExCode;//邀请码
    String Logintoken;//第三方token登录
    String IsExistMobile="no";
    String Identity;
    public Login_RegisterPhoneFragment() {

    }

    //EditText文本内容变化监听方法
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
//

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
//


        }

        @Override
        public void afterTextChanged(Editable s) {
//
            if(mPhoneEditext!=null&&!mPhoneEditext.getText().toString().equals("")){
                mClearImage.setVisibility(View.VISIBLE);
                if (Verification_Utils.checkMobileNumber(mPhoneEditext.getText().toString())){
                    mNext.setEnabled(true);
                    mNext.setTextColor(getResources().getColor(R.color.text_white));
                }else {
                    mNext.setEnabled(false);
                    mNext.setTextColor(getResources().getColor(R.color.text_gray));
                }
            }else {
                mNext.setEnabled(false);
                mNext.setTextColor(getResources().getColor(R.color.text_gray));
                mClearImage.setVisibility(View.INVISIBLE);
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registerphone, container, false);
        init = true;//第一次加载
        Bundle bundle = getArguments();
        mDialog = new HintText_Dialog(getActivity(), R.style.MyDialog);
        IsThree= (String) getActivity().getIntent().getSerializableExtra("IsThree");
        Logintoken= (String) getActivity().getIntent().getSerializableExtra("Logintoken");
        Identity= bundle.getString("Identity");
        mVerificationFragment=new Login_RegisterVerificationFragment();
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        ButterKnife.bind(this, view);
        mNext.setTextColor(getResources().getColor(R.color.text_gray));
        mPhoneEditext.addTextChangedListener(textWatcher);
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

    @OnClick({R.id.phoneEditext, R.id.next, R.id.Agreement, R.id.ClearImage})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phoneEditext:
                break;
            case R.id.next:
              //  mFragmentTransaction.setCustomAnimations(R.anim.shaixuan_translate, 0);
                if(!IsThree.equals("no")){
                    mDialog.show();
                    HintText_Dialog hint = new HintText_Dialog(getActivity(),"正在发送...", "");
                    IsExistMobile2(mPhoneEditext.getText().toString());
                }else{
                    mDialog.show();
                    HintText_Dialog hint = new HintText_Dialog(getActivity(),"正在发送...", "");
                    IsExistMobile(mPhoneEditext.getText().toString());
                }

                break;
            case R.id.Agreement:
                Intent mIntent = new Intent(getActivity(), Login_Agreement_Activity.class);
                mIntent.putExtra("contine","yes");
                startActivity(mIntent);
                break;
            case R.id.ClearImage:
                mPhoneEditext.setText("");
                break;
        }
    }
    public void SendRandomSMS(String mPhone){
        OkHttpUtils.get().url(mApplication.getUrl() + "/service/SendRandomSMS")
                .addParams("mobile", mPhone)
                .addParams("type", "1")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showError("发送失败",0);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                showError("发送成功",2);
                            } else {
                                //后期判断错误原因
                                showError("发送失败",0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    public void IsExistMobile(final String mPhone){
        OkHttpUtils.get().url(mApplication.getUrl() + "/User/IsExistMobile")
                .addParams("mobile", mPhone)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) != 0) {
                                SendRandomSMS(mPhone);
                            } else if (Json_Utils.getCode(response) ==1008){
                                showError("该手机号已注册",0);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    public void IsExistMobile2(final String mPhone){
        OkHttpUtils.get().url(mApplication.getUrl() + "/User/IsExistMobile")
                .addParams("mobile", mPhone)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                IsExistMobile="no";
                            } else if (Json_Utils.getCode(response) ==1008){
                                IsExistMobile="yes";
                            }
                            SendRandomSMS(mPhoneEditext.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
    //发布结果提示
    public void showError(final String errorString, final int is) {
        String isString=null;
        if(is==1){
            isString="success";
        }
        if(is==2){
            isString="success";
        }
        if(is==0){
            isString="fail";
        }
        HintText_Dialog hint = new HintText_Dialog(getActivity(), errorString, isString);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //得到返回值后，2秒后加载框消失
                if(is==2){
                    Bundle bundle = new Bundle();
                    ExCode = bundle.getString("ExCode");
                    bundle.putString("phone", mPhoneEditext.getText().toString());
                    bundle.putString("IsThree",IsThree);
                    bundle.putString("ExCode", ExCode);
                    bundle.putString("Logintoken",Logintoken);
                    bundle.putString("IsExistMobile",IsExistMobile);
                    bundle.putString("Identity",Identity);
                    mVerificationFragment.setArguments(bundle);
                    mFragmentTransaction.replace(R.id.RegisterFrameLayout, mVerificationFragment);
                    mFragmentTransaction.addToBackStack(null);
                    //提交事务
                    mFragmentTransaction.commit();
                }
                mDialog.dismiss();
            }
        }, 1000);
    }
}
