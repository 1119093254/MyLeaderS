package com.multshows.Fragment;

import android.app.Dialog;
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

import com.multshows.R;
import com.multshows.Utils.CLog;
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
 * 描述：注册activity,加载不同的viewpage(邀请码页面)
 * 作者：李萧萧
 * 时间：2016.9/3
 */
public class Login_InvitationCodeFragment extends Fragment {
    @Bind(R.id.inviteEditext)
    EditText mExCode;//邀请码输入框
    @Bind(R.id.invitenext)
    Button mNext;//下一步
    @Bind(R.id.skipButton)
    Button mSkipButton;//跳过
   @Bind(R.id.skipButton2)
    Button mSkipButton2;//跳过2
    @Bind(R.id.InviteClearImage)
    ImageView mClearImage;//清除邀请码按钮
    Login_RegisterPhoneFragment mLogin_RegisterPhoneFragment;
    FragmentTransaction mFragmentTransaction;
    FragmentManager mFragmentManager;
    private boolean init;//记录界面是否为第一次加载
    MyApplication mApplication;
    Dialog mDialog;//消息提示框
    public String IsThree;//是否为第三方登录
    String Logintoken;//第三方token登录
    String Identity;
    public Login_InvitationCodeFragment() {

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
            if(mExCode!=null&&!mExCode.getText().toString().equals("")){
                mClearImage.setVisibility(View.VISIBLE);
                if (mExCode.getText().toString().length()>3){
                    mNext.setEnabled(true);
                    mSkipButton.setVisibility(View.INVISIBLE);
                    mSkipButton2.setVisibility(View.VISIBLE);
                    mNext.setTextColor(getResources().getColor(R.color.text_white));
                }else {
                    mNext.setEnabled(false);
                    mSkipButton.setVisibility(View.VISIBLE);
                    mSkipButton2.setVisibility(View.INVISIBLE);
                    mNext.setTextColor(getResources().getColor(R.color.text_gray));
                }
            }else {
                mNext.setEnabled(false);
                mSkipButton.setVisibility(View.VISIBLE);
                mSkipButton2.setVisibility(View.INVISIBLE);
                mNext.setTextColor(getResources().getColor(R.color.text_gray));
                mClearImage.setVisibility(View.INVISIBLE);
            }

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_invitecode, container, false);
        init = true;//第一次加载
        Bundle bundle = getArguments();
        mApplication= (MyApplication) getActivity().getApplication();
        mDialog = new HintText_Dialog(getActivity(), R.style.MyDialog);
        IsThree= (String) getActivity().getIntent().getSerializableExtra("IsThree");
        Logintoken= (String) getActivity().getIntent().getSerializableExtra("Logintoken");
        Identity= bundle.getString("Identity");
        mLogin_RegisterPhoneFragment=new Login_RegisterPhoneFragment();
        mFragmentManager = getFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        ButterKnife.bind(this, view);
        mExCode.addTextChangedListener(textWatcher);
        mNext.setTextColor(getResources().getColor(R.color.text_gray));
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

    @OnClick({R.id.inviteEditext, R.id.invitenext, R.id.InviteClearImage,R.id.skipButton,R.id.skipButton2})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.inviteEditext:
                break;
            case R.id.invitenext:
              //  mFragmentTransaction.setCustomAnimations(R.anim.shaixuan_translate, 0);
                if(!IsThree.equals("no")){
                    mDialog.show();
                    HintText_Dialog hint = new HintText_Dialog(getActivity(),"正在验证邀请码...", "");
                    IsCheckExCode(mExCode.getText().toString());
                }else{
                    mDialog.show();
                    HintText_Dialog hint = new HintText_Dialog(getActivity(),"正在验证邀请码...", "");
                    IsCheckExCode(mExCode.getText().toString());
                }
                break;
            case R.id.InviteClearImage:
                mExCode.setText("");
                break;
            case R.id.skipButton:
                Bundle bundle = new Bundle();
                bundle.putString("ExCode","");
                bundle.putString("IsThree",IsThree);
                bundle.putString("Identity",Identity);
                bundle.putString("Logintoken",Logintoken);
                mLogin_RegisterPhoneFragment.setArguments(bundle);
                mFragmentTransaction.replace(R.id.RegisterFrameLayout, mLogin_RegisterPhoneFragment);
                mFragmentTransaction.addToBackStack(null);
                //提交事务
                mFragmentTransaction.commit();
                break;
                case R.id.skipButton2:
                Bundle bundle2 = new Bundle();
                bundle2.putString("ExCode","");
                bundle2.putString("IsThree",IsThree);
                bundle2.putString("Logintoken",Logintoken);
                bundle2.putString("Identity",Identity);
                mLogin_RegisterPhoneFragment.setArguments(bundle2);
                mFragmentTransaction.replace(R.id.RegisterFrameLayout, mLogin_RegisterPhoneFragment);
                mFragmentTransaction.addToBackStack(null);
                //提交事务
                mFragmentTransaction.commit();
                break;
        }
    }
    public void IsCheckExCode(String excode){
        CLog.e("excode",excode);
        OkHttpUtils.get().url(mApplication.getUrl() + "/User/IsCheckExCode")
                .addParams("excode", excode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        showError("验证失败",0);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Integer.parseInt(Json_Utils.getTemplate(response)) ==1) {
                                showError("验证成功",2);
                            } else if (Integer.parseInt(Json_Utils.getTemplate(response)) ==2){
                                //后期判断错误原因
                                showError("邀请码不存在",0);
                            } else if (Integer.parseInt(Json_Utils.getTemplate(response)) ==3){
                                //后期判断错误原因
                                showError("邀请码无效",0);
                            }else {
                                //后期判断错误原因
                                showError("邀请码错误",0);
                            }
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
                    bundle.putString("ExCode", mExCode.getText().toString());
                    bundle.putString("IsThree",IsThree);
                    bundle.putString("Logintoken",Logintoken);
                    bundle.putString("Identity",Identity);
                    mLogin_RegisterPhoneFragment.setArguments(bundle);
                    mFragmentTransaction.replace(R.id.RegisterFrameLayout, mLogin_RegisterPhoneFragment);
                    mFragmentTransaction.addToBackStack(null);
                    //提交事务
                    mFragmentTransaction.commit();
                }
                mDialog.dismiss();
            }
        }, 1000);
    }
}
