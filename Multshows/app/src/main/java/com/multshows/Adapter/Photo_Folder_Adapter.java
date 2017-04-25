package com.multshows.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.multshows.Activity.Photo_Album_Activity;
import com.multshows.Activity.Photo_ShowAllPhoto_Activity;
import com.multshows.Beans.Photo_ImageItem_Beans;
import com.multshows.R;
import com.multshows.Utils.CLog;
import com.multshows.Utils.Photo_BitmapCache;
import com.multshows.Utils.Photo_Res_Utils;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 这个是显示所有包含图片的文件夹的适配器
 */
public class Photo_Folder_Adapter extends BaseAdapter {

	private Context mContext;
	private Intent mIntent;
	private DisplayMetrics dm;
	Photo_BitmapCache cache;
	private ArrayList<Photo_ImageItem_Beans> dataList_=new ArrayList<>();
	//相册id，发说说时为0
	int Album_ID;
	//操作类型，11时为上传相册图片，其他为发表说说操作
	int code;
	String userId="";
	String Message_Flag="";

	final String TAG = getClass().getSimpleName();
	public Photo_Folder_Adapter(Context c, int Album_ID, int code,String id,String Message_Flag) {
		cache = new Photo_BitmapCache();
		this.Album_ID=Album_ID;
		this.code=code;
		userId=id;
		this.Message_Flag=Message_Flag;
		init(c);
	}

	// 初始化
	public void init(Context c) {
		mContext = c;
		mIntent = ((Activity) mContext).getIntent();
		dm = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
	}

	

	@Override
	public int getCount() {
		return Photo_Album_Activity.contentList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	Photo_BitmapCache.ImageCallback callback = new Photo_BitmapCache.ImageCallback() {
		@Override
		public void imageLoad(ImageView imageView, Bitmap bitmap,
				Object... params) {
			if (imageView != null && bitmap != null) {
				String url = (String) params[0];
				if (url != null && url.equals(imageView.getTag())) {
					imageView.setImageBitmap(bitmap);
				} else {
					CLog.e(TAG, "callback, bmp not match");
				}
			} else {
				CLog.e(TAG, "callback, bmp null");
			}
		}
	};

	private class ViewHolder {
		//
		public RelativeLayout mLayout;
		// 封面
		public ImageView imageView;
		public ImageView choose_back;
		// 文件夹名称
		public TextView folderName;
		// 文件夹里面的图片数量
		public TextView fileNum;
	}
	ViewHolder holder = null;
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.home_myphoto_albums_item, null);
			holder = new ViewHolder();
			holder.imageView= (ImageView) convertView.findViewById(R.id.home_myHomePage_myPhotoAlbums_item_image);
			holder.folderName= (TextView) convertView.findViewById(R.id.home_myHomePage_myPhotoAlbums_item_AlbumsName);
			holder.fileNum= (TextView) convertView.findViewById(R.id.home_myHomePage_myPhotoAlbums_item_photoNumber);
			holder.mLayout= (RelativeLayout) convertView.findViewById(R.id.home_myHomePage_myPhotoAlbums_item_layout);
			holder.imageView.setAdjustViewBounds(true);
			holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		String path;
		if (Photo_Album_Activity.contentList.get(position).imageList != null) {
			//path = photoAbsolutePathList.get(position);
			//封面图片路径
			path = Photo_Album_Activity.contentList.get(position).imageList.get(0).imagePath;
			// 给folderName设置值为文件夹名称
			//holder.folderName.setText(fileNameList.get(position));
			holder.folderName.setText(Photo_Album_Activity.contentList.get(position).bucketName);
			
			// 给fileNum设置文件夹内图片数量
			//holder.fileNum.setText("" + fileNum.get(position));
			holder.fileNum.setText("" + Photo_Album_Activity.contentList.get(position).count);
			
		} else
			path = "android_hybrid_camera_default";
		if(path==null){}else {
			if (path.contains("android_hybrid_camera_default"))
				holder.imageView.setImageResource(Photo_Res_Utils.getDrawableID("plugin_camera_no_pictures"));
			else {
//			holder.imageView.setImageBitmap( AlbumActivity.contentList.get(position).imageList.get(0).getBitmap());
				final Photo_ImageItem_Beans item = Photo_Album_Activity.contentList.get(position).imageList.get(0);
				holder.imageView.setTag(item.imagePath);
				cache.displayBmp(holder.imageView, item.thumbnailPath, item.imagePath,
						callback);
			}
		}
		// 为封面添加监听
		holder.mLayout.setOnClickListener(new ImageViewClickListener(
				position, mIntent));
		return convertView;
	}

	// 为每一个文件夹构建的监听器
	private class ImageViewClickListener implements OnClickListener {
		private int position;
		private Intent intent;
		private ImageView choose_back;
		public ImageViewClickListener(int position, Intent intent) {
			this.position = position;
			this.intent = intent;

		}
		
		public void onClick(View v) {
			dataList_.clear();
			dataList_.addAll(Photo_Album_Activity.contentList.get(position).imageList);
			Collections.reverse(dataList_);
			Photo_ShowAllPhoto_Activity.dataList = dataList_;
			Intent intent = new Intent();
			String folderName = Photo_Album_Activity.contentList.get(position).bucketName;
			intent.putExtra("folderName", folderName);
			intent.putExtra("Album_ID", Album_ID);
			intent.putExtra("Album_code", code);
			intent.putExtra("userId", userId);
			intent.putExtra("Message", Message_Flag);
			intent.setClass(mContext, Photo_ShowAllPhoto_Activity.class);
			mContext.startActivity(intent);
			//choose_back.setVisibility(v.VISIBLE);
		}
	}

	public int dipToPx(int dip) {
		return (int) (dip * dm.density + 0.5f);
	}

}
