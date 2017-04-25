package com.multshows.Views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.multshows.R;
import com.multshows.Utils.SaveSharedPreferences;


/**
 * 描述：自定义popwindow，用于实现照片查看器底部弹出框样式含有视频选择
 * 作者：李萧萧
 * 时间：2016.10.10
 */
public class MyReleaseWindow extends PopupWindow {
    private View mMenuView;
    Context mContext;

    LinearLayout work_btn;//作品
    LinearLayout Idle_btn;//闲置
    LinearLayout activity_btn;//活动
    LinearLayout PublicWelfare_btn;//公益
    LinearLayout Photo_btn;//相册
    ImageView close;//关闭弹出框
    TextView mTextView;
    ImageView release_img4;//公益图片，任务图片的切换
    TextView release_img4Text;//公益文字，任务文字的切换
    ImageView release_img;//相册图片，圈子图片的切换
    TextView release_imgText;//相册文字，圈子文字的切换
    ImageView IdleorTaskImage;//闲置图片任务图片
    TextView IdleorTaskText;//相册文字，圈子文字的切换
    FrameLayout panel;//点击其它区域返回
    SaveSharedPreferences saveSharedPreferences = new SaveSharedPreferences();

    public MyReleaseWindow(Activity context, final View.OnClickListener itemsOnClick) {
        super(context);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.release_chioce_dialog, null);
        // MySystemBarTintManage_Utils manageUtils=new MySystemBarTintManage_Utils();
        //manageUtils.setSystemBarTintManage(context, R.color.black_alight_2);


        work_btn = (LinearLayout) mMenuView.findViewById(R.id.work_btn);
        IdleorTaskImage = (ImageView) mMenuView.findViewById(R.id.IdleorTask);
        IdleorTaskText = (TextView) mMenuView.findViewById(R.id.IdleorTaskText);
        Idle_btn = (LinearLayout) mMenuView.findViewById(R.id.Idle_btn);
        activity_btn = (LinearLayout) mMenuView.findViewById(R.id.activity_btn);
        PublicWelfare_btn = (LinearLayout) mMenuView.findViewById(R.id.PublicWelfare_btn);
        Photo_btn = (LinearLayout) mMenuView.findViewById(R.id.Photo_btn);
        close = (ImageView) mMenuView.findViewById(R.id.close);
        panel = (FrameLayout) mMenuView.findViewById(R.id.panel);
        release_img4 = (ImageView) mMenuView.findViewById(R.id.release_img4);
        release_img = (ImageView) mMenuView.findViewById(R.id.release_img5);
        mTextView = (TextView) mMenuView.findViewById(R.id.lookPhoto_dialog_text);
        release_img4Text = (TextView) mMenuView.findViewById(R.id.release_img4Text);
        release_imgText = (TextView) mMenuView.findViewById(R.id.release_img5Text);

        if (saveSharedPreferences.Get_PREFS(mContext, "Type").equals("mom")) {

            release_img4.setImageResource(R.drawable.release_img3);
            release_img4Text.setText("活动");
            release_img.setImageResource(R.drawable.release_img2);
            release_imgText.setText("闲置");
            IdleorTaskText.setText("任务");
            IdleorTaskImage.setImageResource(R.drawable.release_img7);
            close.setImageResource(R.drawable.release_delimg);
        }
        if (saveSharedPreferences.Get_PREFS(mContext, "Type").equals("baby")) {
            IdleorTaskText.setText("作品");
            IdleorTaskImage.setImageResource(R.drawable.release_img1);
            Idle_btn.setVisibility(View.VISIBLE);
            work_btn.setVisibility(View.INVISIBLE);
            activity_btn.setVisibility(View.INVISIBLE);
            PublicWelfare_btn.setVisibility(View.INVISIBLE);
            Photo_btn.setVisibility(View.INVISIBLE);
            close.setImageResource(R.drawable.item_bottom_publish_yes);
            /*Photo_btn.setVisibility(View.VISIBLE);
            release_img4.setImageResource(R.drawable.release_img4);
            release_img4Text.setText("公益");
            release_img.setImageResource(R.drawable.release_img5);
            release_imgText.setText("相册");
            IdleorTaskText.setText("闲置");
            IdleorTaskImage.setImageResource(R.drawable.release_img6);*/
        }
        //取消按钮
        close.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        panel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //销毁弹出框
                dismiss();
            }
        });
        //上
        work_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnClick.onClick(v);

            }
        });
        //中
        Idle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnClick.onClick(v);
            }
        });

        //下
        activity_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnClick.onClick(v);
            }
        });
        PublicWelfare_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemsOnClick.onClick(v);
            }
        });
        Photo_btn.setOnClickListener(new View.OnClickListener() {
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
        this.setOutsideTouchable(true);
        //设置SelectPicPopupWindow弹出窗体动画效果
        //this.setAnimationStyle(R.style.AnimBottom);
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        //mMenuView添加OnTouchListener监听判断获取触屏位置如果在选择框外面则销毁弹出框
        mMenuView.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                int height = mMenuView.findViewById(R.id.layoutChioceLayout).getTop();
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