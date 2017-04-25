package com.multshows.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.multshows.Activity.BoundYMD_Activity;
import com.multshows.Activity.My_Information_RealName_Activity;
import com.multshows.Activity.My_Kitting_Activity;
import com.multshows.Beans.User;
import com.multshows.Beans.UserAuthentication;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 提现，一麻袋
 */
public class Kitting_BoundYMD_Fragment extends Fragment {
    View mView;
    Context mContext;
    RelativeLayout mOK;//已绑定一麻袋
    RelativeLayout mNO;//未绑定一麻袋
    TextView mYMD;//一麻袋账户
    ImageView mCheck;//对勾
    MyApplication myApplication;
    Gson mGson = new Gson();
    Dialog mDialog;
    String mYMD_;
    String YMDNumber = "";//一麻袋数字账号
    Dialog_Hint dialog_hint;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.kitting_bound_ymd, null);
        myApplication= (MyApplication) getActivity().getApplication();
        initView();
        initData();
        initListen();
        return mView;
    }

    private void initView() {
        mOK = (RelativeLayout) mView.findViewById(R.id.kitting_Bound_YMD_ok);
        mNO = (RelativeLayout) mView.findViewById(R.id.kitting_Bound_YMD_no);
        mYMD = (TextView) mView.findViewById(R.id.kitting_Bound_YMD_Text);
        mCheck = (ImageView) mView.findViewById(R.id.kitting_Bound_YMD_check);
    }

    private void initData() {
        getYMD();
    }

    private void initListen() {
        //未绑定一麻袋
        mNO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getState();
            }
        });
        //已绑定一麻袋
        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, My_Kitting_Activity.class);
                intent.putExtra("YMD", mYMD_);
                intent.putExtra("YMDNumber", YMDNumber);
                getActivity().setResult(2, intent);
                getActivity().finish();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        getYMD();
    }

    /**
     * 获取一麻袋信息
     */
    private void getYMD() {

        OkHttpUtils.get().url(myApplication.getUrl() + "/User/GetUser")
                .addParams("userid", Login_Static.myUserID)
                .addParams("targetuserid", "0")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "个人信息 失败" + e.toString());
                    }

                    @Override
                    public void onResponse(String response, final int id) {
                        try {
                            CLog.e("testing", "个人信息：" + Json_Utils.getTemplate(response));
                            if (Json_Utils.getCode(response) == 0) {
                                User mUser = mGson.fromJson(Json_Utils.getTemplate(response), User.class);
                                if ("".equals(mUser.getYmdAccount()) || "null".equals(mUser.getYmdAccount())) {
                                    //未绑定一麻袋
                                    mOK.setVisibility(View.GONE);
                                    mNO.setVisibility(View.VISIBLE);
                                } else {
                                    if (mUser.getYmdAccount() != null && mUser.getYmdAccount().length() >= 11) {
                                        mYMD_ = mUser.getYmdAccount();
                                        YMDNumber = mUser.getYmdNumber();
                                        String account = mUser.getYmdAccount().substring(0, 4) + "****" +
                                                mUser.getYmdAccount().substring(8, mUser.getYmdAccount().length());
                                        mYMD.setText(account);
                                    }
                                    mOK.setVisibility(View.VISIBLE);
                                    mNO.setVisibility(View.GONE);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

    }

    /**
     * 判断申请状态
     */
    private void getState() {
        OkHttpUtils.get().url(myApplication.getUrl() + "/User/GetAuthen")
                .addParams("userId", Login_Static.myUserID)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        CLog.e("testing", "获取用户实名认证失败" + e.toString());
                        mDialog = new HintText_Dialog(mContext, R.style.MyDialog);
                        mDialog.show();
                        HintText_Dialog mHintText_dialog = new HintText_Dialog(mContext, "未知异常", "fail");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.dismiss();
                            }
                        }, Login_Static.hintTime);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        CLog.e("testing", "获取用户实名认证" + response);
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                UserAuthentication bean = mGson.fromJson(Json_Utils.getTemplate(response), UserAuthentication.class);
                                if (bean.getUserId() != null && !"null".equals(bean.getUserId()) && !"".equals(bean.getUserId())) {
                                    if (bean.getIsAuthenticated() == 1) {
                                        //1认证通过
                                        Intent intent = new Intent(mContext, BoundYMD_Activity.class);
                                        startActivity(intent);
                                    } else if (bean.getIsAuthenticated() == 2) {
                                        //认证中
                                        mDialog = new HintText_Dialog(mContext, R.style.MyDialog);
                                        mDialog.show();
                                        HintText_Dialog mHintText_dialog = new HintText_Dialog(mContext,
                                                "实名认证审核中无法绑定一麻袋,请等待...", "fail");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                            }
                                        }, Login_Static.hintTime);
                                    } else {
                                        dialog_hint = new Dialog_Hint(mContext, 0,
                                                "还未进行实名认证,是否前往实名认证", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intent intent = new Intent(mContext, My_Information_RealName_Activity.class);
                                                startActivity(intent);
                                                dialog_hint.dismiss();
                                            }
                                        });
                                        dialog_hint.show();
                                    }
                                } else {
                                    dialog_hint = new Dialog_Hint(mContext, 0,
                                            "还未进行实名认证,是否前往实名认证", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent intent = new Intent(mContext, My_Information_RealName_Activity.class);
                                            startActivity(intent);
                                            dialog_hint.dismiss();
                                        }
                                    });
                                    dialog_hint.show();
                                }
                            } else {
                                mDialog = new HintText_Dialog(mContext, R.style.MyDialog);
                                mDialog.show();
                                HintText_Dialog mHintText_dialog = new HintText_Dialog(mContext,
                                        Json_Utils.getMessage(response), "fail");
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        mDialog.dismiss();
                                    }
                                }, Login_Static.hintTime);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });
    }
}
