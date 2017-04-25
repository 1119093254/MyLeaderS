package com.multshows.Views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.multshows.Login_Static;
import com.multshows.R;


/**
 * 描述：提示弹出框
 * 作者：贾强胜
 * 时间：2016.10.24
 */
public class HintText_Dialog extends Dialog{
    //上下文
    Context mContext;
    //控件
    ImageView mImageView;//加载图
    TextView mTextView;//加载提示文字
    //回传的值
    String texts=""; //提示文字
    String state="";//状态
    Animation animation;//补间动画

    public HintText_Dialog(Context context) {
        super(context);
        mContext=context;
    }

    /**
     * 构造方法
     * @param context 上下文
     * @param texts 传过来的提示文字
     * @param state 传过来的返回状态
     */
    public HintText_Dialog(Context context,String texts,String state) {
        super(context);
        mContext=context;
        this.texts=texts;
        this.state=state;
        initListen();
    }
    public HintText_Dialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定自定义布局
        this.setContentView(R.layout.hint_text_dialog);
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
        mTextView= (TextView) findViewById(R.id.hint_text_title);
        mImageView= (ImageView) findViewById(R.id.hint_text_image);
        Login_Static.dialog_textview=mTextView;
        Login_Static.dialog_imageview=mImageView;
        animation = AnimationUtils.loadAnimation(mContext,
                R.anim.rotate_upload);
        Login_Static.dialog_animation=animation;
    }

    /**
     * 功能：接受传的值改变textview和imageview的值
     *     事件监听
     */
    private void initListen() {
        mTextView= (TextView) Login_Static.dialog_textview;
        mTextView.setText(texts);
        if("".equals(state)) {
            //补间动画
            mImageView= (ImageView) Login_Static.dialog_imageview;
            mImageView.setImageResource(R.mipmap.icon_popup_ongoing);
            animation=Login_Static.dialog_animation;
            animation.start();
            mImageView.startAnimation(animation);
        }else if("success".equals(state)) {
            //访问成功
            mImageView= (ImageView) Login_Static.dialog_imageview;
            mImageView.setImageResource(R.mipmap.icon_popup_success);
            animation= Login_Static.dialog_animation;
            animation.cancel();
            mImageView.clearAnimation();
        }else if("fail".equals(state)){
            //访问失败
            mImageView= (ImageView) Login_Static.dialog_imageview;
            mImageView.setImageResource(R.mipmap.icon_popup_fail);
            animation= Login_Static.dialog_animation;
            animation.cancel();
            mImageView.clearAnimation();
        }
    }
}
