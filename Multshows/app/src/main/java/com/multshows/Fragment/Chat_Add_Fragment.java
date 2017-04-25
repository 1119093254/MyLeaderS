package com.multshows.Fragment;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.multshows.Activity.Chat_PhotoPreView_Activity;
import com.multshows.Activity.Photo_Album_Activity;
import com.multshows.Beans.ChatContext_Beans;
import com.multshows.Beans.ChatEvent_Model;
import com.multshows.Login_Static;
import com.multshows.R;
import com.multshows.Utils.Photo_Bimp;
import com.multshows.Utils.SavePicture_toLocal_Utils;
import com.multshows.Utils.Time_Now;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 描述：聊天特殊功能展示界面fragment
 * 作者：贾强胜
 * 时间：2016.9.18
 */
public class Chat_Add_Fragment extends Fragment{
    public static final int REQUEST_CODE = 1;
    public static final int ALUMB_CODE = 10;
    View mView;
    //控件
    ImageView mTakePhoto;
    ImageView mLocalPhoto;
    //上下文
    Context mContext;
    Uri photoUri;
    String otherID;
    int IdType=0;
    String Message_Flag="";
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.chat_add_view,null);
        Bundle bundle=getArguments();
        if(bundle!=null) {
            otherID = bundle.getString("otherID");
            IdType=bundle.getInt("IDType",0);
            Message_Flag=bundle.getString("Message");
        }
        initView();
        initListen();
        return mView;
    }

    /**
     * 初始化
     */
    private void initView() {
        mTakePhoto= (ImageView) mView.findViewById(R.id.chat_Add_View_tackPhoto);
        mLocalPhoto= (ImageView) mView.findViewById(R.id.chat_Add_View_localPhoto);
    }

    /**
     * 事件处理
     */
    private void initListen() {
        //拍照
        mTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                SimpleDateFormat timeStampFormat = new SimpleDateFormat(
                        "yyyy_MM_dd_HH_mm_ss");
                String filename = timeStampFormat.format(new Date());
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, filename);

                photoUri = mContext.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        //打开本地相册
        mLocalPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login_Static.ChatId=otherID;
                Intent intent=new Intent(mContext, Photo_Album_Activity.class);
                intent.putExtra("album_type","chatPhoto");
                intent.putExtra("IDType", IdType);
                intent.putExtra("Message", Message_Flag);
                getActivity().startActivityForResult(intent, ALUMB_CODE);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Photo_Bimp.chatSelectBitmap.clear();
        Photo_Bimp.tempSelectBitmap.clear();
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REQUEST_CODE:
                    //照相返回结果
                    //  Bitmap bm = (Bitmap) data.getExtras().get("data");
                    //这里开始的第二部分，获取图片的存储路径：
                    String[] proj = {MediaStore.Images.Media.DATA};
                    //好像是android多媒体数据库的封装接口，具体的看Android文档
                    Cursor cursor = getActivity().managedQuery(photoUri, proj, null, null, null);
                    //按我个人理解 这个是获得用户选择的图片的索引值
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    //将光标移至开头 ，这个很重要，不小心很容易引起越界
                    cursor.moveToFirst();
                    //最后根据索引值获取图片路径
                    String path = cursor.getString(column_index);
                        if(resultCode==-1) {

                            Bitmap bitmap=convertToBitmap(path,600,600);
                            String name = "Ms_"+System.currentTimeMillis()+".png";
                            try {
                                SavePicture_toLocal_Utils.saveCacheFile(bitmap,name,mContext);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            String subForder=mContext.getExternalCacheDir().getPath()+"/"+name;
                            Intent intent = new Intent(mContext, Chat_PhotoPreView_Activity.class);
                            intent.putExtra("url_Path", subForder);
                            intent.putExtra("otherID", otherID);
                            intent.putExtra("IDType", IdType);
                            intent.putExtra("Message", Message_Flag);
                            startActivity(intent);
                        }
        }
    }




}
