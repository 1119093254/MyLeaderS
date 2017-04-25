package com.multshows.Views.NineGridview.preview;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.R;
import com.multshows.Utils.SavePhoto_toLocal_Utils;
import com.multshows.Views.MyPicPopupWindow;
import com.multshows.Views.MyPicSavePopupWindow;
import com.multshows.Views.NineGridview.ImageInfo;
import com.multshows.Views.NineGridview.NineGridView;

import java.io.IOException;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * ================================================
 * 作    者：廖子尧
 * 版    本：1.0
 * 创建日期：2016/3/21
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ImagePreviewAdapter extends PagerAdapter implements PhotoViewAttacher.OnPhotoTapListener {
    PhotoView imageView;
    ImageView imageView2;
    View mView;
    private List<ImageInfo> imageInfo;
    private Context context;
    private View currentView;
    MyPicSavePopupWindow mPopWindow;
    public ImagePreviewAdapter(Context context, @NonNull List<ImageInfo> imageInfo) {
        super();
        this.imageInfo = imageInfo;
        this.context = context;
    }
    //为弹出窗口实现监听类
    public View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.PhotoSaveButtton:
                    //保存
                    //在调用getDrawingCache()方法从ImageView对象获取图像之前，一定要调用setDrawingCacheEnabled(true)方法
                    // 否则，无法从ImageView对象iv_photo中获取图像
                    imageView.setDrawingCacheEnabled(true);
                    //以清空画图缓冲区，否则，下一次从ImageView对象iv_photo中获取的图像，还是原来的图像
                    Bitmap bitmap = imageView.getDrawingCache();
                    try {
                        SavePhoto_toLocal_Utils.saveFile(bitmap, "multshows" + System.currentTimeMillis() + ".png",
                                "",context);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setDrawingCacheEnabled(false);
                    break;
                default:
                    break;
            }
            mPopWindow.dismiss();
        }
    };
    @Override
    public int getCount() {
        return imageInfo.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        currentView = (View) object;
    }

    public View getPrimaryItem() {
        return currentView;
    }

    public ImageView getPrimaryImageView() {
        return (ImageView) currentView.findViewById(R.id.pv);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        mView = LayoutInflater.from(context).inflate(R.layout.item_photoview, container, false);
        final ProgressBar pb = (ProgressBar) mView.findViewById(R.id.pb);
        imageView= (PhotoView) mView.findViewById(R.id.pv);
        imageView2= (ImageView) mView.findViewById(R.id.pv2);
        ImageInfo info = this.imageInfo.get(position);
        NineGridView.getImageLoader().onDisplayImage(mView.getContext(), imageView2, info.thumbnailUrl);
      //  imageView2.setImageURI(Uri.parse(info.thumbnailUrl));
        imageView.setOnPhotoTapListener(this);
        showExcessPic(info, imageView);
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //底部弹出框
                //实例化MyPicPopupWindow
                mPopWindow = new MyPicSavePopupWindow((Activity) context,
                        itemsOnClick);
                //设置弹出动画效果
                mPopWindow.setAnimationStyle(R.style.PopupAnimation);
                //显示窗口  //设置layout在PopupWindow中显示的位置
                mPopWindow.showAtLocation(mView.
                                findViewById(R.id.lookPhotoLayout),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                return true;
            }
        });
        //如果需要加载的loading,需要自己改写,不能使用这个方法
        NineGridView.getImageLoader().onDisplayImage(mView.getContext(), imageView, info.bigImageUrl);

        pb.setVisibility(View.VISIBLE);
//        Glide.with(context).load(info.bigImageUrl)//
//                .placeholder(R.drawable.ic_default_image)//
//                .error(R.drawable.ic_default_image)//
//                .diskCacheStrategy(DiskCacheStrategy.ALL)//
//                .listener(new RequestListener<String, GlideDrawable>() {
//                    @Override
//                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
//                        pb.setVisibility(View.GONE);
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
//                        pb.setVisibility(View.GONE);
//                        return false;
//                    }
//                }).into(imageView);

        container.addView(mView);
        return mView;
    }

    /** 展示过度图片 */
    private void showExcessPic(ImageInfo imageInfo, PhotoView imageView) {
        //先获取大图的缓存图片
        Bitmap cacheImage = NineGridView.getImageLoader().getCacheImage(imageInfo.bigImageUrl);
        //如果大图的缓存不存在,在获取小图的缓存
        if (cacheImage == null)
            cacheImage = NineGridView.getImageLoader().getCacheImage(imageInfo.thumbnailUrl);
        //如果没有任何缓存,使用默认图片,否者使用缓存
        if (cacheImage == null) {
            imageView.setImageResource(R.drawable.ic_default_color);
        } else {
            imageView.setImageBitmap(cacheImage);
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    /** 单击屏幕关闭 */
    @Override
    public void onPhotoTap(View view, float x, float y) {
        ((ImagePreviewActivity) context).finishActivityAnim();
    }
}