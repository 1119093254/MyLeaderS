package com.multshows.Views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.multshows.R;


/**
 * 描述：自定义dialog实现添加相册的弹出框效果
 * 作者：贾强胜
 * 时间：2016.7.14
 */
public class Dialog_Hint extends Dialog{
    //控件
    TextView mTitle;//主题
    TextView mCancel;//取消
    TextView mOk;//保存
    //上下文
    Context mContext;
    String mTitleContent;//主题文本
    View.OnClickListener mListener;

    public Dialog_Hint(Context context) {
        super(context);
        mContext=context;
    }

    public Dialog_Hint(Context context, int themeResId,String title,View.OnClickListener itemsOnClick) {
        super(context, themeResId);
        mContext=context;
        mTitleContent=title;
        mListener=itemsOnClick;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定自定义布局
        this.setContentView(R.layout.hint_dialog);
        this.setCancelable(false);// 设置点击屏幕Dialog不消失
        //去黑角
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        initView();
        initListen();

    }

    /**
     * 初始化
     */
    private void initView() {
        mTitle= (TextView) findViewById(R.id.hint_dialog_Title);
        mCancel= (TextView) findViewById(R.id.hint_dialog_cancel);
        mOk= (TextView) findViewById(R.id.hint_dialog_ok);
        mTitle.setText(mTitleContent);
    }

    /**
     * 事件监听
     */
    private void initListen() {
        //取消
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        //确认
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onClick(view);
            }
        });
    }
}
