package com.multshows.Views.NineGridview.preview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.multshows.Utils.Json_Utils;
import com.multshows.Utils.Time_Now;
import com.multshows.Views.MyApplication;
import com.multshows.Views.NineGridview.ImageInfo;
import com.multshows.Views.NineGridview.NineGridView;
import com.multshows.Views.NineGridview.NineGridViewAdapter;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import okhttp3.Call;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class NineGridViewClickAdapter extends NineGridViewAdapter {

    private int statusHeight;
    String path;//视频播放地址
    public int IsVideo=2;//是否是视频  1 视频 2图片
    public String VideoName="";//视频名称
    String mUrl;//接收传过来的地址
    String  token;//七牛云token
    MyApplication mMyApplication;
    String timeInt=(Time_Now.getNowTime()+3600)+"";//过期时间戳
    public NineGridViewClickAdapter(Context context, List<ImageInfo> imageInfo) {
        super(context, imageInfo);
        mMyApplication= (MyApplication) context.getApplicationContext();
        statusHeight = getStatusHeight(context);
    }

    @Override
    protected void onImageItemClick(Context context, NineGridView nineGridView, int index, List<ImageInfo> imageInfo) {
        for (int i = 0; i < imageInfo.size(); i++) {
            if(i==0){
                if(imageInfo.get(i).getIsVideo()==1) {
                    mUrl=imageInfo.get(i).getVideoName();
                    GetQiniuDownToken();

                }else {
                    ImageInfo info = imageInfo.get(i);
                    View imageView;
                    if (i < nineGridView.getMaxSize()) {
                        imageView = nineGridView.getChildAt(i);
                    } else {
                        //如果图片的数量大于显示的数量，则超过部分的返回动画统一退回到最后一个图片的位置
                        imageView = nineGridView.getChildAt(nineGridView.getMaxSize() - 1);
                    }
                    info.imageViewWidth = imageView.getWidth();
                    info.imageViewHeight = imageView.getHeight();
                    int[] points = new int[2];
                    imageView.getLocationInWindow(points);
                    info.imageViewX = points[0];
                    info.imageViewY = points[1] - statusHeight;
                }
            }else {
                ImageInfo info = imageInfo.get(i);
                View imageView;
                if (i < nineGridView.getMaxSize()) {
                    imageView = nineGridView.getChildAt(i);
                } else {
                    //如果图片的数量大于显示的数量，则超过部分的返回动画统一退回到最后一个图片的位置
                    imageView = nineGridView.getChildAt(nineGridView.getMaxSize() - 1);
                }
                info.imageViewWidth = imageView.getWidth();
                info.imageViewHeight = imageView.getHeight();
                int[] points = new int[2];
                imageView.getLocationInWindow(points);
                info.imageViewX = points[0];
                info.imageViewY = points[1] - statusHeight;

            }

        }
        if(imageInfo.get(0).getImageViewHeight()!=1){
            Intent intent = new Intent(context, ImagePreviewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ImagePreviewActivity.IMAGE_INFO, (Serializable) imageInfo);
            bundle.putInt(ImagePreviewActivity.CURRENT_ITEM, index);
            intent.putExtras(bundle);
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(0, 0);
        }


    }

    /**
     * 获得状态栏的高度
     */
    public int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
    public void GetQiniuDownToken(){
        Log.e( "","http://ofqiajplg.bkt.clouddn.com/"+mUrl+"?e="+timeInt);
        OkHttpUtils.get().url(mMyApplication.getUrl() + "/service/GetQiniuDownToken")
                .addParams("rawUrl","http://ofqiajplg.bkt.clouddn.com/"+mUrl+"?e="+timeInt)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Log.e("token",""+e.getMessage() );
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        Log.e("testing", response);
                        JSONObject jsonObject = null;
                        try {
                            if (Json_Utils.getCode(response) == 0) {
                                JSONObject jsonArray = new JSONObject(response);
                                token=jsonArray.getString("token");

                                Log.e("token",""+token );
                                path="http://ofqiajplg.bkt.clouddn.com/"+mUrl+"?e="+timeInt+"&token="+token;
                                Log.e("path",""+path );
                                Uri uri = Uri.parse(path);
                                // 调用系统自带的播放器来播放流媒体视频
                                Intent intent = new Intent(Intent.ACTION_VIEW);
                                Log.v("URI:::::::::", uri.toString());
                                intent.setDataAndType(uri, "video/mp4");
                                context.startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

    }
}
