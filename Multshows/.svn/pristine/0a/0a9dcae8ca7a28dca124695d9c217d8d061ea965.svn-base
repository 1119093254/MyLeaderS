package com.multshows.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.MySystemBarTintManage_Utils;
import com.multshows.Utils.Photo_Bimp;
import com.multshows.Utils.SavePicture_toLocal_Utils;
import com.multshows.Views.HintText_Dialog;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;

import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.ImageMessage;
import me.relex.photodraweeview.PhotoDraweeView;

/**
 * 拍照图片预览
 */
public class Chat_PhotoPreView_Activity extends AppCompatActivity {
    PhotoDraweeView mImageView; //预览
    Button mSend;//发送
    ImageView mReturn;//返回

    Intent mIntent;//意图
    String path;//传来的图片路径
    Uri uri;
    String otherID;//对方id
    String Message_Flag="";
    int IdType=0;
    Dialog mDialog;
    PutImageUrl mPutImageUrl=new PutImageUrl() {
        @Override
        public void setImageUrl(String url) {

        }
    };
    public interface PutImageUrl{
        void setImageUrl(String url);
    }
    public void putMessage(PutImageUrl putImageUrl){
        mPutImageUrl=putImageUrl;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_photo_preview);
        //沉浸式
        MySystemBarTintManage_Utils manageUtils = new MySystemBarTintManage_Utils();
        manageUtils.setSystemBarTintManage(Chat_PhotoPreView_Activity.this, R.color.app_topColor);
        if(Photo_Bimp.tempSelectBitmap!=null)
        Photo_Bimp.tempSelectBitmap.clear();
        initView();
        initData();
        initListen();
    }

    /**
     * 初始化
     */
    private void initView() {
        mImageView= (PhotoDraweeView) findViewById(R.id.chat_PhotoPreView_look_image);
        mSend= (Button) findViewById(R.id.chat_PhotoPreView_send);
        mReturn= (ImageView) findViewById(R.id.chat_PhotoPreView_return);
        //屏幕宽高
        WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
        int windowWidth = wm.getDefaultDisplay().getWidth();
        int windowHeight = wm.getDefaultDisplay().getHeight();
        mImageView.setMaxWidth(windowWidth);
        mImageView.setMaxHeight(windowWidth * 3);
    }

    /**
     * 数据处理
     */
    private void initData() {
        mIntent=getIntent();
        path=mIntent.getStringExtra("url_Path");
        otherID=mIntent.getStringExtra("otherID");
        IdType=mIntent.getIntExtra("IDType",0);
        Message_Flag=mIntent.getStringExtra("Message");
        if(Photo_Bimp.chatSelectBitmap.size()!=0){
            otherID= Login_Static.ChatId;
            //Bitmap bitmap=convertToBitmap(Photo_Bimp.chatSelectBitmap.get(0).getImagePath(),600,600);
            Bitmap bitmap=Photo_Bimp.chatSelectBitmap.get(0).getBitmap();
            CLog.e("testingssss","Bitmap: "+bitmap.getRowBytes() * bitmap.getHeight());
            String name = "Ms_"+System.currentTimeMillis()+".png";
            try {
                SavePicture_toLocal_Utils.saveCacheFile(bitmap,name,Chat_PhotoPreView_Activity.this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            path=getExternalCacheDir().getPath()+"/"+name;
        }

        uri = Uri.parse("file://"+path);//Fresco框架 固定加载本地图片格式  file:///...
    }
    /**
     * 根据路径加载bitmap
     *
     * @param path
     *            路径
     * @param w
     *            款
     * @param h
     *            长
     * @return
     */
    public static final Bitmap convertToBitmap(String path, int w, int h) {
        try {
            BitmapFactory.Options opts = new BitmapFactory.Options();
            // 设置为ture只获取图片大小
            opts.inJustDecodeBounds = true;
            opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
            // 返回为空
            BitmapFactory.decodeFile(path, opts);
            int width = opts.outWidth;
            int height = opts.outHeight;
            float scaleWidth = 0.f, scaleHeight = 0.f;
            if (width > w || height > h) {
                // 缩放
                scaleWidth = ((float) width) / w;
                scaleHeight = ((float) height) / h;
            }
            opts.inJustDecodeBounds = false;
            float scale = Math.max(scaleWidth, scaleHeight);
            opts.inSampleSize = (int) scale;
            WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
            Bitmap bMapRotate = Bitmap.createBitmap(weak.get(), 0, 0, weak.get().getWidth(), weak.get().getHeight(), null, true);
            if (bMapRotate != null) {
                return bMapRotate;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 事件监听
     */
    private void initListen() {
        //返回
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /**
         * 实现图片缩放，第三方框架PhotoDraweeView
         * 同时结合 github上的PhotoView框架和Facebook上的Fresco框架
         */
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(uri);
        controller.setOldController(mImageView.getController());
        controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || mImageView == null) {
                    return;
                }
                mImageView.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        mImageView.setController(controller.build());

        //发送图片
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(path!=null && !"".equals(path)) {
                    mDialog = new HintText_Dialog(Chat_PhotoPreView_Activity.this, R.style.MyDialog);
                    mDialog.show();
                    HintText_Dialog mTextDialog = new HintText_Dialog(Chat_PhotoPreView_Activity.this, "发送中...", "");
                    //发送图片消息
                    File imageFileSource = new File(getCacheDir(), "source.png");
                    File imageFileThumb = new File(getCacheDir(), "thumb.png");

                    try {
                        // 读取图片。
                        File foder = new File(path);
                        InputStream is = new FileInputStream(foder);

                        Bitmap bmpSource = BitmapFactory.decodeStream(is);

                        imageFileSource.createNewFile();

                        FileOutputStream fosSource = new FileOutputStream(imageFileSource);

                        // 保存原图。
                        bmpSource.compress(Bitmap.CompressFormat.PNG, 90, fosSource);

                        // 创建缩略图变换矩阵。
                        Matrix m = new Matrix();
                        m.setRectToRect(new RectF(0, 0, bmpSource.getWidth(), bmpSource.getHeight()), new RectF(0, 0, 160, 160), Matrix.ScaleToFit.CENTER);

                        // 生成缩略图。
                        Bitmap bmpThumb = Bitmap.createBitmap(bmpSource, 0, 0, bmpSource.getWidth(), bmpSource.getHeight(), m, true);

                        imageFileThumb.createNewFile();

                        FileOutputStream fosThumb = new FileOutputStream(imageFileThumb);

                        // 保存缩略图。
                        bmpThumb.compress(Bitmap.CompressFormat.PNG, 60, fosThumb);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ImageMessage imgMsg = ImageMessage.obtain(Uri.fromFile(imageFileThumb), Uri.fromFile(imageFileSource));
                    imgMsg.setExtra(Message_Flag);

/**
 * 发送图片消息。
 *
 * @param conversationType         会话类型。
 * @param targetId                 会话目标 Id。根据不同的 conversationType，可能是用户 Id、讨论组 Id、群组 Id 或聊天室 Id。
 * @param imgMsg                   消息内容。
 * @param pushContent              接收方离线时需要显示的push消息内容。
 * @param pushData                 接收方离线时需要在push消息中携带的非显示内容。
 * @param SendImageMessageCallback 发送消息的回调。
 */
                    if (IdType == 2) {
                                RongIMClient.getInstance().sendImageMessage(Conversation.ConversationType.DISCUSSION,
                                otherID, imgMsg, null, null, new RongIMClient.SendImageMessageCallback() {

                                    @Override
                                    public void onAttached(Message message) {
                                        //保存数据库成功
                                        CLog.e("send_image", "保存数据库成功");
                                    }

                                    @Override
                                    public void onError(Message message, RongIMClient.ErrorCode code) {
                                        //发送失败
                                        CLog.e("send_image", "发送失败");
                                        HintText_Dialog mTextDialog = new HintText_Dialog(Chat_PhotoPreView_Activity.this, "发送失败", "fail");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                            }
                                        }, Login_Static.hintTime);
                                    }

                                    @Override
                                    public void onSuccess(Message message) {
                                        //获得json字符串
                                        String mes = "";
                                        try {
                                            String json = new String(message.getContent().encode(), "UTF-8");
                                            JSONObject jsonObject = new JSONObject(json);
                                            mes = jsonObject.getString("imageUri");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        //发送成功
                                        HintText_Dialog mTextDialog = new HintText_Dialog(Chat_PhotoPreView_Activity.this, "发送成功", "success");
                                        final String finalMes = mes;
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                                CLog.e("send_image", "发送成功");
                                                EventBus.getDefault().post(new ChatEvent_Model(finalMes, "Group"));
                                                finish();
                                            }
                                        }, Login_Static.hintTime);

                                    }

                                    @Override
                                    public void onProgress(Message message, int progress) {
                                        //发送进度
                                    }
                                });
                    } else {
                        RongIMClient.getInstance().sendImageMessage(Conversation.ConversationType.PRIVATE,
                                otherID, imgMsg, null, null, new RongIMClient.SendImageMessageCallback() {

                                    @Override
                                    public void onAttached(Message message) {
                                        //保存数据库成功
                                        CLog.e("send_image", "保存数据库成功");
                                    }

                                    @Override
                                    public void onError(Message message, RongIMClient.ErrorCode code) {
                                        //发送失败
                                        CLog.e("send_image", "发送失败");
                                        HintText_Dialog mTextDialog = new HintText_Dialog(Chat_PhotoPreView_Activity.this, "发送失败", "fail");
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                            }
                                        }, Login_Static.hintTime);
                                    }

                                    @Override
                                    public void onSuccess(Message message) {
                                        //获得json字符串
                                        String mes = "";
                                        try {
                                            String json = new String(message.getContent().encode(), "UTF-8");
                                            JSONObject jsonObject = new JSONObject(json);
                                            mes = jsonObject.getString("imageUri");
                                        } catch (UnsupportedEncodingException e) {
                                            e.printStackTrace();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        //发送成功
                                        HintText_Dialog mTextDialog = new HintText_Dialog(Chat_PhotoPreView_Activity.this, "发送成功", "success");
                                        final String finalMes = mes;
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                mDialog.dismiss();
                                                CLog.e("send_image", "发送成功");
                                                EventBus.getDefault().post(new ChatEvent_Model(finalMes, "Chat"));
                                                finish();
                                            }
                                        }, Login_Static.hintTime);

                                    }

                                    @Override
                                    public void onProgress(Message message, int progress) {
                                        //发送进度
                                    }
                                });

                    }
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Photo_Bimp.chatSelectBitmap.clear();
    }
}
