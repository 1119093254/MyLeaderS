package com.multshows.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.multshows.Beans.MyHome_Photo_Beans;
import com.multshows.R;
import com.multshows.Utils.SubString_Utils;
import com.multshows.Views.NineGridview.ImageInfo;
import com.multshows.Views.NineGridview.NineGridView;
import com.multshows.Views.NineGridview.preview.NineGridViewClickAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 描述：我的主页 相册列表适配器
 * 作者：李萧萧
 * 时间：2016.9.28
 */
public class MyHome_Photo_adapter extends BaseAdapter {
    List<MyHome_Photo_Beans> mList;
    private Context mContext;
    ViewHolder viewHolder;
    public MyHome_Photo_adapter(Context mContext, List<MyHome_Photo_Beans> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_photo_item, null);
            viewHolder.mPhoto_month = (TextView) view.findViewById(R.id.photo_month);
            viewHolder.mPhoto_day = (TextView) view.findViewById(R.id.photo_day);
            viewHolder.TodayText = (TextView) view.findViewById(R.id.TodayText);
            viewHolder.mNineGridView = (NineGridView) view.findViewById(R.id.photo_nineGrid);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        MyHome_Photo_Beans beans=mList.get(i);
        viewHolder.mPhoto_month.setText(beans.getMonth()+"月");
        //if(beans.getDay()<10)
        viewHolder.mPhoto_day.setText(beans.getDay());
        String[] strarray=NowString().split("-");
        if(Integer.parseInt(strarray[1])==Integer.parseInt(beans.getMonth())&&Integer.parseInt(strarray[2])==Integer.parseInt(beans.getDay())){
            viewHolder.mPhoto_day.setVisibility(View.INVISIBLE);
            viewHolder.mPhoto_month.setVisibility(View.INVISIBLE);
            viewHolder.TodayText.setVisibility(View.VISIBLE);
        }else {
            viewHolder.mPhoto_day.setVisibility(View.VISIBLE);
            viewHolder.mPhoto_month.setVisibility(View.VISIBLE);
            viewHolder.TodayText.setVisibility(View.INVISIBLE);
        }
       // else viewHolder.mPhoto_day.setText(beans.getDay()+"");
        ArrayList<ImageInfo> imageInfo = new ArrayList<>();
        List<String> imageDetails = beans.getmStringList();
        if (imageDetails != null) {
            for (String imageDetail : imageDetails) {
                ImageInfo info = new ImageInfo();
                info.setThumbnailUrl(SubString_Utils.getImageUrl(imageDetail));
                info.setBigImageUrl(SubString_Utils.getImageUrlclearly(imageDetail));
                imageInfo.add(info);
            }
        }
        viewHolder.mNineGridView.setAdapter(new NineGridViewClickAdapter(mContext, imageInfo));
        return view;
    }
    class ViewHolder {
        TextView mPhoto_month;//任务 月
        TextView mPhoto_day;//任务 日
        TextView TodayText;//今天文本
        NineGridView mNineGridView;//任务图片
    }
    public String NowString() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:m:s");//设置日期格式
        return df.format(new Date()).substring(0,10);// new Date()为获取当前系统时间
    }
}
