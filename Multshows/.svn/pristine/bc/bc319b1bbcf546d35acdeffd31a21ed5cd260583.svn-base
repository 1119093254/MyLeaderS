package com.multshows.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.multshows.R;
import com.multshows.Utils.MySystemBarTintManage_Utils;


/**
 * 描述：自定义popwindow，用于实现照片查看器底部弹出框样式含有视频选择
 * 作者：李萧萧
 * 时间：2016.10.10
 */
public class MyPicpupWindow extends PopupWindow {
    private View mMenuView;
    Context mContext;
    Button btn_Cancel;//取消
    Button btn_Top;//拍照
    Button btn_Center;//手机相册
    Button btn_Bottom;//拍照
    TextView mTextView;
    public MyPicpupWindow(Activity context, final View.OnClickListener itemsOnClick,
                          String top, String center, String bottom, String position) {
        super(context);
        mContext=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.photo_chioce_dialog, null);
       // MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
       // manageUtils.setSystemBarTintManage(context,R.color.black_alight_2);

        btn_Cancel = (Button) mMenuView.findViewById(R.id.chioce_cancle);
        btn_Top = (Button) mMenuView.findViewById(R.id.chioce_camera);
        btn_Center = (Button) mMenuView.findViewById(R.id.chioce_video);
        btn_Bottom = (Button) mMenuView.findViewById(R.id.chioce_photo);
        mTextView= (TextView) mMenuView.findViewById(R.id.lookPhoto_dialog_text);
        btn_Top.setText(top);
        btn_Center.setText(center);
        btn_Bottom.setText(bottom);

        //取消按钮
        btn_Cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //上
        btn_Top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnClick.onClick(v);

            }
        });
        //中
        btn_Center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnClick.onClick(v);
            }
        });
        //下
        btn_Bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnClick.onClick(v);
            }
        });


        //设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        //设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.FILL_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.lookPhoto_dialog_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }
    

}
