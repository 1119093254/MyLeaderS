package com.multshows.Views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.multshows.Beans.Anim_Model;
import com.multshows.R;

import org.greenrobot.eventbus.EventBus;

/**
 * DrawHook
 * Created by Zane on 2015/3/4.
 */
public class DrawHookView extends View {
    //绘制圆弧的进度值
    private int progress = 0;
    //线1的x轴
    private int line1_x = 0;
    //线1的y轴
    private int line1_y = 0;
    //线2的x轴
    private int line2_x = 0;
    //线2的y轴
    private int line2_y = 0;
    private int sped=5;
    boolean isTrue=true;

    public DrawHookView(Context context) {
        super(context);
    }

    public DrawHookView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawHookView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //绘制

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

       // progress++;

        /**
         * 绘制圆弧
         */
        Paint paint = new Paint();
        //设置画笔颜色
        paint.setColor(getResources().getColor(R.color.white));
        //设置圆弧的宽度
        paint.setStrokeWidth(10);
        //设置圆弧为空心
        paint.setStyle(Paint.Style.STROKE);
        //消除锯齿
        paint.setAntiAlias(true);
        //对勾长度
        int length = getWidth() / 2;
        //获取圆心的x坐标
        int center = getWidth() / 3;
        int center1 = center - getWidth() / 4;

        int width=getWidth() / 3;
        int hight=getHeight()/2;
      /*  //圆弧半径
        int radius = getWidth() / 2 - 5;

        //定义的圆弧的形状和大小的界限
        RectF rectF = new RectF(center - radius -1, center - radius -1 ,center + radius + 1, center + radius + 1);

        //根据进度画圆弧
        canvas.drawArc(rectF, 235, -360 * progress / 100, false, paint);*/

        /**
         * 绘制对勾
         */
         /*   //画对勾
            if(line1_x < length/2) {
                line1_x=line1_x+2;
                line1_y=line1_y+2;
            }
            //画第一根线
            //canvas.drawLine(center1, center, center1 + line1_x, center + line1_y, paint);
              canvas.drawLine(0,-hight, center1 + line1_x, center + line1_y, paint);
            if (line1_x == length/2) {
                line2_x = line1_x;
                line2_y = line1_y;
                line1_x=line1_x+2;
                line1_y=line1_y+2;
            }
            if (line1_x >= length/2 && line2_x <= getWidth()-4) {
                line2_x=line2_x+2;
                line2_y=line2_y-2;
            }
            //画第二根线
            canvas.drawLine(center1 + line1_x - 3, center + line1_y, center1 + line2_x, center + line2_y, paint);
        //每隔10毫秒界面刷新
        if(line2_x <= getWidth()-4)
        postInvalidateDelayed(10);*/
        //画对勾
        if(line1_x <=width) {
            line1_x=line1_x+sped;
            line1_y=line1_y+sped;
            //画第一根线
            //canvas.drawLine(center1, center, center1 + line1_x, center + line1_y, paint);
        }
        canvas.drawLine(0,hight,line1_x, hight + line1_y, paint);
        if (line1_x >= width && isTrue) {
            line2_x = width;
            line2_y = 2*hight;
            isTrue=false;
            Log.e("lines","画第二条线");
        }
        if (line1_x >= width && line2_x <= getWidth()-sped) {
            line2_x=line2_x+sped;
            line2_y=line2_y-sped;
            Log.e("lines","画第二条线222222");
            //画第二根线
            canvas.drawLine(width,2*hight,line2_x,line2_y, paint);
        }
        //每隔10毫秒界面刷新
        if(line2_x <= getWidth()-sped) {
            postInvalidateDelayed(10);
        }else {
            EventBus.getDefault().post(new Anim_Model("OK"));
        }
    }
}
