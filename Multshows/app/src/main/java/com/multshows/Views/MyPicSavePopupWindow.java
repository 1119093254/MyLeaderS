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


/**
 * 描述：自定义popwindow，用于实现照片查看器底部弹出框样式
 * 作者：贾强胜
 * 时间：2016.7.15
 */
public class MyPicSavePopupWindow extends PopupWindow {
    private View mMenuView;
    Context mContext;
    Button btn_Cancel;//取消
    Button btn_Save;//保存
    public MyPicSavePopupWindow(Activity context, final View.OnClickListener itemsOnClick
                               ) {
        super(context);
        mContext=context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.photo_save_dialog, null);
      //  MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
      //  manageUtils.setSystemBarTintManage(context,R.color.black_alight_2);

        btn_Cancel = (Button) mMenuView.findViewById(R.id.lookPhoto_dialog_cancel);

        btn_Save = (Button) mMenuView.findViewById(R.id.PhotoSaveButtton);


        //取消按钮
        btn_Cancel.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });


        //保存
       btn_Save.setOnClickListener(new View.OnClickListener() {
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
