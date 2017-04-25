package com.multshows.Views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;

import com.multshows.R;


/**
 * 描述：绘制头像裁剪界面
 * 作者：贾强胜
 * 时间：2016.7.23
 */
public class ClipImageBorder_Views extends View {
    /**
     * 水平方向与View的边距
     */
    private int mHorizontalPadding = 20;
    /**
     * 垂直方向与View的边距
     */
    private int mVerticalPadding;
    /**
     * 绘制的矩形的宽度
     */
    private int mWidth;
    /**
     * 边框的颜色，默认为白色
     */
    private int mBorderColor = Color.parseColor("#ffffff");
    /**
     * 边框的宽度 单位dp
     */
    private int mBorderWidth = 5;
    /**
     * 蒙版
     */
    private Bitmap bitmap;
    /**
     * 画布
     */
    private Canvas mCanvas;
    /**
     * 屏幕大小
     */
    private int width, height;
    /**
     * 起点坐标
     */
    private int startX, startY;
    /**
     * 移动距离
     */
    private int distanceX, distanceY;
    /**
     * 图片坐标
     */
    private int widthX, heightY;

    private Paint mPaint,mPaint2;

    public ClipImageBorder_Views(Context context)
    {
        this(context, null);
        init();
    }

    public ClipImageBorder_Views(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
        init();
    }

    public ClipImageBorder_Views(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        // 计算padding的px
        mHorizontalPadding = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHorizontalPadding, getResources()
                        .getDisplayMetrics());
        mBorderWidth = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mBorderWidth, getResources()
                        .getDisplayMetrics());
        init();
       /* mPaint = new Paint();
        mPaint2 = new Paint();
        //抗锯齿
        mPaint.setAntiAlias(true);
        mPaint2.setAntiAlias(true);*/
    }
    private void init() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);

        width= wm.getDefaultDisplay().getWidth();
        height = wm.getDefaultDisplay().getHeight();
        // 创建空白画布
        bitmap = Bitmap.createBitmap(width,height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bitmap);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(2);
        mPaint.setAntiAlias(true);
    }
    @Override
    protected void onDraw(Canvas canvas)
    {
        super.onDraw(canvas);
      /*  //计算矩形区域的宽度
        mWidth = getWidth() - 2 * mHorizontalPadding;
        //计算距离屏幕垂直边界 的边距
        mVerticalPadding = (getHeight() - mWidth) / 2;*/
        // 清空上一次的绘图状态
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        mCanvas.drawPaint(mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        mCanvas.drawColor(getResources().getColor(R.color.grey_lucency));

        mCanvas.drawCircle(getWidth()/2,getHeight()/2,300, mPaint);
        canvas.drawBitmap(bitmap, 0, 0, null);

    }

}

