package com.multshows.Beans;

import android.graphics.Bitmap;

import com.multshows.Utils.Photo_Bimp;

import java.io.IOException;
import java.io.Serializable;

/**
 * 图片信息bean对象
 */

public class Photo_ImageItem_Beans implements Serializable {
	public String imageId;
	public String thumbnailPath;
	public String imagePath;
	private Bitmap bitmap;
	boolean isVideo=false;//是否为视频
	public boolean isSelected = false;

	public boolean isVideo() {
		return isVideo;
	}

	public void setVideo(boolean video) {
		isVideo = video;
	}

	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public String getThumbnailPath() {
		return thumbnailPath;
	}
	public void setThumbnailPath(String thumbnailPath) {
		this.thumbnailPath = thumbnailPath;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public boolean isSelected() {
		return isSelected;
	}
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}
	public Bitmap getBitmap() {		
		if(bitmap == null){
			try {
				bitmap = Photo_Bimp.revitionImageSize(imagePath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	
	
	
}
