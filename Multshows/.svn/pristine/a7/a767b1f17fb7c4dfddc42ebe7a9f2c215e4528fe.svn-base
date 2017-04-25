package com.multshows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multshows.Beans.PoiItem_Beans;
import com.multshows.R;

import java.util.List;

/**
 * 周边地址适配器
 */
public class Poi_Adapter extends BaseAdapter{
    //上下文
    Context mContext;
    LayoutInflater mInflater;
    List<PoiItem_Beans> mList;

    public Poi_Adapter(Context context, List<PoiItem_Beans> list) {
        mContext = context;
        mList = list;
        mInflater=LayoutInflater.from(mContext);
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
    class POIViewHolder{
        TextView mTitle;//名称
        TextView mAddress;//地址
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        POIViewHolder mViewHolder;
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.poi_show_item,null);
            mViewHolder=new POIViewHolder();
            mViewHolder.mTitle= (TextView) convertView.findViewById(R.id.Poi_Show_Title);
            mViewHolder.mAddress= (TextView) convertView.findViewById(R.id.Poi_Show_Address);
            convertView.setTag(mViewHolder);
        }else {
            mViewHolder= (POIViewHolder) convertView.getTag();
        }

        PoiItem_Beans beans=mList.get(position);
        mViewHolder.mTitle.setText(beans.getPoiTitle());
        mViewHolder.mAddress.setText(beans.getPoiSnippet());
        if(beans.isFlag()){
            mViewHolder.mTitle.setTextColor(mContext.getResources().getColor(R.color.text_green));
            mViewHolder.mAddress.setTextColor(mContext.getResources().getColor(R.color.text_green));
        }else {
            mViewHolder.mTitle.setTextColor(mContext.getResources().getColor(R.color.text_black));
            mViewHolder.mAddress.setTextColor(mContext.getResources().getColor(R.color.light_grey));
        }
        return convertView;
    }
}
