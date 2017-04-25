package com.multshows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.multshows.Beans.MyFragmentItem_Beans;
import com.multshows.R;

import java.util.List;

/**
 * 我的
 */
public class MyFragmentItem_Adapter extends BaseAdapter {
    Context mContext;
    LayoutInflater mInflater;
    List<MyFragmentItem_Beans> mList;

    public MyFragmentItem_Adapter(Context context, List<MyFragmentItem_Beans> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(mContext);
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

    class FragmentViewHolder {
        ImageView image;
        TextView text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FragmentViewHolder mViewHolder;
        if (convertView == null) {
            convertView=mInflater.inflate(R.layout.my_fragment_gridview_item,null);
            mViewHolder=new FragmentViewHolder();
            mViewHolder.image= (ImageView) convertView.findViewById(R.id.myFragment_item_image);
            mViewHolder.text= (TextView) convertView.findViewById(R.id.myFragment_item_text);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder= (FragmentViewHolder) convertView.getTag();
        }
        MyFragmentItem_Beans beans=mList.get(position);
        mViewHolder.image.setImageResource(beans.getImage());
        mViewHolder.text.setText(beans.getText());
        return convertView;
    }
}
