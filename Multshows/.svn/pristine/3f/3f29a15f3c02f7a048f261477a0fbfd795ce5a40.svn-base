package com.multshows.Activity;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.multshows.Adapter.Photo_Folder_Adapter;
import com.multshows.R;
import com.multshows.Utils.Photo_Bimp;
import com.multshows.Utils.Photo_PublicWay;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * 这个类主要是用来进行显示包含图片的文件夹
 *
 */
public class Photo_ImageFile_Activity extends Activity {
	//控件
	private Photo_Folder_Adapter folderAdapter;
	private TextView bt_close;
	private ImageView mReturn;
	private Context mContext;
	//进入类型操作
	int code;
	//相册id
	int Album_ID;
	//用户ID
	String userId;
	//判断是否免打扰
	String Message_Flag="";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_local_albums);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			setTranslucentStatus(true);
		}
		//第三方开源库（解决 沉浸式与输入法遮挡问题）
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		tintManager.setStatusBarTintEnabled(true);
		tintManager.setStatusBarTintResource(R.color.zhong);//通知栏所需颜色
		Intent intent=getIntent();
		code=intent.getIntExtra("Album_code",0);
		Album_ID=intent.getIntExtra("Album_ID",0);
		userId=intent.getStringExtra("userId");
		Message_Flag=intent.getStringExtra("Message");
		Photo_Bimp.mList_activity.clear();
		Photo_Bimp.mList_activity.add(this);
		Photo_PublicWay.activityList.clear();
		Photo_PublicWay.activityList.add(this);
		mContext = this;
		bt_close = (TextView) findViewById(R.id.home_LocalAlbums_close);
		mReturn= (ImageView) findViewById(R.id.home_LocalAlbums_return);
		bt_close.setOnClickListener(new CancelListener());
		mReturn.setOnClickListener(new CancelListener());
		mReturn.setVisibility(View.GONE);
		ListView mListView = (ListView) findViewById(R.id.home_LocalAlbums_listview);

		folderAdapter = new Photo_Folder_Adapter(this,Album_ID,code,userId,Message_Flag);
		mListView.setAdapter(folderAdapter);
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
	//返回/关闭 点击事件
	private class CancelListener implements OnClickListener {// 取消按钮的监听
		public void onClick(View v) {
			//清空选择的图片
		//	Photo_Bimp.tempSelectBitmap.clear();
			Photo_Bimp.Album_open=0;
			finish();
		}
	}
	//当用户点击一个按键时触发
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			//清空选择的图片
			//Photo_Bimp.tempSelectBitmap.clear();
			Photo_Bimp.Album_open=0;
			finish();
		}
		return true;
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//Photo_Bimp.tempSelectBitmap.clear();
	}

}
