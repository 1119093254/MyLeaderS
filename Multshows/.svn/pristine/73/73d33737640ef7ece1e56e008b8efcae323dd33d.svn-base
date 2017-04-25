package com.multshows.Activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.multshows.Beans.Photo_ImageItem_Beans;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Photo_Bimp;
import com.multshows.Utils.Photo_PublicWay;
import com.multshows.Views.PhotoView;
import com.multshows.Views.ViewPagerFixed;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 预览界面
 */
public class Photo_Preview_Activity extends AppCompatActivity {
     ArrayList<View> listViews = null;
     ViewPagerFixed pager;
     MyPageAdapter adapter;
    RelativeLayout home_localPreview_returnTop;
    //当前的位置
    private int location = 0;
    //头部信息
    TextView mTitle;
    //上传按钮
    LinearLayout mUpload;
    //上传数量
    TextView mNumber;
    //编辑布局
    RelativeLayout mEditorLayout;
    //编辑
    Button mEditor;
    //返回
    ImageView mReturn;
    Intent intent;
    int position=0;
    int type;//如果为1则表示是上传相册
    Uri imageUri;
    ArrayList<Photo_ImageItem_Beans> editorList = new ArrayList<>();////临时缓存裁剪图片集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_preview);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        home_localPreview_returnTop= (RelativeLayout) findViewById(R.id.home_localPreview_returnTop);

        /*//第三方开源库（解决 沉浸式与输入法遮挡问题）
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.app_topColor);//通知栏所需颜色*/

        mReturn= (ImageView) findViewById(R.id.home_localPreview_return);
        pager= (ViewPagerFixed) findViewById(R.id.home_localPreview_viewpage);
        mTitle= (TextView) findViewById(R.id.home_localPreview_albumsName);
        mUpload= (LinearLayout) findViewById(R.id.home_localPreview_upload);
        mNumber= (TextView) findViewById(R.id.home_localPreview_uploadNumber);
        mEditorLayout= (RelativeLayout) findViewById(R.id.preview_album_editorLayout);
        mEditor= (Button) findViewById(R.id.preview_album_editorBtn);
        //赋值记录
        editorList.addAll(Photo_Bimp.tempSelectBitmap);

        initData();
        //返回按钮点击事件
        mReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //上传事件监听
        mUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
               // Photo_Bimp.uploadSelectBitmap = Photo_Bimp.tempSelectBitmap;
                Photo_Bimp.albumSelectBitmap.clear();
                Photo_Bimp.albumSelectBitmap = Photo_Bimp.tempSelectBitmap;
                if(type==0) {
                    //查看所有图片的预览
                    CLog.e("testing","进来这里1");
                    if (Photo_Bimp.mPreview_activity != null) {
                        if(Photo_Bimp.mPreview_activity.size()!=0) {
                            CLog.e("testing", "进来这里2");
                            Photo_Bimp.mPreview_activity.get(0).finish();
                            Photo_Bimp.mPreview_activity.clear();
                        }
                    //    Photo_Bimp.mPreview_activity=null;
                    }
                    //查看单个相册图片的预览
                    if (Photo_PublicWay.activityList != null) {
                        if (Photo_PublicWay.activityList.size() != 0) {
                            for (int i = 0; i < Photo_PublicWay.activityList.size(); i++) {
                                Photo_PublicWay.activityList.get(i).finish();
                            }
                            //Photo_Bimp.mList_activity.clear();
                            Photo_PublicWay.activityList=null;
                        }
                    }
                }else {
                    //查看所有图片的预览
                    if (Photo_Bimp.mPreview_activity != null) {
                        //Photo_Bimp.mPreview_activity.get(0).finish();
                        Photo_Bimp.mPreview_activity.clear();
                    }
                    //查看单个相册图片的预览
                    if (Photo_Bimp.mList_activity.size() != 0) {
                        //for (int i = 0; i < Photo_Bimp.mList_activity.size(); i++) {
                            Photo_Bimp.mList_activity.get(0).finish();
                        //}
                        Photo_Bimp.mList_activity.clear();
                    }
                }
                finish();
            }
        });

        //编辑功能
        mEditor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CLog.e("testing","点击了编辑：路径"+Uri.parse(Photo_Bimp.tempSelectBitmap.get(location).getImagePath()));
                selectImageAndCrop();
            }
        });

        pager.setOnPageChangeListener(pageChangeListener);
        for (int i = 0; i < Photo_Bimp.tempSelectBitmap.size(); i++) {
            initListViews( Photo_Bimp.tempSelectBitmap.get(i).getBitmap());
        }
        adapter = new MyPageAdapter(listViews);
        pager.setAdapter(adapter);
        int id = intent.getIntExtra("ID", 0);
        pager.setCurrentItem(id);
        //赋值数据
        mTitle.setText((location+1)+"/"+Photo_Bimp.tempSelectBitmap.size());
        //更新数据
        mNumber.setText(Photo_Bimp.tempSelectBitmap.size()+"");
        adapter.notifyDataSetChanged();
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
    /**
     * 数据处理
     */
    private void initData() {
        //获取传过来的数据
        intent=getIntent();
        type=intent.getIntExtra("type",0);
        position=intent.getIntExtra("photoPosition",0);
     /*   if(position==1){
            home_localPreview_returnTop.setVisibility(View.GONE);
        }*/
    }
    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {

        public void onPageSelected(int arg0) {
            location = arg0;
            mTitle.setText((location+1)+"/"+Photo_Bimp.tempSelectBitmap.size());
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        public void onPageScrollStateChanged(int arg0) {

        }
    };
    //显示图片
    private void initListViews(Bitmap bm) {
        if (listViews == null)
            listViews = new ArrayList<View>();
        PhotoView img = new PhotoView(this);
        img.setBackgroundColor(0xff000000);
        img.setImageBitmap(bm);
        img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        listViews.add(img);
    }
    /**
     * 功能:调用手机裁剪功能界面
     *
     * 作者: 贾强胜
     * 时间: 2017/2/13 13:31
     */
    public void selectImageAndCrop() {
        //After cropping, the image file will be stored here!
       // String subForder=getExternalCacheDir().getPath();
        File outputImage = new File(getExternalCacheDir().getPath(), "cache_" + System.currentTimeMillis() + ".jpg");
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageUri = Uri.fromFile(outputImage);
        //cacheFile = subForder  + "cache_" + System.currentTimeMillis() + ".jpg";
        CLog.e("testing","裁剪前"+location);
        String uri=Photo_Bimp.tempSelectBitmap.get(location).getImagePath();

        Intent intent = new Intent();
        /*设置调用裁剪工具的action*/
        intent.setAction("com.android.camera.action.CROP");
        /*创建一个指向需要操作文件（filename）的文件流。（可解决无法“加载问题”）*/
        intent.setDataAndType(Uri.fromFile(new File(uri)), "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);// 裁剪框比例
        intent.putExtra("aspectY", 1);
        /*intent.putExtra("outputX", 300);// 输出图片大小
        intent.putExtra("outputY", 300);*/
        intent.putExtra("return-data", true);
        intent.putExtra("output", imageUri);
        intent.putExtra("outputFormat", "JPEG");
        startActivityForResult(intent, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        //select an image and crop
        if (requestCode == 2) {

            try {
                //必须先清空pager里的视图
                pager.removeAllViews();
                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));

                String newStr =String.valueOf(imageUri).replace("file://", "");// 替换file://
                Photo_Bimp.tempSelectBitmap.get(location).setImagePath(newStr);
                Photo_Bimp.tempSelectBitmap.get(location).setBitmap(bitmap);
                CLog.e("testing","剪切存的路径："+newStr);
                PhotoView img = new PhotoView(this);
                img.setBackgroundColor(0xff000000);
                img.setImageBitmap(bitmap);
                img.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                CLog.e("testing","裁剪后"+location);
                listViews.set(location,img);
                adapter.setListViews(listViews);
                pager.setCurrentItem(location);
                //赋值数据
                mTitle.setText((location+1)+"/"+Photo_Bimp.tempSelectBitmap.size());


                adapter.notifyDataSetChanged();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
    }









    class MyPageAdapter extends PagerAdapter {

        private ArrayList<View> listViews;

        private int size;
        public MyPageAdapter(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public void setListViews(ArrayList<View> listViews) {
            this.listViews = listViews;
            size = listViews == null ? 0 : listViews.size();
        }

        public int getCount() {
            return size;
        }

        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        public void destroyItem(View arg0, int arg1, Object arg2) {
            ((ViewPagerFixed) arg0).removeView(listViews.get(arg1 % size));
        }

        public void finishUpdate(View arg0) {
        }

        public Object instantiateItem(View arg0, int arg1) {
            try {
                ((ViewPagerFixed) arg0).addView(listViews.get(arg1 % size), 0);

            } catch (Exception e) {
            }
            return listViews.get(arg1 % size);
        }

        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //按键返回
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(Photo_Bimp.mPreview_activity!=null)
            Photo_Bimp.mPreview_activity.clear();
            //放弃编辑的图
            finish();
        }
        return true;
    }

}
