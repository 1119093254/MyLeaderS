package com.multshows.Fragment;

import android.app.Activity;
import android.app.assist.AssistStructure;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.multshows.Beans.UserAPI;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.MomORBaby_Utils;
import com.multshows.Utils.OKHttp;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import okhttp3.Call;

/**
 * 修改昵称
 */
public class My_Information_NickName_Fragment extends Fragment{
    Context mContext;
    View mView;
    //控件
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    TextView mSave;//确定
    EditText mEditText;//昵称
    ImageView mDelete;//删除
    String name="";

    Gson mGson=new Gson();
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.my_information_nickname,null);
        mEditText= (EditText) mView.findViewById(R.id.My_Information_NickName_edit);
        mDelete= (ImageView) mView.findViewById(R.id.My_Information_NickName_delete);
        mReturn= (ImageView) mView.findViewById(R.id.My_Inforation_NickName_return);
        mReturnLayout= (RelativeLayout) mView.findViewById(R.id.My_Inforation_NickName_returnLayout);
        mParentLayout= (LinearLayout) mView.findViewById(R.id.My_Inforation_NickName_ParentsLayout);
        mTopTitle= (TextView) mView.findViewById(R.id.My_Inforation_NickName_TopTitle);
        mSave= (TextView) mView.findViewById(R.id.My_Inforation_NickName_save);
        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(getActivity(),mReturnLayout,mParentLayout,mReturn,mTopTitle,mSave);
        initData();
        initListen();

        return mView;
    }
    /**
     * 数据处理
     */
    private void initData() {
        Bundle bundle=getArguments();
        name=bundle.getString("nickName");
        if(!"".equals(name)||name!=null){
            mEditText.setText(name);
            mDelete.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 事件监听
     */
    private void initListen() {
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(i2>0){
                    mDelete.setVisibility(View.VISIBLE);
                }else {
                    mDelete.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
                mDelete.setVisibility(View.INVISIBLE);
            }
        });
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        //确定保存
        mSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSave.setEnabled(false);
                final String text=mEditText.getText().toString();
                String con=text.replace(" ","");
                if(!"".equals(text) && !"".equals(con)) {
                    UserAPI userAPI = new UserAPI();
                    userAPI.setNickName(text);
                    userAPI.setUserId(Login_Static.myUserID);
                    String data=mGson.toJson(userAPI);
                    OKHttp.OkHttpPost("?type=7", "data", data, new StringCallback() {
                        @Override
                        public void onError(Call call, Exception e, int id) {
                            mSave.setEnabled(true);
                            Toast.makeText(mContext, "输入字符不支持或网络异常", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onResponse(String response, int id) {
                            CLog.e("testing","changeNickName:"+response);
                            mSave.setEnabled(true);
                            try {
                                if(Json_Utils.getCode(response)==0){
                                    Login_Static.mAccount.setNickName(text);
                                    getActivity().finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }else {
                    //提示输入姓名
                    Toast.makeText(mContext, "昵称不能为空", Toast.LENGTH_SHORT).show();
                    mSave.setEnabled(true);
                }
            }
        });

    }
}