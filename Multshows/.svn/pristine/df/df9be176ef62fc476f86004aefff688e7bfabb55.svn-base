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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multshows.Activity.BoundYMD_Activity;
import com.multshows.Activity.Login_FindPassword_Activity;
import com.multshows.Activity.My_MyWalletFragment_Activity;
import com.multshows.Beans.User;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Views.Dialog_Hint;
import com.multshows.Views.HintText_Dialog;
import com.multshows.Views.MyApplication;
import com.multshows.Views.MyPicPopupWindow;
import com.multshows.Views.RechangeNumber_Pop;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 充值
 */
public class My_Kiting_Fragment extends Fragment{
    //上下文
    Context mContext;
    View mView;
    //控件
    TextView mYMD;//一麻袋账户
    EditText mEditText;
    Button mButton;//提现
    RelativeLayout mBound;//绑定一麻袋
    ImageView mImageView;
    boolean flag=false;//绑定一麻袋标志
    Gson mGson=new Gson();
    Dialog mDialog;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.my_kitting_fragment,null);
        mYMD= (TextView) mView.findViewById(R.id.my_kitting_fragment_YMD);
        mEditText= (EditText) mView.findViewById(R.id.my_kitting_fragment_editText);
        mButton= (Button) mView.findViewById(R.id.my_kitting_fragment_Button);
        mBound= (RelativeLayout) mView.findViewById(R.id.my_kitting_fragment_layout);
        mImageView= (ImageView) mView.findViewById(R.id.my_kitting_fragment_toBound);
        mDialog=new HintText_Dialog(mContext,R.style.MyDialog);
        getYMD();
        initListen();
        return mView;
    }

    /**
     * 事件处理
     */
    private void initListen() {
        //确定充值
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(flag){

                }else {
                    mDialog.show();
                    HintText_Dialog hintTextDialog=new HintText_Dialog(mContext,"请先绑定一麻袋","fail");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                          mDialog.dismiss();
                        }
                    },Login_Static.hintTime);
                }
            }
        });
        //点击绑定一麻袋
        mBound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, BoundYMD_Activity.class);
                startActivityForResult(intent,1);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            if(resultCode==2){
                String s=data.getStringExtra("YMD");
                mYMD.setText(s);
                mImageView.setVisibility(View.GONE);
                mBound.setEnabled(false);
                flag=true;
            }
        }
    }

    /**
     * 获取一麻袋信息
     */
    private void getYMD(){
            MyApplication myApplication = (MyApplication) getActivity().getApplication();
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
                                    if("".equals(mUser.getYmdAccount())){
                                        //未绑定一麻袋
                                        mYMD.setText("点击绑定一麻袋");
                                        mImageView.setVisibility(View.VISIBLE);
                                        mBound.setEnabled(true);
                                        flag=false;
                                    }else {
                                        mYMD.setText(mUser.getYmdAccount());
                                        mImageView.setVisibility(View.GONE);
                                        mBound.setEnabled(false);
                                        flag=true;
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

    }


}
