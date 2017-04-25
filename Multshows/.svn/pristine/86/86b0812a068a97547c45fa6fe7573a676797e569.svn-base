package com.multshows.Views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * ScrollView自定义
 */
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //将 onInterceptTouchEvent的返回值设置为false，取消其对触摸事件的处理，将事件分发给子view
    //这样scrollow 里面的listView 就可以用自己的触摸事件了
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        //  return super.onInterceptTouchEvent(ev);
        return false;
    }
}
