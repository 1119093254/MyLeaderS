package com.multshows.Views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.multshows.Adapter.AbstractWheelTextAdapter;
import com.multshows.Interfaces.OnWheelChangedListener;
import com.multshows.Interfaces.OnWheelScrollListener;
import com.multshows.R;

import java.util.ArrayList;


/**
 * 更改封面对话框
 * 
 * @author ywl
 *
 */
public class ApplicationRestrictionsDialog extends Dialog implements View.OnClickListener {

	private WheelView wvProvince;

	private View lyChangeApplication;

	private Button btnSure;

	private Context context;
	private String[] mApplicationRestrictionDatas;


	private ArrayList<String> arrApplicationRestriction = new ArrayList<String>();

	private ApplicationTextAdapter ApplicationRestrictionAdapter;
	String[] mString={""};

	private String strApplication = "有限制3";
	private OnAddressCListener onApplicationCListener;

	public String[] getString() {
		return mString;
	}

	public void setString(String[] string) {
		mString = string;
	}
	public void setString(Number[] numbers) {
		String[] string = new String[numbers.length];
		for (int i=0;i<numbers.length;i++){
			string[i]=numbers[i].toString()+"%";
		}
		mString = string;
	}

	private int maxsize = 24;
	private int minsize = 14;

	public ApplicationRestrictionsDialog(Context context) {
		super(context, R.style.ShareDialog);
		this.context = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_applicationrestrictions);
		wvProvince = (WheelView) findViewById(R.id.Application_Restrictions);
		lyChangeApplication = findViewById(R.id.a_myinfo_changeApplication);
		btnSure = (Button) findViewById(R.id.btn_Application_sure);
		lyChangeApplication.setOnClickListener(this);
		btnSure.setOnClickListener(this);

		initDatas();
		initProvinces();
		ApplicationRestrictionAdapter = new ApplicationTextAdapter(context, arrApplicationRestriction, getProvinceItem(strApplication), maxsize, minsize);
		wvProvince.setVisibleItems(5);
		wvProvince.setViewAdapter(ApplicationRestrictionAdapter);
		wvProvince.setCurrentItem(getProvinceItem(strApplication));



		wvProvince.addChangingListener(new OnWheelChangedListener() {

			@Override
			public void onChanged(WheelView wheel, int oldValue, int newValue) {
				// TODO Auto-generated method stub
				String currentText = (String) ApplicationRestrictionAdapter.getItemText(wheel.getCurrentItem());
				strApplication = currentText;
				setTextviewSize(currentText, ApplicationRestrictionAdapter);

			}
		});

		wvProvince.addScrollingListener(new OnWheelScrollListener() {

			@Override
			public void onScrollingStarted(WheelView wheel) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onScrollingFinished(WheelView wheel) {
				// TODO Auto-generated method stub
				String currentText = (String) ApplicationRestrictionAdapter.getItemText(wheel.getCurrentItem());
				setTextviewSize(currentText, ApplicationRestrictionAdapter);
			}
		});




	}

	private class ApplicationTextAdapter extends AbstractWheelTextAdapter {
		ArrayList<String> list;

		protected ApplicationTextAdapter(Context context, ArrayList<String> list, int currentItem, int maxsize, int minsize) {
			super(context, R.layout.item_closingdate_year, NO_RESOURCE, currentItem, maxsize, minsize);
			this.list = list;
			setItemTextResource(R.id.tempValue);
		}

		@Override
		public View getItem(int index, View cachedView, ViewGroup parent) {
			View view = super.getItem(index, cachedView, parent);
			return view;
		}

		@Override
		public int getItemsCount() {
			return list.size();
		}

		@Override
		protected CharSequence getItemText(int index) {
			return list.get(index) + "";
		}
	}

	/**
	 * 设置字体大小
	 * 
	 * @param curriteItemText
	 * @param adapter
	 */
	public void setTextviewSize(String curriteItemText, ApplicationTextAdapter adapter) {
		ArrayList<View> arrayList = adapter.getTestViews();
		int size = arrayList.size();
		String currentText;
		for (int i = 0; i < size; i++) {
			TextView textvew = (TextView) arrayList.get(i);
			currentText = textvew.getText().toString();
			if (curriteItemText.equals(currentText)) {
				textvew.setTextSize(24);
			} else {
				textvew.setTextSize(14);
			}
		}
	}
	public void setApplicationListener(OnAddressCListener onApplicationCListener) {
		this.onApplicationCListener = onApplicationCListener;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == btnSure) {
			if (onApplicationCListener != null) {
				onApplicationCListener.onClick(strApplication);
			}
		}   else {
			dismiss();
		}
		dismiss();
	}

	/**
	 * 回调接口
	 * 
	 * @author Administrator
	 *
	 */
	public interface OnAddressCListener {
		void onClick(String province);
	}


	/**
	 * 解析数据
	 */
	private void initDatas() {

		mApplicationRestrictionDatas =new String[mString.length];
			for (int i = 0; i < mString.length; i++) {

				String province = mString[i];

				mApplicationRestrictionDatas[i] = province;
			}

	}
	/**
	 * 初始化申请限制
	 */
	public void initProvinces() {
		int length = mString.length;
		for (int i = 0; i < length; i++) {
			arrApplicationRestriction.add(mApplicationRestrictionDatas[i]);
		}
	}




	/**
	 * 初始化限制
	 * 
	 *
	 *
	 */
	public void setApplication(String strApplication) {
		if (strApplication != null && strApplication.length() > 0) {
			this.strApplication = strApplication;
		}

	}

	/**
	 * 返回限制索引索引，没有就返回默认“限制3”
	 * 
	 * @param province
	 * @return
	 */
	public int getProvinceItem(String province) {
		int size = arrApplicationRestriction.size();
		int provinceIndex = 0;
		boolean noApplication = true;
		for (int i = 0; i < size; i++) {
			if (province.equals(arrApplicationRestriction.get(i))) {
				noApplication = false;
				return provinceIndex;
			} else {
				provinceIndex++;
			}
		}
		if (noApplication) {
			strApplication = "限制";
			return 22;
		}
		return provinceIndex;
	}


}