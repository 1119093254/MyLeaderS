package com.multshows.Fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.multshows.R;
import com.multshows.Utils.MomORBaby_Utils;

/**
 * 我的宝贝信息——修改昵称
 */
public class My_BabyChangeName_Fragment extends Fragment{
    Context mContext;
    View mView;
    //控件
    EditText mEditText;
    ImageView mImageView;//清空
    ImageView mReturn;//返回
    RelativeLayout mReturnLayout;//顶部布局
    LinearLayout mParentLayout;//父布局
    TextView mTopTitle;//大标题
    TextView mOK;//确定
    String name="";
    SendBabyMessage mBabyMessage;
    public interface SendBabyMessage{
        void sendBabyMessageName(String name);
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
        mView=inflater.inflate(R.layout.baby_change_name,null);
        mImageView= (ImageView) mView.findViewById(R.id.baby_change_name_delete);
        mReturnLayout= (RelativeLayout) mView.findViewById(R.id.My_BabyChangeName_returnLayout);
        mParentLayout= (LinearLayout) mView.findViewById(R.id.My_BabyChangeName_ParentsLayout);
        mTopTitle= (TextView) mView.findViewById(R.id.My_BabyChangeName_TopTitle);
        mEditText= (EditText) mView.findViewById(R.id.baby_change_name_EditText);
        mReturn= (ImageView) mView.findViewById(R.id.My_BabyChangeName_return);
        mOK= (TextView) mView.findViewById(R.id.My_BabyChangeName_save);

        MomORBaby_Utils momORBabyUtils=new MomORBaby_Utils();
        momORBabyUtils.isMomORBaby(getActivity(),mReturnLayout,mParentLayout,mReturn,mTopTitle,mOK);

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
        if(bundle!=null && !"".equals(name)){
            mEditText.setText(name);
            mImageView.setVisibility(View.VISIBLE);
        }else {
            mEditText.setText("");
            mImageView.setVisibility(View.INVISIBLE);
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
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if(count>0){
                        mImageView.setVisibility(View.VISIBLE);
                    }else {
                        mImageView.setVisibility(View.INVISIBLE);
                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        //清空
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEditText.setText("");
                mImageView.setVisibility(View.INVISIBLE);
            }
        });
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        //保存
        mOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=mEditText.getText().toString();
                String con=text.replace(" ","");
                if(!text.equals("") && !"".equals(con)) {
                    mBabyMessage.sendBabyMessageName(mEditText.getText().toString());
                    getActivity().finish();
                }else {
                    Toast.makeText(mContext, "昵称不能为空", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
