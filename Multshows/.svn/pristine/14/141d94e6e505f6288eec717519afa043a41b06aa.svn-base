package com.multshows.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.multshows.Beans.BabyAPI;
import com.multshows.Beans.UserBase;
import com.multshows.R;
import com.multshows.Utils.SubString_Utils;

import java.util.List;

/**
 * 描述：切换身份选择 popwindow
 * 作者：李萧萧
 * 时间：2016/12/8
 */

public class Change_popupwindow_adapter extends BaseAdapter {
	private Context context;
	private List<UserBase> mList;
	public Change_popupwindow_adapter(Context context, List<UserBase> list) {
		this.context = context;
		this.mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}
	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}
	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup viewGroup) {
		ViewHolder holder;
		if (convertView==null) {
			convertView=LayoutInflater.from(context).inflate(R.layout.change_popwindow, null);
			holder=new ViewHolder();
			convertView.setTag(holder);
			holder.baby_name=(TextView) convertView.findViewById(R.id.baby_name);
			holder.baby_pic= (SimpleDraweeView) convertView.findViewById(R.id.babyPic);
			//holder.LineChange= convertView.findViewById(R.id.LineChange);
		}
		else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.baby_name.setText(mList.get(position).getNickName());
		Uri mUri=Uri.parse(SubString_Utils.getImageUrl(mList.get(position).getPortrait()));
		holder.baby_pic.setImageURI(mUri);
	/*	if(position==mList.size()-1){
			holder.LineChange.setVisibility(View.INVISIBLE);
		}else {
			holder.LineChange.setVisibility(View.INVISIBLE);
		}*/
		return convertView;
	}

	class ViewHolder {
		TextView baby_name;
		SimpleDraweeView baby_pic;
	//	View LineChange;
	}

}
