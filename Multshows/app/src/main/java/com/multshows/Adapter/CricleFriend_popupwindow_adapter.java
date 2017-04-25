package com.multshows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.multshows.R;

import java.util.List;

/**
 * 描述：朋友圈顶部弹出框popupwindow适配器
 * 作者：李萧萧
 * 时间：2016/7/20
 */

public class CricleFriend_popupwindow_adapter extends BaseAdapter {
	private Context context;
	private List<String> list;
	int mPosition;
	public CricleFriend_popupwindow_adapter(Context context, List<String> list, int mPosition) {
		this.context = context;
		this.list = list;
		this.mPosition=mPosition;
	}

	@Override
	public int getCount() {
		return list.size();
	}
	@Override
	public Object getItem(int position) {
		return list.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		ViewHolder holder;
		if (convertView==null) {
			convertView=LayoutInflater.from(context).inflate(R.layout.circlefriend_popupwindow_item, null);
			holder=new ViewHolder();
			convertView.setTag(holder);
			holder.groupItem=(TextView) convertView.findViewById(R.id.groupItem);
			//holder.LineChange=(View) convertView.findViewById(R.id.LineChange);
			holder.background=(TextView) convertView.findViewById(R.id.itembackground);
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
	/*	if(position==list.size()-1){
			holder.LineChange.setVisibility(View.INVISIBLE);
		}else holder.LineChange.setVisibility(View.VISIBLE);
		holder.groupItem.setText(list.get(position));*/
		holder.groupItem.setText(list.get(position));
		if(mPosition==position){
			holder.background.setBackgroundResource(R.drawable.btn_border_5);
		}else {
			holder.background.setBackgroundResource(0);
		}
		return convertView;
	}

	class ViewHolder {
		TextView groupItem;
	//	View LineChange;
		TextView background;
	}

}
