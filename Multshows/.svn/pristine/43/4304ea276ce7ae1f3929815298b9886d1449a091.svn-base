/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.multshows.Views;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;

import com.multshows.R;

import java.util.ArrayList;


//import com.jakewharton.nineoldandroids.sample.R;
//import com.nineoldandroids.animation.ValueAnimator;
//import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

/**
 * This class is the custom view where all of the Droidflakes are drawn. This class has
 * all of the logic for adding, subtracting, and rendering Droidflakes.
 */
public class FlakeRewardView extends View {

    Bitmap droid;       // The bitmap that all flakes use
    Bitmap droid2;
    Bitmap droid3;
    Bitmap droid4;
    Bitmap droid5;
    Bitmap droid6;       // The bitmap that all flakes use
    Bitmap droid7;
    Bitmap droid8;
    Bitmap droid9;

    int numFlakes = 0;  // Current number of flakes
    ArrayList<FlakeReward> flakes = new ArrayList<FlakeReward>(); // List of current flakes

    // Animator used to drive all separate flake animations. Rather than have potentially
    // hundreds of separate animators, we just use one and then update all flakes for each
    // frame of that single animation.
    ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
    long startTime, prevTime; // Used to track elapsed time for animations and fps
    int frames = 0;     // Used to track frames per second
    Paint textPaint;    // Used for rendering fps text
    float fps = 0;      // frames per second
    Matrix m = new Matrix(); // Matrix used to translate/rotate each flake during rendering
    String fpsString = "";
    String numFlakesString = "";

    /**
     * Constructor. Create objects used throughout the life of the View: the Paint and
     * the animator
     */
    public FlakeRewardView(Context context) {
        super(context);
        //打赏图画
        droid = BitmapFactory.decodeResource(getResources(), R.drawable.float_pic1);
        droid2 = BitmapFactory.decodeResource(getResources(), R.drawable.float_pic2);
        droid3 = BitmapFactory.decodeResource(getResources(), R.drawable.float_pic3);
        droid4 = BitmapFactory.decodeResource(getResources(), R.drawable.float_pic5);
        droid5 = BitmapFactory.decodeResource(getResources(), R.drawable.float_pic5);
        droid6 = BitmapFactory.decodeResource(getResources(), R.drawable.float_pic6);
        droid7= BitmapFactory.decodeResource(getResources(), R.drawable.float_pic7);
        droid8 = BitmapFactory.decodeResource(getResources(), R.drawable.float_pic8);
        droid9 = BitmapFactory.decodeResource(getResources(), R.drawable.float_pic1);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(24);

        // This listener is where the action is for the flak animations. Every frame of the
        // animation, we calculate the elapsed time and update every flake's position and rotation
        // according to its speed.
        animator.addUpdateListener(new AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                long nowTime = System.currentTimeMillis();
                float secs = (nowTime - prevTime) / 300f;
                prevTime = nowTime;

                for (int i = 0; i < numFlakes; ++i) {
                    FlakeReward flake = flakes.get(i);
                    flake.y += (flake.speed * secs);
                    if(flake.rom==1){

                        flake.x += (flake.speed * secs)/3;
                    }else  if(flake.rom==2){
                        flake.x -= (flake.speed * secs)/3;
                    }

                    if (flake.y > getHeight()) {
                        // If a flake falls off the bottom, send it back to the top
                        flake.y = 0 - flake.height;
                    }

                    flake.rotation = flake.rotation + (flake.rotationSpeed * secs);
                }
                // Force a redraw to see the flakes in their new positions and orientations
                invalidate();
            }
        });
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(2000);
    }

    public int getNumFlakes() {
    	if(numFlakes > 75)
    	{
    		return 75;
    	}
        return numFlakes;
    }

    private void setNumFlakes(int quantity) {
        numFlakes = quantity;
        numFlakesString = "numFlakes: " + numFlakes;
    }

    /**
     * Add the specified number of droidflakes.
     */
   public void addFlakes(int quantity) {
	   if(numFlakes <= 75)//���ﶨ�廨��������Լ���ɫ��ʽ
	   {
	        for (int i = 0; i < quantity; ++i) {
	        	if(i % 9 == 0){
                    int rom=(int)(1+Math.random()*(1-0+1));//随机生成 整数1 ，2

                    flakes.add(FlakeReward.createFlake(getWidth(), droid,rom));
                }
	        	else if(i % 9 == 1){
                    int rom=(int)(1+Math.random()*(1-0+1));
                    flakes.add(FlakeReward.createFlake(getWidth(), droid2,rom));
                }

	        	else if(i % 9 == 2){
                    int rom=(int)(1+Math.random()*(1-0+1));
                    flakes.add(FlakeReward.createFlake(getWidth(), droid3,rom));
                }

	        	else if(i % 9 == 3){
                    int rom=(int)(1+Math.random()*(1-0+1));
                    flakes.add(FlakeReward.createFlake(getWidth(), droid4,rom));
                }
                else if(i % 9 == 4){
                    int rom=(int)(1+Math.random()*(1-0+1));
                    flakes.add(FlakeReward.createFlake(getWidth(), droid5,rom));
                }else if(i % 9 == 5){
                    int rom=(int)(1+Math.random()*(1-0+1));
                    flakes.add(FlakeReward.createFlake(getWidth(), droid6,rom));
                }else if(i % 9 == 6){
                    int rom=(int)(1+Math.random()*(1-0+1));
                    flakes.add(FlakeReward.createFlake(getWidth(), droid7,rom));
                }else if(i % 9 == 7){
                    int rom=(int)(1+Math.random()*(1-0+1));
                    flakes.add(FlakeReward.createFlake(getWidth(), droid8,rom));
                }
                else{
                    int rom=(int)(1+Math.random()*(1-0+1));
                    flakes.add(FlakeReward.createFlake(getWidth(), droid9,rom));
                }

	        }
	        setNumFlakes(numFlakes + quantity);
	   }
    }

    /**
     * Subtract the specified number of droidflakes. We just take them off the end of the
     * list, leaving the others unchanged.
     */
    public void subtractFlakes(int quantity) {
        for (int i = 0; i < quantity; ++i) {
            int index = numFlakes - i - 1;
            if(index < 0 || index >= numFlakes)
            {
            	return;
            }
            flakes.remove(index);
        }
        setNumFlakes(numFlakes - quantity);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        // Reset list of droidflakes, then restart it with 8 flakes
        flakes.clear();
        numFlakes = 0;
        addFlakes(0);
        // Cancel animator in case it was already running
        animator.cancel();
        // Set up fps tracking and start the animation
        startTime = System.currentTimeMillis();
        prevTime = startTime;
        frames = 0;
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // For each flake: back-translate by half its size (this allows it to rotate around its center),
        // rotate by its current rotation, translate by its location, then draw its bitmap
        for (int i = 0; i < numFlakes; ++i) {
            FlakeReward flake = flakes.get(i);
            m.setTranslate(-flake.width/2, -flake.height/2);
            m.postRotate(flake.rotation);
            m.postTranslate(flake.width/2 + flake.x, flake.height/2 + flake.y);
            canvas.drawBitmap(flake.bitmap, m, null);
        }
        // fps counter: count how many frames we draw and once a second calculate the
        // frames per second
        ++frames;
        long nowTime = System.currentTimeMillis();
        long deltaTime = nowTime - startTime;
        if (deltaTime > 1000) {
            float secs = deltaTime / 1000f;
            fps = frames / secs;
            fpsString = "fps: " + fps;
            startTime = nowTime;
            frames = 0;
        }
        //pfs���ܵĻ�������
        
//        canvas.drawText(numFlakesString, getWidth() - 200, getHeight() - 50, textPaint);
//        canvas.drawText(fpsString, getWidth() - 200, getHeight() - 80, textPaint);
    }

    public void pause() {
        // Make sure the animator's not spinning in the background when the activity is paused.
        animator.cancel();
    }

    public void resume() {
        animator.start();
    }

}
